package com.cn.service;

import com.cn.domain.CheckGroup;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;

import java.util.List;
import java.util.Map;

public interface CheckGroupService {

    void add(CheckGroup checkGroup,Integer[] checkitemIds);
    PageResult pageQuery(QueryPageBean queryPageBean);
    CheckGroup findById(Integer id);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    void edit(CheckGroup checkGroup,Integer[] checkitemIds);
    void delete(Integer id);


    List<CheckGroup> findAll();
}
