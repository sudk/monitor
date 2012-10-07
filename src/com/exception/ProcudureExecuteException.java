package com.exception;

/**
 * 
 * 
 * Description: �洢�����쳣����
 * 
 * @author guo_lei
 * @version 1.0
 * @created 2012-8-3 ����4:15:12
 */
public class ProcudureExecuteException extends Exception {

	/** ���� */

	private static final long serialVersionUID = -4796847903458495614L;

	private static final String MESSAGE = "�洢����ִ��ʧ�ܣ�";

	private Throwable cause = null;

	public ProcudureExecuteException() {
		super(MESSAGE);
	}

	public ProcudureExecuteException(String msg) {
		super(msg);
	}

	public ProcudureExecuteException(String msg, Throwable cause) {
		super(msg);
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getMessage() {
		if (super.getMessage() != null) {
			return super.getMessage();
		} else if (cause != null) {
			return cause.toString();
		} else {
			return null;
		}
	}

}