package com.company.project.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jinhuaquan on 2017/8/2.
 */
@Data
public class UserDto implements Serializable{

    private String name;

    private String email;

    private Long role;

    private String password;

}
