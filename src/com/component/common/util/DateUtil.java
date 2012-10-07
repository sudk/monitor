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
 * 日期帮助类
 * 
 * @author LiuKun
 * @date 2012-7-4
 * @time 上午10:32:54
 * 
 */
public class DateUtil implements Serializable {

	/**
	 * 
	 * 两个日期进行比较
	 * 
	 * @param date1
	 * @param date2
	 * @return date1早于或等于date2，则返回true，否则返回false
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
	 * 序列号
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
	 * 以"yyyy-MM-dd HH:mm:ss.SSS"格式转换给定日期对象
	 * 
	 * @param date
	 *            给定日期对象
	 * @return "yyyy-MM-dd HH:mm:ss.SSS"格式的日期字串
	 */
	public static String getFullText(Date date) {
		return fullFormat.format(date);
	}

	/**
	 * 以"yyyy-MM-dd"格式转换给定日期对象
	 * 
	 * @param date
	 *            给定日期对象
	 * @return "yyyy-MM-dd"格式的日期字串
	 */
	public static String getDateStrWithDASH(Date date) {
		return dateForamt1.format(date);
	}

	/**
	 * 以"yyyy-MM-dd"格式转换当前日期
	 * 
	 * @return "yyyy-MM-dd"格式的当前日期字串
	 */
	public static String getSystemDateStrWithDASH() {
		return getDateStrWithDASH(getSystemDate());
	}

	/**
	 * 以"yyyy/MM/dd"格式转换给定日期对象
	 * 
	 * @param date
	 *            给定日期对象
	 * @return "yyyy/MM/dd"格式的日期字串
	 */
	public static String getDateStrWithSPLASH(Date date) {
		return dateForamt2.format(date);
	}

	/**
	 * 以"yyyyMMdd"格式转换给定日期对象
	 * 
	 * @param date
	 *            给定日期对象
	 * @return "yyyyMMdd"格式的日期字串
	 */
	public static String getDateStri(Date date) {
		return dateForamt3.format(date);
	}

	/**
	 * 以"yyyyMMdd"格式转换给定日期对象
	 * 
	 * @param date
	 *            给定日期对象
	 * @return "yyyyMMdd"格式的日期字串
	 */
	public static Date getDateFromShortStr(String date) {
		try {
			return dateForamt3.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("parse date str err");
		}
	}

	/**
	 * 格式化日期显示形式
	 * 
	 * @param pattern
	 *            日期显示格式
	 * @param date
	 *            要显示的日期对象
	 * @return 格式化的日期字符串
	 */
	public static String formatDate(String pattern, Date date) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		return f.format(date);
	}

	/**
	 * 获得两个日期间的差额
	 * 
	 * @param date1
	 *            [String]源日期，要求“yyyymmdd"格式
	 * @param date2
	 *            [String]比较日期，要求“yyyymmdd"格式
	 * @return 源日期与比较日期之间的差额天数
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
	 * 判别是否是润年
	 * 
	 * @param year
	 *            [int] 输入的年份
	 * @return boolean [ture润年][false非润年]
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
	 * 计算N天之后的日期
	 * 
	 * @param date
	 *            [int] 基准日期
	 * @param n
	 *            [int] 计量天数
	 * @return 基准日期在计量天数之后的日期[date]
	 */
	public static java.sql.Date getAfterNDay(Date date, int n) throws Exception {

		//		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		long nDay = date.getTime() + (long) (n) * 86400000;
		String nDayString = new java.text.SimpleDateFormat("yyyy/MM/dd")
		.format(new Date(nDay));
		return getSqlDateFromStr(nDayString);
	}

	/**
	 * 由字符串获取时间对象
	 * 
	 * @return [java.sql.Date] 时间对象
	 */
	public static java.sql.Date getSqlDateFromStr(String strDt) {
		strDt = strDt.replace(' ', '-');
		strDt = strDt.replace('.', '-');
		strDt = strDt.replace('/', '-');
		return java.sql.Date.valueOf(strDt);
	}

	/**
	 * 得到给定日期
	 * 
	 * @param date
	 *            给定日期
	 * @param pattern
	 *            给定日期格式
	 * @return Date 给定日期
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
	 * 取得指定日期的当天初始时刻
	 * 
	 * @param date
	 *            指定日期
	 * @return
	 */
	public static Date getEndingOfDay(Date date) {
		String dateStr = getDateStrWithDASH(date);
		dateStr = dateStr + " 23:59:59.999";
		return getUtilDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 取得指定日期的当天结束时刻（最后一刻）
	 * 
	 * @param date
	 *            指定日期
	 * @return
	 */
	public static Date getBeginingOfDay(Date date) {
		String dateStr = getDateStrWithDASH(date);
		dateStr = dateStr + " 00:00:00.000";
		return getUtilDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 以"yyyy-MM-dd"格式转换”yyyymmdd“日期字串
	 * 
	 * @param date
	 *            [String]”yyyymmdd“格式的日期字串
	 * @return "yyyy-MM-dd"格式的日期字串
	 */
	public static String getDateString(String date) {
		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
				+ date.substring(6, 8);
	}

	/**
	 * 取得系统当前日期
	 * 
	 * @return 系统当前日期
	 */
	public static Date getSystemDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 取得系统当前日期(java.sql.Date)
	 * 
	 * @return 系统当前日期(java.sql.Date)
	 */
	public static java.sql.Date getSystemSqlDate() {
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}

	/**
	 * 取得系统当前时间戳
	 * 
	 * @return 系统当前时间戳
	 */
	public static Timestamp getSystemTimestamp() {
		return new Timestamp(Calendar.getInstance().getTime().getTime());
	}

	/**
	 * 取得中国地区系统当前日期
	 * 
	 * @return 系统当前日期
	 */
	public static Date getSystemDateOfChina() {
		return Calendar.getInstance(Locale.CHINA).getTime();
	}

	/**
	 * 取得指定地区的系统当前日期
	 * 
	 * @return 系统当前日期
	 * @locale 地区
	 */
	public static Date getSystemDate(Locale locale) {
		return Calendar.getInstance(locale).getTime();
	}

	/**
	 * 根据年月日获取日期
	 * 
	 * @param year
	 *            年 如 ：2012
	 * @param month
	 *            月 如：1-12
	 * @param day
	 *            日 如：1-31
	 * @return 指定年月日的日期
	 */
	@SuppressWarnings("deprecation")
	public static Date getDate(int year, int month, int day) {
		Date date = new Date(year - 1900, month - 1, day);
		return date;
	}

	/**
	 * 获取当前年份
	 * 
	 * @return 当前年份
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentYear() {
		return getSystemDate().getYear() + 1900;
	}

	/**
	 * 获取当前月份
	 * 
	 * @return 当前月份
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentMonth() {
		return getSystemDate().getMonth() + 1;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return 当前日期
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentDay() {
		return getSystemDate().getDate();
	}


	public static int getDaysByYeayAndMonth(int year, int month) {
		Calendar rightNow = Calendar.getInstance();
		//如果写成年月日的形式的话，要写小d，如："yyyy/MM/dd"
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
		try {
			String str = year + "/" + month;
			//要计算你想要的月份，改变这里即可
			rightNow.setTime(simpleDate.parse(str)); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	/**
	 * 获取yyyyMM格式的当前日期字符串
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 下午6:47:08
	 */
	public static String getCurrentYearAndMonth() {
		Date nowDate = getSystemDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(nowDate);
	}

	/**
	 * 获取yyyyMMddHHmmssS格式的当前日期字符串
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-8-17 下午6:47:08
	 */
	public static String getCurrentTime() {
		Date nowDate = getSystemDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssS");
		return format.format(nowDate);
	}

	/**
	 * 日期比较
	 * 
	 * @param date1
	 *            日期参数1
	 * @param date2
	 *            日期参数2
	 * @return 大于0时，date1大于date2；等于0时，date1等于date2；小于0时，date1小于date2;
	 */
	public static int compareTo(Date date1, Date date2) {
		if (date1 == null) {
			throw new RuntimeException("日期不能为空！");
		}

		if (date2 == null) {
			throw new RuntimeException("日期不能为空！");
		}

		return date1.compareTo(date2);
	}

	/**
	 * 日期比较
	 * 
	 * @param date1
	 *            日期参数1
	 * @param date2
	 *            日期参数2
	 * @return date1 == date2时， 返回true；否则返回false
	 */
	public static boolean eq(Date date1, Date date2) {
		return compareTo(date1, date2) == 0;
	}

	/**
	 * 日期比较
	 * 
	 * @param date1
	 *            日期参数1
	 * @param date2
	 *            日期参数2
	 * @return date1 < date2 时，返回true；否则返回false
	 */
	public static boolean lt(Date date1, Date date2) {
		return compareTo(date1, date2) < 0;
	}

	/**
	 * 日期比较
	 * 
	 * @param date1
	 *            日期参数1
	 * @param date2
	 *            日期参数2
	 * @return date1 <= date2 时，返回true；否则返回false
	 */
	public static boolean le(Date date1, Date date2) {
		return compareTo(date1, date2) <= 0;
	}

	/**
	 * 日期比较
	 * 
	 * @param date1
	 *            日期参数1
	 * @param date2
	 *            日期参数2
	 * @return date1 > date2 时，返回true；否则返回false
	 */
	public static boolean gt(Date date1, Date date2) {
		return compareTo(date1, date2) > 0;
	}

	/**
	 * 日期比较
	 * 
	 * @param date1
	 *            日期参数1
	 * @param date2
	 *            日期参数2
	 * @return date1 >= date2 时，返回true；否则返回false
	 */
	public static boolean ge(Date date1, Date date2) {
		return compareTo(date1, date2) >= 0;
	}

	/**
	 * 
	 * 两个日期进行比较
	 * @param date1
	 * @param date2
	 * @return date1早于或等于date2，则返回true，否则返回false
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
	 * 两个日期进行比较
	 * @param date1
	 * @param date2
	 * @return date1早于date2，则返回true，否则返回false
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
