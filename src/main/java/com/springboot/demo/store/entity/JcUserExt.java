package com.springboot.demo.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CMS用户扩展信息表
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JcUserExt implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 个人介绍
     */
    private String intro;

    /**
     * 来自
     */
    private String comefrom;

    /**
     * QQ
     */
    private String qq;

    /**
     * MSN
     */
    private String msn;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 用户头像
     */
    private String userImg;

    /**
     * 用户个性签名
     */
    private String userSignature;


}
