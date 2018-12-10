package com.hello.eshop.service.impl;

import com.hello.eshop.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 扫描待支付订单
 * Created by Hello on 2018/3/24.
 */
@Component
public class PaymentOrderJob {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 监听超过1小时未付款的订单
     */
    public void listenerOrder(){
        System.out.println(orderMapper);
        orderMapper.paymentOrderScan(new Date());
    }

}
