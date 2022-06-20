package com.cn.service;

import com.cn.domain.CheckItem;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;

public interface CheckItemService {

    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    void delete(Integer id);
    /**
     * 分页
     * @param queryPageBean
     * @return
     */
    PageResult getPage(QueryPageBean queryPageBean);
}
