package com.springboot.demo.service;

import com.springboot.demo.entity.Users;

import java.util.List;

public interface UsersService {
    /**
     * 查询用户service
     */
    List<Users> findAll();

    void insertUsers(Users users);

    List<Users> oneUser(int id);


}
