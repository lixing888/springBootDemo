package com.springboot.demo.mapper;

import com.springboot.demo.entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    List<Users> selectMethod();

    Users oneUser(@Param("id") Integer id);

    @Insert("insert into user(name,age) values(#{name},#{age})")
    int addUser(@Param("name")String name,@Param("age")String age);

    @Select("select * from jc_user where id =#{id}")
    @Cacheable(key ="#id")//将查询结果缓存到redis中，（key="#p0"）指定传入的第一个参数作为redis的key。
    Users findById(@Param("id") int id);

    @CachePut(key = "#id")//指定key，将更新的结果同步到redis中
    @Update("update jc_user set name=#{name} where id=#{id}")
    void updataById(@Param("id")String id,@Param("name")String name);

    //如果指定为 true，则方法调用后将立即清空所有缓存
    @CacheEvict(key ="#id",allEntries=true)
    @Delete("delete from jc_user where id=#{id}")
    void deleteById(@Param("id") int id);
}
