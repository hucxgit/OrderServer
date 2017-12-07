package com.dooool.OrderServer.dao.write;

import com.dooool.OrderServer.entity.OrderInfoWrite;

public interface OrderInfoWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfoWrite record);

    int insertSelective(OrderInfoWrite record);

    OrderInfoWrite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfoWrite record);

    int updateByPrimaryKey(OrderInfoWrite record);
}