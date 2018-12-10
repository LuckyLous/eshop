package com.hello.eshop.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计图表的bean，可用于excel和chart
 * Created by Hello on 2018/3/29.
 */
public class Chart implements Serializable {

    private Date date; // 下单日期

    private String name;// 图书名称

    private Integer num;// 销售数量

    private Double price;// 单价

    private Double amount;// 总金额

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
