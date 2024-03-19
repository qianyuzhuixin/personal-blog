package com.xiaoyang.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.UploadFile;
import com.xiaoyang.service.UploadFileService;
import com.xiaoyang.mapper.UploadFileMapper;
import com.xiaoyang.utils.AliyunOSSUtils;
import com.xiaoyang.utils.CommonUtils;
import com.xiaoyang.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author xiaomei
 * @description 针对表【upload_file(上传文件列表)】的数据库操作Service实现
 * @createDate 2024-02-04 09:31:59
 */
@Service
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile>
        implements UploadFileService {
    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;


    @Override
    public String getUploadUrl(MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getSize() < 1) {
            return null;
        }
        long size = file.getSize();
        UploadFile uploadFile = getOne(Wrappers.<UploadFile>lambdaQuery().eq(UploadFile::getFileSize, size));
        if (Objects.nonNull(uploadFile)) {
            return uploadFile.getFileUrl();
        }


        // 获取文件名
        String fileName = file.getOriginalFilename();
        //fileName = FileUtils.getFileName(fileName);
        //String filePath = FileUtils.getUploadPath();
        String filePath = aliyunOSSUtils.uploadFile(file.getBytes(), fileName);
        // 将文件路径备份
        UploadFile uploadFile1 = new UploadFile();
        uploadFile1.setFileSize(size);
        uploadFile1.setUploadTime(DateUtil.date());
        uploadFile1.setFileUrl(filePath);
        save(uploadFile1);
        return uploadFile1.getFileUrl();
    }
}




