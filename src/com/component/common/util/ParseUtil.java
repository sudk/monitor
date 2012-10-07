package com.component.common.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 类型转换帮助类
 * 
 * @author LiuKun
 * @date 2012-6-12
 * @time 21:33
 */
public class ParseUtil implements Serializable {
	private ParseUtil() {

	}

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7780235957568510559L;
	private static final Logger logger = Logger.getLogger(ParseUtil.class);
	private static ParseUtil instance;

	public static ParseUtil getInstance() {
		if (instance == null) {
			synchronized (ParseUtil.class) {
				if (instance == null) {
					instance = new ParseUtil();
				}
			}
		}
		return instance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setInstance(ParseUtil instance) {
		ParseUtil.instance = instance;
	}

	public static Logger getLogger() {
		return logger;
	}

	// *************************************************************************************************************

	public static String parseString(Object value) {
		if (Validator.isEmpty(value)) {
			return "";
		}

		if (value instanceof Date || value instanceof java.sql.Date) {
			return parseDateToString(value);
		}

		return value.toString().trim();
	}

	/**
	 * 将对象（long(1545454154545)/java.sql.Date/java.util.Date/String(yyyy-MM-dd)）
	 * 转成yyyy-MM-dd日期对象
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date parseDate(Object value) throws Exception {
		if (Validator.isEmpty(value)) {
			return null;
		}

		// java.util.Date
		if (value instanceof Date) {
			Date tempDate = (Date) value;
			Date date = new Date(tempDate.getYear(), tempDate.getMonth(),
					tempDate.getDate());
			return date;
		}

		// java.lang.String
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (value instanceof String) {
			if (value.toString().trim().matches("[\\d]+")) {
				Date date = new Date(Long.parseLong(value.toString().trim()));
				return date;
			} else if (value.toString().trim()
					.matches("(\\d{4}-\\d{1,2}-\\d{1,2}).*")) {
				try {
					Date date = format.parse(value.toString().trim());
					return date;
				} catch (ParseException e) {
					throw e;
				}
			}
		}

		// java.sql.Date
		if (value instanceof java.sql.Date) {
			java.sql.Date sqlDate = (java.sql.Date) value;
			Date date = new Date(sqlDate.getYear(), sqlDate.getMonth(),
					sqlDate.getDate());
			return date;
		}

		// java.sql.Timestamp
		if (value instanceof java.sql.Timestamp) {
			java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
			Date date = new Date(timestamp.getYear(), timestamp.getMonth(),
					timestamp.getDate());
			return date;
		}

		return null;
	}

	/**
	 * 将对象（long(1545454154545)/java.sql.Date/java.util.Date/String(yyyy-MM-dd)）
	 * 转成yyyy-MM-dd字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String parseDateToString(Object value) {
		if (Validator.isEmpty(value)) {
			return "";
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		if (value instanceof String) {
			if (value.toString().trim().matches("[\\d]+")) {
				date = format.format(new Date(Long.parseLong(value.toString()
						.trim())));
			} else if (value.toString().trim()
					.matches("(\\d{4}-\\d{1,2}-\\d{1,2}).*")) {
				date = value.toString().trim();
			}
		} else if (value instanceof Date || value instanceof java.sql.Date) {
			date = format.format(value);
		}

		return date;
	}

	/**
	 * 转换为Integer类型（空值转换为0）
	 * 
	 * @param value
	 * @return
	 */
	public static Integer parseInteger(Object value) {
		Integer temp = null;
		if (Validator.isEmpty(value)) {
			temp = 0;
		} else {
			if (value instanceof Integer) {
				return (Integer) value;
			}

			if (value instanceof Number) {
				Number num = (Number) value;
				NumberFormat numberFormat = new DecimalFormat("##");
				try {
					return Integer.parseInt(numberFormat.format(num));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}

			if (value.toString().trim().matches("[\\d]*")) {
				temp = Integer.parseInt(value.toString().trim());
			} else {
				temp = 0;
			}
		}
		return temp;
	}

	/**
	 * 转换为Long类型（空值转换为0L）
	 * 
	 * @param value
	 * @return
	 */
	public static Long parseLong(Object value) {
		Long temp = null;
		if (Validator.isEmpty(value)) {
			temp = 0L;
		} else {
			if (value.toString().trim().matches("[\\d]*")) {
				temp = Long.parseLong(value.toString().trim());
			} else {
				temp = 0L;
			}
		}
		return temp;
	}

	/**
	 * Object转换为Double类型（空值转换为0D）
	 * 
	 * @param value
	 * @return
	 */
	public static Double parseDouble(Object value) {
		Double temp = 0D;

		if (Validator.isEmpty(value)) {
			return 0D;
		}

		if (value instanceof Double || value.getClass().equals(double.class)) {
			Double d = (Double) value;
			return d;
		}

		if (value instanceof String) {
			String str = (String) value;
			if (str.matches("[\\d]+[.]?[\\d]+")) {
				Double d = Double.valueOf(str);
				return d;
			}
		}

		if (value instanceof java.lang.Number) {// BigDecimal/BigInteger/Byte/Double/Float/Long/Integer/Short
			java.lang.Number number = (Number) value;
			try {
				return number.doubleValue();
			} catch (Exception e) {
				if (number.toString().matches("[\\d]+[.]?[\\d]+")) {
					Double d = Double.valueOf(number.toString());
					return d;
				}
			}
		}

		return temp;
	}

	public static Boolean parseBoolean(Object value) throws Exception {
		if (Validator.isEmpty(value)) {
			return null;
		}

		if (value instanceof Boolean) {
			return (Boolean) value;
		}

		if (value instanceof String) {
			String temp = (String) value;
			if ("false".equalsIgnoreCase(temp) || "0".equalsIgnoreCase(temp)) {
				return false;
			} else if ("true".equalsIgnoreCase(temp)
					|| "1".equalsIgnoreCase(temp)) {
				return true;
			}
		}

		if (value instanceof Integer) {
			Integer temp = (Integer) value;
			if (temp == 0) {
				return false;
			} else if (temp == 1) {
				return true;
			}
		}

		return null;
	}
}
