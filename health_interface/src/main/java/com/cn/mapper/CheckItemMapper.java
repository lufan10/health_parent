package com.cn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.domain.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckItemMapper extends BaseMapper<CheckItem> {
    /**
     * 分页兼条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> selectByCondition(String queryString);

}
