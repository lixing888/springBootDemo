package com.springboot.demo.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * CMS管理员站点表
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JcUserSite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "usersite_id", type = IdType.AUTO)
    private Integer usersiteId;

    private Integer userId;

    private Integer siteId;

    /**
     * 审核级别
     */
    private Integer checkStep;

    /**
     * 是否拥有所有栏目的权限
     */
    private Integer isAllChannel;


}
