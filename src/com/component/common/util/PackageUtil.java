package com.component.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * ��װ�������
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 ����11:02:55
 */
public class PackageUtil implements Serializable {
	/**
	 * ���к�
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
	 * �����������ͼ������Ӧ�İ�װ�� Map<������������, ���Ӧ�İ�װ��>
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
	 * �ж����������Ƿ���ȣ����Ի����������ͺͰ�װ�࣬����int = java.lang.Integer��
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
