package com.air.service.impl;

import com.air.dao.DataBaseManagerImpl;
import com.air.po.Order;
import com.air.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linux on 2017年03月28日.
 * Time 01:07
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String ORDER_LIST_BY_USERID = "select t from Order t where t.userId=?0";
    @Resource
    private DataBaseManagerImpl<Order> orderDataBaseManager;

    /**
     * 根据用户id获取订单列表
     *
     * @param id
     * @return
     */
    public List<Order> listOrdersByUserId(Long id) {
        return orderDataBaseManager.query(ORDER_LIST_BY_USERID, Arrays.asList(id));
    }

    /**
     * 下单
     *
     * @param order
     */
    public void createOrder(Order order) {
        orderDataBaseManager.create(order);
    }
}
