package com.cn.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cn.domain.CheckItem;
import com.cn.mapper.CheckItemMapper;
import com.cn.service.CheckItemService;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Resource
    private CheckItemMapper checkitemmapper;

    @Override
    public void add(CheckItem checkItem) {
        checkitemmapper.insert(checkItem);

    }

    @Override
    public void delete(Integer id) {
        checkitemmapper.deleteById(id);
    }

    @Override
    public PageResult getPage(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkitemmapper.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total, rows);
    }

}
