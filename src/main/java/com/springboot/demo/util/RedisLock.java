package com.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈分布式锁〉
 *
 * @author v-lixing.ea
 * @create 2019/8/7
 * @since 1.0.0
 */
@Component
@Slf4j
public class RedisLock {

    /**
     * 失效过期时间
     */
    private static final long EXPIRE_TIME = 20L;
    private static final Object PRESENT = new Object();
    @Resource
    private RedissonClient redissonClient;

    /**
     * 删除锁
     *
     * @param lockName 唯一ID
     */
    public boolean del(String lockName) {
        return redissonClient.getBucket(lockName).delete();
    }

    /**
     * 检查分布式对象是否存在
     *
     * @param name key
     * @return 存在结果
     */
    public Boolean trySet(String name) {
        return redissonClient.getBucket(name).trySet(PRESENT, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 自定义锁超时时间
     *
     * @param name    锁名称
     * @param timeOut 超时时间 单位默认秒
     * @return
     */
    public Boolean trySet(String name, long timeOut) {
        return redissonClient.getBucket(name).trySet(PRESENT, timeOut, TimeUnit.SECONDS);
    }
}
