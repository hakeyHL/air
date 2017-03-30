package com.air.controller;

import com.air.po.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by linux on 2017年03月29日.
 * Time 22:28
 */
public abstract class BaseController {
    protected ModelAndView modelAndView = new ModelAndView();
    protected HttpServletRequest request;
    protected User currentUser;

    /*
    * 注解ModelAttribute的意义是
    * 被注解的方法会在进入每个controller之前执行
    * */
    @ModelAttribute
    private void init(HttpServletRequest request) {
        //将modelAndView中属性清空
        modelAndView.clear();

        //固定优先装入内容
        modelAndView.addObject("msg", "");
        modelAndView.setViewName("error");

        //赋值request和response对象,以便获取request中的属性
        this.request = request;
        User user = (User) this.request.getSession().getAttribute("user");
        if (user != null) {
            currentUser = user;
        }
    }
}
