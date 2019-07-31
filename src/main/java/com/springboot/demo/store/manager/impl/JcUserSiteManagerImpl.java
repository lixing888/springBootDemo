package com.springboot.demo.store.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo.store.entity.JcUserSite;
import com.springboot.demo.store.manager.JcUserSiteManager;
import com.springboot.demo.store.mapper.JcUserSiteMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * CMS管理员站点表 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Service
public class JcUserSiteManagerImpl extends ServiceImpl<JcUserSiteMapper, JcUserSite> implements JcUserSiteManager {

}
