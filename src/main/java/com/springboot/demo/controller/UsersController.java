package com.springboot.demo.controller;

import com.springboot.demo.entity.Users;
import com.springboot.demo.mapper.UserMapper;
import com.springboot.demo.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "/用户管理")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "users/list",method = RequestMethod.GET)
    public String UserInfo(){

        List<Users> result= usersService.findAll();
        return result.toString();

    }
    @GetMapping(value = "/userIno/{id}/")
    @ApiOperation(value = "根据ID查询")
    public Users getUserById(@RequestParam int id){

        //return userMapper.oneUser(id);
        return userMapper.findById(id);

    }

    @PostMapping(value = "saveUserInfo")
    @ApiOperation(value = "保存用户信息",notes="ID不能为空")
    public Users  saveUserInfo(@RequestBody Users users){

        usersService.insertUsers(users);
        return users;

    }
}
