package com.hello.eshop.test;

import com.hello.eshop.utils.IDUtils;

public class IDTest {
	public static void main(String[] args) {
		
		System.out.println(IDUtils.getID());
		for(int i=0;i< 5;i++)
			System.out.println(IDUtils.genItemId());
	}
}
