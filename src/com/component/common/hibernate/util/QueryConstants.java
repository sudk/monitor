package com.component.common.hibernate.util;

/**
 * 查询操作常量
 * 
 * @author LiuKun
 * @version 1.1
 * @created 2012-8-27 下午6:53:30
 */
public interface QueryConstants {

	/**
	 * 升序
	 */
	String ASC = "ASC";
	/**
	 * 降序
	 */
	String DESC = "DESC";

	/**
	 * =
	 */
	String EQ = "EQ";
	String LIKE_START = "%LIKE";
	String LIKE_END = "LIKE%";
	String LIKE = "LIKE";
	/**
	 * <
	 */
	String LT = "LT";
	/**
	 * <=
	 */
	String LE = "LE";
	/**
	 * >=
	 */
	String GE = "GE";
	/**
	 * >
	 */
	String GT = "GT";
	/**
	 * !=或<>
	 */
	String NE = "NE";
	String IN = "IN";
	String NOT_IN = "NOT_IN";
	/**
	 * between...and
	 */
	String BETWEEN = "BETWEEN";
	String SQL = "SQL";
}
