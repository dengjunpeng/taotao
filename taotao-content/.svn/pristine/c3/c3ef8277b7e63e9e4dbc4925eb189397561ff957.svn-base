package com.taotao.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJedisSpring {

	@Test
	public void testJedisSpring() throws Exception{
		//初始化Spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		
		//从容器中获取的JedisClient对象
		JedisClient jedisClient = context.getBean(JedisClient.class);
		//操作数据库
		jedisClient.set("spring", "hello redis spring");
		String string = jedisClient.get("spring");
		System.out.println(string);
		
	}
}
