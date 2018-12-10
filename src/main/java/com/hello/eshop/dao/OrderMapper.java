package com.hello.eshop.dao;

import com.hello.eshop.bean.Chart;
import com.hello.eshop.bean.Order;
import com.hello.eshop.bean.OrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    // 按条件更新订单支付状态,过期1小时则结束订单
    void paymentOrderScan(@Param("date") Date date);

    // 按条件更新订单状态,发货10天后，默认自动完成订单
    void autoFinishOrder();

    // 按照年-月查询已完成的订单信息，并按名称分组，返回统计的bean
    List<Chart> selectByDate(@Param("date") Date date);
}