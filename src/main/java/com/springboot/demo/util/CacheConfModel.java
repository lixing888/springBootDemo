package com.springboot.demo.util;

import lombok.Data;

/**
 * 缓存属性类
 *
 * @author lixing
 */
@Data
public class CacheConfModel implements java.io.Serializable {

    /**
     * 缓存开始时间
     */
    private long beginTime;
    /**
     * 是否持久
     */
    private boolean isForever = false;
    /**
     * 持续时间
     */
    private int durableTime;
}
