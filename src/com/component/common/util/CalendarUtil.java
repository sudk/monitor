package com.component.common.util;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * ����������
 */
public class CalendarUtil implements CalendarConstants, Serializable {
	private CalendarUtil() {
	}

	/**
	 * ���к�
	 */
	private static final long serialVersionUID = 7058686298008510378L;

	private static final Logger logger = Logger.getLogger(CalendarUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static CalendarUtil instance;

	public static CalendarUtil getInstance() {
		if (instance == null) {
			synchronized (CalendarUtil.class) {
				if (instance == null) {
					instance = new CalendarUtil();
				}
			}
		}
		return instance;
	}

	// ///////////////////////////////////

	/**
	 * ������ݺ��·ݣ��ж��������µ�����·ݵ�����
	 * 
	 * @param year
	 *            ���
	 * @param month
	 *            �·�
	 * @return
	 */
	public static int getDay(int year, int month) {
		int[] DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (month != 2) {
			return DAYS[month - 1];
		} else {
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {// �����ж�
				return 29;
			} else {
				return 28;
			}
		}
	}

	/**
	 * ������ݺ��·ݣ���ȡ�ϸ����У��м����ڵ�ǰ��������ʾ(��ǰ�����ܹ���ʾ42�죬�����ϸ��º��¸���),ȡֵ��ΧΪ1-7
	 * 
	 * @param year
	 *            ���
	 * @param month
	 *            �·�
	 * @return
	 */
	public static int getPrevDayForCurrent(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		int idx = c.get(Calendar.DAY_OF_WEEK);
		int count = idx - 1;
		if (count == 0) {
			count = 7;
		}
		return count;
	}
}
