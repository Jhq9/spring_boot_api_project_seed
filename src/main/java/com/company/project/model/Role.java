package com.company.project.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name 角色名称
     */
    private String name;

}