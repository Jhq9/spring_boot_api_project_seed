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

    private String name;

    private String email;

    private Role role;

    @Column(name = "create_time")
    private Date createTime;

    @JSONField(serialize = false)
    private String password;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    private String phone;

}