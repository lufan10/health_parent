package com.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cn.domain.CheckGroup;
import com.cn.domain.CheckItem;
import com.cn.mapper.CheckGroupMapper;
import com.cn.mapper.CheckItemMapper;
import com.cn.service.CheckGroupService;
import com.cn.service.CheckItemService;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Resource
    private CheckGroupMapper checkGroupMapper;

    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if (checkitemIds != null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupMapper.setCheckGroupAndCheckItem(map);
            }
        }
    }
    @Override
    //新增检查组，同时需要让检查组关联检查项
    public void add(CheckGroup checkGroup,Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupMapper.add(checkGroup);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(queryPageBean.getCurrentPage(),pageSize);
        Page<CheckGroup> page = checkGroupMapper.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult() );
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {

        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    //编辑检查组信息，同时需要关联检查项  4
    public void edit(CheckGroup checkGroup,Integer[] checkitemIds) {
        //修改检查组基本信息，操作检查组t_checkgroup表
        checkGroupMapper.edit(checkGroup);
        //清理当前检查组关联的检查项，操作中间关系表t_checkgroup_checkitem表
        checkGroupMapper.deleteAssocication(checkGroup.getId());
        //重新建立当前检查组和检查项的关联关系
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    @Override
    public void delete(Integer id) {
        checkGroupMapper.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroups = checkGroupMapper.findAll();
        return checkGroups;
    }


}
