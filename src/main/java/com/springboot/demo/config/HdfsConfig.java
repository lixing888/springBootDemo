package com.springboot.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2018-2021
 * FileName: HdfsConfig
 * Author:   李兴
 * Date:     2021/5/16 9:22
 * Description: HDFS相关配置
 */
@Configuration
public class HdfsConfig {

    @Value("${hdfs.defaultFS}")
    private String defaultHdfsUri;

    public String getDefaultHdfsUri() {
        return defaultHdfsUri;
    }

    public void setDefaultHdfsUri(String defaultHdfsUri) {
        this.defaultHdfsUri = defaultHdfsUri;
    }


//    @Bean
//    public HDFSUtil getHbaseService(){
//
//        Configuration conf = HBaseConfiguration.create();
//
//        conf.set("fs.defaultFS",defaultHdfsUri);
//
//        return new HDFSUtil(conf,defaultHdfsUri);
//    }


}
