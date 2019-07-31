package com.springboot.demo.store.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo.store.entity.JcUserAttr;
import com.springboot.demo.store.manager.JcUserAttrManager;
import com.springboot.demo.store.mapper.JcUserAttrMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户属性表 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Service
public class JcUserAttrManagerImpl extends ServiceImpl<JcUserAttrMapper, JcUserAttr> implements JcUserAttrManager {

}
