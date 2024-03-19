package com.xiaoyang.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Description: 工具类
 * @Author: xiaomei
 * @Date: 2024/2/8 008
 */
public class CommonUtils {

    // 将字符串所有中间字符改为*
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

    //将map<String,Integer>按Integer大小排序并获取到key
    public static List<String> sortMapByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.add(entry.getKey());
        }
        return result;
    }

    //将含有中文的路径进行编码
    public static String encodePath(String path) {
        String encodedPath = null;
        try {
            encodedPath = URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedPath;
    }

    //将含有中文的路径进行解码
    public static String decodePath(String path) {
        String decodedPath = null;
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedPath;
    }

    //将中文名文件中的命名使用时间戳加uuid进行命名更改,不改后缀
    public static String renameFile(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String name = UUID.nameUUIDFromBytes(fileName.getBytes()).toString();
        return name + suffix;
    }
}
