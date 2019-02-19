package com.springboot.demo.store.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * CMS用户角色关联表
 * </p>
 *
 * @author zhaojingbo
 * @since 2019-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JcUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Integer userId;


}
