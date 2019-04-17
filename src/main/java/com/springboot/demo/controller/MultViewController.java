package com.springboot.demo.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by Administrator on 14-4-9.
 * @author leizhimin 14-4-9 上午10:55
 * 默认是单例模式，即scope="singleton"。
 * 另外scope还有prototype、request、session、global session作用域。scope="prototype"多例
 * @scope默认是单例模式（singleton）
 * 如果需要设置的话@scope("prototype")
 */
@Controller
@RequestMapping("/demo/lsh/ch5")
@Scope("prototype")
public class MultViewController {

    private int index =0;

    @RequestMapping("/show")
    public String toShow(ModelMap model) {

        System.out.println(++index);
        return"/lsh/ch5/show";

    }

    @RequestMapping("/test")
    public String test() {

        System.out.println(++index);
        return"/lsh/ch5/test";

    }
}
