package com.springboot.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcTemplateConfig {
    @Bean("jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("bpmDataSource") DataSource bpmDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(bpmDataSource);
        return jdbcTemplate;
    }
}
