package com.company.project.dao;

import com.company.project.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * Save a new user
     * @param user
     * @return
     */
    Long saveUser(User user);

    /**
     * Delete the user by id
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * Update the user's info
     * @param user
     * @return
     */
    Long updateUser(User user);

    /**
     * Find the user by id
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * List all of the user
     * @return
     */
    List<User> findAll();

    /**
     * Find user by phone
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * Find the user by email
     * @param email
     * @return
     */
    User findByEmail(String email);

}