package com.springboot.demo.enumbean;

import lombok.Getter;

/**
 * @author lixing
 */
public enum OpertionTypeEnum {

    add(0), update(1), delete(2);
    @Getter
    private Integer opertionType;

    OpertionTypeEnum(Integer type) {
        this.opertionType = opertionType;
    }

}
