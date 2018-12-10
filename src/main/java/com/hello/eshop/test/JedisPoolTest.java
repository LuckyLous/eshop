package com.hello.eshop.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {
	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);// 设置最大连接数
		config.setMaxIdle(10);// 设置最大空闲连接数
		
		JedisPool jedisPool = new JedisPool(config, "192.168.0.112", 6379);
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
//				jedis.auth("123456");
//				jedis.set("helloPool", "JedisPool");
				String value = jedis.get("SHOP_CATEGORY_LIST");
				System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
			if (jedisPool != null) {
				jedisPool.close();
			}
		}
		
		
	}
}
