package com.component.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ������������
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-7-30 ����3:58:02
 */
public class Base64 {

	/**
	 * ��int�ͱ���Ϊchar��
	 * 
	 * @param sixbit
	 *            Դint������
	 * @return ������char������
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
	 * ��char�����ݱ���Ϊint��
	 * 
	 * @param c
	 *            Դchar������
	 * @return ������int������
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
	 * ��byte�ַ�������Base64���б���
	 * 
	 * @param raw
	 *            byte����������
	 * @return ������String�ַ���
	 */
	public static String encode(byte raw[]) {
		StringBuffer encoded = new StringBuffer();

		for (int i = 0; i < raw.length; i += 3) {
			encoded.append(encodeBlock(raw, i));
		}

		return encoded.toString();
	}

	/**
	 * ��byte���������ݣ�����ָ��ƫ��������Ϊchar����������
	 * 
	 * @param raw
	 *            Դbyte����������
	 * @param offset
	 *            ָ��ƫ����
	 * @return ������char��������
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
	 * ��String�ַ���������Ϊbyte��������
	 * 
	 * @param base64
	 *            ԴString�ַ���
	 * @return ��������byte����
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
	 * ��object�����������������������ת��ΪString����
	 * 
	 * @param o
	 *            object����
	 * @return ����õ���String�ַ���
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
	 * ��String���󾭹�����ת��ΪObject����
	 * 
	 * @param s
	 *            ԴString�ַ���
	 * @return ����
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