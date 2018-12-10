package com.hello.eshop.bean;

import java.io.Serializable;

/**
 * 后台导入索引库的bean
 * 前台搜索结果的bean
 * @author Hello
 *
 */
public class SearchItem implements Serializable{

	private String id; 
	private String prodName;// 商品名称
	private String author;// 作者
	private String press;// 出版社
	private Double shopPrice;// 商店价格
	private String image;// 图片
	private String cateName;// 二级分类名称
	private String description;// 商品描述

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public Double getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(Double shopPrice) {
		this.shopPrice = shopPrice;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
