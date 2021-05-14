package com.springboot.demo.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.springboot.demo.entity.Users;
import com.springboot.demo.enumbean.DraftAuditStatus;
import com.springboot.demo.service.UsersService;
import com.springboot.demo.vo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Api(tags = "/用户管理")
public class UsersController {

    @Autowired
    UsersService usersService;
    @Value("${url}")
    private String url;

    @RequestMapping(value = "users/list", method = RequestMethod.GET)
    public List<Users> studentList() {
        //判断字符串是否纯数字
        org.apache.commons.lang.math.NumberUtils.isDigits("9023112");
        List<Users> result = usersService.findAll();
        List<Student> students = result.stream()
                .map(record -> new Student().setName(record.getName())
                        .setAddress(record.getGroupId()).setAge(record.getRank())
                ).collect(Collectors.toList());
        Objects.requireNonNull(result, "不能为空");

        int status = DraftAuditStatus.draft.getStatus();
        //iter点击回车
        for (Student student : students) {

        }
        return result;

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

    /**
     * 读取所有cookie
     * 注意二、从客户端读取Cookie时，包括maxAge在内的其他属性都是不可读的，也不会被提交。浏览器提交Cookie时只会提交name与value属性。maxAge属性只被浏览器用来判断Cookie是否过期
     *
     * @param request
     * @param response
     */
    @GetMapping("/showCookies")
    public void showCookies(HttpServletRequest request, HttpServletResponse response) {
        //这样便可以获取一个cookie数组
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            System.out.println("没有cookie=========");
        } else {
            for (Cookie cookie : cookies) {
                System.out.println("name:" + cookie.getName() + ",value:" + cookie.getValue());
            }
        }

    }

    /**
     * 添加cookie
     *
     * @param response
     * @param name
     * @param value
     */
    @PostMapping("/addCookie")
    public void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name.trim(), value.trim());
        // 设置为30min
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");
        System.out.println("已添加===============");
        response.addCookie(cookie);
    }

    /**
     * 修改cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value    注意一、修改、删除Cookie时，新建的Cookie除value、maxAge之外的所有属性，例如name、path、domain等，都要与原Cookie完全一样。否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。
     */
    @RequestMapping(value = "/editCookie",method = RequestMethod.POST)
    public void editCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            System.out.println("没有cookie==============");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    System.out.println("原值为:" + cookie.getValue());
                    cookie.setValue(value);
                    cookie.setPath("/");
                    // 设置为30min
                    cookie.setMaxAge(30 * 60);
                    System.out.println("被修改的cookie名字为:" + cookie.getName() + ",新值为:" + cookie.getValue());
                    response.addCookie(cookie);
                    break;
                }
            }
        }

    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param name
     */
    @RequestMapping(value = "/delCookie",method = RequestMethod.POST)
    public void delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            System.out.println("没有cookie==============");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    // 立即销毁cookie
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:" + cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
