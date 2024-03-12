package com.xiaoyang.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 工具类
 * @Author: xiaomei
 * @Date: 2024/2/8 008
 */
public class CommonUtils {

    // 将用户名所有中间字符改为*
    public static String editMiddleStr(String str) {
        char[] chars = str.toCharArray();
        int len = chars.length;
        if (len > 2) {
            return chars[0] + "**" + chars[len - 1];
        }
        return chars[0] + "**";
    }

    // 获取客户端ip地址
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
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
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
