package com.dooool.OrderServer.service.impl;

import com.dooool.OrderServer.common.redis.RedisClientAdapterImpl;
import com.dooool.OrderServer.dao.read.OrderInfoReadMapper;
import com.dooool.OrderServer.dao.write.OrderInfoWriteMapper;
import com.dooool.OrderServer.entity.OrderInfoRead;
import com.dooool.OrderServer.entity.OrderInfoWrite;
import com.dooool.OrderServer.model.response.OrderReponse;
import com.dooool.OrderServer.service.OrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.dooool.OrderServer.common.json.FasterJsonTool;

import java.util.Arrays;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderInfoReadMapper orderInfoReadMapper;

    @Autowired
    OrderInfoWriteMapper orderInfoWriteMapper;


    @Autowired
    RedisClientAdapterImpl redisClientAdapter;

    //@Value("${redis.cacheTime}")
    //private int cacheTime;

    public OrderReponse serviceGetOrderById(Integer id) {
        OrderInfoRead result = null;
        String json = redisClientAdapter.getString("redis_test_key"+String.valueOf(id));
        if (json != null && !StringUtils.equals(json,"null")) {
            result = FasterJsonTool.readValue(json, OrderInfoRead.class);
        } else {
            result = this.orderInfoReadMapper.selectByPrimaryKey(id);
            if (result != null) {
                redisClientAdapter.set("redis_test_key"+String.valueOf(id),FasterJsonTool.writeValueAsString(result), 100);
            }
        }
        return new OrderReponse(result.getId(),result.getOrderName());
    }

    public Integer serviceAddOrder(Integer userId, String orderName) {
        OrderInfoWrite orderInfoWrite = new OrderInfoWrite();
        orderInfoWrite.setUserid(userId);
        orderInfoWrite.setOrderName(orderName);
        return this.orderInfoWriteMapper.insertSelective(orderInfoWrite);
    }
}
