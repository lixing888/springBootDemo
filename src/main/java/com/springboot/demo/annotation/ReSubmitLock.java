package com.springboot.demo.annotation;

import java.lang.annotation.*;

/**
 * 注意：只是防止重复提交，不解决分布式方法锁问题
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReSubmitLock {
    /**
     * 默认锁10秒失效
     *
     * @return
     */
    long timeOut() default 10;
}
