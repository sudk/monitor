package com.component.common.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 
 * 随机对象帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 上午10:49:22
 */
public class Randomizer extends Random {

	private static final long serialVersionUID = -7824860469025527633L;

	public static Randomizer getInstance() {
		return _instance;
	}

	public Randomizer() {
		super();
	}

	public Randomizer(long seed) {
		super(seed);
	}

	/**
	 * 取得小于指定数的一组指定长度的一组随机整数
	 * 
	 * @param n
	 *            随机数最大值
	 * @param size
	 *            随机数组长度（当此长度大于n时，将被n取代）
	 * @return 产生的随机数组
	 */
	public int[] nextInt(int n, int size) {
		if (size > n) {
			size = n;
		}
		Set<Integer> set = new LinkedHashSet<Integer>();
		for (int i = 0; i < size; i++) {
			while (true) {
				Integer value = new Integer(nextInt(n));
				if (!set.contains(value)) {
					set.add(value);
					break;
				}
			}
		}
		int[] array = new int[set.size()];
		Iterator<Integer> itr = set.iterator();
		for (int i = 0; i < array.length; i++) {
			array[i] = ((Integer) itr.next()).intValue();
		}
		return array;
	}

	/**
	 * 将char数组按照随机顺序重新排列
	 * 
	 * @param array
	 *            源char数组
	 */
	public void randomize(char array[]) {
		int length = array.length;

		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			char y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

	/**
	 * 将int数组按照随机顺序重新排列
	 * 
	 * @param array
	 *            源int数组
	 */
	public void randomize(int array[]) {
		int length = array.length;

		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			int y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

	/**
	 * 将list元素按照随机顺序重新排列
	 * 
	 * @param list
	 *            源list
	 */
	public void randomize(List<Object> list) {
		int size = list.size();
		for (int i = 0; i <= size; i++) {
			int j = nextInt(size);
			Object obj = list.get(i);
			list.set(i, list.get(i + j));
			list.set(i + j, obj);
			size--;
		}
	}

	/**
	 * 将数组按照随机顺序重新排列
	 * 
	 * @param array
	 *            源数组
	 */
	public void randomize(Object array[]) {
		int length = array.length;

		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			Object y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

	/**
	 * 将源字符串中字符按随机顺序重新排序
	 * 
	 * @param s
	 *            源字符串
	 * @return 重新排序后的字串
	 */
	public String randomize(String s) {
		if (s == null) {
			return null;
		}

		char[] array = s.toCharArray();

		randomize(array);

		return new String(array);
	}

	private static Randomizer _instance = new Randomizer();

}