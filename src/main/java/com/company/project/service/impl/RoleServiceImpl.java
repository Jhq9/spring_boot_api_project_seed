package com.company.project.service.impl;

import com.company.project.dao.RoleMapper;
import com.company.project.model.Role;
import com.company.project.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
    public Long saveRole(Role role) {
        return roleMapper.saveRole(role);
    }

    @Override
    public Long deleteById(Long id) {
       return roleMapper.deleteById(id);
    }

    @Override
    @Transactional
    public Long updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
}
