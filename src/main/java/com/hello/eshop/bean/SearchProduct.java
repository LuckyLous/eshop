package com.hello.eshop.bean;

import java.io.Serializable;

/**
 * 后台查询商品的bean
 * @author Hello
 *
 */
public class SearchProduct implements Serializable {

	private Integer status;// 状态
	
	private Integer bigCateId;// 一级分类的id
	
	private Integer cateId;// 二级分类的id
	
	private String prodName;// 商品名称

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Integer getBigCateId() {
		return bigCateId;
	}

	public void setBigCateId(Integer bigCateId) {
		this.bigCateId = bigCateId;
	}
	
}
