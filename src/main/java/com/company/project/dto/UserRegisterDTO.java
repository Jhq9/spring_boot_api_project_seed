package com.company.project.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * Created by jinhuaquan on 2017/8/2.
 */
@Data
@ApiModel(value = "用户注册请求类", description = "用户注册")
public class UserRegisterDTO {

    @Length(min = 3, max = 16, message = "请控制用户名的长度在3~16个字符之间")
    private String name;

    @Email(message = "请输入合法的邮箱地址")
    private String email;

    private String roleName;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 12, message = "请控制密码的长度在6-12个字符之间")
    private String password;

    @Length(min = 11, message = "请输入合法的手机号")
    private String phone;

}
