package com.xiaoyang.utils;

import cn.hutool.core.util.IdUtil;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 文件处理相关
 * @Author: xiaomei
 * @Date: 2024/2/1 001
 */
public class FileUtils {

    public static String uploadFilePath = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/upload/img/";

    /**
     * 在文件名中加入时间戳
     * * @return java.lang.String
     */
    public static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index) + "_" + IdUtil.simpleUUID() + fileName.substring(index);
        return fileName;
    }

    //获取当前文件路径
    public static String getUploadPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(), "static" + uploadFilePath);

        if (!upload.exists()) {
            upload.mkdirs();
        }
        System.out.println("path:" + upload.getAbsolutePath());
        return upload.getAbsolutePath();
    }

    // 获取图片路径
    public static String getImagePath() {
        return uploadFilePath;
    }
}
