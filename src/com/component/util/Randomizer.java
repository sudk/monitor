package com.component.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * ������󹤾���.
 * UserBase: Administrator
 * Date: 2007-1-4
 * Time: 16:36:14
 * To change this template use File | Settings | File Templates.
 */
public class Randomizer extends Random {

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
     * ȡ��С��ָ������һ��ָ�����ȵ�һ���������
     * @param n ��������ֵ
     * @param size ������鳤�ȣ����˳��ȴ���nʱ������nȡ����
     * @return �������������
     */
    public int[] nextInt(int n, int size) {
		if (size > n) {
			size = n;
		}

		Set set = new LinkedHashSet();

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

		Iterator itr = set.iterator();

		for (int i = 0; i < array.length; i++) {
			array[i] = ((Integer)itr.next()).intValue();
		}

		return array;
	}

	/**
     * ��char���鰴�����˳����������
     * @param array Դchar����
     */
    public void randomize(char array[]) {
		int length = array.length;

		for(int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			char y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

    /**
     * ��int���鰴�����˳����������
     * @param array Դint����
     */
    public void randomize(int array[]) {
		int length = array.length;

		for(int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			int y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

    /**
     * ��listԪ�ذ������˳����������
     * @param list Դlist
     */
    public void randomize(List list) {
		int size = list.size();

		for(int i = 0; i <= size; i++) {
			int j = nextInt(size);
			Object obj = list.get(i);

			list.set(i, list.get(i + j));
			list.set(i + j, obj);

			size--;
		}
	}

    /**
     * �����鰴�����˳����������
     * @param array Դ����
     */
    public void randomize(Object array[]) {
		int length = array.length;

		for(int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			Object y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

    /**
     * ��Դ�ַ������ַ������˳����������
     * @param s  Դ�ַ���
     * @return  �����������ִ�
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