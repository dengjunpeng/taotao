package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public TaotaoResult addContent(TbContent content) {
		// 补全pojo属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 插入内容表
		contentMapper.insert(content);

		// 同步缓存
		// 删除对应的缓存信息
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentsByCid(long cid) {
		// 先查询缓存
		try {
			// 查询缓存
			String json = jedisClient.hget(INDEX_CONTENT, cid + "");
			// 查询到结果，将json装换成list返回
			if (StringUtils.isNotEmpty(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);

		// 添加缓存
		try {
			jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
