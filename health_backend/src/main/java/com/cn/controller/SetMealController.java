package com.cn.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cn.constant.MessageConstant;
import com.cn.constant.RedisConstant;
import com.cn.domain.Setmeal;
import com.cn.service.SetMealService;
import com.cn.utils.PageResult;
import com.cn.utils.QiNiuUtils;
import com.cn.utils.QueryPageBean;
import com.cn.utils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * 体检套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference //通过dubbo容器注入查找服务
    private SetMealService setMealService;
    @Resource
    private RedisTemplate redisTemplate;
    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
//        System.out.println(imgFile);
        String originalFilename = imgFile.getOriginalFilename();//原始文件名
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1); // 截取产生后缀 .jpg
        String fileName = UUID.randomUUID().toString()+extention;//
        try {
            //将文件上传到七牛云服务器
            QiNiuUtils.upload2QiNiu(imgFile.getBytes(),fileName);
            //当用户上传图片后，将图片名称保持到redis的一个Set集合中
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }
    /**
     * 1.页面完善后，新增套餐
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setMealService.add(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    /**
     * 2.检查项之分页查询兼条件查询,同时导入真实数据到数据库表中
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setMealService.pageQuery(queryPageBean);
    }

    /**
     * 3.根据Id查询套餐
     */
    @GetMapping("{id}")
    //select * from t_setmeal where id = #{id}
    public Result findById(@PathVariable Integer id) {
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    //根据检查组ID查询当前套餐ID包含的检查组ID,方便测试在中间关系表中构造数据
    //select checkgroup_id  from t_setmeal_checkgroup where setmeal_id = #{id}
    @RequestMapping("/findCheckGroupIdsBySetMealId")
    public Result findCheckGroupIdsBySetMealId(Integer id){

        try {
            List<Integer> checkgroupIds = setMealService.findCheckGroupIdsBySetMealId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }








}
