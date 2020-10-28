package com.springboot.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: springBootDemo
 * @description: 用户信息
 * @author: lixing
 * @create: 2020-10-23 17:13
 **/
@Data
public class UserInfo {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "别名")
    private String nickname;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "salt")
    private String salt;
    @ApiModelProperty(value = "组织ID")
    private String groupId;
    @ApiModelProperty(value = "等级")
    private int rank;
}
