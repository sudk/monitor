package com.exception;

import org.apache.log4j.Logger;

/**
 * ����ʱ�쳣����
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 ����4:06:00
 */
public class BaseRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 7963620858029901200L;
	private static final Logger logger = Logger
			.getLogger(BaseRuntimeException.class);

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// ////////////////////////

	public BaseRuntimeException() {
		super();
	}

	public BaseRuntimeException(String message) {
		super(message);
	}

	public BaseRuntimeException(Throwable t) {
		super(t);
	}

	public BaseRuntimeException(String message, Throwable t) {
		super(message, t);
	}
}
