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
 * Description: ֵ�����ʽ��װ����
 * 
 * @author guo_lei
 * @version 1.0
 * @created 2012-8-4 ����2:34:26
 */
public class ValueDisplayUtil {
	private static final Logger logger = Logger
			.getLogger(ValueDisplayUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	// ��������
	public static final String DATA_TYPE_STRING = "string";
	public static final String DATA_TYPE_NUMBER = "number";
	public static final String DATA_TYPE_DATE = "date";
	public static final String DATA_TYPE_TIME = "time";

	// ���ݸ�ʽ
	public static final String DATA_FMT_NUMBER_INT = "#####";
	public static final String DATA_FMT_NUMBER_FLOAT = "0.0000";
	public static final String DATA_FMT_NUMBER_LINT = "###,###";
	public static final String DATA_FMT_NUMBER_DEC = "###,##0.0000";
	public static final String DATA_FMT_NUMBER5_PER = "###,##0.0000%";
	public static final String DATA_FMT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
	public static final String DATA_FMT_DATE = "yyyy-MM-dd";
	public static final String DATA_FMT_SHORTDATE = "yyyyMMdd";
	public static final String DATA_FMT_TIME = "HH:mm:ss";

	// С���㱣��λ��
	public static final int NUM_PRECISION = 4;

	/**
	 * ��ָ�����󣬰��ո������ͽ��и�ʽ��
	 * 
	 * @param colObj
	 *            ָ����ֵ����
	 * @param type
	 *            ����������
	 * @return ��ʽ�����ֵ��ʾ�ַ���
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
	 * ���� �����ַ���ת��Ϊ����
	 * 
	 * @param numStr
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 ����3:00:42
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
	 * ����:���ַ���ת��Ϊ����
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @author guo_lei
	 * @created 2012-8-4 ����3:01:03
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
		throw new Exception("���ڴ���ʽ�쳣����!");
	}

	/**
	 * 
	 * ���� :�� �����ʽ��ֵת��Ϊ���ֻ����ַ���
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 ����3:01:24
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
	 * ���� :�� �����ʽ��ֵת��Ϊ���ڵ��ַ���
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 ����3:02:47
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
	 * ���� :�� �����ʽ��ֵת��Ϊʱ����ַ���
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 ����3:03:38
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
	 * ���� :�������ʽ��ֵ��ת��Ϊ�ַ���
	 * 
	 * @param colObj
	 * @return
	 * @author guo_lei
	 * @created 2012-8-4 ����3:03:59
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
	 * ��ָ����ֵ�����ո��������ͣ���ʽ��Ϊֵ�ִ���
	 * 
	 * @param resultList
	 *            list��ÿ��Ԫ����һ��HashMap���󣻶����keyΪ��ֵ��valueΪ��Ӧ��ֵ����
	 * @param typeMap
	 *            type
	 *            keyΪ��ֵ����ӦresultList��Ԫ�ص�Map�ļ�ֵ��valueΪ��Ӧ�����ͣ�string��number��date��
	 * @return ��ʽ�����ֵlist��ÿ��Ԫ��������HashMap���󣻶����keyΪ��ֵ��valueΪ��Ӧ�ĸ�ʽ�����ֵ�ַ���
	 */
	public static List<Map<String, Object>> resultDisplayDecorator(
			List<Map<String, Object>> resultList, Map<String, Object> typeMap,
			Map<String, Object> fmtMap) throws Exception {
		List<Map<String, Object>> result = null;
		// ��ʽ�����
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
