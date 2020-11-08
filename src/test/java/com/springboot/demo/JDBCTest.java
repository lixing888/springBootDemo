package com.springboot.demo;

import com.springboot.demo.util.JDBCUtils;
import com.springboot.demo.vo.Person;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: springBootDemo
 * @description: jdbc测试类
 * @author: lixing
 * @create: 2020-11-08 17:14
 **/
public class JDBCTest {
    //用于测试的方法
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, ClassNotFoundException {
        Connection conn = JDBCUtils.getConnection();
        ResultSet rs = null;
        PreparedStatement psmt = null;
        System.out.println(conn);
        try {
            psmt = conn.prepareStatement("select * from user");
            rs = psmt.executeQuery();
            List list = JDBCUtils.Populate(rs, Person.class);
            for (int i = 0; i < list.size(); i++) {
                Person per = (Person) list.get(i);
                System.out.println("person : id = " + per.getId() + " name = " + per.getUsername() + " password = " + per.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                rs = null;
            }
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                psmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn = null;
            }
        }

    }
}
