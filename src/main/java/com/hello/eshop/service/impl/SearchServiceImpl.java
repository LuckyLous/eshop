package com.hello.eshop.service.impl;


import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.SearchItem;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.dao.ItemMapper;
import com.hello.eshop.dao.SearchDao;
import com.hello.eshop.exception.MessageException;
import com.hello.eshop.service.SearchService;
import com.hello.eshop.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 索引库Service
 * 商品搜索
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Autowired
	private SearchDao searchDao;

	/**
	 * 根据id删除索引
	 * @param id
	 */
	public void deleteDocument(String id){
		try {
			//删除文档
			solrServer.deleteById(id);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			throw new MessageException("删除索引失败!");
		}
	}

	/**
	 * 将所有商品导入索引库
	 * @return
	 */
	@Override
	public Result importAllItems() {
		try {
			// 清空索引
			solrServer.deleteByQuery("*:*");

			//查询商品列表(多表查询，不能用逆向工程，要自己写sql)
			List<SearchItem> itemList = itemMapper.getItemList();
			//遍历商品列表
			for (SearchItem searchItem : itemList) {
				//创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				//向文档对象中添加域
				document.addField("id", searchItem.getId());
				document.addField("prod_name", searchItem.getProdName());
				document.addField("book_author", searchItem.getAuthor());
				document.addField("press", searchItem.getPress());
				document.addField("shop_price", searchItem.getShopPrice());
				document.addField("image", searchItem.getImage());
				document.addField("cate_name", searchItem.getCateName());
				document.addField("prod_desc", searchItem.getDescription());
				//把文档对象写入索引库
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
			//返回导入成功
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			Result result = Result.fail();
			result.setMsg("数据导入时发生异常");
			return result;

		}
	}
	
	/**
	 * 以关键词查询商品,其余过滤
	 * 查询 关键词  过滤条件
	 * 价格排序 分页 开始行 每页数 高亮 默认域 只查询指定域
	 */
	@Override
	public PageInfo<SearchItem> search(String keyword, String cateName, String price, String sort,
			Integer page, Integer rows) throws Exception {
		//创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(keyword);
		//设置过滤条件
		if(StringUtils.isNotBlank(cateName)){
			query.set("fq", "cate_name:"+cateName);
		}
		if(StringUtils.isNotBlank(price)){
			String[] p = price.split("-");
			query.set("fq", "shop_price:["+p[0] + " TO " + p[1] +"]");
		}
		if(Constant.SEARCH_SORT_DESC.equals(sort)){
			query.addSort("shop_price", ORDER.desc);
		}else{
			query.addSort("shop_price", ORDER.asc);
		}
		//设置分页条件
		if (page <=0 ) page = 1;
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		//设置默认搜索域
		// 如果包含出版社，就更改默认域,不用ik复制域
		String regex = ".*[出版社]+";
		Matcher matcher = Pattern.compile(regex).matcher(keyword);

		if(matcher.matches()){
			query.set("df", "press");
		}else {
			query.set("df", "prod_keywords");

		}
		//只查询指定域
		query.set("fl", "id,prod_name,shop_price,image,cate_name,book_author,press");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("prod_name");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");

		//调用dao执行查询
		PageInfo<SearchItem> pageInfo = searchDao.search(query);
		//返回结果
		return pageInfo;
	}

}
