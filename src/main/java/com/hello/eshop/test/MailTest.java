package com.hello.eshop.test;

import javax.mail.Session;

import com.hello.eshop.utils.Mail;
import com.hello.eshop.utils.MailUtils;

public class MailTest {

	public static void main(String[] args) {
		Session session = MailUtils.createSession("smtp.163.com", "alittlegoods@163.com", "hello163");
		Mail mail = new Mail("alittlegoods@163.com","347129306@qq.com", "还是测试邮件", "这是正文，不是垃圾邮件！");
		try {
			MailUtils.send(session, mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
