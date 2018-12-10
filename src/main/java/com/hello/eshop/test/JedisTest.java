package com.hello.eshop.test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.0.112", 6379);
//		jedis.auth("123456");
//		jedis.set("helloJedis", "jedis");
		String value = jedis.get("SHOP_CATEGORY_LIST");
		System.out.println(value);
		jedis.close();
	}
}
