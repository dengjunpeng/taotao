package com.taotao.search.service.impl;

import org.apache.jute.RecordReader;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my10000h.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

/**
 * 搜索功能
 * 
 * @author Administrator
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		// 根据查询条件，拼装查询对象
		// 创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 分页条件
		if (page < 1) {
			page = 1;
		}
		query.setStart((page - 1) * rows);
		if (rows < 1) {
			rows = 10;
		}
		query.setRows(rows);
		// 默认搜索域
		query.set("df", "item_title");
		// 设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		// 调用dao执行查询
		SearchResult searchResult = searchDao.search(query);
		// 计算总页数
		long recordCount = searchResult.getRecordCount();
		long totalPages = recordCount / rows;
		if (recordCount % rows > 0) {
			totalPages++;
		}
		
		searchResult.setTotalPages((int) totalPages);
		// 返回结果
		return searchResult;
	}

}
