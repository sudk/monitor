package com.component.common.util;

import com.component.util.Base64;

/**
 * ���������
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-7-30 ����3:59:48
 */
public class PasswordUtil {
	/**
	 * ���ܣ�������룩
	 * 
	 * @param decode
	 *            ԭ����
	 * @return ���ܺ������
	 */
	public static String encode(String decode) {
		String encode = "";
		if (decode == null || "".equalsIgnoreCase(decode.trim())) {
			return encode;
		}
		encode = Base64.encode(decode.getBytes());
		return encode;
	}

	/**
	 * ���ܣ���(��), ��(��)��
	 * 
	 * @param encode
	 *            ���ܵ�����
	 * @return ����֮���ԭ����
	 */
	public static String decode(String encode) {
		String decode = "";
		if (encode == null || "".equalsIgnoreCase(encode)) {
			return decode;
		}
		decode = new String(Base64.decode(encode));
		return decode;
	}
}