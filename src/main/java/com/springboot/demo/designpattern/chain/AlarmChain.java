package com.springboot.demo.designpattern.chain;

/**
 * @program: springBootDemo
 * @description: 责任链模式
 * @author: lixing
 * @create: 2020-12-17 10:42
 **/

/**
 * 责任链
 */
public abstract class AlarmChain<T> {

    abstract boolean needExecute(T t);

    void process(T t) {
        if (needExecute(t)) {
            execute();
        }
    }

    abstract void execute();
}
