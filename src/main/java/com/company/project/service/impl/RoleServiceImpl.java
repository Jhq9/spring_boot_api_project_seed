package com.company.project.service.impl;

import com.company.project.dao.RoleMapper;
import com.company.project.vo.RoleRequestVO;
import com.company.project.model.Role;
import com.company.project.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by CodeGenerator on 2017/10/16.
 *
 * Role的service层具体实现
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public Long saveRole(RoleRequestVO roleRequestVO) {
        Role role = new Role();

        role.setName(roleRequestVO.getName());
        roleMapper.saveRole(role);

        return role.getId();
    }

    @Override
    public Integer deleteById(Long id) {
       return roleMapper.deleteById(id);
    }

    @Override
    @Transactional
    public Long updateRole(Long id, RoleRequestVO requestDTO) {
        Role role = new Role();
        role.setId(id);
        role.setName(requestDTO.getName());

        return roleMapper.updateRole(role);
    }

    @Override
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }

    @Override
    public PageInfo<Role> findAll(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);

        PageInfo<Role> pageInfo = new PageInfo<>(roleMapper.findAll());

        return pageInfo;
    }
}
