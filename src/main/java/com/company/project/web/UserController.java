package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.core.ResultGenerator;
import com.company.project.core.ServiceException;
import com.company.project.vo.UserRegisterVO;
import com.company.project.vo.UserRequestVO;
import com.company.project.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

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

    /**
     * 用户的注册
     * @param userRegisterVO
     * @return
     */
    @ApiOperation(value="注册新User", notes="注册一个User")
    @RequestMapping(value = "/users/actions/register", method = RequestMethod.POST)
    public Result saveUser(@RequestBody @Valid UserRegisterVO userRegisterVO){
        Long userId = userService.saveUser(userRegisterVO);

        return ResultGenerator.genSuccessResult(userId);
    }

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation(value="用户登录", notes="用户进行登录")
    @ApiImplicitParams({
                           @ApiImplicitParam(name = "userName", value = "user的phone或email", required = true),
                           @ApiImplicitParam(name = "password", value = "user的密码", required = true)
    })
    @RequestMapping(value = "/users/actions/login", method = RequestMethod.GET)
    public Result login(@RequestHeader String userName, @RequestHeader String password)
    {
        try {
            return ResultGenerator.genSuccessResult(userService.login(userName, password));
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * 刷新TOKEN
     * @param oldToken
     * @return
     */
    @ApiOperation(value="刷新token", notes="刷新token")
    @ApiImplicitParam(paramType = "header", name = "oldToken", value = "old token", required = true)
    @RequestMapping(value = "/users/actions/refresh", method = RequestMethod.GET)
    public Result refresh(@RequestHeader("Authorization") String oldToken){

        try {
            return ResultGenerator.genSuccessResult(userService.refreshToken(oldToken));
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }

    }

    @ApiOperation(value="根据id删除user", notes="根据url中的id来指定删除user对象")
    @ApiImplicitParam(name = "id", value = "user的id", required = true, dataType = "Long")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public Result deleteUser(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(userService.deleteById(id));
    }


    @ApiOperation(value="更新user信息", notes="根据url中的id来指定更新对象，并根据传过来的user信息来更新user详细信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "user的id", required = true)
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public Result updateUser(@PathVariable Long id, @RequestBody UserRequestVO requestDTO) {
        return ResultGenerator.genSuccessResult(userService.updateUser(id, requestDTO));
    }


    @ApiOperation(value="根据id查询User", notes="根据url中的id来获取User")
    @ApiImplicitParam(name = "id", value = "User的id", required = true, dataType = "int")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public Result getUser(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(userService.findById(id));
    }


    @ApiOperation(value="获取User列表", notes="分页查询User列表")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiImplicitParams(value = {
                                    @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码"),
                                   @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页大小")
    })
    @RolesAllowed({"ROLE_AUTHOR"})
    public Result listUser(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                           @RequestParam(defaultValue = "0", required = false) Integer pagesize) {
        return ResultGenerator.genSuccessResult(userService.findAll(pageNo, pagesize));
    }

}
