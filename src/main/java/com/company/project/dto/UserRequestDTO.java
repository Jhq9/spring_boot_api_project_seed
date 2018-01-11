package com.company.project.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author jinhuaquan
 * @create 2018-01-11 下午10:02
 * @desc The request DTO of user
 **/
@Data
@ApiModel(value = "The request DTO of user", description = "DTO")
public class UserRequestDTO {

    /**
     * name 用户名
     */
    private String name;

    /**
     * email 邮箱
     */
    private String email;

    /**
     * role 用户角色
     */
    private String roleName;

    /**
     * phone 用户注册的手机号
     */
    private String phone;

}
