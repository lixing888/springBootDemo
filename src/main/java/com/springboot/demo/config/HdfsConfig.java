package com.springboot.demo.config;

import com.springboot.demo.util.Hadoop.HDFSUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Copyright (C), 2018-2021
 * FileName: HdfsConfig
 * Author:   李兴
 * Date:     2021/5/16 9:22
 * Description: HDFS相关配置
 */
public class HdfsConfig {
    @Value("${hdfs.defaultFS}")
    private String defaultHdfsUri;

    @Bean
    public HDFSUtil getHbaseService(){

        Configuration conf = HBaseConfiguration.create();

        conf.set("fs.defaultFS",defaultHdfsUri);

        return new HDFSUtil(conf,defaultHdfsUri);
    }
}
