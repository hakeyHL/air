package com.air.controller;

import com.air.service.OrderService;
import com.air.service.TrainNumberService;
import com.air.service.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by linux on 2017年03月28日.
 * Time 00:52
 */
@Controller
public class UserController {
    @Resource
    UserService userService;
    @Resource
    TrainNumberService trainNumberService;
    @Resource
    OrderService orderService;

    //注册
    //用户名,密码,身份证,性别
    //身份证校验,手机号校验,身份证重复校验


    //登录
    //用户名,密码,验证码

    //添加联系人
    //输入姓名,身份证号,性别进行校验; 一个人只能加5个联系人


    //购票
    //第一次9.5折,第二次9,第三次7.5依次-0.5,最低为7折
    //用户可以看到购票次数和所享折扣
}
