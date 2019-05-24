package com.springboot.demo.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lixing
 */
@Data
public class Address {
    @NotBlank
    private String postcode;

    private String street;

    private String state;

}

