package com.company.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name="user")
public class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private Long role;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "password")
    private String password;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

}