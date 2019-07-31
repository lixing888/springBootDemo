package com.springboot.demo.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈权限系统接口〉
 *
 * @author v-zhaojingbo.ea
 * @create 2019/4/1
 * @since 1.0.0
 */
@Service
@Component
public interface AmsService {

    /**
     * 根据部门ID查询 所有上级部门，以 '@' 号分隔
     *
     * @param departmentId
     * @return
     */
    String listDepartmentPath(String departmentId);

}
