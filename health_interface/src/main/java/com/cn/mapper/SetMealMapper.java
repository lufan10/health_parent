package com.cn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.domain.CheckGroup;
import com.cn.domain.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface SetMealMapper extends BaseMapper<Setmeal> {

    void add(Setmeal setmeal);
    void setSetMealAndCheckGroup(Map<String, Integer> map);
    Page<Setmeal> selectByCondition(String queryString);
    Setmeal findById(Integer id);
    List<Integer> findCheckGroupIdsBySetMealId(Integer id);
}
