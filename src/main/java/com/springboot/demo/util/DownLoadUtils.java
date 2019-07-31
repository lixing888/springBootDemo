package com.springboot.demo.util;

import cn.hutool.core.io.IoUtil;
import com.sun.tools.internal.ws.wsdl.framework.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author lixing
 */
@Slf4j
public class DownLoadUtils {

    //===================================================================

    /**
     * download
     * 弹出框下载
     * 导出原始execl
     */
    public Boolean download(Integer id) {
        if (Objects.isNull(id)) {
            throw new ValidationException("序号ID不能为空");
        }
//        RuleCostFile costFile = ruleCostFileManager.getById(id);
//        if (Objects.isNull(costFile)) {
//            throw new ValidationException("该文件异常！");
//        }
        Object content = new Object();
        //文件名称
        String fileName = "测试execl文件.xlsx";
        //将测试文件test.execl读入此字节数组
        Boolean result;
        //根据文件Id查询该文件信息 解析blob 字段(将Object强转为二进制字节byte[]) 通过流的形式 还原为execl文件 并进行下载
        InputStream is = new ByteArrayInputStream((byte[]) content);
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Content-Disposition",
                    String.format("attachment; filename=\"%s\"", java.net.URLEncoder.encode(fileName, "UTF-8")));
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Content-Length", String.valueOf(is.available()));
            //写入数据
            IoUtil.copy(is, response.getOutputStream());
            result = true;
        } catch (IOException e) {
            log.error("{}", e);
            result = false;
        }
        return result;
    }
}
