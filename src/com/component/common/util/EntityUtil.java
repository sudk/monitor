package com.component.common.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

/**
 * 
 * 实体帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-7-27 上午9:01:17
 */
public class EntityUtil implements Serializable {
	private EntityUtil() {
	}

	private static final long serialVersionUID = 2439489274771081474L;
	private static final Logger logger = Logger.getLogger(EntityUtil.class);
	private static EntityUtil instance = null;

	public static EntityUtil getInstance() {
		if (instance == null) {
			synchronized (EntityUtil.class) {
				if (instance == null) {
					instance = new EntityUtil();
				}
			}
		}
		return instance;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// /////////////////////////////////////////

	/**
	 * 根据实体类对象，获取表名
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 上午9:05:16
	 */
	public static <E> String getTableName(Class<E> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (null != table) {
			return table.name();
		}
		return null;
	}

	/**
	 * 根据实体类对象，获取定义的所有属性名和列名
	 * 
	 * @param clazz
	 *            实体类对象
	 * @return Map<String,String> = Map<属性名,列名>
	 * @author LiuKun
	 * @created 2012-7-27 上午9:13:54
	 */
	public static <E> Map<String, String> getColumnMap(Class<E> clazz) {
		Map<String, Method> getMethodMap = ReflectUtil
				.getGetMethodMapByEntityClass(clazz);
		Map<String, String> columnMap = new HashMap<String, String>();
		for (Method method : getMethodMap.values()) {
			Column column = method.getAnnotation(Column.class);
			if (null != column) {
				String columnName = column.name();
				String methodName = method.getName();
				String name = methodName.replaceFirst("get", "");
				String fieldName = Character.toString(name.charAt(0))
						.toLowerCase() + name.substring(1, name.length());
				columnMap.put(fieldName, columnName);
			}
		}
		return columnMap;
	}

	/**
	 * 根据实体类对象，获取定义的所有列名
	 * 
	 * @param clazz
	 *            实体类对象
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 上午9:13:54
	 */
	public static <E> List<String> getColumnNameList(Class<E> clazz) {
		List<String> columnList = new ArrayList<String>();
		Map<String, String> columnMap = getColumnMap(clazz);
		columnList.addAll(columnMap.values());
		return columnList;
	}

	/**
	 * 根据实体类对象，获取与数据库表列关联的属性名
	 * 
	 * @param clazz
	 *            实体类对象
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 上午9:13:54
	 */
	public static <E> List<String> getFieldNameList(Class<E> clazz) {
		List<String> fieldNameList = new ArrayList<String>();
		Map<String, String> columnMap = getColumnMap(clazz);
		fieldNameList.addAll(columnMap.keySet());
		return fieldNameList;
	}

	/**
	 * 获取实体对应表的主键列名
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 上午10:05:17
	 */
	public static <E> String getPKColumnName(Class<E> clazz) {
		Map<String, Method> getMethodMap = ReflectUtil
				.getGetMethodMapByEntityClass(clazz);
		for (Method method : getMethodMap.values()) {
			Column column = method.getAnnotation(Column.class);
			if (null != column) {
				Id id = method.getAnnotation(Id.class);
				if (null != id) {
					String columnName = column.name();
					return columnName;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取实体对应表的主键的实体属性名
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 上午10:05:17
	 */
	public static <E> String getPKFieldName(Class<E> clazz) {
		Map<String, Method> getMethodMap = ReflectUtil
				.getGetMethodMapByEntityClass(clazz);
		for (Method method : getMethodMap.values()) {
			Column column = method.getAnnotation(Column.class);
			if (null != column) {
				Id id = method.getAnnotation(Id.class);
				if (null != id) {
					String methodName = method.getName();
					String name = methodName.replaceFirst("get", "");
					String fieldName = Character.toString(name.charAt(0))
							.toLowerCase() + name.substring(1, name.length());
					return fieldName;
				}
			}
		}
		return null;
	}
	
}
