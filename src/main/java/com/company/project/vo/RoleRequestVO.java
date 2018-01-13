package com.company.project.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author jinhuaquan
 * @create 2018-01-11 下午9:52
 * @desc The request DTO of role
 **/
@Data
@ApiModel(value = "The request DTO of role", description = "DTO")
public class RoleRequestVO {

    /**
     * role name
     */
    @NotEmpty(message = "The name can't be null or empty")
    private String name;

}
