package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {

	/**
	 * 简单连接
	 * @throws Exception
	 */
	@Test
	public void testJedis() throws Exception {
		// 创建jedis对象
		Jedis jedis = new Jedis("192.168.25.128", 6379);
		// 操作数据库
		jedis.set("jedis", "ready");
		String string = jedis.get("jedis");
		System.out.println(string);
		// 关闭
		jedis.close();
	}

	/**
	 * 连接池测试
	 * @throws Exception
	 */
	@Test
	public void testJedisPool() throws Exception {
		// 创建数据库连接池
		JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
		// 获得连接
		Jedis jedis = jedisPool.getResource();
		// 操作数据库
		String string = jedis.get("jedis");
		System.out.println(string);
		// 关闭连接
		jedis.close();
		// 系统关闭前关闭连接池
		jedisPool.close();
	}

	/**
	 * 集群测试
	 * @throws Exception
	 */
	@Test
	public void testJedisCluster() throws Exception {
		// 创建一个JedisCluster对象
		Set<HostAndPort> nodes = new HashSet<>();
		// 配置节点
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 操作redis
		jedisCluster.set("cluster", "hello redis cluster");
		String string = jedisCluster.get("cluster");
		System.out.println(string);
		// 系统关闭前关闭JedisCluster
		jedisCluster.close();
	}
}
