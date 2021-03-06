package com.springboot.demo.service.serviceImpl;

import com.springboot.demo.dao.UsersDao;
import com.springboot.demo.entity.Users;
import com.springboot.demo.mapper.UserMapper;
import com.springboot.demo.service.UsersService;
import com.springboot.demo.store.mapper.JcUserMapper;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Component
public class UserserviceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JcUserMapper jcUserMapper;

    @Override
    public List<Users> findAll() {

        return usersDao.findAll();

    }

    @Override
    public void insertUsers(@RequestBody @ApiParam(value = "json格式", required = true) Users users) {
        usersDao.insertUsers(users);
    }

    @Override
    public List<Users> oneUser(@RequestParam("user_id") int id) {
        return usersDao.oneUser(id);
    }


    public Integer getMax(@RequestParam("user_id") Integer id) {
        return jcUserMapper.getMax(id);
    }
}
