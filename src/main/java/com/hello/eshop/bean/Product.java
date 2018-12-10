package com.hello.eshop.bean;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private String id;

    @NotEmpty(message = "商品名称不能为空")
    private String prodName;

    @Min(1)
    private Double shopPrice;

    @Min(1)
    private Integer num;

    private Double discount;

    @NotEmpty(message = "上传图片不能为空")
    private String image;

    private Date addTime;

    private Integer isHot;

    private String description;

    private Integer status;

    private Integer bigCateId;

    private Integer cateId;
    
    // 设置对应的一级分类属性，查询商品时将category的一些属性一并查询出来
    private Category bigCate;
    
    // 设置二级分类属性
    private Category cate;
    
    // 设置对应的Book属性,查询商品将book的其余属性一并查询出来
    private Book book;

    public Category getBigCate() {
		return bigCate;
	}
	public void setBigCate(Category bigCate) {
		this.bigCate = bigCate;
	}
	public Category getCate() {
		return cate;
	}
	public void setCate(Category cate) {
		this.cate = cate;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public Double getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Double shopPrice) {
        this.shopPrice = shopPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBigCateId() {
        return bigCateId;
    }

    public void setBigCateId(Integer bigCateId) {
        this.bigCateId = bigCateId;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }
}