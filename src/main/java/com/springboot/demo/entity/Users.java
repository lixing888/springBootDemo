package com.springboot.demo.entity;

import com.fasterxml.jackson.databind.*;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@Setter
@Getter
@ApiModel(value = "User", description = "用户对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)//驼峰转换
public class Users implements Serializable{

    @ApiModelProperty(value = "id",notes = "必须是int类型",example = "1")
    private Integer id;
    @ApiModelProperty(value = "姓名",example = "李兴")
    private String name;
    @ApiModelProperty(value = "邮箱",example = "85210279@qq.com")
    private String email;
    @ApiModelProperty(value = "班组",example = "12")
    private String groupId;
    @ApiModelProperty(value = "等级",example = "1")
    private String rank;
    @TableLogic
    private String delete;

    private List<Car> cars;

//    @ApiModelProperty(value = "登录名")
//    private String username;
//    @ApiModelProperty(value = "邮箱",access = "hidden",example = "85210279@qq.com")
//    private int email;
//    @ApiModelProperty(value = "等级")
//    private int rank;


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email=" + email +
//                ", username='" + username + '\'' +
//
//                ", rank=" + rank +
                '}';
    }

}
