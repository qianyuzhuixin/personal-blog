package com.xiaoyang.service;

import com.xiaoyang.pojo.UploadFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaomei
 * @description 针对表【upload_file(上传文件列表)】的数据库操作Service
 * @createDate 2024-02-04 09:31:59
 */
public interface UploadFileService extends IService<UploadFile> {

    String getUploadUrl(MultipartFile file);
}
