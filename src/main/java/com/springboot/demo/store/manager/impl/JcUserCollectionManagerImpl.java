package com.springboot.demo.store.manager.impl;

import com.springboot.demo.store.entity.JcUserCollection;
import com.springboot.demo.store.mapper.JcUserCollectionMapper;
import com.springboot.demo.store.manager.JcUserCollectionManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏关联表 服务实现类
 * </p>
 *
 * @author zhaojingbo
 * @since 2019-02-19
 */
@Service
public class JcUserCollectionManagerImpl extends ServiceImpl<JcUserCollectionMapper, JcUserCollection> implements JcUserCollectionManager {

}
