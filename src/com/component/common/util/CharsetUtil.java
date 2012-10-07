package com.component.common.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * �ַ����������
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 ����6:28:00
 */
public class CharsetUtil implements CharsetConstants, Serializable {
	private CharsetUtil() {
	}

	/**
	 * ���к�
	 */
	private static final long serialVersionUID = 7058686298008510378L;

	private static final Logger logger = Logger.getLogger(CharsetUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static CharsetUtil instance;

	public static CharsetUtil getInstance() {
		if (instance == null) {
			synchronized (CharsetUtil.class) {
				if (instance == null) {
					instance = new CharsetUtil();
				}
			}
		}
		return instance;
	}

	// ///////////////////////////////////

	static {
		if (logger.isInfoEnabled()) {
			logger.info("---jvm default charset =["
					+ Charset.defaultCharset().name() + "]---");
		}
	}

	/**
	 * ���ַ�����ת����US-ASCII��
	 */
	public static String toASCII(String str) {
		return changeCharset(str, US_ASCII);
	}

	/**
	 * ���ַ�����ת����ISO-8859-1��
	 */
	public static String toISO_8859_1(String str) {
		return changeCharset(str, ISO_8859_1);
	}

	/**
	 * ���ַ�����ת����UTF-8��
	 */
	public static String toUTF_8(String str) {
		return changeCharset(str, UTF_8);
	}

	/**
	 * ���ַ�����ת����UTF-16BE��
	 */
	public static String toUTF_16BE(String str) {
		return changeCharset(str, UTF_16BE);
	}

	/**
	 * ���ַ�����ת����UTF-16LE��
	 */
	public static String toUTF_16LE(String str) {
		return changeCharset(str, UTF_16LE);
	}

	/**
	 * ���ַ�����ת����UTF-16��
	 */
	public static String toUTF_16(String str) {
		return changeCharset(str, UTF_16);
	}

	/**
	 * ���ַ�����ת����GBK��
	 */
	public static String toGBK(String str) {
		return changeCharset(str, GBK);
	}

	/**
	 * Ĭ�ϱ���ת��
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-9 ����3:13:00
	 */
	public static String changeCharset(String value) {
		if (Validator.isEmpty(value)) {
			return "";
		}
		// ȡֵ����
		String getEncode = getEncoding(value);
		// ��Ҫת���ı���
		String setEncode = JVM_DEFAULT_CHARSET;
		if(hasEncode(value)){
			try {
				setEncode = getEncoding(new String(value.getBytes(getEncode), JVM_DEFAULT_CHARSET));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}else{
			setEncode = getEncode;
			getEncode = JVM_DEFAULT_CHARSET;
		}
		if(!getEncode.equalsIgnoreCase(setEncode)){
			value = changeCharset(value, getEncode, setEncode);
		}
		return value;
	}

	/**
	 * �ַ�������ת����ʵ�ַ���
	 * 
	 * @param value
	 * @param setEncode
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 ����5:02:10
	 */
	public static String changeCharset(String value, String setEncode) {
		return changeCharset(value, JVM_DEFAULT_CHARSET, setEncode);
	}

	/**
	 * �ַ�������ת����ʵ�ַ���
	 * 
	 * @param value
	 *            ��ת��������ַ���
	 * @param getEncode
	 *            ԭ����
	 * @param setEncode
	 *            Ŀ�����
	 * @return
	 */
	public static String changeCharset(String value, String getEncode,
			String setEncode) {
		if (Validator.isEmpty(value) || Validator.isEmpty(getEncode)
				|| Validator.isEmpty(setEncode)) {
			return "";
		}
		String newValue = null;
		try {
			newValue = new String(value.getBytes(getEncode), setEncode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		if (logger.isInfoEnabled()) {
			logger.info("---value[" + value + "]---getEncode[" + getEncode
					+ "]---setEncode[" + setEncode + "]---newValue[" + newValue
					+ "]---");
		}
		return newValue;
	}

	/**
	 * 
	 * java���ļ����д�ֵʱ��Ĭ��ת��
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 ����5:03:30
	 */
	public static String javaDefaultChangeCharset(String value) {
		return changeCharset(value, JAVA_DEFAULT_CHARSET, JAVA_DEFAULT_CHARSET);
	}

	/**
	 * ��jspҳ�����java����ʱ��Ĭ��ת��
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 ����5:00:10
	 */
	public static String jspDefaultChangeCharset(String value) {
		return changeCharset(value, ISO_8859_1, JSP_DEFAULT_CHARSET);
	}

	/**
	 * ��ȡ�ַ����ı���
	 */
	public static String getEncoding(String value) {
		if (Validator.isEmpty(value)) {
			return null;
		}
		String encode = null;

		encode = GB2312;
		try {
			if (value.equals(new String(value.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}

		encode = ISO_8859_1;
		try {
			if (value.equals(new String(value.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}

		encode = UTF_8;
		try {
			if (value.equals(new String(value.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}

		encode = GBK;
		try {
			if (value.equals(new String(value.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}

		encode = null;
		if (logger.isInfoEnabled()) {
			logger.info("---����ϵͳ֧�ֵ����б���---");
		}
		// ��ȡϵͳ֧�ֵ����� ����
		Map<String, Charset> charsetMap = Charset.availableCharsets();
		for (Entry<String, Charset> entry : charsetMap.entrySet()) {
			if (logger.isInfoEnabled()) {
				logger.info("---����ϵͳ֧�ֵ����б���[" + entry.getKey() + "]---");
			}
			try {
				if (value.equals(new String(value.getBytes(entry.getKey()),
						entry.getKey()))) {
					encode = entry.getKey();
					break;
				}
			} catch (Exception exception) {
			}
		}
		return encode;
	}

	/**
	 * 
	 * �ж��ַ����Ƿ��������
	 * 
	 * ��getBytes(encoding)�������ַ�����һ��byte����
	 * 
	 * ��b[0]Ϊ 63ʱ��Ӧ����ת�����
	 * 
	 * A��������ĺ����ַ�����
	 * 
	 * 1��encoding��GB2312ʱ��ÿbyte�Ǹ�����
	 * 
	 * 2��encoding��ISO8859_1ʱ��b[i]ȫ��63��
	 * 
	 * B������ĺ����ַ�����
	 * 
	 * 1��encoding��ISO8859_1ʱ��ÿbyteҲ�Ǹ�����
	 * 
	 * 2��encoding��GB2312ʱ��b[i]�󲿷���63��
	 * 
	 * C��Ӣ���ַ���
	 * 
	 * 1��encoding��ISO8859_1��GB2312ʱ��ÿbyte������0��
	 * 
	 * <p/>
	 * 
	 * �ܽ᣺����һ���ַ�������getBytes("iso8859_1")
	 * 
	 * 1�����b[i]��63������ת�룻 A-2
	 * 
	 * 2�����b[i]ȫ����0����ôΪӢ���ַ���������ת�룻 B-1
	 * 
	 * 3�����b[i]��С��0�ģ���ô�Ѿ����룬Ҫת�롣 C-1
	 */
	public static Boolean hasEncode(String value) {
		if (value == null) {
			return null;
		}
		Boolean hasEncode = false;
		byte b[];
		try {
			b = value.getBytes("ISO-8859-1");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 63) {
					break; // 1
				} else if (b1 > 0) {
					continue;// 2
				} else if (b1 < 0) { // ������Ϊ0��0Ϊ�ַ���������
					hasEncode = Boolean.TRUE;
					// retStr = new String(b, "GB2312");
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return hasEncode;
	}
	
	/**
	 * ����Mapת��
	 * 
	 * @param parameterMap
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 ����5:44:03
	 */
	public static Map<String, String[]> changeCharsetParameterMap(
			Map<String, String[]> parameterMap) {
		if (Validator.isEmpty(parameterMap)) {
			return parameterMap;
		}
		for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = iterator.next();
			String[] value = parameterMap.get(key);
			for (int i = 0; i < value.length; i++) {
				if (logger.isInfoEnabled()) {
					logger.info("---ת��֮ǰ[" + key + "=" + value[i] + "]---");
				}
				value[i] = CharsetUtil.changeCharset(value[i]);
				if (logger.isInfoEnabled()) {
					logger.info("---ת��֮��[" + key + "=" + value[i] + "]---");
				}
			}
		}
		return parameterMap;
	}
}
