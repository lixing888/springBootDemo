package com.springboot.demo.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student {
    //姓名
    @NotBlank
    private String name;
    //年龄
    private String age;
    //性别
    private Integer sex;
    //住址
    private String address;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * &#x89d2;&#x8272;id
     */
    private Integer roleId;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户邮箱
     */
    @JsonIgnore
    private String userEmail;

    /**
     * 创建时间
     */


    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 逻辑删除位，1代表已删除，0代理正常
     */
    @TableLogic
    private Integer deleted;

    public String getUserName() {
        if (userName == null) {
            return StringUtils.EMPTY;
        }
        return userName;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", address="
                + address + "]";
    }




}