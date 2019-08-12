package com.springboot.demo.aop;

import com.springboot.demo.annotation.ReSubmitLock;
import com.springboot.demo.util.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br>
 * 〈注意：只是防止重复提交，不解决分布式方法锁问题〉
 *
 * @author v-dingqianwen.ea
 * @create 2019/8/7
 * @since 1.0.0
 */
@Aspect
@Component
@Slf4j
public class ReSubmitLockAspect {

    /**
     * 锁的key前缀，防止与其他key重复
     */
    private static final String LOCK_KEY_PRE = "wfc_rule_LOCK_KEY_PRE_";
    @Resource
    private RedisLock redisLock;

    /**
     * 当某一个方法中有Lock注解时，则加锁，执行完毕后解锁
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(com.springboot.demo.annotation.ReSubmitLock)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法名
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ReSubmitLock lock = method.getAnnotation(ReSubmitLock.class);
        //获取锁的超时时间
        long time = lock.timeOut();
        //锁前缀加当前登录用户加被执行的类名加方法名

        String className = joinPoint.getThis().getClass().getName();
        String employeeId = "1108061";
        //todo 如果调用方法查询条件经常改变，建议加上方法参数作为锁的key
        String lockName = LOCK_KEY_PRE + employeeId + "_" + className + "_" + method.getName();
        //加锁成功
        if (!redisLock.trySet(lockName, time)) {
            //锁已存在
            log.warn("{}方法锁已经存在，请勿重复操作！", method.getName());
            throw new ValidationException("请勿重复操作！");
        }
        try {
            log.info("{}方法加锁成功，Lock Key:{}", method.getName(), lockName);
            return joinPoint.proceed();
        } finally {
            //方法执行完之后进行解锁
            redisLock.del(lockName);
            log.info("{}方法锁已经移除，Lock Key:{}", method.getName(), lockName);
        }
    }
}
