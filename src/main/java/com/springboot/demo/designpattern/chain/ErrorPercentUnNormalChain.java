package com.springboot.demo.designpattern.chain;

/**
 * @program: springBootDemo
 * @description: 错误报警的实现类
 * @author: lixing
 * @create: 2020-12-17 10:45
 **/
/**
 * 错误报警的实现类
 */
public class ErrorPercentUnNormalChain extends AlarmChain<ChainCondition> {
    @Override
    public boolean needExecute(ChainCondition chainCondition) {
        return chainCondition.isErrorPercentUnNormal();
    }

    @Override
    public void execute() {
        System.out.println("错误报警");
    }
}
