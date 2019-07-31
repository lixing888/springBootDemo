package com.springboot.demo.store.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo.store.entity.JcUserRole;
import com.springboot.demo.store.manager.JcUserRoleManager;
import com.springboot.demo.store.mapper.JcUserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * CMS用户角色关联表 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Service
public class JcUserRoleManagerImpl extends ServiceImpl<JcUserRoleMapper, JcUserRole> implements JcUserRoleManager {

}
