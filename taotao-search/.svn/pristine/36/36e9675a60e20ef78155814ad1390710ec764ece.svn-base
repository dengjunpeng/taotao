package com.taotao.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {
	@Test
	public void testAddDocument() throws Exception {
		// 创建连接SolrServer对象
		// 指定solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		// 创建文档对象solrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		// 向文档中添加域，必须要有id域，域名称必须在schame.xml中定义
		document.addField("id", "123");
		document.addField("item_title", "测试商品名称2");
		document.addField("item_price", 10000);
		// 把文档对象写入索引库
		solrServer.add(document);
		// 提交
		solrServer.commit();

	}

	@Test
	public void deleteDocumentById() throws Exception {

		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		solrServer.deleteById("test001");
		solrServer.commit();
	}

	@Test
	public void deleteDocumentByQuery() throws Exception {

		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		solrServer.deleteByQuery("id:123");
		solrServer.commit();
	}

	@Test
	public void searchDocument() throws Exception {
		// 创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		// 创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件，过滤条件，分页条件，排序条件，高亮条件
		// q-查询条件
		// query.set("q", "*:*");
		query.setQuery("手机");
		// 分页
		query.setStart(30);
		query.setRows(10);
		// 设置默认搜索域
		query.set("df", "item_keywords");
		// 设置高亮
		query.setHighlight(true);
		// 高亮显示的域
		query.addHighlightField("item_title");
		// 高亮前缀和高亮后缀
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		
		
		// 执行查询
		
		QueryResponse response = solrServer.query(query);
		
		// 取查询结果总记录数
		SolrDocumentList results = response.getResults();
		System.out.println("总记录数："+ results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			// 取高亮
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if(list != null && list.size() > 0) {
				itemTitle = list.get(0);
			}else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			System.out.println(itemTitle);
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println("================================");
		}
		
	}
}
