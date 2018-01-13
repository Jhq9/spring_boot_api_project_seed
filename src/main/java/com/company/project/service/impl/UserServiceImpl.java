package com.company.project.service.impl;

import com.company.project.core.ServiceException;
import com.company.project.dao.RoleMapper;
import com.company.project.dao.UserMapper;
import com.company.project.vo.UserRegisterVO;
import com.company.project.vo.UserRequestVO;
import com.company.project.model.Role;
import com.company.project.model.User;
import com.company.project.security.GeneratorUserDetailService;
import com.company.project.security.JwtTokenUtil;
import com.company.project.security.SecurityUser;
import com.company.project.service.UserService;
import com.company.project.utils.CglibBeanCopierUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.transaction.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * Created by CodeGenerator on 2017/10/15.
 *
 * @author jinhuaquan
 *
 * User的service层具体实现
 */
@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private GeneratorUserDetailService userDetailService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Long saveUser(UserRegisterVO registerDTO) {
        Date now = Date.from(Instant.now());
        Role role = roleMapper.findByName(registerDTO.getRoleName());

        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        CglibBeanCopierUtils.copyProperties(registerDTO, user);
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setRole(role);

        user.setCreateTime(now);
        user.setLastPasswordResetDate(now);

        userMapper.saveUser(user);

        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Integer deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = TransactionException.class)
    public Long updateUser(Long id, UserRequestVO requestDTO) {
        User user = new User();
        user.setId(id);
        CglibBeanCopierUtils.copyProperties(requestDTO, user);

        if (!StringUtils.isEmpty(requestDTO.getRoleName())) {
            user.setRole(roleMapper.findByName(requestDTO.getRoleName()));
        }

        return userMapper.updateUser(user);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public PageInfo<User> findAll(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.findAll());

        return pageInfo;
    }

    @Override
    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public String login(String userName, String password) {
        checkArgument(!StringUtils.isEmpty(userName), "用户名不能为空");
        checkArgument(!StringUtils.isEmpty(password), "密码不能为空");
        /**
         * 根据用户输入的账号及密码生成AuthenticationToken
         */
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            /**
             * 用户名密码登陆效验
             */
            final Authentication authentication = authenticationManager.authenticate(upToken);
            /**
             * 认证成功，将认证信息存入holder中
             */
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            /**
             * jwt工具生成的TOKEN
             */
            final String token = jwtTokenUtil.generateToken(userDetails);

            return token;
        }catch (AuthenticationException e){
            throw new ServiceException("用户不存在或密码错误");
        }
    }

    @Override
    public String refreshToken(String oldToken) {
        final String token = oldToken.substring(tokenHead.length(),oldToken.length());
        LOGGER.info("token is {}", token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LOGGER.info("username is {}", username);

        try {
            SecurityUser user = (SecurityUser) userDetailService.loadUserByUsername(username);

            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
                return jwtTokenUtil.refreshToken(token);
            }else {
                throw new ServiceException("token刷新失败");
            }
        }catch (AuthenticationException e) {
            throw new ServiceException("认证失败，无法刷新token");
        }
    }

}
