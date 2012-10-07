package com.component.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * System env 帮助类
 * 
 * @author LiuKun
 * @version 1.1
 */
public class EnvUtil implements Serializable {
	private EnvUtil() {
	}

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2074750583133673728L;

	private static final Logger logger = Logger.getLogger(EnvUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static EnvUtil instance;

	public static EnvUtil getInstance() {
		if (instance == null) {
			synchronized (EnvUtil.class) {
				if (instance == null) {
					instance = new EnvUtil();
				}
			}
		}
		return instance;
	}

	// ******************************************************************************************************************

	/**
	 * 判断当前所在系统是否为windows系列的系统
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("windows") > -1) {
			return true;
		}
		return false;
	}

	/**
	 * 获取系统根目录
	 * 
	 * @return
	 */
	public static String getSystemDrive() {
		return System.getenv("SystemDrive");
	}

	/**
	 * 获取系统编码
	 * 
	 * @return
	 */
	public static String getFileEncoding() {
		return System.getProperty("file.encoding");
	}

	/**
	 * 获取jvm默认编码
	 * 
	 * @return
	 */
	public static String getDefaultCharset() {
		return Charset.defaultCharset().name();
	}

	/**
	 * 获取系统语言
	 * 
	 * @return
	 */
	public static String getUserLanguage() {
		return System.getProperty("user.language");
	}

	public static Map<String, String> getEnv() {
		Map<String, String> map = new HashMap<String, String>();
		String OS = System.getProperty("os.name").toLowerCase();

		Process p = null;
		BufferedReader br = null;
		/**
		 * 以windows为例.
		 */
		if (OS.indexOf("windows") > -1) {
			try {
				p = Runtime.getRuntime().exec("cmd /c set");
				br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					String[] str = line.split("=");
					map.put(str[0], str[1]);
				}
				br.close();
			} catch (IOException ioe) {
				logger.error(ioe.getMessage(), ioe);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}

			}
		}
		return map;
	}

	/**
	 * native2ascii
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String native2ascii(String value) throws Exception {
		if (null == value || "".equalsIgnoreCase(value)) {
			return "";
		}
		String unicode = "";
		char[] charAry = new char[value.length()];
		for (int i = 0; i < charAry.length; i++) {
			charAry[i] = (char) value.charAt(i);
			unicode += "\\u" + Integer.toString(charAry[i], 16);
		}
		return unicode;
	}
	
	/**
	 * java 调用系统计算器
	 * 
	 * @author LiuKun
	 * @created 2012-7-21 上午1:30:08
	 */
	public static void calc() {
		String f = "C:/Windows/System32/calc.exe";
		Runtime r = Runtime.getRuntime();
		try {
			r.exec("cmd /c start " + f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
