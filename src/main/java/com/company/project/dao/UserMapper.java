package com.company.project.dao;

import com.company.project.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    Long saveUser(User user);

    void deleteById(Long id);

    Long updateUser(User user);

    User findById(Long id);

    List<User> findAll();

    User findByPhone(String phone);

    User findByEmail(String email);

}