package com.cn.service;

import com.cn.domain.CheckItem;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;

import java.util.List;

public interface CheckItemService {

    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 删除检查项
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 查询单个检查项
     * @param id
     * @return
     */
    CheckItem find(Integer id);

    /**
     * 分页
     * @param queryPageBean
     * @return
     */
    PageResult getPage(QueryPageBean queryPageBean);

    List<CheckItem> getAll();
}
