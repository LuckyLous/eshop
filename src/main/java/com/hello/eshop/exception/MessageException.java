package com.hello.eshop.exception;

/**
 * 自定义异常类
 */
public class MessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MessageException(String msg) {
		super(msg);
	}
	
}
