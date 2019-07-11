package com.springboot.demo.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.demo.dao.UsersDao;
import com.springboot.demo.entity.Users;
import com.springboot.demo.mapper.UserMapper;
import com.springboot.demo.service.UsersService;
import com.springboot.demo.store.entity.JcUser;
import com.springboot.demo.store.manager.JcUserManager;
import com.springboot.demo.store.mapper.JcUserMapper;
import io.swagger.annotations.ApiParam;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class UserserviceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JcUserMapper jcUserMapper;
    @Autowired
    private JcUserManager jcUserManager;
    @Autowired
    private RedissonClient redissonClient;

    public static final String BILL_TYPES = "billtypes";

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

    /**
     * 动态加载业务类别
     */
    public List<String> billTypes() {
        //缓存处理
        RList<String> rList = redissonClient.getList(BILL_TYPES);
        if (rList.isEmpty()) {
            LambdaQueryWrapper<JcUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(JcUser::getName).groupBy(JcUser::getName);

            List<JcUser> list = jcUserManager.list(queryWrapper);
            List<String> billTypes = list.stream().map(record -> record.getName()).collect(Collectors.toList());
            rList.addAll(billTypes);
            //设置缓存过期时间
            Date current = new Date();
            //时间加4小时
            current.setTime(current.getTime() + (14400000));
            rList.expireAt(current);
            //return Collections.emptyList();
            return billTypes;
        } else {
            return rList;
        }


    }

}
