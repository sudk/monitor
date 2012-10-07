package com.component.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * 包装类帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 上午11:02:55
 */
public class PackageUtil implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6077690721364179906L;

	private PackageUtil() {
	}

	private static final Logger logger = Logger.getLogger(PackageUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	private static PackageUtil instance;

	public static PackageUtil getInstance() {
		if (null == instance) {
			synchronized (PackageUtil.class) {
				if (null == instance) {
					instance = new PackageUtil();
				}
			}
		}

		return instance;
	}

	// ///////////////////////////////////////////////////////////////////////

	/**
	 * 基本数据类型及其相对应的包装类 Map<基本数据类型, 相对应的包装类>
	 */
	public static final Map<String, String> packageClassMap = new HashMap<String, String>();
	static {
		packageClassMap.put(byte.class.getName(), Byte.class.getName());
		packageClassMap.put(boolean.class.getName(), Boolean.class.getName());
		packageClassMap.put(char.class.getName(), Character.class.getName());
		packageClassMap.put(short.class.getName(), Short.class.getName());
		packageClassMap.put(int.class.getName(), Integer.class.getName());
		packageClassMap.put(long.class.getName(), Long.class.getName());
		packageClassMap.put(float.class.getName(), Float.class.getName());
		packageClassMap.put(double.class.getName(), Double.class.getName());
	}

	/**
	 * 判断两种类型是否相等（忽略基本数据类型和包装类，即：int = java.lang.Integer）
	 * 
	 * @param type1
	 * @param type2
	 * @return
	 */
	public static boolean equalsIgnorePackageClass(String type1, String type2) {
		if (type1 == null || type2 == null || "".equalsIgnoreCase(type1.trim())
				|| "".equalsIgnoreCase(type2.trim())) {
			return false;
		}

		boolean isEq = false;
		if (type1.trim().equalsIgnoreCase(type2.trim())) {
			isEq = true;
		} else {
			if (packageClassMap.containsKey(type1.trim())) {
				type1 = packageClassMap.get(type1.trim());
			}
			if (packageClassMap.containsKey(type2.trim())) {
				type2 = packageClassMap.get(type2.trim());
			}
			if (type1.trim().equalsIgnoreCase(type2.trim())) {
				isEq = true;
			}
		}
		return isEq;
	}
}
