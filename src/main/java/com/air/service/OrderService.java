package com.air.service;

import com.air.po.TrainOrder;

import java.util.List;

/**
 * Created by linux on 2017年03月28日.
 * Time 01:06
 */
public interface OrderService {
    List<TrainOrder> listOrdersByUserId(Long id);

    void createOrder(TrainOrder order);
}
