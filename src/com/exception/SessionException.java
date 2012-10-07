package com.exception;

import org.apache.log4j.Logger;

/**
 * 
 * sessionʧЧ�쳣
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 ����4:09:59
 */
public class SessionException extends BaseRuntimeException {

	private static final long serialVersionUID = -1385119679333683700L;

	private static final Logger logger = Logger
			.getLogger(SessionException.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	// ////////////////////////////////////////

	public SessionException() {
		//super();
		this("û���û���¼��Ϣ�������µ�¼��");
	}

	public SessionException(String message) {
		super(message);
	}

	public SessionException(Throwable t) {
		super(t);
	}

	public SessionException(String message, Throwable t) {
		super(message, t);
	}
}
