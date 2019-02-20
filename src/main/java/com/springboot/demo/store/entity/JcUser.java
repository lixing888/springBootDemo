package com.springboot.demo.store.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * CMS用户表
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer groupId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 注册IP
     */
    private String registerIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 管理员级别
     */
    private Integer rank;

    /**
     * 上传总大小
     */
    private Long uploadTotal;

    /**
     * 上传大小
     */
    private Integer uploadSize;

    /**
     * 上传日期
     */
    private Date uploadDate;

    /**
     * 是否管理员
     */
    private Integer isAdmin;

    /**
     * 是否只管理自己的数据
     */
    private Integer isSelfAdmin;

    /**
     * 是否禁用
     */
    private Integer isDisabled;

    private String sessionId;

    /**
     * 年假时长(剩余)
     */
    private BigDecimal paidannual;

    /**
     * 年休假天数
     */
    private BigDecimal paidannualset;

    /**
     * 姓名
     */
    private String name;


}
