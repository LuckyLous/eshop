package com.hello.eshop.dao;

import java.util.List;

import com.hello.eshop.bean.SearchItem;

public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(Integer itemId);
}
