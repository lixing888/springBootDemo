package com.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author:lixing 获取目录工具类
 */
@Slf4j
public class GetServerRealPathUnit {

    public static String getPath(String subdirectory) {
        //获取跟目录---与jar包同级目录的upload目录下指定的子目录subdirectory
        File upload = null;
        try {
            //本地测试时获取到的是"工程目录G:\springBootDemo\target\classes\
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            upload = new File(path.getAbsolutePath(), subdirectory);
            if (!upload.exists()) {
                upload.mkdirs();//如果不存在则创建目录
            }
            String realPath = upload + File.separator;
            log.info("与jar包同级目录，路径{}", realPath);
            return realPath;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取服务器路径发生错误！");
        }
    }
}
