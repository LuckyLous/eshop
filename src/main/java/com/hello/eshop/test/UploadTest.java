package com.hello.eshop.test;

import com.hello.eshop.utils.UploadUtils;

public class UploadTest {
	public static void main(String[] args) {
		String fileName = "1.jpg";
		System.out.println(UploadUtils.getRealName(fileName));
		System.out.println(UploadUtils.getImageName(fileName));
		System.out.println(UploadUtils.getDir());
	}
}
