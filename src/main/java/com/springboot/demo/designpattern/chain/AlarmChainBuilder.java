package com.springboot.demo.designpattern.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springBootDemo
 * @description: 责任链的建造者
 * @author: lixing
 * @create: 2020-12-17 10:48
 **/
public class AlarmChainBuilder<T> {

    private List<AlarmChain<T>> chains;

    private AlarmChainBuilder(ChainBuilder chainBuilder) {
        this.chains = chainBuilder.chains;
    }

    public static ChainBuilder newBuilder() {
        return new ChainBuilder();
    }


    static class ChainBuilder<T> {
        private List<AlarmChain<T>> chains = new ArrayList<>();


        public ChainBuilder add(AlarmChain<T> chain) {
            chains.add(chain);
            return this;
        }

        public AlarmChainBuilder build() {
            return new AlarmChainBuilder(this);
        }
    }

    public void execute(T t) {
        chains.forEach(x -> x.process(t));
    }
}
