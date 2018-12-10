package com.hello.eshop.test;

import java.io.InputStream;

public class ResourceBundleTest {

	public static void main(String[] args) {
//		String merId = ResourceBundle.getBundle("conf/payment").getString("p8_Url");
//		System.out.println(merId);

		InputStream inp = ResourceBundleTest.class.getClassLoader().getResourceAsStream("template/"+"prodExporTemplate.xls");
		System.out.println(inp);
	}

}
