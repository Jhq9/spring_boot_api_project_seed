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

    /**
     * 注册一个新用户
     *
     * @param userRegisterDTO
     * @return
     */
    Long saveUser(UserRegisterDTO userRegisterDTO);

    /**
     * 根据id删除用户
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    Long updateUser(User user);

    /**
     * 根据id查询特定用户
     *
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 查询所有的用户
     * @return
     */
    List<User> findAll();

    /**
     * 根据手机号查询出用户
     *
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据email查询出用户
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    String login(String userName, String password);

    /**
     * 刷新用户的token
     * @param oldToken
     * @return
     */
    String refreshToken(String oldToken);
}
