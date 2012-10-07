package com.component.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 密码加密与解密
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-7-30 下午3:58:02
 */
public class Base64 {

	/**
	 * 将int型编码为char型
	 * 
	 * @param sixbit
	 *            源int型数据
	 * @return 编码后的char型数据
	 */
	protected static char getChar(int sixbit) {
		if (sixbit >= 0 && sixbit <= 25) {
			return (char) (65 + sixbit);
		}

		if (sixbit >= 26 && sixbit <= 51) {
			return (char) (97 + (sixbit - 26));
		}

		if (sixbit >= 52 && sixbit <= 61) {
			return (char) (48 + (sixbit - 52));
		}

		if (sixbit == 62) {
			return '+';
		}

		return sixbit != 63 ? '?' : '/';
	}

	/**
	 * 将char型数据编码为int型
	 * 
	 * @param c
	 *            源char型数据
	 * @return 编码后的int型数据
	 */
	protected static int getValue(char c) {
		if (c >= 'A' && c <= 'Z') {
			return c - 65;
		}

		if (c >= 'a' && c <= 'z') {
			return (c - 97) + 26;
		}

		if (c >= '0' && c <= '9') {
			return (c - 48) + 52;
		}

		if (c == '+') {
			return 62;
		}

		if (c == '/') {
			return 63;
		}

		return c != '=' ? -1 : 0;
	}

	/**
	 * 对byte字符数组用Base64进行编码
	 * 
	 * @param raw
	 *            byte数组型数据
	 * @return 编码后的String字符串
	 */
	public static String encode(byte raw[]) {
		StringBuffer encoded = new StringBuffer();

		for (int i = 0; i < raw.length; i += 3) {
			encoded.append(encodeBlock(raw, i));
		}

		return encoded.toString();
	}

	/**
	 * 将byte数组型数据，按照指定偏移量编码为char数组型数据
	 * 
	 * @param raw
	 *            源byte数组型数据
	 * @param offset
	 *            指定偏移量
	 * @return 编码后的char数组数据
	 */
	protected static char[] encodeBlock(byte raw[], int offset) {
		int block = 0;
		int slack = raw.length - offset - 1;
		int end = slack < 2 ? slack : 2;

		for (int i = 0; i <= end; i++) {
			byte b = raw[offset + i];

			int neuter = b >= 0 ? ((int) (b)) : b + 256;
			block += neuter << 8 * (2 - i);
		}

		char base64[] = new char[4];

		for (int i = 0; i < 4; i++) {
			int sixbit = block >>> 6 * (3 - i) & 0x3f;
			base64[i] = getChar(sixbit);
		}

		if (slack < 1) {
			base64[2] = '=';
		}

		if (slack < 2) {
			base64[3] = '=';
		}

		return base64;
	}

	/**
	 * 将String字符串解码码为byte数组数据
	 * 
	 * @param base64
	 *            源String字符串
	 * @return 解码码后的byte数组
	 */
	public static byte[] decode(String base64) {
		int pad = 0;

		for (int i = base64.length() - 1; base64.charAt(i) == '='; i--) {
			pad++;
		}

		int length = (base64.length() * 6) / 8 - pad;
		byte raw[] = new byte[length];
		int rawindex = 0;

		for (int i = 0; i < base64.length(); i += 4) {
			int block = (getValue(base64.charAt(i)) << 18)
					+ (getValue(base64.charAt(i + 1)) << 12)
					+ (getValue(base64.charAt(i + 2)) << 6)
					+ getValue(base64.charAt(i + 3));

			for (int j = 0; j < 3 && rawindex + j < raw.length; j++) {
				raw[rawindex + j] = (byte) (block >> 8 * (2 - j) & 0xff);
			}

			rawindex += 3;
		}

		return raw;
	}

	/**
	 * 将object对象用流读出，经过解码后转换为String对象
	 * 
	 * @param o
	 *            object对象
	 * @return 解码得到的String字符串
	 */
	public static String objectToString(Object o) {
		if (o == null) {
			return null;
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream(32000);

		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new BufferedOutputStream(baos));
			os.flush();
			os.writeObject(o);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encode(baos.toByteArray());
	}

	/**
	 * 将String对象经过编码转换为Object对象
	 * 
	 * @param s
	 *            源String字符串
	 * @return 编码
	 */
	public static Object stringToObject(String s) {
		if (s == null) {
			return null;
		}

		byte byteArray[] = decode(s);

		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

		try {
			ObjectInputStream is = new ObjectInputStream(
					new BufferedInputStream(bais));

			return is.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		String encode = Base64.encode("abcde".getBytes());
		System.out.println(encode);
		String decode = new String(Base64.decode(encode));
		System.out.println(decode);
	}

}