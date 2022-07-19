package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.constant.MessageConstant;
import com.cn.domain.CheckGroup;
import com.cn.domain.CheckItem;
import com.cn.service.CheckGroupService;
import com.cn.utils.PageResult;
import com.cn.utils.QueryPageBean;
import com.cn.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 检查组管理
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference //通过dubbo容器注入查找服务
    private CheckGroupService checkGroupService;

    /**
     * 1.页面完善后，新增检查项
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    /**
     * 2.检查项之分页查询兼条件查询,同时导入真实数据到数据库表中
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.pageQuery(queryPageBean);
    }

    /**
     * 3.根据Id查询检查组
     */
    @GetMapping("{id}")
    //select * from t_checkgroup where id = #{id}
    public Result findById(@PathVariable Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查组
     */
    @GetMapping
    public Result findAll(){
        try {
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //根据检查组ID查询当前检查组包含的检查项ID,方便测试在中间关系表中构造数据
    //select checkitem_id  from t_checkgroup_checkitem where checkgroup_id = #{id}
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){

        try {
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


    /**
     * 删除检查项
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            checkGroupService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }





}
