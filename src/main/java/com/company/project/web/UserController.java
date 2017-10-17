package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.dto.UserRegisterDTO;
import com.company.project.model.User;
import com.company.project.security.GeneratorUserDetailService;
import com.company.project.security.JwtTokenUtil;
import com.company.project.security.SecurityUser;
import com.company.project.service.RoleService;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* Created by CodeGenerator on 2017/10/15.
*
* User的Restful接口集合
*/
@RestController
@RequestMapping("/api")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private GeneratorUserDetailService userDetailService;


    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 用户的注册
     * @param userRegisterDto
     * @return
     */
    @ApiOperation(value="注册新User", notes="注册一个User")
    @ApiImplicitParam(name = "UserRegisterDTO", value = "DTO", required = true, dataType = "UserRegisterDTO")
    @RequestMapping(value = "/users/actions/register", method = RequestMethod.POST)
    public Result saveUser(@RequestBody @Valid UserRegisterDTO userRegisterDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }

        Long userId = userService.saveUser(userRegisterDto);

        return ResultGenerator.genSuccessResult(userId);
    }





    @ApiOperation(value="根据id删除user", notes="根据url中的id来指定删除user对象")
    @ApiImplicitParam(name = "id", value = "user的id", required = true, dataType = "Long")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public Result deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="更新user信息", notes="根据url中的id来指定更新对象，并根据传过来的user信息来更新user详细信息")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "user的id", required = true, dataType = "Long"),
    @ApiImplicitParam(name = "user", value = "实体user", required = true, dataType = "user")
        })
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public Result updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(user);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id查询User", notes="根据url中的id来获取User")
    @ApiImplicitParam(name = "id", value = "User的id", required = true, dataType = "Long")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public Result getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @ApiOperation(value="获取User列表", notes="分页查询User列表")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Result listUser(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/users/actions/login", method = RequestMethod.GET)
    public Result login(@RequestHeader String userName, @RequestHeader String password)
    {
        if (!StringUtils.hasText(userName)){
            return ResultGenerator.genFailResult("用户名不能为空");
        }
        if (!StringUtils.hasText(password)){
            return ResultGenerator.genFailResult("密码不能为空哦");
        }
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

            return ResultGenerator.genSuccessResult(token);
        }catch (AuthenticationException e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("账号不存在或密码错误哦");
        }
    }

    /**
     * 刷新TOKEN
     * @param oldToken
     * @return
     */
    @GetMapping("/actions/refresh")
    public Result refresh(@RequestHeader("Authorization") String oldToken){
        final String token = oldToken.substring(tokenHead.length(),oldToken.length());
        LOGGER.info("token is "+token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LOGGER.info("username is "+username);

        try {
            SecurityUser user = (SecurityUser) userDetailService.loadUserByUsername(username);

            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
                return ResultGenerator.genSuccessResult(jwtTokenUtil.refreshToken(token));
            }else {
                return ResultGenerator.genFailResult("Token刷新失败了 =。=");
            }
        }catch (AuthenticationException e) {
            return ResultGenerator.genFailResult("认证失败了 =。=");
        }
    }
}
