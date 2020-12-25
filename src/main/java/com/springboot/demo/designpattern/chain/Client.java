package com.springboot.demo.designpattern.chain;

/**
 * @program: springBootDemo
 * @description: 客户端调用的方式
 * @author: lixing
 * @create: 2020-12-17 10:47
 **/
public class Client {

    public static void main(String[] args) {
        ChainCondition chainCondition = new ChainCondition();

        chainCondition.setOverTime(false);
        chainCondition.setErrorPercentUnNormal(true);
        chainCondition.setNetworkTrafficAbnormallyFalling(true);

        //执行顺序可以变更的
        AlarmChainBuilder.newBuilder()
                .add(new NetworkTrafficAbnormallyFallingChain())
                .add(new AlarmOverTimeChain())
                .add(new ErrorPercentUnNormalChain())
                .build().execute(chainCondition);

    }
}
