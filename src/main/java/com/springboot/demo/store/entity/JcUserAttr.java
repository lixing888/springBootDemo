package com.springboot.demo.store.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户属性表
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JcUserAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String attrName;

    private String attrValue;


}
