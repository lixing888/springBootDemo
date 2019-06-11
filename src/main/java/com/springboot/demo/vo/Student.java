package com.springboot.demo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private String userEmail;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除位，1代表已删除，0代理正常
     */
    @TableLogic
    private Integer deleted;

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", address="
                + address + "]";
    }


}