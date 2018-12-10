package com.hello.eshop.service;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.SearchItem;
import com.hello.eshop.utils.Result;

public interface SearchService {

	/**
	 * 根据id删除索引
	 */
	void deleteDocument(String id);

	/**
	 * 将所有商品导入索引库
	 * @return
	 */
	Result importAllItems();
	
	/**
	 * 以关键词查询商品,其余过滤
	 */
	PageInfo<SearchItem> search(String keyword, String cataName, String price, String sort, Integer page, Integer rows) throws Exception;

}
