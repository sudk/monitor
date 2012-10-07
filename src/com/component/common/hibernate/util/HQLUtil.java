package com.component.common.hibernate.util;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Query;

/**
 * HQLÓï¾ä°ïÖúÀà(Hibernate query language)
 * 
 * @author LiuKun
 * @version 1.1
 */
public class HQLUtil implements Serializable {
	private HQLUtil() {

	}

	/**
	 * ÐòÁÐºÅ
	 */
	private static final long serialVersionUID = -655880589256046154L;

	private static final Logger logger = Logger.getLogger(HQLUtil.class);

	private static HQLUtil instance;

	public static HQLUtil getInstance() {
		if (instance == null) {
			synchronized (HQLUtil.class) {
				if (instance == null) {
					instance = new HQLUtil();
				}
			}
		}
		return instance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	public static void setParameter(Query query, Object... values) {
		if (query != null && values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
	}

	public static void setParameter(Query query, Map<String, Object> map) {
		if (query != null && map != null && map.size() > 0) {
			for (Entry<String, Object> entry : map.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	public static void setParameter(Query query, Map<String, Object> setmap,
			Map<String, Object> wheremap) {
		int count = 0;
		if (setmap != null && setmap.size() != 0) {
			for (Entry<String, Object> elem : setmap.entrySet()) {
				query.setParameter(count++, elem.getValue());
			}
		}

		if (wheremap != null && wheremap.size() != 0) {
			for (Entry<String, Object> elem : wheremap.entrySet()) {
				String key = elem.getKey();
				if (checkHasWord(key, "in")) {
					Object[] objs = (Object[]) elem.getValue();
					for (int i = 0; i < objs.length; i++) {
						query.setParameter(count++, objs[i]);
					}
				} else {
					query.setParameter(count++, elem.getValue());
				}
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ÅÐ¶ÏÄ³×Ö·û´®ÖÐÊÇ·ñº¬ÓÐÄ³µ¥´Ê
	 * 
	 * @param src
	 *            Ä¸×Ö·û´®
	 * @param word
	 *            ´ý¼ìÑéµ¥´Ê
	 * @return ´æÔÚ·µ»Øtrue£¬²»´æÔÚ·µ»Øfalse
	 */
	public static boolean checkHasWord(String src, String word) {
		if (src == null) {
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
	}

	public static String getFormatSetMap(Map<String, Object> setmap) {
		if (setmap == null || setmap.size() == 0) {
			return "";
		}
		StringBuilder hql = new StringBuilder();
		hql.append(" set ");
		for (Entry<String, Object> elem : setmap.entrySet()) {
			hql.append(elem.getKey() + "= ? ,");
		}
		hql.deleteCharAt(hql.length() - 1);
		return hql.toString();
	}

	public static String getFormatWhereMap(Map<String, Object> wheremap) {
		if (wheremap == null || wheremap.size() == 0) {
			return "";
		}
		StringBuilder hql = new StringBuilder();
		hql.append(" where ");
		for (Entry<String, Object> elem : wheremap.entrySet()) {
			String key = elem.getKey();
			if (key.indexOf("=") != -1 || key.indexOf(">") != -1
					|| key.indexOf("<") != -1 || key.indexOf(">=") != -1
					|| key.indexOf("<=") != -1 || key.indexOf("!=") != -1) {
				hql.append(key + " ? and ");
				continue;
			}

			if (checkHasWord(key, "like")) {
				hql.append(key + " ? and ");
				continue;
			}

			if (checkHasWord(key, "in")) {
				hql.append(key + " ( ");
				Object[] objs = (Object[]) elem.getValue();
				for (int i = 0; i < objs.length; i++) {
					hql.append("?");
					if (i != objs.length - 1) {
						hql.append(",");
					}
				}
				hql.append(" ) and ");
				continue;
			}
			hql.append(key + " = ? and ");

		}
		hql.delete(hql.length() - 5, hql.length() - 1);
		return hql.toString();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////

}
