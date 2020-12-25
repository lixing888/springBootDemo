package com.springboot.demo.designpattern.chain;

/**
 * @program: springBootDemo
 * @description: 流量异常下跌的实现类
 * @author: lixing
 * @create: 2020-12-17 10:46
 **/
public class NetworkTrafficAbnormallyFallingChain extends AlarmChain<ChainCondition> {
    @Override
    public boolean needExecute(ChainCondition chainCondition) {
        return chainCondition.isNetworkTrafficAbnormallyFalling();
    }

    @Override
    public void execute() {
        System.out.println("流量异常下跌报警");
    }
}
