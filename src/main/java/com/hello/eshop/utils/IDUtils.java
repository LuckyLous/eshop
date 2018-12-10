package com.hello.eshop.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 各种id生成策略的工具类
 */
public class IDUtils {
	
	/**
	 * 返回一个不重复的字符串:UUID
	 */
	public static String getID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	/**
	 * 返回一个不重复的激活码
	 */
	public static String getActiveCode() {
		return getID();
	}
	
	/**
	 * 商品id生成:时间
	 */
	public static String genItemId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return String.valueOf(id);
	}
	
	/**
	 * 图片名生成:时间
	 */
	public static String genImageName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		
		return str;
	}
	
}
