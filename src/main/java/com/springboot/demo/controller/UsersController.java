package com.springboot.demo.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.springboot.demo.entity.Users;
import com.springboot.demo.service.UsersService;
import com.springboot.demo.vo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.jsoup.select.Collector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(tags = "/用户管理")
public class UsersController {

    @Autowired
    UsersService usersService;
    @Value("${url}")
    private String url;

    @RequestMapping(value = "users/list", method = RequestMethod.GET)
    public List<Student> studentList() {

        List<Users> result = usersService.findAll();
        List<Student> students=result.stream()
                .map(record -> new Student().setName(record.getName())
                        .setAddress(record.getGroupId()).setAge(record.getRank())
                ).collect(Collectors.toList());
        return students;

    }

    @PostMapping(value = "/getUserById")
    @ApiOperation(value = "根据ID查询")
    public List<Users> getUserById(@RequestParam("user_id") int id) {

        return usersService.oneUser(id);

    }

    @PostMapping(value = "saveUserInfo")
    @ApiOperation(value = "保存用户信息", notes = "ID不能为空")
    public Users saveUserInfo(@RequestBody Users users) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "lixing");
        String post = HttpUtil.post(url, JSON.toJSONString(map));
        JSONObject resultJson = JSONObject.fromObject(post);
        usersService.insertUsers(users);
        return users;

    }
}
