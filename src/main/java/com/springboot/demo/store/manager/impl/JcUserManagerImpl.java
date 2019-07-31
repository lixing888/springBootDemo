package com.springboot.demo.store.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo.store.entity.JcUser;
import com.springboot.demo.store.manager.JcUserManager;
import com.springboot.demo.store.mapper.JcUserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * CMS用户表 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Service
public class JcUserManagerImpl extends ServiceImpl<JcUserMapper, JcUser> implements JcUserManager {

}
