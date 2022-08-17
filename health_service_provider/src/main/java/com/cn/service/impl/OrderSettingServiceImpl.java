package com.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cn.domain.OrderSetting;
import com.cn.mapper.OrderSettingMapper;
import com.cn.service.OrderSettingService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Resource
    private OrderSettingMapper orderSettingMapper;
    @Override
    //批量导入预约设置数据
    public void add(List<OrderSetting> list) {
        if (list != null && list.size()>0){
            for (OrderSetting orderSetting : list) {
                //判断当前日期是否已经进行了预约设置
                long countByOrderDate = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
                if(countByOrderDate > 0){
                    //已经进行了预约设置，执行更新操作
                    orderSettingMapper.editNumberByOrderDate(orderSetting);
                }else{
                    //没有进行预约设置，执行插入操作
                    orderSettingMapper.add(orderSetting);
                }

            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {//格式：yyyy-MM
        String begin = date + "-1"; //2022-7-1
        String end = date + "-31"; //2022-7-31
        HashMap<String, String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        //对应mapper，根据日期范围查询预约设置数据
        List<OrderSetting> list = orderSettingMapper.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list !=null && list.size()>0){
            for (OrderSetting orderSetting : list) {
                HashMap<String, Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate()); //获取日期数字
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //根据日期查询是否已经进行了预约设置
        Date orderDate = orderSetting.getOrderDate();
        long count = orderSettingMapper.findCountByOrderDate(orderDate);
        if(count > 0){
            //当前日期已经进行了预约设置，需要执行更新操作
            orderSettingMapper.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有预约设置，则需要执行插入操作
            orderSettingMapper.add(orderSetting);
        }
    }
}
