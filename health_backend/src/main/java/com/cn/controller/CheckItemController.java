package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.constant.MessageConstant;
import com.cn.domain.CheckItem;
import com.cn.service.CheckItemService;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;
import com.cn.utils.Result;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 控制层模型
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController{

    @Reference //通过dubbo容器注入查找服务
    private CheckItemService checkitemservice;

    /**
     * 新增检查项
     */
    @RequestMapping("add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkitemservice.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 删除检查项
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        try {
        checkitemservice.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @PutMapping
    public Result update(@RequestBody CheckItem checkItem) {
        try {
            checkitemservice.update(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @GetMapping("{id}")
    public Result find(@PathVariable Integer id) {
        CheckItem checkItem = checkitemservice.find(id);
        return new Result(true, checkItem);
    }

    /**
     * 检查项之分页查询兼条件查询
     */
    @RequestMapping("/findPage")
    public PageResult getPage(@RequestBody QueryPageBean queryPageBean) {
        return checkitemservice.getPage(queryPageBean);
    }

    @GetMapping
    public Result findAll() {

        try {
            List<CheckItem> list = checkitemservice.getAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);

        }
    }

}
