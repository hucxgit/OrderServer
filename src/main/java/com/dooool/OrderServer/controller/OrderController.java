package com.dooool.OrderServer.controller;


import com.dooool.OrderServer.common.json.FasterJsonTool;
import com.dooool.OrderServer.common.response.Response;
import com.dooool.OrderServer.common.utils.PropertyUtils;
import com.dooool.OrderServer.model.request.OrderRequest;
import com.dooool.OrderServer.model.response.OrderReponse;
import com.dooool.OrderServer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@Slf4j
@Controller
@RequestMapping(value = "/order")
public class OrderController {


    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;


    /**
     * 根据id获取订单
     * @param id
     * @return
     */

    @RequestMapping(value = "/getOrder.action")
    @ResponseBody
    public String getOrderById(@RequestParam Integer id){
        return "order";
    }


    /**
     * 根据id获取订单
     * @param param
     * @return
     */


    @RequestMapping(value = "/getOrder1.action")
    @ResponseBody
    public Response getOrderById_1(@RequestBody OrderRequest param){
        logger.info("获取订单信息 入参={}", FasterJsonTool.writeValueAsString(param));
        OrderReponse orderReponse = this.orderService.serviceGetOrderById(param.getId());
        logger.info("获取订单信息 返回结果={}", FasterJsonTool.writeValueAsString(orderReponse));
        return new Response("",1,orderReponse);
    }

    @RequestMapping(value = "/addOrder.action")
    @ResponseBody
    public String  addOrder(@RequestBody OrderRequest param){
        int result= this.orderService.serviceAddOrder(param.getUserId(),param.getOrderName());
        return String.valueOf(result);
    }


    @RequestMapping(value = "/getProperty.action")
    @ResponseBody
    public String  getProperty(@RequestBody OrderRequest param){
        return PropertyUtils.getProperty("jdbc.house.url.read");
    }



}
