package com.springboot.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.demo.store.entity.JcUser;
import com.springboot.demo.store.manager.JcUserManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class JcUserNewController {

    @Autowired
    private JcUserManager jcUserManager;

    @PostMapping("/selectById")
    @ApiOperation(value = "根据ID获取用户信息", notes = "根据ID获取用户信息")
    public JcUser selectById(@RequestParam("user_id") String userId) {
        QueryWrapper<JcUser> getById = new QueryWrapper<JcUser>();
        JcUser jcUser = jcUserManager.getOne(getById.lambda().eq(JcUser::getUserId, userId));

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
}

