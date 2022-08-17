package com.cn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.domain.OrderSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface OrderSettingMapper extends BaseMapper<OrderSetting> {
    void add(OrderSetting orderSetting);
    void editNumberByOrderDate(OrderSetting orderSetting);
    long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(HashMap<String, String> map);
}
