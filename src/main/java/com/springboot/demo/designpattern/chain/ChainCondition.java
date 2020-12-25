package com.springboot.demo.designpattern.chain;

/**
 * @program: springBootDemo
 * @description: 报警条件
 * @author: lixing
 * @create: 2020-12-17 10:34
 **/
public class ChainCondition {

    //是否超时
    boolean overTime;

    //是否错误率超高
    boolean errorPercentUnNormal;

    //是否流量异常下跌
    boolean networkTrafficAbnormallyFalling;

    public boolean isOverTime() {
        return overTime;
    }

    public void setOverTime(boolean overTime) {
        this.overTime = overTime;
    }

    public boolean isErrorPercentUnNormal() {
        return errorPercentUnNormal;
    }

    public void setErrorPercentUnNormal(boolean errorPercentUnNormal) {
        this.errorPercentUnNormal = errorPercentUnNormal;
    }

    public boolean isNetworkTrafficAbnormallyFalling() {
        return networkTrafficAbnormallyFalling;
    }

    public void setNetworkTrafficAbnormallyFalling(boolean networkTrafficAbnormallyFalling) {
        this.networkTrafficAbnormallyFalling = networkTrafficAbnormallyFalling;
    }
}
