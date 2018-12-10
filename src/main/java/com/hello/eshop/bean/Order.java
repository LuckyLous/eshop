package com.hello.eshop.bean;

import com.hello.eshop.constant.Constant;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String id;

    private Date orderTime;

    private Double total;

    private Integer status;

    private String address;

    private String receiverName;

    private String receiverPhone;

    private Integer userId;

    private Date updateTime;
    
    // 表示当前订单属于哪个user(用户)
    private User user;
    // 表示当前订单包含的订单项(存储订单项列表)
    private List<OrderItem> items;
    
    /**
     *  判断订单的处理状态,true则处理完毕，交易完成或关闭
     */
    public static Boolean isCompleted(Order order){
    	if(order.getStatus() == Constant.ORDER_IS_FINISH || 
    		order.getStatus() == Constant.ORDER_IS_CLOSE ){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}