package com.hello.eshop.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class SolrJTest {
	
	private ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
	
	public static void main(String[] args) {
		try {
//			SolrJTest solrJTest = new SolrJTest();
//			solrJTest.deleteDocument();
//			solrJTest.queryComplexIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 // 添加document到索引库
	//@Test
	public void addDocument() throws Exception{
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = (SolrServer) ac.getBean("httpSolrServer");
		//创建一个文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义。
		document.addField("id", "doc01");
		document.addField("prod_name", "测试商品01");
		document.addField("shop_price", 30.50);
		document.addField("prod_desc", "销售记录超过一亿册的侦探小说 被改编为二十部影视剧 同名话剧七十年常演不衰");
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	// 从索引库中删除document
	public void deleteDocument() throws Exception{
		SolrServer solrServer = (SolrServer) ac.getBean("httpSolrServer");
		//删除文档
//		solrServer.deleteById("doc01");
		solrServer.deleteByQuery("*:*");
		//提交
		solrServer.commit();
	}
	
	// 从索引库中查询document
	public void queryIndex() throws Exception {
		//创建一个SolrServer对象。
		SolrServer solrServer = (SolrServer) ac.getBean("httpSolrServer");
		//创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		//设置查询条件。
		//query.setQuery("*:*");
		query.set("q", "*:*");
		//执行查询，QueryResponse对象。
		QueryResponse response = solrServer.query(query);
		//取文档列表。取查询结果的总记录数
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("prod_name"));
			System.out.println(solrDocument.get("book_author"));
			System.out.println(solrDocument.get("press"));
			System.out.println(solrDocument.get("shop_price"));
			System.out.println(solrDocument.get("image"));
			System.out.println(solrDocument.get("cate_name"));
		}
	}
	
	// 从索引库中复杂查询document
	public void queryComplexIndex() throws Exception {
		//创建一个SolrServer对象。
		SolrServer solrServer = (SolrServer) ac.getBean("httpSolrServer");
		//创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		//设置查询条件。
		query.setQuery("出版社");
		//设置查询条件
		query.set("fq", "shop_price:[" + 20 + " TO " + 100 + "]");
		query.addSort("shop_price", ORDER.desc);
		
		query.setStart(0);
		query.setRows(20);
		query.set("df", "prod_keywords");
		query.setHighlight(true);
		query.addHighlightField("prod_name");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//执行查询，QueryResponse对象。
		QueryResponse response = solrServer.query(query);
		//取文档列表。取查询结果的总记录数
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		//遍历文档列表，从取域的内容。
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			
			//取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("prod_name");
			// 如果是复制域查询出来，这里有可能为空
			String prod_name = "";
			if(list != null && list.size() > 0){
				prod_name = list.get(0);
			}else{// 为空，则不带高亮的正常查名称
				prod_name = (String) solrDocument.get("prod_name");
			}
			System.out.println(prod_name);
			
			System.out.println(solrDocument.get("book_author"));
			System.out.println(solrDocument.get("press"));
			System.out.println(solrDocument.get("shop_price"));
			System.out.println(solrDocument.get("image"));
			System.out.println(solrDocument.get("cate_name"));
		}
	}
}
