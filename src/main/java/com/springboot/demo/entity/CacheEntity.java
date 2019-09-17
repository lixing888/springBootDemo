package com.springboot.demo.entity;


import java.io.Serializable;

/**
 *本地缓存保存的实体
 *
 * @author lixing
 * @version $Id: LocalCache.java, v 0.1 2014年9月6日 下午1:13:43 Lenovo Exp $
 */
public class CacheEntity implements Serializable {

    private static final long serialVersionUID = 7172649826282703560L;
    /**
     * 值
     */
    private Object value;

    /**
     * 保存的时间戳
     */
    private long gmtModify;

    /**
     * 过期时间
     */
    private int expire;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(long gmtModify) {
        this.gmtModify = gmtModify;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public CacheEntity(Object value, long gmtModify, int expire) {
        super();
        this.value = value;
        this.gmtModify = gmtModify;
        this.expire = expire;
    }

}
