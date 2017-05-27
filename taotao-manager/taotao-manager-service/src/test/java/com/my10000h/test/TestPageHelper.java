package com.my10000h.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {

	@Test
	public void testPageHelper() throws Exception {
		// 1.在mybatis配置文件中配置分页插件
		// 2.在执行查询语句之前配置分页条件
		PageHelper.startPage(1, 10);// 第1页，取10条
		// 3.执行查询
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		//查询所有记录
		List<TbItem> list = itemMapper.selectByExample(example);
		// 4.取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println("总记录数：" + pageInfo.getTotal());
		System.out.println("总页数：" + pageInfo.getPages());
		System.out.println("返回的记录数：" + list.size());
	}
}
