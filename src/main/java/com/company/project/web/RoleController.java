package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Role;
import com.company.project.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/10/16.
*
* Role的Restful接口集合
*/
@RestController
@RequestMapping("/api")
public class RoleController {
    @Resource
    private RoleService roleService;


    @ApiOperation(value="保存Role", notes="新增一个Role")
    @ApiImplicitParam(name = "Role", value = "实体Role", required = true, dataType = "Role")
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public Result saveRole (@RequestBody Role role) {
        roleService.saveRole(role);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id删除role", notes="根据url中的id来指定删除role对象")
    @ApiImplicitParam(name = "id", value = "role的id", required = true, dataType = "Long")
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public Result deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="更新role信息", notes="根据url中的id来指定更新对象，并根据传过来的role信息来更新role详细信息")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "role的id", required = true, dataType = "Long"),
    @ApiImplicitParam(name = "role", value = "实体role", required = true, dataType = "role")
        })
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
    public Result updateRole(@PathVariable Long id, @RequestBody Role role) {
        roleService.updateRole(role);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id查询Role", notes="根据url中的id来获取Role")
    @ApiImplicitParam(name = "id", value = "Role的id", required = true, dataType = "Long")
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public Result getRole(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResultGenerator.genSuccessResult(role);
    }

    @ApiOperation(value="获取Role列表", notes="分页查询Role列表")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Result listRole(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Role> list = roleService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
