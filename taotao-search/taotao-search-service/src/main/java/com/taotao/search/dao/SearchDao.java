package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my10000h.common.pojo.SearchItem;
import com.my10000h.common.pojo.SearchResult;

/**
 * 搜索索引库商品dao
 * 
 * @author Administrator
 *
 */
@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;

	public SearchResult search(SolrQuery query) throws Exception {
		// 根据query对象进行查询
		QueryResponse response = solrServer.query(query);
		// 取查询结果
		SolrDocumentList results = response.getResults();
		// 取查询结果记录数
		long numFound = results.getNumFound();
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		List<SearchItem> itemList = new ArrayList<>();
		// 查询结果封装
		for (SolrDocument solrDocument : results) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
//			取一张图片
			String image = (String) solrDocument.get("item_image");
			if (StringUtils.isNotBlank(image)) {
				image = image.split(",")[0];
			}
			item.setImage(image);
			item.setPrice((Long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
//			取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list != null && list.size() > 0) {
				title = list.get(0);
			}else {
				title = (String) solrDocument.get("item_title");
			}
			
			item.setTitle(title);
			itemList.add(item);
		}
		result.setItemList(itemList);
		// 返回
		return result;
	}
}
