package com.springboot.demo.dao;

import com.springboot.demo.entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface UsersDao {

    @Results({
         @Result(column = "user_id",property = "id"),
         @Result(column = "email",property = "email"),
         @Result(column = "username",property = "name")

    })
    @Select("select user_id,username,email from jc_user")
    List<Users> findAll();

    @Insert({ "insert into jc_user(user_id,group_id,username) values(#{id},#{groupid},#{name})" })
    int insertUsers(Users users);

    @Results({
            @Result(column = "user_id",property = "id"),
            @Result(column = "email",property = "email"),
            @Result(column = "username",property = "name")

    })

    @Select("select user_id,username,email from jc_user where user_id =#{id}")
    List<Users> oneUser (int id);




}
