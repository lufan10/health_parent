package com.cn.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
@Data
public class PageResult implements Serializable{
    private Long total;//总记录数
    private List rows;//当前页结果

    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

}
