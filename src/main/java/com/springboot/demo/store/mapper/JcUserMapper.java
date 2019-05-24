package com.springboot.demo.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.demo.store.entity.JcUser;

/**
 * <p>
 * CMS用户表 Mapper 接口
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
public interface JcUserMapper extends BaseMapper<JcUser> {

    int getMax(Integer userId);

}
