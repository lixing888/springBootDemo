package com.springboot.demo.store.manager.impl;

import com.springboot.demo.store.entity.JcUserRole;
import com.springboot.demo.store.mapper.JcUserRoleMapper;
import com.springboot.demo.store.manager.JcUserRoleManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * CMS用户角色关联表 服务实现类
 * </p>
 *
 * @author zhaojingbo
 * @since 2019-02-19
 */
@Service
public class JcUserRoleManagerImpl extends ServiceImpl<JcUserRoleMapper, JcUserRole> implements JcUserRoleManager {

}
