package com.dooool.OrderServer.model.response;

public class OrderReponse {
    private Integer id;
    private String orderName;

    public OrderReponse(Integer id,String orderName){
        this.id = id;
        this.orderName = orderName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
