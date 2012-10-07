package com.component.common.util;

import java.nio.charset.Charset;

/**
 * 字符编码常量接口
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 下午6:28:00
 */
public interface CharsetConstants {
	/**
	 * Java文件(*.java) 默认编码
	 */
	String JAVA_DEFAULT_CHARSET = "GBK";

	/**
	 * JSP文件(*.jsp) 默认编码
	 */
	String JSP_DEFAULT_CHARSET = "UTF-8";

	/**
	 * JVM 默认编码
	 */
	String JVM_DEFAULT_CHARSET = Charset.defaultCharset().name();

	/**
	 * 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
	 */
	String US_ASCII = "US-ASCII";

	/**
	 * ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
	 */
	String ISO_8859_1 = "ISO-8859-1";

	/**
	 * 8 位 UCS 转换格式
	 */
	String UTF_8 = "UTF-8";

	/**
	 * 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
	 */

	String UTF_16BE = "UTF-16BE";

	/**
	 * 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
	 */
	String UTF_16LE = "UTF-16LE";

	/**
	 * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
	 */
	String UTF_16 = "UTF-16";

	/**
	 */
	String GB2312 = "GB2312";

	/**
	 * 中文超大字符集
	 */
	String GBK = "GBK";

}
