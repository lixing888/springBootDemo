package com.springboot.demo.store.manager.impl;

import com.springboot.demo.store.entity.JcUserAccount;
import com.springboot.demo.store.mapper.JcUserAccountMapper;
import com.springboot.demo.store.manager.JcUserAccountManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户稿费收费配置 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-19
 */
@Service
public class JcUserAccountManagerImpl extends ServiceImpl<JcUserAccountMapper, JcUserAccount> implements JcUserAccountManager {

}
