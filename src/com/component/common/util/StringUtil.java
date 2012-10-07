package com.component.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * �ַ���������
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 ����10:16:12
 */
public class StringUtil {
	private static final Logger logger = Logger.getLogger(StringUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	/**
	 * �ַ�������ָ���ָ�������Ϊ�ַ���
	 * 
	 * @param seperator
	 *            �ָ���
	 * @param strings
	 *            �ַ���
	 * @return
	 */
	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = new StringBuffer(length * strings[0].length())
				.append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	/**
	 * ��Iterator������ָ���ָ������ӳ��ַ���
	 * 
	 * @param seperator
	 *            ָ���ָ���
	 * @param objects
	 *            ��Ҫ���ӵ�Iterator����
	 * @return
	 */
	public static String join(String seperator, Iterator<String> objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append(objects.next());
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}

	/**
	 * ��Դ�ִ���׷���ַ������á�����Ϊ���
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param add
	 *            ׷�ӵ��ַ���
	 * @return ׷�Ӻ�����ַ���
	 */
	public static String add(String s, String add) {
		return add(s, add, StringPool.COMMA);
	}

	/**
	 * ��Դ�ִ���׷���ַ�������ָ����������
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param add
	 *            ׷�ӵ��ַ���
	 * @param delimiter
	 *            �����
	 * @return ׷�Ӻ�����ַ���
	 */
	public static String add(String s, String add, String delimiter) {
		return add(s, add, delimiter, false);
	}

	/**
	 * ��Դ�ִ���׷���ַ�������ָ���������� �� ���涨�˲������ظ��������׷�ӵ��ִ���������Դ�ִ������Ϲ���Ž���׷��
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param add
	 *            ׷�ӵ��ַ���
	 * @param delimiter
	 *            �����
	 * @param allowDuplicates
	 *            �Ƿ������ظ�
	 * @return ׷�Ӻ�����ַ���
	 */
	public static String add(String s, String add, String delimiter,
			boolean allowDuplicates) {

		if ((add == null) || (delimiter == null)) {
			return null;
		}

		if (s == null) {
			s = StringPool.BLANK;
		}

		if (allowDuplicates || !contains(s, add, delimiter)) {
			if (Validator.isNull(s) || s.endsWith(delimiter)) {
				s += add + delimiter;
			} else {
				s += delimiter + add + delimiter;
			}
		}

		return s;
	}

	/**
	 * �ж��Ƿ�Դ�ִ��а����˸������ִ�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param text
	 *            ���ִ�
	 * @return ���������򷵻�true�����򷵻�false
	 */
	public static boolean contains(String s, String text) {
		return contains(s, text, StringPool.COMMA);
	}

	/**
	 * �ж��Ƿ�Դ�ִ��а����˸������ִ�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param text
	 *            ���ִ�
	 * @param delimiter
	 *            �ִ��ָ���
	 * @return ���������򷵻�true�����򷵻�false
	 */
	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		int pos = s.indexOf(delimiter + text + delimiter);

		if (pos == -1) {
			if (s.startsWith(text + delimiter)) {
				return true;
			}

			return false;
		}

		return true;
	}

	/**
	 * ����Դ�ִ��У������˼����������ִ�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param text
	 *            ���ִ�
	 * @return ���ִ���Դ�ִ��еĸ���
	 */
	public static int count(String s, String text) {
		if ((s == null) || (text == null)) {
			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());
			count++;
		}

		return count;
	}

	/**
	 * �ж�Դ�ִ��Ƿ���ָ���ַ���β
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param end
	 *            ָ���ַ�
	 * @return ��Դ�ִ���ָ���ַ���β���򷵻�true�����򷵻�false
	 */
	public static boolean endsWith(String s, char end) {
		return endsWith(s, (new Character(end)).toString());
	}

	/**
	 * �ж�Դ�ִ��Ƿ���ָ���ַ�����β
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param end
	 *            ָ���ַ���
	 * @return ��Դ�ִ���ָ���ַ�����β���򷵻�true�����򷵻�false
	 */
	public static boolean endsWith(String s, String end) {
		if ((s == null) || (end == null)) {
			return false;
		}

		if (end.length() > s.length()) {
			return false;
		}

		String temp = s.substring(s.length() - end.length(), s.length());

		return temp.equalsIgnoreCase(end);
	}

	/**
	 * ��ȡԴ�ִ��е��ַ�����Ӣ����ĸ��
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ��ȡ�ַ���ɵ����ִ�
	 */
	public static String extractChars(String s) {
		if (s == null) {
			return "";
		}

		char[] c = s.toCharArray();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < c.length; i++) {
			if (Validator.isLetter(c[i])) {
				sb.append(c[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * ��ȡԴ�ִ��е�����
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ��ȡ����������ɵ����ִ�
	 */
	public static String extractDigits(String s) {
		if (s == null) {
			return "";
		}

		char[] c = s.toCharArray();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < c.length; i++) {
			if (Validator.isDigit(c[i])) {
				sb.append(c[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * ��ȡԴ�ִ��е�һ���Ӵ�����ָ���ָ���Ϊ�ָ���
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param delimiter
	 *            ָ���ָ���
	 * @return ��ȡ���ĵ�һ���Ӵ�
	 */
	public static String extractFirst(String s, String delimiter) {
		if (s == null) {
			return null;
		} else {
			String[] array = split(s, delimiter);

			if (array.length > 0) {
				return array[0];
			} else {
				return null;
			}
		}
	}

	/**
	 * ��ȡԴ�ִ������һ���Ӵ�����ָ���ָ���Ϊ�ָ���
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param delimiter
	 *            ָ���ָ���
	 * @return ��ȡ�������һ���Ӵ�
	 */
	public static String extractLast(String s, String delimiter) {
		if (s == null) {
			return null;
		} else {
			String[] array = split(s, delimiter);

			if (array.length > 0) {
				return array[array.length - 1];
			} else {
				return null;
			}
		}
	}

	/**
	 * ��Դ�ִ�ת��Ϊȫ��Сд
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ת��ΪСд������ִ�
	 */
	public static String lowerCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toLowerCase();
		}
	}

	/**
	 * ���ַ���list�ö���ƴ��ת��Ϊ�ַ���
	 * 
	 * @param list
	 *            Դ�ַ���list
	 * @return ƴ�Ӻ�����ִ�
	 */
	public static String merge(List<String> list) {
		return merge(list, StringPool.COMMA);
	}

	/**
	 * ���ַ���list�÷ָ���ƴ��ת��Ϊ�ַ���
	 * 
	 * @param list
	 *            Դ�ַ���list
	 * @param delimiter
	 *            �ָ���
	 * @return ƴ�Ӻ�����ִ�
	 */
	public static String merge(List<String> list, String delimiter) {
		return merge((String[]) list.toArray(new String[0]), delimiter);
	}

	/**
	 * ���ַ��������ö���ƴ��ת��Ϊ�ַ���
	 * 
	 * @param array
	 *            Դ�ַ�������
	 * @return ƴ�Ӻ�����ִ�
	 */
	public static String merge(String[] array) {
		return merge(array, StringPool.COMMA);
	}

	/**
	 * ���ַ��������÷ָ���ƴ��ת��Ϊ�ַ���
	 * 
	 * @param array
	 *            Դ�ַ�������
	 * @param delimiter
	 *            �ָ���
	 * @return ƴ�Ӻ�����ִ�
	 */
	public static String merge(String[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			sb.append(array[i].trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	/**
	 * �������Դ�ִ��е��ַ�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ������������ִ�
	 */
	public static String randomize(String s) {
		return Randomizer.getInstance().randomize(s);
	}

	/**
	 * ��ȡָ����װ��·���£�ָ����Դ�ļ�����
	 * 
	 * @param classLoader
	 *            ��װ����
	 * @param name
	 *            ��Դ�ļ�
	 * @return ��Դ�ļ����ݣ��ַ�����ʽ��
	 * @throws IOException
	 */
	public static String read(ClassLoader classLoader, String name)
			throws IOException {

		return read(classLoader.getResourceAsStream(name));
	}

	/**
	 * ��ȡ��������������
	 * 
	 * @param is
	 *            ������
	 * @return ���������ݣ��ַ�����ʽ��
	 * @throws IOException
	 */
	public static String read(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuffer sb = new StringBuffer();
		String line;

		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}

		br.close();

		return sb.toString().trim();
	}

	/**
	 * ��Դ�ִ��в��ָ���ִ����Զ���Ϊʶ��ָ�����
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param remove
	 *            Ҫժ����ָ���ִ�
	 * @return ����Ӵ�����´�
	 */
	public static String remove(String s, String remove) {
		return remove(s, remove, StringPool.COMMA);
	}

	/**
	 * ��Դ�ִ��в��ָ���ִ�����ָ���ָ���Ϊʶ��ָ�����
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param remove
	 *            Ҫժ����ָ���ִ�
	 * @param delimiter
	 *            ָ���ָ���
	 * @return ����Ӵ�����´�
	 */
	public static String remove(String s, String remove, String delimiter) {
		if ((s == null) || (remove == null) || (delimiter == null)) {
			return null;
		}

		if (Validator.isNotNull(s) && !s.endsWith(delimiter)) {
			s += delimiter;
		}

		while (contains(s, remove, delimiter)) {
			int pos = s.indexOf(delimiter + remove + delimiter);

			if (pos == -1) {
				if (s.startsWith(remove + delimiter)) {
					int x = remove.length() + delimiter.length();
					int y = s.length();

					s = s.substring(x, y);
				}
			} else {
				int x = pos + remove.length() + delimiter.length();
				int y = s.length();

				s = s.substring(0, pos) + s.substring(x, y);
			}
		}

		return s;
	}

	/**
	 * ��Դ�ִ��е�ָ���ַ��������ַ����
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param oldSub
	 *            ָ��Ҫ��������ַ�
	 * @param newSub
	 *            ���ַ�
	 * @return �滻����´�
	 */
	public static String replace(String s, char oldSub, char newSub) {
		return replace(s, oldSub, Character.toString(newSub));
	}

	/**
	 * ��Դ�ִ��е�ָ���ַ��������ַ������
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param oldSub
	 *            ָ��Ҫ��������ַ�
	 * @param newSub
	 *            ���ַ���
	 * @return �滻����´�
	 */
	public static String replace(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		char[] c = s.toCharArray();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < c.length; i++) {
			if (c[i] == oldSub) {
				sb.append(newSub);
			} else {
				sb.append(c[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * ��Դ�ִ��е�ָ���ַ����������ַ������
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param oldSub
	 *            ָ��Ҫ��������ַ���
	 * @param newSub
	 *            ���ַ���
	 * @return �滻������ַ���
	 */
	public static String replace(String s, String oldSub, String newSub) {
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		int y = s.indexOf(oldSub);

		if (y >= 0) {
			StringBuffer sb = new StringBuffer();

			int length = oldSub.length();
			int x = 0;

			while (x <= y) {
				sb.append(s.substring(x, y));
				sb.append(newSub);
				x = y + length;
				y = s.indexOf(oldSub, x);
			}

			sb.append(s.substring(x));

			return sb.toString();
		} else {
			return s;
		}
	}

	/**
	 * ��Դ�ַ�����һ��ָ���ִ�����һ���´��滻
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param oldSubs
	 *            Ҫ���滻��һ������ִ�
	 * @param newSubs
	 *            һ�����ִ�
	 * @return �滻������ַ���
	 */
	public static String replace(String s, String[] oldSubs, String[] newSubs) {
		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	/**
	 * 
	 * ���� ���ַ����еĻس������з����Ʊ��
	 * 
	 * @param srcStr
	 * @param replacement
	 * @return
	 * @author guo_lei
	 * @created 2012-9-12 ����3:35:20
	 */
	public static String replaceBlank(String srcStr, String replacement) {
		if (Validator.isEmpty(srcStr)) {
			return null;
		}

		Pattern p = Pattern.compile("\t|\r|\n");
		Matcher m = p.matcher(srcStr);
		String destStr = m.replaceAll(replacement);

		return destStr;
	}

	/**
	 * ��Դ�ִ������γ����ִ�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ���������ִ�
	 */
	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] c = s.toCharArray();
		char[] reverse = new char[c.length];

		for (int i = 0; i < c.length; i++) {
			reverse[i] = c[c.length - i - 1];
		}

		return new String(reverse);
	}

	/**
	 * ȡԴ�ִ�ǰ20���ַ���ʣ���á�������ʾ
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return �������´�
	 */
	public static String shorten(String s) {
		return shorten(s, 20);
	}

	/**
	 * ȡԴ�ִ�ָ�����ȵ�ǰ�����ַ���ʣ���á�������ʾ
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param length
	 *            ��ʾ��ǰ�������ַ�����
	 * @return ���������ִ�
	 */
	public static String shorten(String s, int length) {
		return shorten(s, length, "...");
	}

	/**
	 * ȡԴ�ִ�ǰ20���ַ���ʣ����ָ��ʡ���ַ���ʾ
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param suffix
	 *            ָ��ʡ���ַ�
	 * @return ���������ִ�
	 */
	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	/**
	 * ȡԴ�ִ�ָ�����ȵ�ǰ�����ַ���ʣ����ָ��ʡ���ַ���ʾ
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param length
	 *            ��ʾ��ǰ�������ַ�����
	 * @param suffix
	 *            ָ��ʡ���ַ�
	 * @return ���������ִ�
	 */
	public static String shorten(String s, int length, String suffix) {
		if (s == null || suffix == null) {
			return null;
		}

		if (s.length() > length) {
			for (int j = length; j >= 0; j--) {
				if (Character.isWhitespace(s.charAt(j))) {
					length = j;

					break;
				}
			}

			s = s.substring(0, length) + suffix;
		}

		return s;
	}

	/**
	 * ��Դ�ִ��ö��ŷָ���ȡ�÷ָ�����ַ�����
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return �ָ�����ַ�����
	 */
	public static String[] split(String s) {
		return split(s, StringPool.COMMA);
	}

	/**
	 * ��Դ�ִ���ָ���ָ����ָ���ȡ�÷ָ�����ַ�����
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param delimiter
	 *            ָ���ָ���
	 * @return �ָ�����ַ�����
	 */
	public static String[] split(String s, String delimiter) {
		if (s == null || delimiter == null) {
			return new String[0];
		}

		s = s.trim();

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		if (s.equals(delimiter)) {
			return new String[0];
		}

		List<String> nodeValues = new ArrayList<String>();

		if (delimiter.equals("\n") || delimiter.equals("\r")) {
			try {
				BufferedReader br = new BufferedReader(new StringReader(s));

				String line;

				while ((line = br.readLine()) != null) {
					nodeValues.add(line);
				}

				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			int offset = 0;
			int pos = s.indexOf(delimiter, offset);

			while (pos != -1) {
				nodeValues.add(s.substring(offset, pos));

				offset = pos + delimiter.length();
				pos = s.indexOf(delimiter, offset);
			}
		}

		return (String[]) nodeValues.toArray(new String[0]);
	}

	/**
	 * ��Դ�ִ���ָ���ָ����ָ����ָ���ת��boolean������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param delimiter
	 *            ָ���ָ���
	 * @param x
	 *            boolean��Ԫ��Ĭ��ֵ
	 * @return �ָ����boolean����
	 */
	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * ��Դ�ִ���ָ���ָ����ָ����ָ���ת��Ϊdouble������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param delimiter
	 *            ָ���ָ���
	 * @param x
	 *            double��Ԫ��Ĭ��ֵ
	 * @return �ָ����double����
	 */
	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * ��Դ�ִ���ָ���ָ����ָ����ָ���ת��Ϊfloat������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param delimiter
	 *            ָ���ָ���
	 * @param x
	 *            float��Ԫ��Ĭ��ֵ
	 * @return �ָ����float����
	 */
	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * ��Դ�ִ���ָ���ָ����ָ����ָ���ת��Ϊint������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param delimiter
	 *            ָ���ָ���
	 * @param x
	 *            int��Ԫ��Ĭ��ֵ
	 * @return �ָ����int����
	 */
	public static int[] split(String s, String delimiter, int x) {
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = x;

			try {
				value = Integer.parseInt(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * ��Դ�ִ���ָ���ָ����ָ����ָ���ת��long������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param delimiter
	 *            ָ���ָ���
	 * @param x
	 *            long��Ԫ��Ĭ��ֵ
	 * @return �ָ����long����
	 */
	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * ��Դ�ִ���ָ���ָ����ָ����ָ���ת��short������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param delimiter
	 *            ָ���ָ���
	 * @param x
	 *            short��Ԫ��Ĭ��ֵ
	 * @return �ָ����short����
	 */
	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * �ж�Դ�ִ��Ƿ���ָ���ַ���ͷ
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param begin
	 *            ָ���ַ�
	 * @return ��Դ�ִ���ָ���ַ���ͷ���򷵻�true�����򷵻�false
	 */
	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	/**
	 * �ж�Դ�ִ��Ƿ���ָ�����ִ���ͷ
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @param start
	 *            ָ�����ִ�
	 * @return ��Դ�ִ���ָ�����ִ���ͷ���򷵻�true�����򷵻�false
	 */
	public static boolean startsWith(String s, String start) {
		if ((s == null) || (start == null)) {
			return false;
		}

		if (start.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, start.length());

		if (temp.equalsIgnoreCase(start)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ȥ��Դ�ִ���ͷ�Ŀո�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ���������ִ�
	 */
	public static String trimLeading(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return s.substring(i, s.length());
			}
		}

		return StringPool.BLANK;
	}

	/**
	 * ȥ��Դ�ִ�β���Ŀո�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ���������ִ�
	 */
	public static String trimTrailing(String s) {
		for (int i = s.length() - 1; i >= 0; i--) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return s.substring(0, i + 1);
			}
		}

		return StringPool.BLANK;
	}

	/**
	 * ȥ��Դ�ִ���ȫ���Ŀո�
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ���������ִ�
	 */
	public static String trimWhole(String s) {
		// ���sĩβ�Ŀո�
		s = s.trim();
		char[] exprArray = new char[s.length()];
		// ��s�е�Ԫ�ظ��Ƶ�����exprArray��
		for (int i = s.length() - 1; i >= 0; i--)
			exprArray[i] = s.charAt(i);
		// ������ո����
		for (int i = s.length() - 1; i >= 0; i--) {
			int j;
			if (exprArray[i] == ' ') {
				j = i;
				while (j < exprArray.length - 1) {
					exprArray[j] = exprArray[j + 1];
					j++;
				}
				exprArray[exprArray.length - 1] = ' ';
			}
		}
		// ��������ʽת����StringBuffer��ʽ
		StringBuffer exprStrBuf = new StringBuffer("");
		for (int i = 0; i < exprArray.length; i++) {
			exprStrBuf.insert(i, exprArray[i]);
		}
		// ��StringBuffer��ʽת����String��ʽ
		s = exprStrBuf.toString().trim();

		return s;
	}

	/**
	 * ��Դ�ִ�ȫ��ת��Ϊ��д
	 * 
	 * @param s
	 *            Դ�ִ�
	 * @return ת��Ϊ��д������ִ�
	 */
	public static String upperCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toUpperCase();
		}
	}

	/**
	 * ��Դ�ִ��ı���ָ������80���ַ����У�ÿ80���ַ�����һ���س�����
	 * 
	 * @param text
	 *            Դ�ִ�
	 * @return ���������ִ�
	 */
	public static String wrap(String text) {
		return wrap(text, 80, "\n");
	}

	/**
	 * ��Դ�ִ��ı���ָ�����ȵ��ַ������зָ���
	 * 
	 * @param text
	 *            Դ�ִ�
	 * @param width
	 *            ָ������
	 * @param lineSeparator
	 *            �зָ���
	 * @return �������ִ�
	 */
	public static String wrap(String text, int width, String lineSeparator) {
		if (text == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new StringReader(text));

			String s = StringPool.BLANK;

			while ((s = br.readLine()) != null) {
				if (s.length() == 0) {
					sb.append(lineSeparator);
				} else {
					String[] tokens = s.split(StringPool.SPACE);
					boolean firstWord = true;
					int curLineLength = 0;

					for (int i = 0; i < tokens.length; i++) {
						if (!firstWord) {
							sb.append(StringPool.SPACE);
							curLineLength++;
						}

						if (firstWord) {
							sb.append(lineSeparator);
						}

						sb.append(tokens[i]);

						curLineLength += tokens[i].length();

						if (curLineLength >= width) {
							firstWord = true;
							curLineLength = 0;
						} else {
							firstWord = false;
						}
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * ȡ��ָ�����ڵ��Ӵ�
	 * 
	 * @param string
	 *            ԭ�ַ���
	 * @param delimiter
	 *            ��ָ���
	 * @param index
	 *            Ҫȡ�ĵڼ����򣨴�1��ʼ��
	 * @return �Ӵ�
	 * @throws Exception
	 */
	public static String subStringByDomain(String string, String delimiter,
			int index) throws Exception {
		if (index < 1) {
			throw new Exception("index must beyond 0");
		}
		String[] sub = split(string, delimiter);
		return sub[index - 1];
	}

	/**
	 * ȡ�ð���ָ���ڼ�λ�ָ����ָ��ɵ��Ӵ�����
	 * 
	 * @param string
	 *            ԭ�ַ���
	 * @param delimiter
	 *            �ָ���
	 * @param index
	 *            �ڼ�λ�ָ���
	 * @return �ָ��ɵ��Ӵ�����
	 * @throws Exception
	 */
	public static String[] splitByIndex(String string, String delimiter,
			int index) throws Exception {
		String[] result = new String[2];
		String[] sub = split(string, delimiter);
		if (sub.length < index) {
			throw new Exception("index beyond the limit!");
		}
		StringBuffer subStr = new StringBuffer();
		for (int i = 0; i < index; i++) {
			if (i > 0) {
				subStr.append(delimiter);
			}
			subStr.append(sub[i]);
		}
		result[0] = subStr.toString();
		if (sub.length > index) {
			subStr = new StringBuffer();
			for (int i = index; i < sub.length; i++) {
				if (i > index) {
					subStr.append(delimiter);
				}
				subStr.append(sub[i]);
			}
			result[1] = subStr.toString();
		}
		return result;
	}

	/**
	 * �����ַ����ĳ���
	 * 
	 * @param value
	 *            ����֤�ַ���
	 * @return �ַ����ֽ���
	 */
	public static int length(String value) {
		if (Validator.isNotEmpty(value)) {
			return value.getBytes().length;
		} else {
			return 0;
		}
	}

	/**
	 * �ж�ĳ�ַ������Ƿ���ĳ����
	 * 
	 * @param src
	 *            ĸ�ַ���
	 * @param word
	 *            �����鵥��
	 * @return ���ڷ���true�������ڷ���false
	 */
	public static boolean checkHasWord(String src, String word) {
		if (Validator.isEmpty(src) || Validator.isEmpty(word)) {
			return false;
		}
		String[] arr = src.split(" ");
		if (arr != null && arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].equalsIgnoreCase(word)) {
					return true;
				}
			}
		}
		return false;
		// TODO LIUKUN-TASK:ʹ��������ʽ�Ż��˷���
		// String regex = ".*\b"+word+"\b.*";
		// Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// Matcher matcher = pattern.matcher(src);
		// return matcher.matches();

	}

	/**
	 * String[] ȥ��
	 * 
	 * @param strs
	 */
	public static String[] distinctArray(String[] strs) {
		Set<String> distinctSet = new HashSet<String>();
		for (int i = 0; strs != null && i < strs.length; i++) {
			distinctSet.add(strs[i]);
		}
		strs = new String[distinctSet.size()];
		int i = 0;
		for (String str : distinctSet) {
			strs[i] = str;
			i++;
		}
		return strs;
	}

	/**
	 * List<String> ȥ��
	 * 
	 * @param strs
	 */
	public static List<String> distinctList(List<String> list) {
		if (Validator.isEmpty(list)) {
			return new ArrayList<String>();
		}
		Set<String> distinctSet = new HashSet<String>();
		for (int i = 0; list != null && i < list.size(); i++) {
			distinctSet.add(list.get(i));
		}
		List<String> distinctList = new ArrayList<String>();
		distinctList.addAll(distinctSet);
		return distinctList;
	}

	/**
	 * �������е��ַ������ѷָ���spliter��������
	 * 
	 * @param coll
	 *            �ַ�������
	 * @param spliter
	 *            �ָ���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String join(Collection<?> coll, String spliter) {
		if (Validator.isEmpty(coll)) {
			logger.error("�ַ�������Ϊ�գ�");
			return "";
		}
		if (Validator.isEmpty(spliter)) {
			logger.error("�ָ���Ϊ�գ�");
			spliter = "";
		}
		String temp = "";
		Iterator<Object> it = (Iterator<Object>) coll.iterator();
		while (it.hasNext()) {
			String str = it.next().toString();
			temp += (spliter + str);
		}
		if (Validator.isNotEmpty(spliter)) {
			temp = temp.replaceFirst(spliter, "");
		}
		return temp;
	}

	/**
	 * String[] ת List<String>
	 * 
	 * @param strs
	 * @return
	 * @author LiuKun
	 * @created 2012-8-10 ����10:46:30
	 */
	public static List<String> toList(String[] strs) {
		List<String> strList = new ArrayList<String>();
		for (int i = 0; strs != null && i < strs.length; i++) {
			strList.add(strs[i]);
		}
		return strList;
	}

	/**
	 * ���ַ���ת��ΪMap
	 * 
	 * @param value
	 *            ��Ҫת�����ַ���(�磺"key1=value1;key2=value2")
	 * @param keyValueSpliter
	 *            map��key��value�ķָ���(��:"=")
	 * @param mapSpliter
	 *            map��ÿ��key-value�ķָ���(��:";")
	 * @return
	 * @author LiuKun
	 * @created 2012-8-14 ����11:59:12
	 */
	public static Map<String, String> toMap(String value,
			String keyValueSpliter, String mapSpliter) {
		if (Validator.isEmpty(value) || Validator.isEmpty(keyValueSpliter)
				|| Validator.isEmpty(mapSpliter)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		String[] mapArr = value.split("[" + mapSpliter + "]");
		for (int i = 0; mapArr != null && i < mapArr.length; i++) {
			String[] keyValueArr = mapArr[i].split("[" + keyValueSpliter + "]");
			if (null != keyValueArr && keyValueArr.length == 2) {
				map.put(keyValueArr[0], keyValueArr[1]);
			}
		}
		return map;
	}

	/**
	 * ��ֵ����ָ���ķָ�����ת��Ϊ����
	 * 
	 * @param value
	 *            ֵ
	 * @param spliter
	 *            �ָ���(�磺����:",";���:[,|.])
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 ����3:36:49
	 */
	public static String[] split2ArrayFilterEmpty(String value, String spliter) {
		if (Validator.isEmpty(value)) {
			throw new RuntimeException("ֵΪ�գ�");
		}
		if (Validator.isEmpty(spliter)) {
			throw new RuntimeException("�ָ���Ϊ�գ�");
		}
		String[] values = value.split(spliter);
		List<String> valueList = new ArrayList<String>();
		for (int i = 0; values != null && i < values.length; i++) {
			if (Validator.isNotEmpty(values[i])) {
				valueList.add(values[i]);
			}
		}
		values = valueList.toArray(new String[0]);
		return values;
	}

	/**
	 * ��ֵ����ָ���ķָ�����ת��Ϊ����
	 * 
	 * @param value
	 *            ֵ
	 * @param spliter
	 *            �ָ���(�磺����:",";���:[,|.])
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 ����3:36:49
	 */
	public static List<String> split2ListFilterEmpty(String value,
			String spliter) {
		if (Validator.isEmpty(value)) {
			throw new RuntimeException("ֵΪ�գ�");
		}
		if (Validator.isEmpty(spliter)) {
			throw new RuntimeException("�ָ���Ϊ�գ�");
		}
		String[] values = value.split(spliter);
		List<String> valueList = new ArrayList<String>();
		for (int i = 0; values != null && i < values.length; i++) {
			if (Validator.isNotEmpty(values[i])) {
				valueList.add(values[i]);
			}
		}
		return valueList;
	}

	/**
	 * �������÷ָ����ָ�ת���ַ����������ַ����ס�ĩλ��Ҳ���÷ָ���
	 * 
	 * @param values
	 * @param spliter
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 ����3:59:45
	 */
	public static String join2String(Collection<String> coll, String spliter) {
		if (Validator.isEmpty(coll)) {
			// throw new RuntimeException("ֵ����Ϊ�գ�");
			return "";
		}
		// if (Validator.isEmpty(spliter)) {
		// throw new RuntimeException("�ָ���Ϊ�գ�");
		// }
		String value = join(coll, spliter);
		value = spliter + value + spliter;
		return value;
	}

	/**
	 * �������÷ָ����ָ�ת���ַ����������ַ����ס�ĩλ��Ҳ���÷ָ���
	 * 
	 * @param values
	 * @param spliter
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 ����3:59:45
	 */
	public static String join2String(String[] values, String spliter) {
		if (Validator.isEmpty(values)) {
			throw new RuntimeException("ֵ����Ϊ�գ�");
		}
		if (Validator.isEmpty(spliter)) {
			throw new RuntimeException("�ָ���Ϊ�գ�");
		}
		String value = join(spliter, values);
		value = spliter + value + spliter;
		return value;
	}

}