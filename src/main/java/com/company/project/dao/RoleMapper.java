package com.company.project.dao;

import com.company.project.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    /**
     * Save a new role
     * @param role
     * @return
     */
    Long saveRole(Role role);

    /**
     * Delete the role by id
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * Update the role info
     * @param role
     * @return
     */
    Long updateRole(Role role);

    /**
     * Find the role by id
     * @param id
     * @return
     */
    Role findById(Long id);

    /**
     * Find all role
     * @return
     */
    List<Role> findAll();

    /**
     * Find the role by name
     * @param name
     * @return
     */
    Role findByName(String name);

}