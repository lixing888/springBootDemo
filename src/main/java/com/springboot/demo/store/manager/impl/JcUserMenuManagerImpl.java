package com.springboot.demo.store.manager.impl;

import com.springboot.demo.store.entity.JcUserMenu;
import com.springboot.demo.store.mapper.JcUserMenuMapper;
import com.springboot.demo.store.manager.JcUserMenuManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户常用菜单 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Service
public class JcUserMenuManagerImpl extends ServiceImpl<JcUserMenuMapper, JcUserMenu> implements JcUserMenuManager {

}
