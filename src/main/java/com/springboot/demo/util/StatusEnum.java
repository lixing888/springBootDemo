package com.springboot.demo.util;

import java.math.BigDecimal;

/**
 * 状态枚举
 */
public enum StatusEnum implements CommonEnum {

//    WAITTING(0, "WAITTING", "等待"),
//    STARTED(1, "STARTED", "开始"),
//    END(2, "END", "结束");

    MDBS00000002("MDBS00000002","用户获取成本",BigDecimal.valueOf(23232.00)),
    MDBS00000001("MDBS00000001","分成返点",BigDecimal.valueOf(999999999999.00));


    private String code;
    private String name;
    private BigDecimal maxNbr;

    StatusEnum(String code, String name, BigDecimal maxNbr) {
        this.code = code;
        this.name = name;
        this.maxNbr = maxNbr;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public BigDecimal maxNbr() {
        return maxNbr;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMaxNbr() {
        return maxNbr;
    }

    public void setMaxNbr(BigDecimal maxNbr) {
        this.maxNbr = maxNbr;
    }
}
