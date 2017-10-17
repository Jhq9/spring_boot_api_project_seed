package com.company.project.service;

import com.company.project.dto.UserRegisterDTO;
import com.company.project.model.User;

import java.util.List;

/**
 * Created by CodeGenerator on 2017/10/15.
 *
 * User的service层接口
 */
public interface UserService {

    Long saveUser(UserRegisterDTO userRegisterDTO);

    void deleteById(Long id);

    Long updateUser(User user);

    User findById(Long id);

    List<User> findAll();

    User findByPhone(String phone);

    User findByEmail(String email);
}
