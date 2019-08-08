package com.springboot.demo.service.serviceImpl;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.demo.dao.UsersDao;
import com.springboot.demo.entity.Person;
import com.springboot.demo.entity.Users;
import com.springboot.demo.mapper.UserMapper;
import com.springboot.demo.service.UsersService;
import com.springboot.demo.store.entity.JcUser;
import com.springboot.demo.store.manager.JcUserManager;
import com.springboot.demo.store.mapper.JcUserMapper;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Component
@Slf4j
public class UserserviceImpl implements UsersService {

    public static final String BILL_TYPES = "billtypes";
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
     * 文件下载
     * @param id
     * @return
     */
    public Boolean download(Integer id) {
        Person costFile = new Person();
        if (Objects.isNull(costFile)) {
            throw new ValidationException("该文件异常，请尝试重新上传此文件！");
        }
        //文件名称
        String fileName = "文件名称";
        //将测试文件test.execl读入此字节数组
        Boolean result;
        //根据文件Id查询该文件信息 解析blob 字段 通过流的形式 还原为execl文件 并进行下载
        try (InputStream is = new ByteArrayInputStream((byte[]) costFile.getContent())) {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Content-Disposition",
                    String.format("attachment; filename=\"%s\"", java.net.URLEncoder.encode(fileName, "UTF-8")));
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Content-Length", String.valueOf(is.available()));
            //写入数据
            IoUtil.copy(is, response.getOutputStream());
            result = true;
            log.info("【导出规则】操作人：{}，操作时间：{}，规则文件名称：{}",
                    "李兴", new Date(), costFile.getName());
        } catch (IOException e) {
            log.error("{}", e);
            result = false;
        }
        return result;
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
