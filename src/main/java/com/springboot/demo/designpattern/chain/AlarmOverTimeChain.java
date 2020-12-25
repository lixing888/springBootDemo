package com.springboot.demo.designpattern.chain;

/**
 * @program: springBootDemo
 * @description: 超时的实现类
 * @author: lixing
 * @create: 2020-12-17 10:44
 **/
/**
 * 超时的实现类
 */
public class AlarmOverTimeChain extends AlarmChain<ChainCondition> {
    @Override
    public boolean needExecute(ChainCondition chainCondition) {
        return chainCondition.isOverTime();
    }

    @Override
    public void execute() {
        System.out.println("超时报警");
    }
}

