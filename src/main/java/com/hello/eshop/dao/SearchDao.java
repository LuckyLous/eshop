package com.hello.eshop.dao;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.SearchItem;
import com.hello.eshop.utils.ListUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索dao
 * <p>Title: SearchDao</p>
 */
@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;

	/**
	 * 根据查询条件查询索引库
	 * @param query
	 * @return
	 */
	public PageInfo<SearchItem> search(SolrQuery query) throws Exception {
		//根据query查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		//取查询结果。
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//取查询结果总记录数
		/*long numFound = solrDocumentList.getNumFound();*/
		//取商品列表，需要取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setAuthor((String) solrDocument.get("book_author"));
			item.setPress((String) solrDocument.get("press"));
			item.setShopPrice(Double.parseDouble(solrDocument.get("shop_price")+""));
			item.setImage((String) solrDocument.get("image"));
			item.setCateName((String) solrDocument.get("cate_name"));
			//取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("prod_name");
			String prodName = "";
			if (ListUtils.isNotEmpty(list)) {
				prodName = list.get(0);
			} else {
				prodName = (String) solrDocument.get("prod_name");
			}
			item.setProdName(prodName);
			//添加到商品列表
			itemList.add(item);
		}

		//result.setItemList(itemList);

		// 交给PageInfo处理
		PageInfo<SearchItem> pageInfo = new PageInfo<SearchItem>(itemList,5);
		//返回结果
		return pageInfo;
	}
	
}
