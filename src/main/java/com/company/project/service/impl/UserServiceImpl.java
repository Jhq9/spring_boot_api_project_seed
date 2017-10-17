package com.company.project.service.impl;

import com.company.project.core.ServiceException;
import com.company.project.dao.RoleMapper;
import com.company.project.dao.UserMapper;
import com.company.project.dto.UserRegisterDTO;
import com.company.project.model.Role;
import com.company.project.model.User;
import com.company.project.security.GeneratorUserDetailService;
import com.company.project.security.JwtTokenUtil;
import com.company.project.security.SecurityUser;
import com.company.project.service.UserService;
import lombok.AllArgsConstructor;
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

import java.util.Date;
import java.util.List;


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
    @Transactional
    public Long saveUser(UserRegisterDTO registerDTO) {

        Role role = roleMapper.findByName(registerDTO.getRoleName());

        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.setName(registerDTO.getName());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setCreateTime(new Date());
        user.setLastPasswordResetDate(new Date());
        user.setRole(role);

        userMapper.saveUser(user);

        return user.getId();
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    @Transactional
    public Long updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
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

        //根据用户输入的账号及密码生成AuthenticationToken
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            // 用户名密码登陆效验
            final Authentication authentication = authenticationManager.authenticate(upToken);
            // 认证成功，将认证信息存入holder中
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            //jwt工具生成的TOKEN
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
