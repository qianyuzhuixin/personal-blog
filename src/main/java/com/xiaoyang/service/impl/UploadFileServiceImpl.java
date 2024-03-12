package com.xiaoyang.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyang.pojo.UploadFile;
import com.xiaoyang.service.UploadFileService;
import com.xiaoyang.mapper.UploadFileMapper;
import com.xiaoyang.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * @author xiaomei
 * @description 针对表【upload_file(上传文件列表)】的数据库操作Service实现
 * @createDate 2024-02-04 09:31:59
 */
@Service
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile>
        implements UploadFileService {

    @Override
    public String getUploadUrl(MultipartFile file) {
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
        fileName = FileUtils.getFileName(fileName);
        String filePath = FileUtils.getUploadPath();
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath + File.separator + fileName))) {
            out.write(file.getBytes());
            out.flush();

            // 将文件路径备份
            UploadFile uploadFile1 = new UploadFile();
            uploadFile1.setFileSize(size);
            uploadFile1.setUploadTime(DateUtil.date());
            uploadFile1.setFileUrl(FileUtils.getImagePath() + fileName);
            save(uploadFile1);
            return uploadFile1.getFileUrl();
        } catch (Exception e) {
            log.error("上传文件失败:" + e.getMessage());
        }
        return null;
    }
}




