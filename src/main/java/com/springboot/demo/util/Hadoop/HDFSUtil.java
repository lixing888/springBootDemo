package com.springboot.demo.util.Hadoop;

import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
/**
 * Copyright (C), 2018-2021
 * FileName: HDFSUtil
 * Author:   李兴
 * Date:     2021/5/16 9:01
 * Description: hdfs util 接口
 */
@Slf4j
public class HDFSUtil {
    private Configuration conf = null;

    /**
     * 默认的HDFS路径，比如：hdfs://192.168.197.130:9000
     */
    private String defaultHdfsUri;

    public HDFSUtil(Configuration conf,String defaultHdfsUri) {
        this.conf = conf;
        this.defaultHdfsUri = defaultHdfsUri;
    }

    /**
     * 获取HDFS文件系统
     * @return org.apache.hadoop.fs.FileSystem
     */
    private FileSystem getFileSystem() throws IOException {
        return FileSystem.get(conf);
    }

    /**
     * 创建HDFS目录
     * @author zifangsky
     * @date 2018/7/20 15:08
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir
     * @return boolean 是否创建成功
     */
    public boolean mkdir(String path){
        //如果目录已经存在，则直接返回
        if(checkExists(path)){
            return true;
        }else{
            FileSystem fileSystem = null;

            try {
                fileSystem = getFileSystem();

                //最终的HDFS文件目录
                String hdfsPath = generateHdfsPath(path);
                //创建目录
                return fileSystem.mkdirs(new Path(hdfsPath));
            } catch (IOException e) {
                log.error(MessageFormat.format("创建HDFS目录失败，path:{0}",path),e);
                return false;
            }finally {
                close(fileSystem);
            }
        }
    }

    /**
     * 上传文件至HDFS
     * @author zifangsky
     * @date 2018/7/20 15:28
     * @since 1.0.0
     * @param srcFile 本地文件路径，比如：D:/test.txt
     * @param dstPath HDFS的相对目录路径，比如：/testDir
     */
    public void uploadFileToHdfs(String srcFile, String dstPath){
        this.uploadFileToHdfs(false, true, srcFile, dstPath);
    }

    /**
     * 上传文件至HDFS
     * @author zifangsky
     * @date 2018/7/20 15:28
     * @since 1.0.0
     * @param delSrc 是否删除本地文件
     * @param overwrite 是否覆盖HDFS上面的文件
     * @param srcFile 本地文件路径，比如：D:/test.txt
     * @param dstPath HDFS的相对目录路径，比如：/testDir
     */
    public void uploadFileToHdfs(boolean delSrc, boolean overwrite, String srcFile, String dstPath){
        //源文件路径
        Path localSrcPath = new Path(srcFile);
        //目标文件路径
        Path hdfsDstPath = new Path(generateHdfsPath(dstPath));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            fileSystem.copyFromLocalFile(delSrc,overwrite,localSrcPath,hdfsDstPath);
        } catch (IOException e) {
            log.error(MessageFormat.format("上传文件至HDFS失败，srcFile:{0},dstPath:{1}",srcFile,dstPath),e);
        }finally {
            close(fileSystem);
        }
    }

    /**
     * 判断文件或者目录是否在HDFS上面存在
     * @author zifangsky
     * @date 2018/7/20 15:37
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir、/testDir/a.txt
     * @return boolean
     */
    public boolean checkExists(String path){
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            //最终的HDFS文件目录
            String hdfsPath = generateHdfsPath(path);

            //创建目录
            return fileSystem.exists(new Path(hdfsPath));
        } catch (IOException e) {
            log.error(MessageFormat.format("'判断文件或者目录是否在HDFS上面存在'失败，path:{0}",path),e);
            return false;
        }finally {
            close(fileSystem);
        }
    }

    /**
     * 获取HDFS上面的某个路径下面的所有文件或目录（不包含子目录）信息
     * @author zifangsky
     * @date 2018/7/20 18:12
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public List<Map<String,Object>> listFiles(String path, PathFilter pathFilter){
        //返回数据
        List<Map<String,Object>> result = new ArrayList<>();

        //如果目录已经存在，则继续操作
        if(checkExists(path)){
            FileSystem fileSystem = null;

            try {
                fileSystem = getFileSystem();

                //最终的HDFS文件目录
                String hdfsPath = generateHdfsPath(path);

                FileStatus[] statuses;
                //根据Path过滤器查询
                if(pathFilter != null){
                    statuses = fileSystem.listStatus(new Path(hdfsPath),pathFilter);
                }else{
                    statuses = fileSystem.listStatus(new Path(hdfsPath));
                }

                if(statuses != null){
                    for(FileStatus status : statuses){
                        //每个文件的属性
                        Map<String,Object> fileMap = new HashMap<>(2);

                        fileMap.put("path",status.getPath().toString());
                        fileMap.put("isDir",status.isDirectory());

                        result.add(fileMap);
                    }
                }
            } catch (IOException e) {
                log.error(MessageFormat.format("获取HDFS上面的某个路径下面的所有文件失败，path:{0}",path),e);
            }finally {
                close(fileSystem);
            }
        }

        return result;
    }


    /**
     * 从HDFS下载文件至本地
     * @author zifangsky
     * @date 2018/7/23 14:01
     * @since 1.0.0
     * @param srcFile HDFS的相对目录路径，比如：/testDir/a.txt
     * @param dstFile 下载之后本地文件路径（如果本地文件目录不存在，则会自动创建），比如：D:/test.txt
     */
    public void downloadFileFromHdfs(String srcFile, String dstFile){
        //HDFS文件路径
        Path hdfsSrcPath = new Path(generateHdfsPath(srcFile));
        //下载之后本地文件路径
        Path localDstPath = new Path(dstFile);

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            fileSystem.copyToLocalFile(hdfsSrcPath,localDstPath);
        } catch (IOException e) {
            log.error(MessageFormat.format("从HDFS下载文件至本地失败，srcFile:{0},dstFile:{1}",srcFile,dstFile),e);
        }finally {
            close(fileSystem);
        }
    }

    /**
     * 打开HDFS上面的文件并返回 InputStream
     * @author zifangsky
     * @date 2018/7/23 17:08
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir/c.txt
     * @return FSDataInputStream
     */
    public FSDataInputStream open(String path){
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            return fileSystem.open(hdfsPath);
        } catch (IOException e) {
            log.error(MessageFormat.format("打开HDFS上面的文件失败，path:{0}",path),e);
        }

        return null;
    }

    /**
     * 打开HDFS上面的文件并返回byte数组，方便Web端下载文件
     * <p>new ResponseEntity<byte[]>(byte数组, headers, HttpStatus.CREATED);</p>
     * <p>或者：new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(templateFile), headers, HttpStatus.CREATED);</p>
     * @author zifangsky
     * @date 2018/7/23 17:08
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir/b.txt
     * @return FSDataInputStream
     */
    public byte[] openWithBytes(String path){
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        try {
            fileSystem = getFileSystem();
            inputStream = fileSystem.open(hdfsPath);

            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error(MessageFormat.format("打开HDFS上面的文件失败，path:{0}",path),e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return null;
    }

    /**
     * 打开HDFS上面的文件并返回String字符串
     * @author zifangsky
     * @date 2018/7/23 17:08
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir/b.txt
     * @return FSDataInputStream
     */
    public String openWithString(String path){
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        try {
            fileSystem = getFileSystem();
            inputStream = fileSystem.open(hdfsPath);

            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error(MessageFormat.format("打开HDFS上面的文件失败，path:{0}",path),e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return null;
    }

    /**
     * 打开HDFS上面的文件并转换为Java对象（需要HDFS上门的文件内容为JSON字符串）
     * @author zifangsky
     * @date 2018/7/23 17:08
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir/c.txt
     * @return FSDataInputStream
     */
    public <T extends Object> T openWithObject(String path, Class<T> clazz){
        //1、获得文件的json字符串
        String jsonStr = this.openWithString(path);

        //2、使用com.alibaba.fastjson.JSON将json字符串转化为Java对象并返回
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 重命名
     * @author zifangsky
     * @date 2018/7/23 15:11
     * @since 1.0.0
     * @param srcFile 重命名之前的HDFS的相对目录路径，比如：/testDir/b.txt
     * @param dstFile 重命名之后的HDFS的相对目录路径，比如：/testDir/b_new.txt
     */
    public boolean rename(String srcFile, String dstFile) {
        //HDFS文件路径
        Path srcFilePath = new Path(generateHdfsPath(srcFile));
        //下载之后本地文件路径
        Path dstFilePath = new Path(dstFile);

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            return fileSystem.rename(srcFilePath,dstFilePath);
        } catch (IOException e) {
            log.error(MessageFormat.format("重命名失败，srcFile:{0},dstFile:{1}",srcFile,dstFile),e);
        }finally {
            close(fileSystem);
        }

        return false;
    }

    /**
     * 删除HDFS文件或目录
     * @author zifangsky
     * @date 2018/7/23 15:28
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir/c.txt
     * @return boolean
     */
    public boolean delete(String path) {
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            return fileSystem.delete(hdfsPath,true);
        } catch (IOException e) {
            log.error(MessageFormat.format("删除HDFS文件或目录失败，path:{0}",path),e);
        }finally {
            close(fileSystem);
        }

        return false;
    }

    /**
     * 获取某个文件在HDFS集群的位置
     * @author zifangsky
     * @date 2018/7/23 15:41
     * @since 1.0.0
     * @param path HDFS的相对目录路径，比如：/testDir/a.txt
     * @return org.apache.hadoop.fs.BlockLocation[]
     */
    public BlockLocation[] getFileBlockLocations(String path) {
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();
            FileStatus fileStatus = fileSystem.getFileStatus(hdfsPath);

            return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        } catch (IOException e) {
            log.error(MessageFormat.format("获取某个文件在HDFS集群的位置失败，path:{0}",path),e);
        }finally {
            close(fileSystem);
        }

        return null;
    }


    /**
     * 将相对路径转化为HDFS文件路径
     * @author zifangsky
     * @date 2018/7/20 15:18
     * @since 1.0.0
     * @param dstPath 相对路径，比如：/data
     * @return java.lang.String
     */
    private String generateHdfsPath(String dstPath){
        String hdfsPath = defaultHdfsUri;
        if(dstPath.startsWith("/")){
            hdfsPath += dstPath;
        }else{
            hdfsPath = hdfsPath + "/" + dstPath;
        }

        return hdfsPath;
    }

    /**
     * close方法
     */
    private void close(FileSystem fileSystem){
        if(fileSystem != null){
            try {
                fileSystem.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
