package com.component.common.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * ���ڰ�����
 * 
 * @author LiuKun
 * @date 2012-7-4
 * @time ����10:32:54
 * 
 */
public class DateUtil implements Serializable {

	/**
	 * 
	 * �������ڽ��бȽ�
	 * 
	 * @param date1
	 * @param date2
	 * @return date1���ڻ����date2���򷵻�true�����򷵻�false
	 */
	public static boolean compareDates(Date date1, Date date2) {

		boolean res = false;

		int result = date1.compareTo(date2);
		if (result < 0) {
			res = true;
		}
		return res;
	}

	/**
	 * ���к�
	 */
	private static final long serialVersionUID = 8190413818369140703L;

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static SimpleDateFormat fullFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	private static SimpleDateFormat dateForamt1 = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static SimpleDateFormat dateForamt2 = new SimpleDateFormat(
			"yyyy/MM/dd");

	private static SimpleDateFormat dateForamt3 = new SimpleDateFormat(
			"yyyyMMdd");

	/**
	 * ��"yyyy-MM-dd HH:mm:ss.SSS"��ʽת���������ڶ���
	 * 
	 * @param date
	 *            �������ڶ���
	 * @return "yyyy-MM-dd HH:mm:ss.SSS"��ʽ�������ִ�
	 */
	public static String getFullText(Date date) {
		return fullFormat.format(date);
	}

	/**
	 * ��"yyyy-MM-dd"��ʽת���������ڶ���
	 * 
	 * @param date
	 *            �������ڶ���
	 * @return "yyyy-MM-dd"��ʽ�������ִ�
	 */
	public static String getDateStrWithDASH(Date date) {
		return dateForamt1.format(date);
	}

	/**
	 * ��"yyyy-MM-dd"��ʽת����ǰ����
	 * 
	 * @return "yyyy-MM-dd"��ʽ�ĵ�ǰ�����ִ�
	 */
	public static String getSystemDateStrWithDASH() {
		return getDateStrWithDASH(getSystemDate());
	}

	/**
	 * ��"yyyy/MM/dd"��ʽת���������ڶ���
	 * 
	 * @param date
	 *            �������ڶ���
	 * @return "yyyy/MM/dd"��ʽ�������ִ�
	 */
	public static String getDateStrWithSPLASH(Date date) {
		return dateForamt2.format(date);
	}

	/**
	 * ��"yyyyMMdd"��ʽת���������ڶ���
	 * 
	 * @param date
	 *            �������ڶ���
	 * @return "yyyyMMdd"��ʽ�������ִ�
	 */
	public static String getDateStri(Date date) {
		return dateForamt3.format(date);
	}

	/**
	 * ��"yyyyMMdd"��ʽת���������ڶ���
	 * 
	 * @param date
	 *            �������ڶ���
	 * @return "yyyyMMdd"��ʽ�������ִ�
	 */
	public static Date getDateFromShortStr(String date) {
		try {
			return dateForamt3.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("parse date str err");
		}
	}

	/**
	 * ��ʽ��������ʾ��ʽ
	 * 
	 * @param pattern
	 *            ������ʾ��ʽ
	 * @param date
	 *            Ҫ��ʾ�����ڶ���
	 * @return ��ʽ���������ַ���
	 */
	public static String formatDate(String pattern, Date date) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		return f.format(date);
	}

	/**
	 * ����������ڼ�Ĳ��
	 * 
	 * @param date1
	 *            [String]Դ���ڣ�Ҫ��yyyymmdd"��ʽ
	 * @param date2
	 *            [String]�Ƚ����ڣ�Ҫ��yyyymmdd"��ʽ
	 * @return Դ������Ƚ�����֮��Ĳ������
	 */
	public static int getdaysbetween(String date1, String date2) {
		int iYear = Integer.parseInt(date1.substring(0, 4));
		int iMonth = Integer.parseInt(date1.substring(4, 6)) - 1;
		int iDay = Integer.parseInt(date1.substring(6, 8));
		GregorianCalendar ca1 = new GregorianCalendar(iYear, iMonth, iDay);

		iYear = Integer.parseInt(date2.substring(0, 4));
		iMonth = Integer.parseInt(date2.substring(4, 6)) - 1;
		iDay = Integer.parseInt(date2.substring(6, 8));
		GregorianCalendar ca2 = new GregorianCalendar(iYear, iMonth, iDay);

		int year1 = ca1.get(Calendar.YEAR);
		int year2 = ca2.get(Calendar.YEAR);

		int dayofYear1 = ca1.get(Calendar.DAY_OF_YEAR);
		int dayofYear2 = ca2.get(Calendar.DAY_OF_YEAR);

		//		int days = 0;
		int ip = 0;
		for (int i = year1; i < year2; i++) {
			if (isLeapyear(i)) {
				ip = ip + 366;
				// ip =i;
			} else {
				ip = ip + 365;
				// ip=i;
			}
		}
		int temp = ip + (dayofYear2 - dayofYear1 + 1);
		return temp;
	}

	/**
	 * �б��Ƿ�������
	 * 
	 * @param year
	 *            [int] ��������
	 * @return boolean [ture����][false������]
	 */
	public static boolean isLeapyear(int year) {
		boolean isproyear = false;
		if ((year % 400 == 0) | (year % 100 != 0 && year % 4 == 0)) {
			isproyear = true;
		} else {
			isproyear = false;
		}
		return isproyear;
	}

	/**
	 * ����N��֮�������
	 * 
	 * @param date
	 *            [int] ��׼����
	 * @param n
	 *            [int] ��������
	 * @return ��׼�����ڼ�������֮�������[date]
	 */
	public static java.sql.Date getAfterNDay(Date date, int n) throws Exception {

		//		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		long nDay = date.getTime() + (long) (n) * 86400000;
		String nDayString = new java.text.SimpleDateFormat("yyyy/MM/dd")
		.format(new Date(nDay));
		return getSqlDateFromStr(nDayString);
	}

	/**
	 * ���ַ�����ȡʱ�����
	 * 
	 * @return [java.sql.Date] ʱ�����
	 */
	public static java.sql.Date getSqlDateFromStr(String strDt) {
		strDt = strDt.replace(' ', '-');
		strDt = strDt.replace('.', '-');
		strDt = strDt.replace('/', '-');
		return java.sql.Date.valueOf(strDt);
	}

	/**
	 * �õ���������
	 * 
	 * @param date
	 *            ��������
	 * @param pattern
	 *            �������ڸ�ʽ
	 * @return Date ��������
	 */
	public static Date getUtilDateFromString(String date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			return formatter.parse(date);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * ȡ��ָ�����ڵĵ����ʼʱ��
	 * 
	 * @param date
	 *            ָ������
	 * @return
	 */
	public static Date getEndingOfDay(Date date) {
		String dateStr = getDateStrWithDASH(date);
		dateStr = dateStr + " 23:59:59.999";
		return getUtilDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * ȡ��ָ�����ڵĵ������ʱ�̣����һ�̣�
	 * 
	 * @param date
	 *            ָ������
	 * @return
	 */
	public static Date getBeginingOfDay(Date date) {
		String dateStr = getDateStrWithDASH(date);
		dateStr = dateStr + " 00:00:00.000";
		return getUtilDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * ��"yyyy-MM-dd"��ʽת����yyyymmdd�������ִ�
	 * 
	 * @param date
	 *            [String]��yyyymmdd����ʽ�������ִ�
	 * @return "yyyy-MM-dd"��ʽ�������ִ�
	 */
	public static String getDateString(String date) {
		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
				+ date.substring(6, 8);
	}

	/**
	 * ȡ��ϵͳ��ǰ����
	 * 
	 * @return ϵͳ��ǰ����
	 */
	public static Date getSystemDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * ȡ��ϵͳ��ǰ����(java.sql.Date)
	 * 
	 * @return ϵͳ��ǰ����(java.sql.Date)
	 */
	public static java.sql.Date getSystemSqlDate() {
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}

	/**
	 * ȡ��ϵͳ��ǰʱ���
	 * 
	 * @return ϵͳ��ǰʱ���
	 */
	public static Timestamp getSystemTimestamp() {
		return new Timestamp(Calendar.getInstance().getTime().getTime());
	}

	/**
	 * ȡ���й�����ϵͳ��ǰ����
	 * 
	 * @return ϵͳ��ǰ����
	 */
	public static Date getSystemDateOfChina() {
		return Calendar.getInstance(Locale.CHINA).getTime();
	}

	/**
	 * ȡ��ָ��������ϵͳ��ǰ����
	 * 
	 * @return ϵͳ��ǰ����
	 * @locale ����
	 */
	public static Date getSystemDate(Locale locale) {
		return Calendar.getInstance(locale).getTime();
	}

	/**
	 * ���������ջ�ȡ����
	 * 
	 * @param year
	 *            �� �� ��2012
	 * @param month
	 *            �� �磺1-12
	 * @param day
	 *            �� �磺1-31
	 * @return ָ�������յ�����
	 */
	@SuppressWarnings("deprecation")
	public static Date getDate(int year, int month, int day) {
		Date date = new Date(year - 1900, month - 1, day);
		return date;
	}

	/**
	 * ��ȡ��ǰ���
	 * 
	 * @return ��ǰ���
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentYear() {
		return getSystemDate().getYear() + 1900;
	}

	/**
	 * ��ȡ��ǰ�·�
	 * 
	 * @return ��ǰ�·�
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentMonth() {
		return getSystemDate().getMonth() + 1;
	}

	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return ��ǰ����
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentDay() {
		return getSystemDate().getDate();
	}


	public static int getDaysByYeayAndMonth(int year, int month) {
		Calendar rightNow = Calendar.getInstance();
		//���д�������յ���ʽ�Ļ���ҪдСd���磺"yyyy/MM/dd"
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
		try {
			String str = year + "/" + month;
			//Ҫ��������Ҫ���·ݣ��ı����Ｔ��
			rightNow.setTime(simpleDate.parse(str)); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	/**
	 * ��ȡyyyyMM��ʽ�ĵ�ǰ�����ַ���
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 ����6:47:08
	 */
	public static String getCurrentYearAndMonth() {
		Date nowDate = getSystemDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(nowDate);
	}

	/**
	 * ��ȡyyyyMMddHHmmssS��ʽ�ĵ�ǰ�����ַ���
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 ����6:47:08
	 */
	public static String getCurrentTime() {
		Date nowDate = getSystemDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssS");
		return format.format(nowDate);
	}

	/**
	 * ���ڱȽ�
	 * 
	 * @param date1
	 *            ���ڲ���1
	 * @param date2
	 *            ���ڲ���2
	 * @return ����0ʱ��date1����date2������0ʱ��date1����date2��С��0ʱ��date1С��date2;
	 */
	public static int compareTo(Date date1, Date date2) {
		if (date1 == null) {
			throw new RuntimeException("���ڲ���Ϊ�գ�");
		}

		if (date2 == null) {
			throw new RuntimeException("���ڲ���Ϊ�գ�");
		}

		return date1.compareTo(date2);
	}

	/**
	 * ���ڱȽ�
	 * 
	 * @param date1
	 *            ���ڲ���1
	 * @param date2
	 *            ���ڲ���2
	 * @return date1 == date2ʱ�� ����true�����򷵻�false
	 */
	public static boolean eq(Date date1, Date date2) {
		return compareTo(date1, date2) == 0;
	}

	/**
	 * ���ڱȽ�
	 * 
	 * @param date1
	 *            ���ڲ���1
	 * @param date2
	 *            ���ڲ���2
	 * @return date1 < date2 ʱ������true�����򷵻�false
	 */
	public static boolean lt(Date date1, Date date2) {
		return compareTo(date1, date2) < 0;
	}

	/**
	 * ���ڱȽ�
	 * 
	 * @param date1
	 *            ���ڲ���1
	 * @param date2
	 *            ���ڲ���2
	 * @return date1 <= date2 ʱ������true�����򷵻�false
	 */
	public static boolean le(Date date1, Date date2) {
		return compareTo(date1, date2) <= 0;
	}

	/**
	 * ���ڱȽ�
	 * 
	 * @param date1
	 *            ���ڲ���1
	 * @param date2
	 *            ���ڲ���2
	 * @return date1 > date2 ʱ������true�����򷵻�false
	 */
	public static boolean gt(Date date1, Date date2) {
		return compareTo(date1, date2) > 0;
	}

	/**
	 * ���ڱȽ�
	 * 
	 * @param date1
	 *            ���ڲ���1
	 * @param date2
	 *            ���ڲ���2
	 * @return date1 >= date2 ʱ������true�����򷵻�false
	 */
	public static boolean ge(Date date1, Date date2) {
		return compareTo(date1, date2) >= 0;
	}

	/**
	 * 
	 * �������ڽ��бȽ�
	 * @param date1
	 * @param date2
	 * @return date1���ڻ����date2���򷵻�true�����򷵻�false
	 */
	public static boolean compareStringDates(Date date1, Date date2) {

		boolean res = false;

		String d1 = getDateStrWithDASH(date1);
		String d2 = getDateStrWithDASH(date2);
		int result = d1.compareTo(d2);
		if (result <= 0) {
			res = true;
		}
		return res;
	}


	/**
	 * 
	 * �������ڽ��бȽ�
	 * @param date1
	 * @param date2
	 * @return date1����date2���򷵻�true�����򷵻�false
	 */
	public static boolean compareStringDatesNoEquel(Date date1, Date date2) {

		boolean res = false;

		String d1 = getDateStrWithDASH(date1);
		String d2 = getDateStrWithDASH(date2);
		int result = d1.compareTo(d2);
		if (result < 0) {
			res = true;
		}
		return res;
	}
}
