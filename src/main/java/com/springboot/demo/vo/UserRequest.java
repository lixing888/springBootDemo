package com.springboot.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRequest {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "组织ID")
    private String groupId;
    @ApiModelProperty(value = "等级")
    private int rank;


}
