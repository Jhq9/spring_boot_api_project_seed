package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.vo.RoleRequestVO;
import com.company.project.service.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
*
 * @author CodeGenerator
 * @date 2017/10/16
 *
* Role的Restful接口集合
*/
@RestController
@RequestMapping("/api")
public class RoleController {
    @Resource
    private RoleService roleService;


    @ApiOperation(value="保存Role", notes="新增一个Role")
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public Result saveRole (@Valid @RequestBody RoleRequestVO requestDTO) {
        return ResultGenerator.genSuccessResult(roleService.saveRole(requestDTO));
    }

    @ApiOperation(value="根据id删除role", notes="根据url中的id来指定删除role对象")
    @ApiImplicitParam(name = "id", value = "role的id", required = true, dataType = "Long")
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public Result deleteRole(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(roleService.deleteById(id));
    }

    @ApiOperation(value="更新role信息", notes="根据url中的id来指定更新对象，并根据传过来的role信息来更新role详细信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "role的id", required = true, dataType = "Long")
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
    public Result updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequestVO roleRequestVO) {
        return ResultGenerator.genSuccessResult(roleService.updateRole(id, roleRequestVO));
    }

    @ApiOperation(value="根据id查询Role", notes="根据url中的id来获取Role")
    @ApiImplicitParam(paramType = "path", name = "id", value = "Role的id", required = true, dataType = "int")
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public Result getRole(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(roleService.findById(id));
    }

    @ApiOperation(value="获取Role列表", notes="分页查询Role列表")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Result listRole(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                           @RequestParam(defaultValue = "0", required = false) Integer pageSize) {
        return ResultGenerator.genSuccessResult(roleService.findAll(pageNo, pageSize));
    }
}
