package com.xiaoyang.personalblog;

import com.aliyun.oss.common.utils.IOUtils;
import com.xiaoyang.utils.AliyunOSSUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.lang.model.SourceVersion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Description: 阿里云oss测试类
 * @Author: xiaomei
 * @Date: 2024/3/17 017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OSSTest {
    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;


    @Test
    public void uploadFile() throws Exception {
        //1.读取需要上传的文件
        FileInputStream file = new FileInputStream("server块.png");
        //2.转成字节流
        byte[] data = IOUtils.readStreamAsByteArray(file);
        //3.上传
        String url = aliyunOSSUtils.uploadFile(data, "server块.png");
        System.out.println("url = " + url);
        System.out.println(" = " + aliyunOSSUtils.listFiles());
    }
}
