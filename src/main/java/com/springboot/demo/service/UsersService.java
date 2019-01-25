package com.springboot.demo.service;

import com.springboot.demo.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {
    /**
     * 查询用户service
     */
    List<Users> findAll();

    void insertUsers(Users users);

    List<Users> oneUser(int id);


}
