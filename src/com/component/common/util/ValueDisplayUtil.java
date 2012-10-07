package com.component.common.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * Description: 值对象格式化装饰器
 * 
 * @author guo_lei
 * @version 1.0
 * @created 2012-8-4 下午2:34:26
 */
public class ValueDisplayUtil {
	private static final Logger logger = Logger
			.getLogger(ValueDisplayUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	// 数据类型
	public static final String DATA_TYPE_STRING = "string";
	public static final String DATA_TYPE_NUMBER = "number";
	public static final String DATA_TYPE_DATE = "date";
	public static final String DATA_TYPE_TIME = "time";

	// 数据格式
	public static final String DATA_FMT_NUMBER_INT = "#####";
	public static final String DATA_FMT_NUMBER_FLOAT = "0.0000";
	public static final String DATA_FMT_NUMBER_LINT = "###,###";
	public static final String DATA_FMT_NUMBER_DEC = "###,##0.0000";
	public static final String DATA_FMT_NUMBER5_PER = "###,##0.0000%";
	public static final String DATA_FMT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
	public static final String DATA_FMT_DATE = "yyyy-MM-dd";
	public static final String DATA_FMT_SHORTDATE = "yyyyMMdd";
	public static final String DATA_FMT_TIME = "HH:mm:ss";

	// 小数点保留位数
	public static final int NUM_PRECISION = 4;

	/**
	 * 将指定对象，按照给定类型进行格式化
	 * 
	 * @param colObj
	 *            指定的值对象
	 * @param type
	 *            给定的类型
	 * @return 格式化后的值显示字符串
	 */
	public static String formatColValueStringByType(Object colObj, String type,
			String fmt) {
		if (colObj == null) {
			return null;
		}
		String colStr = null;

		if (DATA_TYPE_DATE.equals(type)) {
			if (fmt != null) {
				try {
					if (colObj instanceof String) {
						colObj = converStringToDate((String) colObj);
					} else if (colObj instanceof Integer) {
						colObj = converStringToDate(String.valueOf(colObj));
					} else if (colObj instanceof BigDecimal) {
						DecimalFormat format1 = new DecimalFormat("####");
						String dStr = format1.format(colObj);
						colObj = converStringToDate(dStr);
					}
					SimpleDateFormat format = new SimpleDateFormat(fmt);
					colStr = format.format(colObj);
				} catch (Exception e) {

					colStr = convertColValueToDateStr(colObj);
				}
			} else {
				colStr = convertColValueToDateStr(colObj);
			}

		} else if (DATA_TYPE_TIME.equals(type)) {
			if (fmt != null) {
				try {
					if (colObj instanceof String) {
						colObj = converStringToDate((String) colObj);
					}
					SimpleDateFormat format = new SimpleDateFormat(fmt);
					colStr = format.format(colObj);
				} catch (Exception e) {
					colStr = convertColValueToTimeStr(colObj);
				}
			} else {
				colStr = convertColValueToTimeStr(colObj);
			}

		} else if (DATA_TYPE_NUMBER.equals(type)) {
			if (fmt != null) {
				DecimalFormat format = new DecimalFormat(fmt);
				if (colObj instanceof String) {
					colObj = converStringToNumber((String) colObj);
				}
				colStr = format.format(colObj);
			} else {
				colStr = convertColValueToNumStr(colObj);
			}

		} else {
			colStr = convertColValueToString(colObj);

		}
		return colStr;
	}

	/**
	 * 
	 * 描述 ：将字符串转化为数字
	 * 
	 * @param numStr
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 下午3:00:42
	 */
	private static BigDecimal converStringToNumber(String numStr) {
		if (numStr == null) {
			return null;
		}
		BigDecimal num = new BigDecimal(numStr);
		return num;
	}

	/**
	 * 
	 * 描述:将字符串转化为日期
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @author guo_lei
	 * @created 2012-8-4 下午3:01:03
	 */
	private static Date converStringToDate(String dateStr) throws Exception {
		if (dateStr == null) {
			return null;
		}
		if (dateStr.length() == DATA_FMT_SHORTDATE.length()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					DATA_FMT_SHORTDATE);
			return dateFormat.parse(dateStr);
		}
		if (dateStr.length() == DATA_FMT_DATE.length()
				&& dateStr.indexOf("-") > 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FMT_DATE);
			return dateFormat.parse(dateStr);
		}
		if (dateStr.length() == DATA_FMT_TIME.length()
				&& dateStr.indexOf(":") > 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FMT_TIME);
			return dateFormat.parse(dateStr);
		}
		if (dateStr.length() == DATA_FMT_TIMESTAMP.length()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					DATA_FMT_TIMESTAMP);
			return dateFormat.parse(dateStr);
		}
		throw new Exception("日期串格式异常错误!");
	}

	/**
	 * 
	 * 描述 :将 任意格式的值转化为数字化的字符串
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 下午3:01:24
	 */
	private static String convertColValueToNumStr(Object colObj) {
		String colStr = null;
		if (colObj == null) {
			colStr = "";
		} else {
			if (colObj instanceof java.sql.Date) {
				java.sql.Date date = (java.sql.Date) colObj;
				colStr = DateUtil.getDateStri(new Date(date.getTime()));
			} else if (colObj instanceof Timestamp) {
				Timestamp time = (Timestamp) colObj;
				colStr = String.valueOf(time.getTime());
			} else if (colObj instanceof Double) {
				DecimalFormat df = new DecimalFormat("0.0000");
				colStr = df.format(colObj);
			} else if (colObj instanceof BigDecimal) {
				colStr = ((BigDecimal) colObj).toPlainString();
			} else {
				colStr = colObj.toString();
			}

			String res = "";
			if (colStr.indexOf(".") >= 0) {
				String[] temp1 = StringUtil.split(colStr, ".");
				res += temp1[0];
			} else {
				res = colStr;
			}
			BigDecimal dec = new BigDecimal(res);
			NumberFormat numberFormat = NumberFormat.getInstance();
			res = numberFormat.format(dec);

			if (colStr.indexOf(".") >= 0) {
				res += ".";
				String[] temp2 = StringUtil.split(colStr, ".");
				if (temp2[1].length() > NUM_PRECISION) {
					temp2[1] = temp2[1].substring(0, NUM_PRECISION);
				}
				res += temp2[1];
			}
			colStr = res;
		}
		return colStr;
	}

	/**
	 * 
	 * 描述 :将 任意格式的值转化为日期的字符串
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 下午3:02:47
	 */
	private static String convertColValueToDateStr(Object colObj) {
		String colStr = null;
		if (colObj == null) {
			colStr = "";
		} else if (colObj instanceof java.sql.Date) {

			java.sql.Date date = (java.sql.Date) colObj;

			colStr = DateUtil.getDateStrWithDASH(new Date(date.getTime()));
		} else if (colObj instanceof Timestamp) {
			Timestamp time = (Timestamp) colObj;
			colStr = DateUtil.getFullText(new Date(time.getTime()));
		} else if (colObj instanceof BigDecimal) {
			String res = "";
			BigDecimal dec = (BigDecimal) colObj;
			colStr = dec.toString();
			if (colStr.indexOf(".") >= 0) {
				String[] temp1 = StringUtil.split(colStr, ".");
				res += temp1[0];
			} else {
				res = colStr;
			}
			colStr = StringUtil.replace(res, ",", "");
		} else {
			colStr = colObj.toString();
		}
		colStr = colStr.trim();
		if (colStr.length() == 8 && Validator.isDigital(colStr)) {
			colStr = colStr.substring(0, 4) + "-" + colStr.substring(4, 6)
					+ "-" + colStr.substring(6, 8);
		}
		if (Validator.isDigital(colStr) && colStr.length() <= 6) {
			if (colStr.length() == 5) {
				colStr = "0" + colStr;
			} else if (colStr.length() == 4) {
				colStr = "00" + colStr;
			} else if (colStr.length() == 3) {
				colStr = "000" + colStr;
			} else if (colStr.length() == 2) {
				colStr = "0000" + colStr;
			} else if (colStr.length() == 1) {
				colStr = "00000" + colStr;
			}
			colStr = colStr.substring(0, 2) + ":" + colStr.substring(2, 4)
					+ ":" + colStr.substring(4, 6);
		}
		return colStr;
	}

	/**
	 * 
	 * 描述 :将 任意格式的值转化为时间的字符串
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 下午3:03:38
	 */
	private static String convertColValueToTimeStr(Object colObj) {
		String colStr = null;
		if (colObj == null) {
			colStr = "";
		} else if (colObj instanceof java.sql.Date) {

			java.sql.Date date = (java.sql.Date) colObj;

			colStr = DateUtil.getDateStrWithDASH(new Date(date.getTime()));
		} else if (colObj instanceof Timestamp) {
			Timestamp time = (Timestamp) colObj;
			colStr = DateUtil.getFullText(new Date(time.getTime()));
		} else if (colObj instanceof BigDecimal) {
			String res = "";
			BigDecimal dec = (BigDecimal) colObj;
			colStr = dec.toString();
			if (colStr.indexOf(".") >= 0) {
				String[] temp1 = StringUtil.split(colStr, ".");
				res += temp1[0];
			} else {
				res = colStr;
			}
			colStr = StringUtil.replace(res, ",", "");

		} else {
			colStr = colObj.toString();
		}
		colStr = colStr.trim();
		if (Validator.isDigital(colStr) && colStr.length() <= 6) {
			if (colStr.length() == 5) {
				colStr = "0" + colStr;
			} else if (colStr.length() == 4) {
				colStr = "00" + colStr;
			} else if (colStr.length() == 3) {
				colStr = "000" + colStr;
			} else if (colStr.length() == 2) {
				colStr = "0000" + colStr;
			} else if (colStr.length() == 1) {
				colStr = "00000" + colStr;
			}
			colStr = colStr.substring(0, 2) + ":" + colStr.substring(2, 4)
					+ ":" + colStr.substring(4, 6);
		}
		return colStr;
	}

	/**
	 * 
	 * 描述 :将任意格式的值，转化为字符串
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 下午3:03:59
	 */
	private static String convertColValueToString(Object colObj) {
		String colStr = null;
		if (colObj == null) {
			colStr = "";
		} else if (colObj instanceof java.sql.Date) {
			java.sql.Date date = (java.sql.Date) colObj;
			colStr = DateUtil.getDateStrWithDASH(new Date(date.getTime()));
		} else if (colObj instanceof Timestamp) {
			Timestamp time = (Timestamp) colObj;
			colStr = DateUtil.getFullText(new Date(time.getTime()));
		} else if (colObj instanceof BigDecimal) {
			String res = "";
			BigDecimal dec = (BigDecimal) colObj;
			NumberFormat numberFormat = NumberFormat.getInstance();
			colStr = numberFormat.format(dec);
			if (colStr.indexOf(".") >= 0) {
				String[] temp1 = StringUtil.split(colStr, ".");
				res += temp1[0];
			} else {
				res = colStr;
			}
			colStr = dec.toPlainString();
			if (colStr.indexOf(".") >= 0) {
				res += ".";
				String[] temp2 = StringUtil.split(colStr, ".");
				res += temp2[1];
			}
			colStr = res;
		} else {
			colStr = colObj.toString();
		}
		return colStr;
	}

	/**
	 * 将指定的值集按照给定的类型，格式化为值字串。
	 * 
	 * @param resultList
	 *            list的每个元素是一个HashMap对象；对象的key为键值；value为对应的值对象
	 * @param typeMap
	 *            type
	 *            key为键值，对应resultList中元素的Map的键值；value为对应的类型（string，number，date）
	 * @return 格式化后的值list，每个元素依旧是HashMap对象；对象的key为键值；value为对应的格式化后的值字符串
	 */
	public static List<Map<String, Object>> resultDisplayDecorator(
			List<Map<String, Object>> resultList, Map<String, Object> typeMap,
			Map<String, Object> fmtMap) throws Exception {
		List<Map<String, Object>> result = null;
		// 格式化结果
		if (resultList != null) {
			result = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < resultList.size(); i++) {
				Map<String, Object> element = resultList.get(i);
				Iterator<String> it = element.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					Object colObj = element.get(key);
					String type = DATA_TYPE_STRING;
					String fmt = null;
					if (typeMap != null) {
						type = (String) typeMap.get(key.toLowerCase());
					}
					if (fmtMap != null) {
						fmt = (String) fmtMap.get(key.toLowerCase());
					}
					String colStr = formatColValueStringByType(colObj, type,
							fmt);

					element.put(key, colStr);
				}
				result.add(element);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		BigDecimal dec = new BigDecimal(20080907);
		String str = ValueDisplayUtil.formatColValueStringByType(dec, "date",
				"yyyy-MM-dd");
		System.out.println(str);
	}
}
