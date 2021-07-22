package com.springboot.demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * Copyright (C), 2018-2021
 * FileName: ShutdownFilter
 * Author:   李兴
 * Date:     2021/7/22 10:33
 * Description: 对actuator的管理端点进行ip白名单限制
 */
@WebFilter(filterName = "shutdownFilter", urlPatterns = {"/shutdown", "/pause", "/restart"})
@Slf4j
public class ShutdownFilter implements Filter {
    @Value("${shutdown.whitelist:0:0:0:0:0:0:0:1}")
    private String[] shutdownIpWhitelist;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequest;

        String ip = this.getIpAddress(request);
        log.info("访问shutdown的机器的原始IP：{}", ip);

        if (!isMatchWhiteList(ip)) {
            sresponse.setContentType("application/json");
            sresponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = sresponse.getWriter();
            writer.write("{\"code\":401}");
            writer.flush();
            writer.close();
            return;
        }

        filterChain.doFilter(srequest, sresponse);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info("shutdown filter is init.....");
    }

    /**
     * 匹配是否是白名单
     *
     * @param ip
     * @return
     */
    private boolean isMatchWhiteList(String ip) {
        List<String> list = Arrays.asList(shutdownIpWhitelist);
        if (list.contains(ip)) {
            return true;
        }
        return list.stream().anyMatch(data -> ip.startsWith(data));
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
