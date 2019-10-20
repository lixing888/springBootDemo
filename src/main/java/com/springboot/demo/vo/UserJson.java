package com.springboot.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJson implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -329066647199569031L;

    private String userName;

    private String orderNo;
}