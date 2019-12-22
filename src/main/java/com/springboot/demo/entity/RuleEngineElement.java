package com.springboot.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RuleEngineElement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String code;
    /**
     * 辅助查询
     */
    private String codeName;
    private String valueType;

    private Integer bizId;

    private String bizCode;

    private String bizName;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer deleted;

    private String description;


}

