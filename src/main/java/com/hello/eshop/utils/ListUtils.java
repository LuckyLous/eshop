package com.hello.eshop.utils;

import java.util.List;

/**
 * 处理逆向工程产生的List，确定selectOne(只有一条数据)时，即可使用
 * @author Hello
 *
 */
public class ListUtils {
	
	/**
	 * 根据List拿到第一个元素
	 * @param list
	 * @return
	 */
	public static <T> T listToBean(List<T> list){
		if(isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 判断List是否不为空(包括引用和数量)
	 * @param list
	 * @return
	 */
	public static <T> Boolean isNotEmpty(List<T> list){
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断List是否为空(包括引用和数量)
	 * @param list
	 * @return
	 */
	public static <T> Boolean isEmpty(List<T> list){
		return !isNotEmpty(list);
	}
	
}
