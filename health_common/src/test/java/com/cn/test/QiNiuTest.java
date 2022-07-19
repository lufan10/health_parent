package com.cn.test;

import com.cn.utils.QiNiuUtils;
import org.junit.jupiter.api.Test;

public class QiNiuTest {

    @Test
    //1.使用七牛云提供的SDK实现将本地图片上传到七牛云服务器
    public void test1(){
        QiNiuUtils.upload2QiNiu("C:\\Users\\lufan\\Desktop\\图片资源\\03a36073-a140-4942-9b9b-712cecb144901.jpg","abc.jpg");

    }
    @Test
//    2.删除空间中的文件
    public void test2(){
        QiNiuUtils.deleteFileFromQiNiu("abc.jpg");

    }
}
