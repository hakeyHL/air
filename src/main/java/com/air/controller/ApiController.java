package com.air.controller;

import com.air.po.TrainNumber;
import com.air.po.TrainOrder;
import com.air.po.User;
import com.air.po.UserContact;
import com.air.service.OrderService;
import com.air.service.TrainNumberService;
import com.air.service.UserContactService;
import com.air.service.UserService;
import com.air.utils.ConstantUtils;
import com.air.utils.IdCardCheck;
import com.air.utils.ImageUtils;
import com.air.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linux on 2017年03月28日.
 * Time 00:52
 */
@Controller
@RequestMapping("api")
public class ApiController extends BaseController {
    @Resource
    UserService userService;
    @Resource
    TrainNumberService trainNumberService;
    @Resource
    OrderService orderService;
    @Resource
    UserContactService userContactService;

    //注册
    //用户名,密码,身份证,性别
    //身份证校验,手机号校验,身份证重复校验
    @RequestMapping("register")
    private ModelAndView registerUser(User user) {
        //姓名校验和手机号校验放在jsp中,使用bootstrap的validate校验
        //身份证的校验采用jsp页面中手动点击校验的方式(校验正确性),jsp页面做基本的校验
        //2. 重复
        boolean exist = userService.checkIdCardIsExist(user.getIdCardNumber());
        if (exist) {
            //valid
            modelAndView.addObject("msg", "失败,ID已注册!");
            modelAndView.setViewName("layout/register");
            modelAndView.addObject("data", user);
            return modelAndView;
        }
        Long id = userService.saveUser(user);
        if (id > 0) {
            modelAndView.addObject("msg", "谢谢您加入我们!");
            //创建用户时他自己是自己的联系人
            UserContact userContact = new UserContact();
            userContact.setContactUserId(id);
            userContact.setUserId(id);
            userContactService.saveContact(userContact);
        }

        modelAndView.setViewName("login");
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
            List<TrainNumber> trainNumbers = trainNumberService.listTrains(new TrainNumber());
            modelAndView.addObject("trains", trainNumbers);

            modelAndView.setViewName("layout/listtrain");
            request.getSession().setAttribute("user", users.get(0));
        } else {
            modelAndView.addObject("msg", "用户名或密码错误!");
            modelAndView.addObject("data", user);
            modelAndView.setViewName("/login");
        }
        return modelAndView;
    }

    //添加联系人
    //输入姓名,身份证号,性别进行校验; 一个人只能加5个联系人
    @RequestMapping("add/contact")
    private ModelAndView addContact(User user) {
        modelAndView.setViewName("layout/addContact");

        //0. 当前用户已拥有的联系人数,已为5则结束流程
        List<UserContact> userContacts = userContactService.listUserContacts(currentUser.getId());
        if (userContacts != null && userContacts.size() == 5) {
            //结束
            modelAndView.addObject("msg", "最多只能添加5个联系人!");
            modelAndView.addObject("data", user);
            return modelAndView;
        }
        //1. 根据身份证号获取用户
        User queryUser = new User();
        queryUser.setIdCardNumber(user.getIdCardNumber());
        List<User> users = userService.listAllUser(queryUser);
        if (users == null || users.size() < 1) {
            //查无此人,结束
            modelAndView.addObject("data", user);
            modelAndView.addObject("msg", "添加失败,身份证或姓名有误!");
            return modelAndView;
        }

        //2. 用户是否已经为当前用户的联系人
        User addedUser = users.get(0);
        boolean isContactAlready = false;
        for (UserContact itUser : userContacts) {
            if (itUser.getUserId() == addedUser.getId()) {
                isContactAlready = true;
            }
        }
        if (isContactAlready) {
            //3. 是,结束
            //结束,已经是联系人
            modelAndView.addObject("data", user);
            modelAndView.addObject("msg", "添加失败,该用户已经是您的联系人!");
            return modelAndView;
        }
        //4. 不是则校验用户输入其他信息是否与真实用户信息匹配
        if (!addedUser.getIdCardNumber().equals(user.getIdCardNumber()) || !addedUser.getName().equals(user.getName())) {
            //5. 不匹配则拒绝
            //结束,有信息不正确
            modelAndView.addObject("data", user);
            modelAndView.addObject("msg", "添加失败,身份证或姓名有误!");
            return modelAndView;
        }
        //6. 匹配则添加至用户联系人列表中
        UserContact userContact = new UserContact();
        userContact.setUserId(currentUser.getId());
        userContact.setContactUserId(addedUser.getId());
        userContactService.saveContact(userContact);

        return userInfo();
    }

    //购票
    //第一次9.5折,第二次9 依次-0.5,最低为7折
    //用户可以看到购票次数和所享折扣
    //Ticket
    @RequestMapping("ticket")
    private ModelAndView ticket(TrainOrder order) {
        modelAndView.setViewName("message");
        if (order.getUserId() == null || order.getUserId() < 1) {
            modelAndView.addObject("msg", "请选择乘车人!");
            return trainInfo(order.getTrainId());
        }

        Long repoCount;
        //检查是否有库存
        if (order != null && order.getTrainId() != null && order.getTrainId() > 0) {
            repoCount = ConstantUtils.trainTicketRepoArrayQueue.get(order.getTrainId());
            if (repoCount == null || repoCount < 1) {
                modelAndView.addObject("msg", "非常抱歉,票已售完!");
                return trainInfo(order.getTrainId());
            }
        }

        order.setUserId(currentUser.getId());

        //1. 用户是否购买过当前车次

        int frequency = 0;
        List<TrainOrder> userOrders = orderService.listOrdersByUserId(currentUser.getId());
        if (userOrders != null && userOrders.size() > 0) {
            frequency = userOrders.size();
        }
        //1.1 有,则按照折扣规则进行折扣
        //1.2 无,则按照原价9.5折购买
        BigDecimal ticketDiscount = StringUtils.getTicketDiscount(frequency);
        //2. 购票
        order.setCreateTime(new Date().getTime());
        order.setDiscount(ticketDiscount.floatValue());

        //先减去库存
        ConstantUtils.subTicketCountFromQuque(order.getTrainId());
        try {
            orderService.createOrder(order);
        } catch (Exception e) {
            //如果发生异常就还原库存
            ConstantUtils.subTicketCountFromQuque(order.getTrainId());
            modelAndView.addObject("msg", "服务器故障,请联系管理员!");
            return trainInfo(order.getTrainId());
        }
        //获取该车次信息
        TrainNumber trainNumber = trainNumberService.getTrainById(order.getTrainId());
        if (trainNumber != null) {
            trainNumber.setSeatsNumber(trainNumber.getSeatsNumber() - 1);
            //更新库存
            trainNumberService.updateTrainInfo(trainNumber);
        }
        userOrders.add(order);

        setOrderProperties(userOrders);

        modelAndView.addObject("orders", userOrders);
        addUserContact2ModelAndView();

        modelAndView.setViewName("/layout/userInfo");
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
    @ResponseBody
    @RequestMapping("idCard/validate")
    private Map idCardValidate(@RequestParam(defaultValue = "0") String idCardNumber) {
        Map dateMap = new HashMap();
        //1. 校验身份证是否合法
        dateMap.put("valid", true);

        IdCardCheck idCardCheck = new IdCardCheck(idCardNumber);
        if (idCardCheck.isCorrect() != 0) {
            dateMap.put("valid", false);
            return dateMap;
        }
        return dateMap;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping("userInfo")
    private ModelAndView userInfo() {
        modelAndView.setViewName("layout/userInfo");

        //用户并封装用户订单列表
        List<TrainOrder> userOrders = orderService.listOrdersByUserId(currentUser.getId());
        setOrderProperties(userOrders);
        modelAndView.addObject("orders", userOrders);

        //用户的联系人列表
        addUserContact2ModelAndView();
        return modelAndView;
    }

    /**
     * 获取用户的联系人列表并放入到modelAndView中用于页面显示
     */
    private void addUserContact2ModelAndView() {
        List<UserContact> userContacts = userContactService.listUserContacts(currentUser.getId());
        for (UserContact userContact : userContacts) {
            User user = userService.getUserById(userContact.getContactUserId());
            if (user != null) {
                userContact.setUserName(user.getName());
                userContact.setIdCardNumber(user.getIdCardNumber());
            }
        }
        modelAndView.addObject("contacts", userContacts);
    }

    /**
     * 通过查询将一些要显示的属性封装到TrainOrder中
     *
     * @param userOrders
     */
    private void setOrderProperties(List<TrainOrder> userOrders) {
        for (TrainOrder trainOrder : userOrders) {
            //封装一些显示属性
            TrainNumber trainNumber = trainNumberService.getTrainById(trainOrder.getTrainId());
            User user = userService.getUserById(trainOrder.getUserId());
            trainOrder.setPassenger(user.getName());
            trainOrder.setStartSite(trainNumber.getStartSite());
            trainOrder.setEndSite(trainNumber.getEndSite());
            trainOrder.setPrice(trainNumber.getPrice());
        }
    }

    /**
     * 获取车辆信息到order页面然后进行下单买票
     *
     * @return
     */
    @RequestMapping("trainInfo/{id}")
    private ModelAndView trainInfo(@PathVariable("id") Long id) {
        modelAndView.setViewName("layout/order");
        TrainNumber trainNumber = trainNumberService.getTrainById(id);
        modelAndView.addObject("train", trainNumber);

        addUserContact2ModelAndView();

        return modelAndView;
    }

    /**
     * 获取和查询车辆列表
     *
     * @param trainNumber
     * @return
     */
    @RequestMapping("trains")
    private ModelAndView trains(TrainNumber trainNumber) {
        List<TrainNumber> trainNumbers = trainNumberService.listTrains(trainNumber);
        modelAndView.addObject("trains", trainNumbers);
        modelAndView.addObject("data", trainNumber);
        modelAndView.setViewName("layout/listtrain");
        return modelAndView;
    }

    /**
     * 生成验证码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("validateCode")
    private Map validateCode(String validateCode) {
        Map dataMap = new HashMap();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(validateCode)) {
            //验证
            String sessionValidateCode = (String) request.getSession().getAttribute("validateCode");
            if (sessionValidateCode.toLowerCase().trim().equals(validateCode)) {
                dataMap.put("valid", true);
            }
        } else {
            ImageUtils.getValidationCode(response, request);
        }
        return dataMap;
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @RequestMapping("to/register")
    private ModelAndView toRegisterPage() {
        modelAndView.setViewName("layout/register");
        return modelAndView;
    }

    /**
     * 跳转到添加联系人页面
     *
     * @return
     */
    @RequestMapping("to/addContact")
    private ModelAndView toAddContact() {
        modelAndView.setViewName("layout/addContact");
        return modelAndView;
    }
}
