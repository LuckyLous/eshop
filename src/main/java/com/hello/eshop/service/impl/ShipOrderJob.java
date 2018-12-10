package com.hello.eshop.service.impl;

import com.hello.eshop.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扫描发货后的订单
 * Created by Hello on 2018/4/17.
 */
@Component
public class ShipOrderJob {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 监听发货后10天的订单
     */
    public void autoFinishOrder(){
        System.out.println(orderMapper);
        orderMapper.autoFinishOrder();
    }
}
