package com.springboot.demo.controller;

import com.springboot.demo.service.HdfsService;
import com.springboot.demo.vo.Result;
import com.springboot.demo.vo.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.BlockLocation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Copyright (C), 2018-2021
 * FileName: HdfsController
 * Author:   ZSB
 * Date:     2021/5/16 10:25
 * Description: hadoop调用控制器
 */
@RestController
@RequestMapping("/hadoop/hdfs")
@Api(tags = "/hadoop调用控制器")
@Slf4j
public class HdfsController {
    /**
     * 创建文件夹
     * @param path
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "mkdir", method = RequestMethod.POST)
    @ResponseBody
    public Result mkdir(@RequestParam("path") String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            log.debug("请求参数为空");
            return new Result(500, "请求参数为空",null);
        }
        // 创建空文件夹
        boolean isOk = HdfsService.mkdir(path);
        if (isOk) {
            log.debug("文件夹创建成功");
            return new Result(200, "文件夹创建成功",isOk);
        } else {
            log.debug("文件夹创建失败");
            return new Result(500, "文件夹创建失败",isOk);
        }
    }

    /**
     * 读取HDFS目录信息
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readPathInfo")
    public Result readPathInfo(@RequestParam("path") String path) throws Exception {
        List<Map<String, Object>> list = HdfsService.readPathInfo(path);
        return new Result(200, "读取HDFS目录信息成功", list);
    }

    /**
     * 获取HDFS文件在集群中的位置
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/getFileBlockLocations")
    public Result getFileBlockLocations(@RequestParam("path") String path) throws Exception {
        BlockLocation[] blockLocations = HdfsService.getFileBlockLocations(path);
        return new Result(200, "获取HDFS文件在集群中的位置", blockLocations);
    }

    /**
     * 创建文件
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/createFile")
    public Result createFile(@RequestParam("path") String path, @RequestParam("file") MultipartFile file)
            throws Exception {
        if (StringUtils.isEmpty(path) || null == file.getBytes()) {
            return new Result(500, "请求参数为空",path);
        }
        HdfsService.createFile(path, file);
        return new Result(200, "创建文件成功",path);
    }

    /**
     * 读取HDFS文件内容
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readFile")
    public Result readFile(@RequestParam("path") String path) throws Exception {
        String targetPath = HdfsService.readFile(path);
        return new Result(200, "读取HDFS文件内容", targetPath);
    }

    /**
     * 读取HDFS文件转换成Byte类型
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/openFileToBytes")
    public Result openFileToBytes(@RequestParam("path") String path) throws Exception {
        byte[] files = HdfsService.openFileToBytes(path);
        return new Result(200, "读取HDFS文件转换成Byte类型", files);
    }

    /**
     * 读取HDFS文件装换成User对象
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/openFileToUser")
    public Result openFileToUser(@RequestParam("path") String path) throws Exception {
        User user = HdfsService.openFileToObject(path, User.class);
        return new Result(200, "读取HDFS文件装换成User对象", user);
    }

    /**
     * 读取文件列表
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/listFile")
    public Result listFile(@RequestParam("path") String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return new Result(500, "请求参数为空",null);
        }
        List<Map<String, String>> returnList = HdfsService.listFile(path);
        return new Result(200, "读取文件列表成功", returnList);
    }

    /**
     * 重命名文件
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    @PostMapping("/renameFile")
    public Result renameFile(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName)
            throws Exception {
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            return new Result(500, "请求参数为空",null);
        }
        boolean isOk = HdfsService.renameFile(oldName, newName);
        if (isOk) {
            return new Result(200, "文件重命名成功",isOk);
        } else {
            return new Result(400, "文件重命名失败",isOk);
        }
    }

    /**
     * 删除文件
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestParam("path") String path) throws Exception {
        boolean isOk = HdfsService.deleteFile(path);
        if (isOk) {
            return new Result(200, "delete file success",isOk);
        } else {
            return new Result(500, "delete file fail",null);
        }
    }

    /**
     * 上传文件
     * @param path
     * @param uploadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("path") String path, @RequestParam("uploadPath") String uploadPath)
            throws Exception {
        HdfsService.uploadFile(path, uploadPath);
        return new Result(200, "upload file success",path);
    }

    /**
     * 下载文件
     * @param path
     * @param downloadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/downloadFile")
    public Result downloadFile(@RequestParam("path") String path, @RequestParam("downloadPath") String downloadPath)
            throws Exception {
        HdfsService.downloadFile(path, downloadPath);
        return new Result(200, "download file success",path);
    }

    /**
     * HDFS文件复制
     * @param sourcePath
     * @param targetPath
     * @return
     * @throws Exception
     */
    @PostMapping("/copyFile")
    public Result copyFile(@RequestParam("sourcePath") String sourcePath, @RequestParam("targetPath") String targetPath)
            throws Exception {
        HdfsService.copyFile(sourcePath, targetPath);
        return new Result(200, "copy file success","success");
    }

    /**
     * 查看文件是否已存在
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/existFile")
    public Result existFile(@RequestParam("path") String path) throws Exception {
        boolean isExist = HdfsService.existFile(path);
        return new Result(200, "file isExist: " + isExist,isExist);
    }


}
