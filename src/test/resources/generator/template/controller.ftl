package ${basePackage}.web;
import ${basePackage}.core.Result;
import ${basePackage}.core.ResultGenerator;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*
* ${modelNameUpperCamel}的Restful接口集合
*/
@RestController
@RequestMapping("/api")
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;


    @ApiOperation(value="保存${modelNameUpperCamel}", notes="新增一个${modelNameUpperCamel}")
    @ApiImplicitParam(name = "${modelNameUpperCamel}", value = "实体${modelNameUpperCamel}", required = true, dataType = "${modelNameUpperCamel}")
    @RequestMapping(value = "${baseRequestMapping}", method = RequestMethod.POST)
    public Result save${modelNameUpperCamel} (@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id删除${modelNameLowerCamel}", notes="根据url中的id来指定删除${modelNameLowerCamel}对象")
    @ApiImplicitParam(name = "id", value = "${modelNameLowerCamel}的id", required = true, dataType = "Long")
    @RequestMapping(value = "${baseRequestMapping}/{id}", method = RequestMethod.DELETE)
    public Result delete${modelNameUpperCamel}(@PathVariable Long id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="更新${modelNameLowerCamel}信息", notes="根据url中的id来指定更新对象，并根据传过来的${modelNameLowerCamel}信息来更新${modelNameLowerCamel}详细信息")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "${modelNameLowerCamel}的id", required = true, dataType = "Long"),
    @ApiImplicitParam(name = "${modelNameLowerCamel}", value = "实体${modelNameLowerCamel}", required = true, dataType = "${modelNameLowerCamel}")
        })
    @RequestMapping(value = "${baseRequestMapping}/{id}", method = RequestMethod.PUT)
    public Result update${modelNameUpperCamel}(@PathVariable Long id, @RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id查询${modelNameUpperCamel}", notes="根据url中的id来获取${modelNameUpperCamel}")
    @ApiImplicitParam(name = "id", value = "${modelNameUpperCamel}的id", required = true, dataType = "Long")
    @RequestMapping(value = "${baseRequestMapping}/{id}", method = RequestMethod.GET)
    public Result get${modelNameUpperCamel}(@PathVariable Long id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @ApiOperation(value="获取${modelNameUpperCamel}列表", notes="分页查询${modelNameUpperCamel}列表")
    @RequestMapping(value = "${baseRequestMapping}", method = RequestMethod.GET)
    public Result list${modelNameUpperCamel}(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
