package com.company.project.service;

import com.company.project.vo.UserRegisterVO;
import com.company.project.vo.UserRequestVO;
import com.company.project.model.User;
import com.github.pagehelper.PageInfo;

/**
 *
 * @author CodeGenerator
 * @date 2017/10/15
 *
 * User的service层接口
 */
public interface UserService {

    /**
     * 注册一个新用户
     * @param userRegisterVO
     * @return
     */
    Long saveUser(UserRegisterVO userRegisterVO);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * 更新用户信息
     * @param id
     * @param requestDTO
     * @return
     */
    Long updateUser(Long id, UserRequestVO requestDTO);

    /**
     * 根据id查询特定用户
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 查询所有的用户
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<User> findAll(Integer pageNo, Integer pageSize);

    /**
     * 根据手机号查询出用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据email查询出用户
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
