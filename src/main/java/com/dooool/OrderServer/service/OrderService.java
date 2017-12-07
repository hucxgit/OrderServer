package com.dooool.OrderServer.service;

import com.dooool.OrderServer.model.response.OrderReponse;

public interface OrderService {
    OrderReponse serviceGetOrderById(Integer id);

    Integer serviceAddOrder(Integer userId,String orderName);
}
