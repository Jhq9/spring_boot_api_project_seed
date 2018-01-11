package com.company.project.service;

import com.company.project.model.Role;

import java.util.List;

/**
 * Created by CodeGenerator on 2017/10/16.
 *
 * Role的service层接口
 */
public interface RoleService {

    /**
     * @param role
     *
     *  新增一个角色Role
     */
    Long saveRole(Role role);

    /**
     * @param id
     *
     *  根据id删除一个角色
     */
    Long deleteById(Long id);

    /**
     *  更新角色信息
     *
     *  @param role
     */
    Long updateRole(Role role);

    /**
     *  根据id找到角色
     *  @param id
     *
     *  @return
     */
    Role findById(Long id);

    /**
     * 找出所有的角色
     *
     * @return
     */
    List<Role> findAll();
}
