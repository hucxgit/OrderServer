package com.dooool.OrderServer.dao.read;

import com.dooool.OrderServer.entity.OrderInfoRead;

public interface OrderInfoReadMapper {
    OrderInfoRead selectByPrimaryKey(Integer id);
}