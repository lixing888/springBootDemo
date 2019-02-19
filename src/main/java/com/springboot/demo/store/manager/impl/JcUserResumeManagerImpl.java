package com.springboot.demo.store.manager.impl;

import com.springboot.demo.store.entity.JcUserResume;
import com.springboot.demo.store.mapper.JcUserResumeMapper;
import com.springboot.demo.store.manager.JcUserResumeManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户简历 服务实现类
 * </p>
 *
 * @author lixing
 * @since 2019-02-19
 */
@Service
public class JcUserResumeManagerImpl extends ServiceImpl<JcUserResumeMapper, JcUserResume> implements JcUserResumeManager {

}
