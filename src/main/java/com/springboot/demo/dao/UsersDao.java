package com.springboot.demo.dao;

import com.springboot.demo.entity.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersDao {

    @Results({
         @Result(column = "user_id",property = "id"),
         @Result(column = "group_id",property = "groupid"),
         @Result(column = "username",property = "name")

    })
    @Select("select user_id,username from jc_user")
    List<Users> findAll();

    @Insert({ "insert into jc_user(user_id,group_id,username) values(#{id},#{groupid},#{name})" })
    int insertUsers(Users users);



}
