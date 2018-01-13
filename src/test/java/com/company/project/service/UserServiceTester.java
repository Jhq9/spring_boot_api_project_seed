package com.company.project.service;

import com.company.project.Tester;
import com.company.project.vo.UserRegisterVO;
import com.company.project.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author jinhuaquan
 * @create 2017-10-16 上午7:46
 * @desc UserService的测试类
 **/
public class UserServiceTester extends Tester{

    @Autowired
    private UserService userService;

    /**
     * 保存一个新用户
     */
    @Before
    public void init() {
        UserRegisterVO userRegisterVO = new UserRegisterVO();

        userRegisterVO.setEmail("1044038055@qq.com");
        userRegisterVO.setName("jhq");
        userRegisterVO.setPassword("qwq1qwqwqw");
        userRegisterVO.setPhone("15800084691");
        userRegisterVO.setRoleName("ROLE_USER");

        Long id = userService.saveUser(userRegisterVO);

        Assert.isTrue(id != null, "新user保存失败");
    }


    /**
     * 根据id查询对应的user对象
     */
    @Test
    public void testFindById() {

        User user = userService.findById(13L);

        Assert.notNull(user, "不存在id为5的user");
        Assert.isTrue(user.getId() != null, "user的id为空");
    }

    /**
     * 根据phone查询对应的user对象
     */
    @Test
    public void testFindByPhone() {
        String phone = "15800084691";
        User user = userService.findByPhone(phone);

        Assert.notNull(user, "不存在phone='15800084691'的user");
        Assert.isTrue(user.getId() != null, "找到的user的id为空");
    }


}
