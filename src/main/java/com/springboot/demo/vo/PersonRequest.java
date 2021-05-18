package com.springboot.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Copyright (C), 2018-2021
 * FileName: PersonRequest
 * Author:   李兴
 * Date:     2021/5/18 11:32
 * Description: 使用校验注解对请求的参数进行校验
 * ^string : 匹配以 string 开头的字符串
 * string$ ：匹配以 string 结尾的字符串
 * ^string$ ：精确匹配 string 字符串
 * (^Man$|^Woman$|^UGM$) : 值只能在 Man,Woman,UGM 这三个值中选择
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {

    @NotNull(message = "classId 不能为空")
    private String classId;

    @Size(max = 33)
    @NotNull(message = "name 不能为空")
    private String name;

    @Pattern(regexp = "(^Man$|^Woman$|^UGM$)", message = "sex 值不在可选范围")
    @NotNull(message = "sex 不能为空")
    private String sex;

}
