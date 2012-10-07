package com.component.common.util;

import java.nio.charset.Charset;

/**
 * �ַ����볣���ӿ�
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 ����6:28:00
 */
public interface CharsetConstants {
	/**
	 * Java�ļ�(*.java) Ĭ�ϱ���
	 */
	String JAVA_DEFAULT_CHARSET = "GBK";

	/**
	 * JSP�ļ�(*.jsp) Ĭ�ϱ���
	 */
	String JSP_DEFAULT_CHARSET = "UTF-8";

	/**
	 * JVM Ĭ�ϱ���
	 */
	String JVM_DEFAULT_CHARSET = Charset.defaultCharset().name();

	/**
	 * 7λASCII�ַ���Ҳ����ISO646-US��Unicode�ַ����Ļ���������
	 */
	String US_ASCII = "US-ASCII";

	/**
	 * ISO ������ĸ�� No.1��Ҳ���� ISO-LATIN-1
	 */
	String ISO_8859_1 = "ISO-8859-1";

	/**
	 * 8 λ UCS ת����ʽ
	 */
	String UTF_8 = "UTF-8";

	/**
	 * 16 λ UCS ת����ʽ��Big Endian����͵�ַ��Ÿ�λ�ֽڣ��ֽ�˳��
	 */

	String UTF_16BE = "UTF-16BE";

	/**
	 * 16 λ UCS ת����ʽ��Little-endian����ߵ�ַ��ŵ�λ�ֽڣ��ֽ�˳��
	 */
	String UTF_16LE = "UTF-16LE";

	/**
	 * 16 λ UCS ת����ʽ���ֽ�˳���ɿ�ѡ���ֽ�˳��������ʶ
	 */
	String UTF_16 = "UTF-16";

	/**
	 */
	String GB2312 = "GB2312";

	/**
	 * ���ĳ����ַ���
	 */
	String GBK = "GBK";

}
