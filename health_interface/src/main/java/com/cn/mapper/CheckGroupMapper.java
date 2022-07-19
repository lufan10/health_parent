package com.cn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.domain.CheckGroup;
import com.cn.domain.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckGroupMapper extends BaseMapper<CheckGroup> {

    void add(CheckGroup checkGroup);
    void setCheckGroupAndCheckItem(Map map);
    /**
     * 分页兼条件查询
     */
    Page<CheckGroup> selectByCondition(String queryString);
    CheckGroup findById(Integer id);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    void edit(CheckGroup checkGroup);

    void deleteAssocication(Integer id);

    List<CheckGroup> findAll();

}
