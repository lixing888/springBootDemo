package com.springboot.demo.store.manager.impl;

import com.springboot.demo.store.entity.JcUserExt;
import com.springboot.demo.store.mapper.JcUserExtMapper;
import com.springboot.demo.store.manager.JcUserExtManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * CMS用户扩展信息表 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Service
public class JcUserExtManagerImpl extends ServiceImpl<JcUserExtMapper, JcUserExt> implements JcUserExtManager {

}
