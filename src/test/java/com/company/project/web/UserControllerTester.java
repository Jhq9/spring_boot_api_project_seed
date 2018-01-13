package com.company.project.web;

import com.company.project.vo.UserRegisterVO;
import com.company.project.model.User;
import com.company.project.ControllerTester;
import com.company.project.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author jinhuaquan
 * @create 2017-10-16 上午9:38
 * @desc UserController的测试类
 **/
public class UserControllerTester extends ControllerTester{

    @Autowired
    private RoleService roleService;

    /**
     * 设置一个User
     *
     * @return
     */
    public User setUser() {
        User user = new User();

        user.setCreateTime(new Date());
        user.setEmail("1044038055@qq.com");
        user.setLastPasswordResetDate(new Date());
        user.setName("jhq");
        user.setPassword("qwq1qwqwqw");
        user.setRole(roleService.findById(1L));

        return user;
    }


    /**
     * 新增一个用户接口的接口测试   POST
     *
     * @throws Exception
     */
    @Test
    public void testSaveUser() throws Exception {

        UserRegisterVO userRegisterVO = new UserRegisterVO();

        userRegisterVO.setEmail("1044038055@qq.com");
        userRegisterVO.setName("jhq");
        userRegisterVO.setPassword("qwq1qwqwqw");
        userRegisterVO.setPhone("15800084691");
        userRegisterVO.setRoleName("ROLE_AUTHOR");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/api/users/actions/register")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(mapper.writeValueAsString(userRegisterVO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.message", is("SUCCESS")))
            .andExpect(jsonPath("$.data", notNullValue()));
    }


    /**
     * 根据id获取某个特定的用户接口的接口测试  GET
     *
     * @throws Exception
     */
    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/api/users/13")
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.message", is("SUCCESS")))
            .andExpect(jsonPath("$.data", notNullValue()))
            .andExpect(jsonPath("$.data.id", is(13)));
    }


    /**
     * 根据id删除某个特定的用户接口的接口测试  DELETE
     * @throws Exception
     */
    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/13")
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.message", is("SUCCESS")))
            .andExpect(jsonPath("$.data", nullValue()));
    }


    /**
     * 根据id更新某个特定用户的信息接口的接口测试  PUT
     * @throws Exception
     */
    @Test
    public void testUpdateUser() throws Exception {

        User user = setUser();

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/api/users/13")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.message", is("SUCCESS")))
            .andExpect(jsonPath("$.data", nullValue()));

    }

}
