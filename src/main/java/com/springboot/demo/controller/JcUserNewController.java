package com.springboot.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.demo.store.entity.JcUser;
import com.springboot.demo.store.manager.JcUserManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CMS用户表 前端控制器
 * </p>
 *
 * @author lixing
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/jcUserNewController")
@Api(tags = "获取用户信息")
@Slf4j
public class JcUserNewController {

    @Autowired
    private JcUserManager jcUserManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/selectById")
    @ApiOperation(value = "根据ID获取用户信息", notes = "根据ID获取用户信息")
    public JcUser selectById(@RequestParam("user_id") String userId) {
        QueryWrapper<JcUser> getById = new QueryWrapper<>();
        JcUser jcUser = jcUserManager.getOne(getById.lambda().eq(JcUser::getUserId, userId));
        log.info("【导出数据】操作人：{}，操作时间：{}，规则文件名称：{}",
                jcUser.getName(), new Date(), jcUser.getEmail());
        return jcUserManager.getById(userId);
    }

    @PostMapping("/userList")

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public List<JcUser> userList() {

        return jcUserManager.list();

    }


    @PostMapping("/delById")
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Boolean delById(@RequestParam("user_id") String userId) {

        return jcUserManager.removeById(userId);

    }

    @PostMapping("/updateOrderSql")
    @ApiOperation(value = "updateOrderSql", notes = "更新sql脚本")
    @Transactional
    public Boolean updateOrderSql() {

        String sql = "UPDATE rule_biz_rule_data \n" +
                "SET order_no = order_no + 100 \n" +
                "WHERE\n" +
                "\torder_no >= 2 \n" +
                "\tAND order_no <= 24 \n" +
                "\tAND rule_id IN ( 1, 2 )";
        jdbcTemplate.execute(sql);

        String sql1 = "UPDATE rule_biz_rule_data \n" +
                "SET order_no = order_no -70 \n" +
                "WHERE\n" +
                "\torder_no >= 72 \n" +
                "\tAND order_no <= 94 \n" +
                "\tAND rule_id IN ( 1, 2 )";
        jdbcTemplate.execute(sql1);

        String sql2 = "UPDATE rule_biz_rule_data \n" +
                "SET order_no = order_no -30 \n" +
                "WHERE\n" +
                "\torder_no >= 102 \n" +
                "\tAND order_no <= 124 \n" +
                "\tAND rule_id IN ( 1, 2 )";
        jdbcTemplate.execute(sql2);

        return true;

    }
}

