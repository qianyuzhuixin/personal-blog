package com.xiaoyang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName upload_file
 */
@TableName(value = "upload_file")
@Data
public class UploadFile implements Serializable {
    @TableId(value = "upload_file_list_id")
    private String uploadFileListId;

    private Long fileSize;

    private String fileUrl;

    private Date uploadTime;

    private static final long serialVersionUID = 1L;
}