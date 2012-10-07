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
 * 字符串帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 上午10:16:12
 */
public class StringUtil {
	private static final Logger logger = Logger.getLogger(StringUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	/**
	 * 字符数组用指定分隔符链接为字符串
	 * 
	 * @param seperator
	 *            分隔符
	 * @param strings
	 *            字符串
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
	 * 将Iterator对象，用指定分隔符连接成字符串
	 * 
	 * @param seperator
	 *            指定分隔符
	 * @param objects
	 *            需要连接的Iterator对象
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
	 * 在源字串后追加字符串，用‘，’为间隔
	 * 
	 * @param s
	 *            源字串
	 * @param add
	 *            追加的字符串
	 * @return 追加后的新字符串
	 */
	public static String add(String s, String add) {
		return add(s, add, StringPool.COMMA);
	}

	/**
	 * 在源字串后追加字符串，用指定间隔符间隔
	 * 
	 * @param s
	 *            源字串
	 * @param add
	 *            追加的字符串
	 * @param delimiter
	 *            间隔符
	 * @return 追加后的新字符串
	 */
	public static String add(String s, String add, String delimiter) {
		return add(s, add, delimiter, false);
	}

	/**
	 * 在源字串后追加字符串，用指定间隔符间隔 ； 若规定了不允许重复，则仅在追加的字串不包含在源字串的情况瞎，才进行追加
	 * 
	 * @param s
	 *            源字串
	 * @param add
	 *            追加的字符串
	 * @param delimiter
	 *            间隔符
	 * @param allowDuplicates
	 *            是否允许重复
	 * @return 追加后的新字符串
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
	 * 判断是否源字串中包含了给定子字串
	 * 
	 * @param s
	 *            源字串
	 * @param text
	 *            子字串
	 * @return 若包含，则返回true；否则返回false
	 */
	public static boolean contains(String s, String text) {
		return contains(s, text, StringPool.COMMA);
	}

	/**
	 * 判断是否源字串中包含了给定子字串
	 * 
	 * @param s
	 *            源字串
	 * @param text
	 *            子字串
	 * @param delimiter
	 *            字串分隔符
	 * @return 若包含，则返回true；否则返回false
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
	 * 计算源字串中，包含了几个给定子字串
	 * 
	 * @param s
	 *            源字串
	 * @param text
	 *            子字串
	 * @return 子字串在源字串中的个数
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
	 * 判断源字串是否以指定字符结尾
	 * 
	 * @param s
	 *            源字串
	 * @param end
	 *            指定字符
	 * @return 若源字串以指定字符结尾，则返回true；否则返回false
	 */
	public static boolean endsWith(String s, char end) {
		return endsWith(s, (new Character(end)).toString());
	}

	/**
	 * 判断源字串是否以指定字符串结尾
	 * 
	 * @param s
	 *            源字串
	 * @param end
	 *            指定字符串
	 * @return 若源字串以指定字符串结尾，则返回true；否则返回false
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
	 * 提取源字串中的字符（中英文字母）
	 * 
	 * @param s
	 *            源字串
	 * @return 提取字符组成的新字串
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
	 * 提取源字串中的数字
	 * 
	 * @param s
	 *            源字串
	 * @return 提取出的数字组成的新字串
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
	 * 提取源字串中第一个子串（以指定分隔符为分隔）
	 * 
	 * @param s
	 *            源字串
	 * @param delimiter
	 *            指定分隔符
	 * @return 提取出的第一个子串
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
	 * 提取源字串中最后一个子串（以指定分隔符为分隔）
	 * 
	 * @param s
	 *            源字串
	 * @param delimiter
	 *            指定分隔符
	 * @return 提取出的最后一个子串
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
	 * 将源字串转换为全部小写
	 * 
	 * @param s
	 *            源字串
	 * @return 转换为小写后的新字串
	 */
	public static String lowerCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toLowerCase();
		}
	}

	/**
	 * 将字符串list用逗号拼接转换为字符串
	 * 
	 * @param list
	 *            源字符串list
	 * @return 拼接后的新字串
	 */
	public static String merge(List<String> list) {
		return merge(list, StringPool.COMMA);
	}

	/**
	 * 将字符串list用分隔符拼接转换为字符串
	 * 
	 * @param list
	 *            源字符串list
	 * @param delimiter
	 *            分隔符
	 * @return 拼接后的新字串
	 */
	public static String merge(List<String> list, String delimiter) {
		return merge((String[]) list.toArray(new String[0]), delimiter);
	}

	/**
	 * 将字符串数组用逗号拼接转换为字符串
	 * 
	 * @param array
	 *            源字符串数组
	 * @return 拼接后的新字串
	 */
	public static String merge(String[] array) {
		return merge(array, StringPool.COMMA);
	}

	/**
	 * 将字符串数组用分隔符拼接转换为字符串
	 * 
	 * @param array
	 *            源字符串数组
	 * @param delimiter
	 *            分隔符
	 * @return 拼接后的新字串
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
	 * 随机重组源字串中的字符
	 * 
	 * @param s
	 *            源字串
	 * @return 随机重组后的新字串
	 */
	public static String randomize(String s) {
		return Randomizer.getInstance().randomize(s);
	}

	/**
	 * 读取指定类装载路径下，指定资源文件内容
	 * 
	 * @param classLoader
	 *            类装载器
	 * @param name
	 *            资源文件
	 * @return 资源文件内容（字符串形式）
	 * @throws IOException
	 */
	public static String read(ClassLoader classLoader, String name)
			throws IOException {

		return read(classLoader.getResourceAsStream(name));
	}

	/**
	 * 读取给定输入流内容
	 * 
	 * @param is
	 *            输入流
	 * @return 输入流内容（字符串形式）
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
	 * 从源字串中拆除指定字串（以逗号为识别分隔符）
	 * 
	 * @param s
	 *            源字串
	 * @param remove
	 *            要摘除的指定字串
	 * @return 拆除子串后的新串
	 */
	public static String remove(String s, String remove) {
		return remove(s, remove, StringPool.COMMA);
	}

	/**
	 * 从源字串中拆除指定字串（以指定分隔符为识别分隔符）
	 * 
	 * @param s
	 *            源字串
	 * @param remove
	 *            要摘除的指定字串
	 * @param delimiter
	 *            指定分隔符
	 * @return 拆除子串后的新串
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
	 * 将源字串中的指定字符，用新字符替代
	 * 
	 * @param s
	 *            源字串
	 * @param oldSub
	 *            指定要被替代的字符
	 * @param newSub
	 *            新字符
	 * @return 替换后的新串
	 */
	public static String replace(String s, char oldSub, char newSub) {
		return replace(s, oldSub, Character.toString(newSub));
	}

	/**
	 * 将源字串中的指定字符，用新字符串替代
	 * 
	 * @param s
	 *            源字串
	 * @param oldSub
	 *            指定要被替代的字符
	 * @param newSub
	 *            新字符串
	 * @return 替换后的新串
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
	 * 将源字串中的指定字符串，用新字符串替代
	 * 
	 * @param s
	 *            源字串
	 * @param oldSub
	 *            指定要被替代的字符串
	 * @param newSub
	 *            新字符串
	 * @return 替换后的新字符串
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
	 * 将源字符串中一组指定字串，用一组新串替换
	 * 
	 * @param s
	 *            源字符串
	 * @param oldSubs
	 *            要被替换的一组旧子字串
	 * @param newSubs
	 *            一组新字串
	 * @return 替换后的新字符串
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
	 * 描述 除字符串中的回车、换行符、制表符
	 * 
	 * @param srcStr
	 * @param replacement
	 * @return
	 * @author guo_lei
	 * @created 2012-9-12 下午3:35:20
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
	 * 将源字串倒序，形成新字串
	 * 
	 * @param s
	 *            源字串
	 * @return 倒序后的新字串
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
	 * 取源字串前20个字符，剩余用“…”显示
	 * 
	 * @param s
	 *            源字串
	 * @return 处理后的新串
	 */
	public static String shorten(String s) {
		return shorten(s, 20);
	}

	/**
	 * 取源字串指定长度的前几个字符，剩余用“…”显示
	 * 
	 * @param s
	 *            源字串
	 * @param length
	 *            显示的前若个个字符个数
	 * @return 处理后的新字串
	 */
	public static String shorten(String s, int length) {
		return shorten(s, length, "...");
	}

	/**
	 * 取源字串前20个字符，剩余用指定省略字符显示
	 * 
	 * @param s
	 *            源字串
	 * @param suffix
	 *            指定省略字符
	 * @return 处理后的新字串
	 */
	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	/**
	 * 取源字串指定长度的前几个字符，剩余用指定省略字符显示
	 * 
	 * @param s
	 *            源字串
	 * @param length
	 *            显示的前若个个字符个数
	 * @param suffix
	 *            指定省略字符
	 * @return 处理后的新字串
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
	 * 将源字串用逗号分隔，取得分隔后的字符数组
	 * 
	 * @param s
	 *            源字串
	 * @return 分隔后的字符数组
	 */
	public static String[] split(String s) {
		return split(s, StringPool.COMMA);
	}

	/**
	 * 将源字串用指定分隔符分隔，取得分隔后的字符数组
	 * 
	 * @param s
	 *            源字串
	 * @param delimiter
	 *            指定分隔符
	 * @return 分隔后的字符数组
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
	 * 将源字串用指定分隔符分隔，分隔后转换boolean型数组
	 * 
	 * @param s
	 *            源字符串
	 * @param delimiter
	 *            指定分隔符
	 * @param x
	 *            boolean型元素默认值
	 * @return 分隔后的boolean数组
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
	 * 将源字串用指定分隔符分隔，分隔后转换为double型数组
	 * 
	 * @param s
	 *            源字符串
	 * @param delimiter
	 *            指定分隔符
	 * @param x
	 *            double型元素默认值
	 * @return 分隔后的double数组
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
	 * 将源字串用指定分隔符分隔，分隔后转换为float型数组
	 * 
	 * @param s
	 *            源字符串
	 * @param delimiter
	 *            指定分隔符
	 * @param x
	 *            float型元素默认值
	 * @return 分隔后的float数组
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
	 * 将源字串用指定分隔符分隔，分隔后转换为int型数组
	 * 
	 * @param s
	 *            源字符串
	 * @param delimiter
	 *            指定分隔符
	 * @param x
	 *            int型元素默认值
	 * @return 分隔后的int数组
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
	 * 将源字串用指定分隔符分隔，分隔后转换long型数组
	 * 
	 * @param s
	 *            源字符串
	 * @param delimiter
	 *            指定分隔符
	 * @param x
	 *            long型元素默认值
	 * @return 分隔后的long数组
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
	 * 将源字串用指定分隔符分隔，分隔后转换short型数组
	 * 
	 * @param s
	 *            源字符串
	 * @param delimiter
	 *            指定分隔符
	 * @param x
	 *            short型元素默认值
	 * @return 分隔后的short数组
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
	 * 判断源字串是否以指定字符开头
	 * 
	 * @param s
	 *            源字串
	 * @param begin
	 *            指定字符
	 * @return 若源字串以指定字符开头，则返回true；否则返回false
	 */
	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	/**
	 * 判断源字串是否以指定子字串开头
	 * 
	 * @param s
	 *            源字串
	 * @param start
	 *            指定子字串
	 * @return 若源字串以指定子字串开头，则返回true；否则返回false
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
	 * 去除源字串开头的空格
	 * 
	 * @param s
	 *            源字串
	 * @return 处理后的新字串
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
	 * 去除源字串尾部的空格
	 * 
	 * @param s
	 *            源字串
	 * @return 处理后的新字串
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
	 * 去除源字串中全部的空格
	 * 
	 * @param s
	 *            源字串
	 * @return 处理后的新字串
	 */
	public static String trimWhole(String s) {
		// 清除s末尾的空格
		s = s.trim();
		char[] exprArray = new char[s.length()];
		// 将s中的元素复制到数组exprArray中
		for (int i = s.length() - 1; i >= 0; i--)
			exprArray[i] = s.charAt(i);
		// 逐个将空格清除
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
		// 将数组形式转换成StringBuffer形式
		StringBuffer exprStrBuf = new StringBuffer("");
		for (int i = 0; i < exprArray.length; i++) {
			exprStrBuf.insert(i, exprArray[i]);
		}
		// 将StringBuffer形式转换成String形式
		s = exprStrBuf.toString().trim();

		return s;
	}

	/**
	 * 将源字串全部转换为大写
	 * 
	 * @param s
	 *            源字串
	 * @return 转换为大写后的新字串
	 */
	public static String upperCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toUpperCase();
		}
	}

	/**
	 * 将源字串文本按指定长度80个字符折行（每80个字符插入一个回车符）
	 * 
	 * @param text
	 *            源字串
	 * @return 处理后的新字串
	 */
	public static String wrap(String text) {
		return wrap(text, 80, "\n");
	}

	/**
	 * 将源字串文本按指定长度的字符插入行分隔符
	 * 
	 * @param text
	 *            源字串
	 * @param width
	 *            指定长度
	 * @param lineSeparator
	 *            行分隔符
	 * @return 处理后的字串
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
	 * 取得指定域内的子串
	 * 
	 * @param string
	 *            原字符串
	 * @param delimiter
	 *            域分隔符
	 * @param index
	 *            要取的第几个域（从1开始）
	 * @return 子串
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
	 * 取得按照指定第几位分隔符分隔成的子串数组
	 * 
	 * @param string
	 *            原字符串
	 * @param delimiter
	 *            分隔副
	 * @param index
	 *            第几位分隔符
	 * @return 分隔成的子串数组
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
	 * 计算字符串的长度
	 * 
	 * @param value
	 *            待验证字符串
	 * @return 字符串字节数
	 */
	public static int length(String value) {
		if (Validator.isNotEmpty(value)) {
			return value.getBytes().length;
		} else {
			return 0;
		}
	}

	/**
	 * 判断某字符串中是否含有某单词
	 * 
	 * @param src
	 *            母字符串
	 * @param word
	 *            待检验单词
	 * @return 存在返回true，不存在返回false
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
		// TODO LIUKUN-TASK:使用正则表达式优化此方法
		// String regex = ".*\b"+word+"\b.*";
		// Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// Matcher matcher = pattern.matcher(src);
		// return matcher.matches();

	}

	/**
	 * String[] 去重
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
	 * List<String> 去重
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
	 * 将集合中的字符串，已分隔符spliter进行联接
	 * 
	 * @param coll
	 *            字符串集合
	 * @param spliter
	 *            分隔符
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String join(Collection<?> coll, String spliter) {
		if (Validator.isEmpty(coll)) {
			logger.error("字符串集合为空！");
			return "";
		}
		if (Validator.isEmpty(spliter)) {
			logger.error("分隔符为空！");
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
	 * String[] 转 List<String>
	 * 
	 * @param strs
	 * @return
	 * @author LiuKun
	 * @created 2012-8-10 上午10:46:30
	 */
	public static List<String> toList(String[] strs) {
		List<String> strList = new ArrayList<String>();
		for (int i = 0; strs != null && i < strs.length; i++) {
			strList.add(strs[i]);
		}
		return strList;
	}

	/**
	 * 将字符串转换为Map
	 * 
	 * @param value
	 *            需要转换的字符串(如："key1=value1;key2=value2")
	 * @param keyValueSpliter
	 *            map中key与value的分隔符(如:"=")
	 * @param mapSpliter
	 *            map中每个key-value的分隔符(如:";")
	 * @return
	 * @author LiuKun
	 * @created 2012-8-14 上午11:59:12
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
	 * 把值根据指定的分隔符，转换为数组
	 * 
	 * @param value
	 *            值
	 * @param spliter
	 *            分隔符(如：单个:",";多个:[,|.])
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 下午3:36:49
	 */
	public static String[] split2ArrayFilterEmpty(String value, String spliter) {
		if (Validator.isEmpty(value)) {
			throw new RuntimeException("值为空！");
		}
		if (Validator.isEmpty(spliter)) {
			throw new RuntimeException("分隔符为空！");
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
	 * 把值根据指定的分隔符，转换为集合
	 * 
	 * @param value
	 *            值
	 * @param spliter
	 *            分隔符(如：单个:",";多个:[,|.])
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 下午3:36:49
	 */
	public static List<String> split2ListFilterEmpty(String value,
			String spliter) {
		if (Validator.isEmpty(value)) {
			throw new RuntimeException("值为空！");
		}
		if (Validator.isEmpty(spliter)) {
			throw new RuntimeException("分隔符为空！");
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
	 * 将集合用分隔符分割转换字符串，并且字符串首、末位置也含该分隔符
	 * 
	 * @param values
	 * @param spliter
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 下午3:59:45
	 */
	public static String join2String(Collection<String> coll, String spliter) {
		if (Validator.isEmpty(coll)) {
			// throw new RuntimeException("值集合为空！");
			return "";
		}
		// if (Validator.isEmpty(spliter)) {
		// throw new RuntimeException("分隔符为空！");
		// }
		String value = join(coll, spliter);
		value = spliter + value + spliter;
		return value;
	}

	/**
	 * 将数组用分隔符分割转换字符串，并且字符串首、末位置也含该分隔符
	 * 
	 * @param values
	 * @param spliter
	 * @return
	 * @author LiuKun
	 * @created 2012-8-23 下午3:59:45
	 */
	public static String join2String(String[] values, String spliter) {
		if (Validator.isEmpty(values)) {
			throw new RuntimeException("值数组为空！");
		}
		if (Validator.isEmpty(spliter)) {
			throw new RuntimeException("分隔符为空！");
		}
		String value = join(spliter, values);
		value = spliter + value + spliter;
		return value;
	}

}