package com.springboot.demo.mapper;

import com.springboot.demo.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {

    List<Users> selectMethod();

    Users oneUser(@Param("id") Integer id);
}
