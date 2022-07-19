package com.cn.service;

import com.cn.domain.Setmeal;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;

import java.util.List;

public interface SetMealService {


    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    Setmeal findById(Integer id);

    List<Integer> findCheckGroupIdsBySetMealId(Integer id);
}
