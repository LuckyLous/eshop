package com.hello.eshop.bean;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private Integer id;

    @NotEmpty(message = "分类名称不能为空")
    private String name;

    private Integer parentId;

    private Integer isParent;

    private String description;

    private Integer sortOrder;

    // 存储对应的父分类
    private Category pCate;
    // 存储对应子类(二级分类)
    private List<Category> cList;

    public Category getpCate() {
        return pCate;
    }
    public void setpCate(Category pCate) {
        this.pCate = pCate;
    }

    public List<Category> getcList() {
		return cList;
	}
	public void setcList(List<Category> cList) {
		this.cList = cList;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}