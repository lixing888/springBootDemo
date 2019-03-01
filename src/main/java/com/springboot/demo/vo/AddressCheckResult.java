package com.springboot.demo.vo;

import lombok.Data;

/**
 * @author lixng
 */
@Data
public class AddressCheckResult {
    //true:通过校验；false：未通过校验
    private boolean postCodeResult = false;

}
