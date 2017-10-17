package com.company.project.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"password"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Role role;

    /**
     * createTime 用户的创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * password 用户的密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * lastPasswordResetDate 上次更新密码的时间
     */
    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    /**
     * phone 用户注册的手机号
     */
    private String phone;

}