package com.component.common.util;

import com.component.util.Base64;

/**
 * 密码帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-7-30 下午3:59:48
 */
public class PasswordUtil {
	/**
	 * 加密（译成密码）
	 * 
	 * @param decode
	 *            原密码
	 * @return 加密后的密码
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
	 * 解密（译(码), 解(码)）
	 * 
	 * @param encode
	 *            加密的密码
	 * @return 解密之后的原密码
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