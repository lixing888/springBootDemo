package com.springboot.demo.controller;

import com.springboot.demo.util.DownloadUtils;
import io.swagger.annotations.Api;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 实现利用浏览器下载文件
 * http://127.0.0.1:9090/downLoad/downloadFile?path=E:/UltraEdit64.zip
 *
 * @author lixing
 */
@Controller
@RequestMapping(value = "/downLoad")
@Api(tags = "/实现利用浏览器下载文件")
public class DownLoadFileController {

    /**
     * @param path
     * @return
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<FileSystemResource> downloadFile(String path) throws UnsupportedEncodingException {
        return export(new File(path));
    }

    public ResponseEntity<FileSystemResource> export(File file) throws UnsupportedEncodingException {
        if (file == null) {
            return null;
        }
        String filename = file.getName();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        //new String(filename.getBytes("UTF-8"),"ISO8859-1") 防止中文变下划线
        headers.add("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }

    /**
     * 下载文件
     * http://127.0.0.1:9090/downLoad/downloadLocal?path=E:/UltraEdit64.zip
     *
     * @param path
     * @return
     */
    @GetMapping("/downloadLocal")
    public void downloadLocal(String path, HttpServletResponse response) throws IOException {
        DownloadUtils.downloadLocal(path, response);
    }

    /**
     * 下载文件夾
     * http://127.0.0.1:9090/downLoad/zipDownloadRelativePath?path=E:/Redis
     *
     * @param path
     * @return
     */
    @GetMapping("/zipDownloadRelativePath")
    public void zipDownloadRelativePath(String path, HttpServletResponse response) throws IOException {
        DownloadUtils.zipDownloadRelativePath(response, path);
    }

}
