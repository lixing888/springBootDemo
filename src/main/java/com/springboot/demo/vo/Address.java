package com.springboot.demo.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author lixing
 */
@Data
public class Address {

    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "只能由英文数字下划线组成。")
    @NotBlank(message = "邮政编码不能为空")
    private String postcode;

    private String street;

    private String state;

}

