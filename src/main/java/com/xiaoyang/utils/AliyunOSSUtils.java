package com.xiaoyang.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 阿里云oss工具类
 * @Author: xiaomei
 * @Date: 2024/3/17 017
 */
@Component
public class AliyunOSSUtils {

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.rootPath}")
    private String rootPath;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    //上传文件（以字节流的显示上传,获取永久url）
    public String uploadFile(byte[] data, String fileName) throws OSSException {
        fileName = getFileName(fileName);
        //上传资源到Oss
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(data));
        return getUrl(fileName);
    }

    //上传文件（以字节流的显示上传,获取有效时间url）
    public String uploadFile(byte[] data, String fileName, Date date) throws OSSException {
        fileName = getFileName(fileName);
        //上传资源到Oss
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(data));
        return getUrl(fileName, date);
    }

    //上传文件（直接上传,获取永久url）
    public String uploadFile(String fileName, File file) throws OSSException {
        fileName = getFileName(fileName);
        //上传资源到Oss
        ossClient.putObject(bucketName, fileName, file);
        return getUrl(fileName);
    }

    //上传文件（直接上传,获取有效时间url）
    public String uploadFile(String fileName, File file, Date date) throws OSSException {
        fileName = getFileName(fileName);
        //上传资源到Oss
        ossClient.putObject(bucketName, fileName, file);
        return getUrl(fileName, date);
    }

    // 列举文件
    public List<String> listFiles() {
        //列举文件
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        List<String> files = new ArrayList<>();
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            files.add(objectSummary.getKey());
        }
        return files;
    }


    // 删除文件
    public void deleteFile(String fileName) {
        //删除文件
        ossClient.deleteObject(fileName, bucketName);
    }

    // 获取访问路径(永久访问路径)
    public String getUrl(String fileName) {
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }

    // 获取访问路径（设置路径访问有效期）
    public String getUrl(String fileName, Date date) {
        return ossClient.generatePresignedUrl(bucketName, fileName, date).toString();
    }

    //getPath(获取格式化后的文件名)
    public String getFileName(String fileName) {
        fileName = CommonUtils.renameFile(fileName);
        long time = new Date().getTime();
        fileName = rootPath + sdf.format(time) + "/" + time + fileName;
        return fileName;
    }


}
