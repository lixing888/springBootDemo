package com.springboot.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.demo.entity.RuleEngineElement;
import org.apache.ibatis.annotations.Select;

/**
 * @author lixing
 */
public interface RuleEngineElementMapper extends BaseMapper<RuleEngineElement> {

    @Select("SELECT count(*) FROM `rule_engine_rule_set_json` WHERE JSON_CONTAINS(JSON_EXTRACT(count_info, '$.elementList'),JSON_ARRAY(#{elementId})) and published !=3 and deleted = 0")
    Integer getCounts(Long elementId);
}
