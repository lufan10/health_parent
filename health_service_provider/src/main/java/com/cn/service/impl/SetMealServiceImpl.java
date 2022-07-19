package com.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cn.constant.RedisConstant;
import com.cn.domain.CheckGroup;
import com.cn.domain.Setmeal;
import com.cn.mapper.SetMealMapper;
import com.cn.service.SetMealService;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检套餐服务
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {
    @Resource
    private SetMealMapper setMealMapper;
    @Resource
    private RedisTemplate redisTemplate;

    //设置套餐和检查组多对多关系，操作t_setmeal_checkgroup
    public void setSetMealAndCheckGroup(Integer setMealId,Integer[] checkGroupIds){
        if (checkGroupIds != null && checkGroupIds.length>0){
            for (Integer checkGroupId : checkGroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setMealId);
                map.put("checkgroupId",checkGroupId);
                setMealMapper.setSetMealAndCheckGroup(map);
            }
        }
    }
    //新增套餐，同时需要让套餐关联检查组
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //新增套餐，操作t_setmeal表
        setMealMapper.add(setmeal);
        //设置检查组和套餐的多对多的关联关系，操作t_setmeal_checkgroup表
        Integer setmealId = setmeal.getId();
        this.setSetMealAndCheckGroup(setmealId,checkgroupIds);
        //将图片名称保持到Redis集合中
        String fileName = setmeal.getImg();
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealMapper.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult() );
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealMapper.findById(id);
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id) {
        return setMealMapper.findCheckGroupIdsBySetMealId(id);
    }



}
