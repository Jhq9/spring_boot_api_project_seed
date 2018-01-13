package com.company.project.service;

import com.company.project.Tester;
import com.company.project.vo.RoleRequestVO;
import com.company.project.model.Role;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author jinhuaquan
 * @create 2017-10-16 下午3:31
 * @desc RoleService测试类
 **/
public class RoleServiceTester extends Tester {

    @Autowired
    private RoleService roleService;


    /**
     * 保存一个新角色
     */
    @Before
    public void testSaveRole() {

        RoleRequestVO role = new RoleRequestVO();
        role.setName("ROLE_TESTER");

        Long num = roleService.saveRole(role);

        Assert.isTrue(num == 1L, "新角色保存失败");
    }

    /**
     * 根据id查询某个角色
     */
    @Test
    public void testFindById() {
        Role role = roleService.findById(1L);

        Assert.isTrue(role.getName().equalsIgnoreCase("ROLE_AUTHOR"), "角色查询不正确");
    }

    /**
     * 查询出所有的role对象
     */
    @Test
    public void testFindAll() {
        PageInfo<Role> role = roleService.findAll(1, 10);
        Assert.isTrue(role.getList().size() > 0, "角色查询不正确");
    }

    /**
     * 根据id删除指定role对象
     */
    @Test
    public void testDeleteRole() {
        Integer num = roleService.deleteById(3L);

        Assert.isTrue(num == 1L , "角色删除失败");
    }

    /**
     * 更新某个指定的角色信息
     */
    @Test
    public void testUpdateRole() {
        Role role = roleService.findById(2L);
        role.setName("Role_UPDATER");
        RoleRequestVO roleRequestVO = new RoleRequestVO();
        roleRequestVO.setName("1123");
        Long num = roleService.updateRole(role.getId(), roleRequestVO);

        Assert.isTrue(num == 1L, "角色信息更新失败");
    }
}
