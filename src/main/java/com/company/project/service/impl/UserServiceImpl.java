package com.company.project.service.impl;

import com.company.project.dao.RoleMapper;
import com.company.project.dao.UserMapper;
import com.company.project.dto.UserRegisterDTO;
import com.company.project.model.Role;
import com.company.project.model.User;
import com.company.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2017/10/15.
 *
 * @author jinhuaquan
 *
 * User的service层具体实现
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    private final  RoleMapper roleMapper;


    @Override
    @Transactional
    public Long saveUser(UserRegisterDTO registerDTO) {

        Role role = roleMapper.findByName(registerDTO.getRoleName());

        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.setName(registerDTO.getName());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setCreateTime(new Date());
        user.setLastPasswordResetDate(new Date());
        user.setRole(role);

        userMapper.saveUser(user);

        return user.getId();
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    @Transactional
    public Long updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

}
