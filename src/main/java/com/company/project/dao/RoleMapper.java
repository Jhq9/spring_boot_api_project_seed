package com.company.project.dao;

import com.company.project.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    Long saveRole(Role role);

    Long deleteById(Long id);

    Long updateRole(Role role);

    Role findById(Long id);

    List<Role> findAll();

    Role findByName(String name);

}