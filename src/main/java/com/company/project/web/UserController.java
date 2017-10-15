package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/10/15.
*
* User的Restful接口集合
*/
@RestController
@RequestMapping("/api")
public class UserController {
    @Resource
    private UserService userService;


    @ApiOperation(value="保存User", notes="新增一个User")
    @ApiImplicitParam(name = "User", value = "实体User", required = true, dataType = "User")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Result saveUser (@RequestBody User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
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
        userService.update(user);
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
}
