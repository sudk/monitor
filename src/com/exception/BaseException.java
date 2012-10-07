package com.exception;

import org.apache.log4j.Logger;

/**
 * 异常基类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 下午4:06:00
 */
public class BaseException extends Exception {
	private static final long serialVersionUID = 7963620858029901200L;
	private static final Logger logger = Logger.getLogger(BaseException.class);

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// ////////////////////////

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable t) {
		super(t);
	}

	public BaseException(String message, Throwable t) {
		super(message, t);
	}
}
