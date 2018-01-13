package com.company.project.service;

import com.company.project.vo.RoleRequestVO;
import com.company.project.model.Role;
import com.github.pagehelper.PageInfo;

/**
 * @author CodeGenerator
 * @date 2017/10/16
 * Role的service层接口
 */
public interface RoleService {

    /**
     * 新增一个角色Role
     * @param requestDTO
     * @return
     */
    Long saveRole(RoleRequestVO requestDTO);

    /**
     * 根据id删除一个角色
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     *  更新角色信息
     *  @param id
     *  @param requestDTO
     *  @return
     */
    Long updateRole(Long id, RoleRequestVO requestDTO);

    /**
     *  根据id找到角色
     *  @param id
     *  @return
     */
    Role findById(Long id);

    /**
     * 找出所有的角色
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Role> findAll(Integer pageNo, Integer pageSize);
}
