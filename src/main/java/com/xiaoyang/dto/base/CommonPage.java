package com.xiaoyang.dto.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 分页转化器
 * @Author: xiaomei
 * @Date: 2023/11/12 012
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPage<T> {
    private int currentPage;   // 当前页码
    private int pageSize;      // 每页显示的数据量
    private long total;    // 总数据条数
    private List<T> data;      // 当前页的数据集合


    public static <T> CommonPage<T> restPage(IPage<T> page) {
        return new CommonPage<>((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), page.getRecords());
    }

    public Long getTotalPages() {
        long totalPages = this.total / this.pageSize;
        if (this.total % this.pageSize != 0) {
            totalPages++;
        }
        return totalPages;
    }
}
