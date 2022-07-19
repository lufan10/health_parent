package com.cn.jobs;

import com.cn.constant.RedisConstant;
import com.cn.utils.QiNiuUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 具体执行的工作
 * 自定义Job，实现定时清理垃圾图片
 */
@Component
public class ClearImgJob {
    @Resource
    private RedisTemplate redisTemplate;
    @Scheduled(cron = "0/10 * * * * ?")
    public void clearImg(){
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set !=null){
            for (String picName : set) {
                //删除七牛云服务器上的图片
                QiNiuUtils.deleteFileFromQiNiu(picName);
                //从Redis集合中删除图片mc
                redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("自定义任务执行，清理垃圾图片"+picName);
            }
        }
    }
}