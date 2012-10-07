package com.component.common.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

/**
 * 
 * ��url��ذ�����
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-10 ����2:48:13
 */
public class URLUtil implements URLConstants, Serializable {
	private URLUtil() {
	}

	/**
	 * ���к�
	 */
	private static final long serialVersionUID = 7058686298008510378L;

	private static final Logger logger = Logger.getLogger(URLUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static URLUtil instance;

	public static URLUtil getInstance() {
		if (instance == null) {
			synchronized (URLUtil.class) {
				if (instance == null) {
					instance = new URLUtil();
				}
			}
		}
		return instance;
	}

	// ///////////////////////////////////

	/**
	 * ����url��ת����ַ���
	 * (�ο�����ࣺorg.apache.commons.lang.StringEscapeUtils)
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-10 ����2:50:34
	 */
	@SuppressWarnings("deprecation")
	public static String decode(String value) {
		if (Validator.isEmpty(value)) {
			return "";
		}
		String decodeValue = value;
		try {
			decodeValue = URLDecoder.decode(value,
//					CharsetUtil.getEncoding(URLDecoder.decode(URLDecoder.decode(value))));
					CharsetUtil.getEncoding(URLDecoder.decode(value)));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return decodeValue;
	}

	/**
	 * ����url��ת����ַ���
	 * 
	 * @param value
	 * @param encode
	 *            �����ַ�����
	 * @return
	 * @author LiuKun
	 * @created 2012-8-10 ����2:50:34
	 */
	public static String decode(String value, String encode) {
		if (Validator.isEmpty(value) || Validator.isEmpty(encode)) {
			return "";
		}
		String decodeValue = value;
		try {
			decodeValue = URLDecoder.decode(value, encode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return decodeValue;
	}
	
	/**
	 * ���ַ�ת��
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-10 ����3:10:33
	 */
	public static String encode(String value, String encode) {
		if (Validator.isEmpty(value) || Validator.isEmpty(encode)) {
			return "";
		}
		String decodeValue = value;
		try {
			decodeValue = URLEncoder.encode(value, encode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return decodeValue;
	}
	
	
	/**
	 * ���ַ�ת��
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-10 ����3:10:33
	 */
	@SuppressWarnings("deprecation")
	public static String encode(String value) {
		if (Validator.isEmpty(value)) {
			return "";
		}
		String decodeValue = URLEncoder.encode(value);
		return decodeValue;
	}

}
