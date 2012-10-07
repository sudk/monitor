package com.component.common.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * 字符编码帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 下午6:28:00
 */
public class CharsetUtil implements CharsetConstants, Serializable {
	private CharsetUtil() {
	}

	/**
	 * 序列号
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
	 * 将字符编码转换成US-ASCII码
	 */
	public static String toASCII(String str) {
		return changeCharset(str, US_ASCII);
	}

	/**
	 * 将字符编码转换成ISO-8859-1码
	 */
	public static String toISO_8859_1(String str) {
		return changeCharset(str, ISO_8859_1);
	}

	/**
	 * 将字符编码转换成UTF-8码
	 */
	public static String toUTF_8(String str) {
		return changeCharset(str, UTF_8);
	}

	/**
	 * 将字符编码转换成UTF-16BE码
	 */
	public static String toUTF_16BE(String str) {
		return changeCharset(str, UTF_16BE);
	}

	/**
	 * 将字符编码转换成UTF-16LE码
	 */
	public static String toUTF_16LE(String str) {
		return changeCharset(str, UTF_16LE);
	}

	/**
	 * 将字符编码转换成UTF-16码
	 */
	public static String toUTF_16(String str) {
		return changeCharset(str, UTF_16);
	}

	/**
	 * 将字符编码转换成GBK码
	 */
	public static String toGBK(String str) {
		return changeCharset(str, GBK);
	}

	/**
	 * 默认编码转换
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-9 下午3:13:00
	 */
	public static String changeCharset(String value) {
		if (Validator.isEmpty(value)) {
			return "";
		}
		// 取值编码
		String getEncode = getEncoding(value);
		// 需要转换的编码
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
	 * 字符串编码转换的实现方法
	 * 
	 * @param value
	 * @param setEncode
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 下午5:02:10
	 */
	public static String changeCharset(String value, String setEncode) {
		return changeCharset(value, JVM_DEFAULT_CHARSET, setEncode);
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param value
	 *            待转换编码的字符串
	 * @param getEncode
	 *            原编码
	 * @param setEncode
	 *            目标编码
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
	 * java类文件进行传值时，默认转码
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 下午5:03:30
	 */
	public static String javaDefaultChangeCharset(String value) {
		return changeCharset(value, JAVA_DEFAULT_CHARSET, JAVA_DEFAULT_CHARSET);
	}

	/**
	 * 由jsp页面进入java代码时，默认转码
	 * 
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 下午5:00:10
	 */
	public static String jspDefaultChangeCharset(String value) {
		return changeCharset(value, ISO_8859_1, JSP_DEFAULT_CHARSET);
	}

	/**
	 * 获取字符串的编码
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
			logger.info("---遍历系统支持的所有编码---");
		}
		// 获取系统支持的所有 编码
		Map<String, Charset> charsetMap = Charset.availableCharsets();
		for (Entry<String, Charset> entry : charsetMap.entrySet()) {
			if (logger.isInfoEnabled()) {
				logger.info("---遍历系统支持的所有编码[" + entry.getKey() + "]---");
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
	 * 判断字符串是否存在乱码
	 * 
	 * 用getBytes(encoding)：返回字符串的一个byte数组
	 * 
	 * 当b[0]为 63时，应该是转码错误
	 * 
	 * A、不乱码的汉字字符串：
	 * 
	 * 1、encoding用GB2312时，每byte是负数；
	 * 
	 * 2、encoding用ISO8859_1时，b[i]全是63。
	 * 
	 * B、乱码的汉字字符串：
	 * 
	 * 1、encoding用ISO8859_1时，每byte也是负数；
	 * 
	 * 2、encoding用GB2312时，b[i]大部分是63。
	 * 
	 * C、英文字符串
	 * 
	 * 1、encoding用ISO8859_1和GB2312时，每byte都大于0；
	 * 
	 * <p/>
	 * 
	 * 总结：给定一个字符串，用getBytes("iso8859_1")
	 * 
	 * 1、如果b[i]有63，不用转码； A-2
	 * 
	 * 2、如果b[i]全大于0，那么为英文字符串，不用转码； B-1
	 * 
	 * 3、如果b[i]有小于0的，那么已经乱码，要转码。 C-1
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
				} else if (b1 < 0) { // 不可能为0，0为字符串结束符
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
	 * 参数Map转码
	 * 
	 * @param parameterMap
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 下午5:44:03
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
					logger.info("---转码之前[" + key + "=" + value[i] + "]---");
				}
				value[i] = CharsetUtil.changeCharset(value[i]);
				if (logger.isInfoEnabled()) {
					logger.info("---转码之后[" + key + "=" + value[i] + "]---");
				}
			}
		}
		return parameterMap;
	}
}
