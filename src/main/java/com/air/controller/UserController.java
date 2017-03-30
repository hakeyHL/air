package com.air.controller;

import com.air.po.User;
import com.air.service.OrderService;
import com.air.service.TrainNumberService;
import com.air.service.UserService;
import com.air.utils.IdCardCheck;
import com.air.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by linux on 2017年03月28日.
 * Time 00:52
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    UserService userService;
    @Resource
    TrainNumberService trainNumberService;
    @Resource
    OrderService orderService;

    //注册
    //用户名,密码,身份证,性别
    //身份证校验,手机号校验,身份证重复校验
    @RequestMapping("register")
    private ModelAndView registerUser(User user) {
        //姓名校验和手机号校验放在jsp中,使用bootstrap的validate校验
        //身份证的校验采用jsp页面中手动点击校验的方式(校验正确性),jsp页面做基本的校验

        int updatedCount = userService.saveUser(user);
        if (updatedCount > 0) {
            modelAndView.addObject("msg", "success!");
        }

        modelAndView.setViewName("");
        //注册成功显示一个提示然后跳转到登录页面

        return modelAndView;
    }


    //登录
    //用户名,密码,验证码

    @RequestMapping("login")
    private ModelAndView login(User user) {
        //验证码校验
        //查询用户
        List<User> users = userService.listAllUser(user);
        if (users != null && users.size() == 1) {
            //只能有一个
            modelAndView.addObject("msg", "ok");
            modelAndView.setViewName("购票页面");
            request.getSession().setAttribute("user", users.get(0));
        }
        return modelAndView;
    }

    //添加联系人
    //输入姓名,身份证号,性别进行校验; 一个人只能加5个联系人
    @RequestMapping("add/contact")
    private ModelAndView addContact(User user) {
        //0. 当前用户已拥有的联系人数,已为5则结束流程
        //1. 根据身份证号获取用户
        //2. 用户是否已经为当前用户的联系人
        //3. 是,结束
        //4. 不是则校验用户输入其他信息是否与真实用户信息匹配
        //5. 不匹配则拒绝
        //6. 匹配则添加至用户联系人列表中
        return modelAndView;
    }

    //购票
    //第一次9.5折,第二次9 依次-0.5,最低为7折
    //用户可以看到购票次数和所享折扣
    //Ticket
    @RequestMapping("ticket")
    private ModelAndView ticket() {
        //1. 用户是否购买过当前车次
        int frequency = 0;
        //1.1 有,则按照折扣规则进行折扣
        //1.2 无,则按照原价9.5折购买
        BigDecimal ticketDiscount = StringUtils.getTicketDiscount(frequency);
        //2. 购票
        //2.1 这里需要测试和实现票竞争的问题


        //3. 购票完之后查看自己的订单
        return modelAndView;
    }

    /**
     * 身份证校验方法
     * 这里仅仅做合法和重复校验不做常规校验以及放供给校验等等
     *
     * @param idCardNumber
     * @return
     */
    @RequestMapping("idCard/validate")
    private ModelAndView idCardValidate(@RequestParam(defaultValue = "0") String idCardNumber) {
        //1. 校验身份证是否合法
        modelAndView.addObject("msg", "ok");

        IdCardCheck idCardCheck = new IdCardCheck(idCardNumber);
        if (idCardCheck.isCorrect() != 0) {
            modelAndView.addObject("msg", idCardCheck.getErrMsg());
            return modelAndView;
        }
        //2. 重复
        boolean exist = userService.checkIdCardIsExist(idCardNumber);
        if (exist) {
            modelAndView.addObject("msg", "此身份证已注册!");
        }
        return modelAndView;
    }
}
