package com.component.common.util;

import java.io.Serializable;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * 
 * 路径帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-9-10 上午11:07:49
 */
public class PathUtil implements PathConstants, Serializable {
	private PathUtil() {
	}

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7058686298008510378L;

	private static final Logger logger = Logger.getLogger(PathUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static PathUtil instance;

	public static PathUtil getInstance() {
		if (instance == null) {
			synchronized (PathUtil.class) {
				if (instance == null) {
					instance = new PathUtil();
				}
			}
		}
		return instance;
	}

	// ///////////////////////////////////

	/**
	 * 获取以classes为根目录的路径。如：。。。/ufcaudit/WebContent/WEB-INF/classes
	 * 
	 * @return classes为根目录的路径
	 */
	public static String getClassPath() {
		// return PathUtil.class.getClassLoader().getResource("").getPath();
		// return
		// Thread.currentThread().getContextClassLoader().getResource("").getPath();
		return PathUtil.class
				.getResource("")
				.getPath()
				.replaceFirst("file:/", "")
				.replaceFirst(
						"/" + PathUtil.class.getPackage().getName() + "/", "");
	}

	/**
	 * 获取以WebRoot为根目录的路径。如：。。。/ufcaudit/WebContent
	 * 
	 * @return 以WebRoot为根目录的路径
	 */
	public static String getWebRootPath() {
		String classpath = getClassPath();
		return classpath.substring(0, classpath.indexOf("/WEB-INF/classes"));
	}

	/**
	 * 获取以项目为根目录的路径。如：。。。/ufcaudit
	 * 
	 * @return 以项目为根目录的路径
	 */
	public static String getAppPath() {
		String rootPath = getWebRootPath();
		return rootPath.substring(0, rootPath.lastIndexOf("/"));
	}

	/**
	 * 修正操作系统不同导致的路径问题（ "\"与"/"的问题）,统一修改为"/"
	 * 
	 * @param path
	 *            文件路径
	 * @return 修正后的文件路径
	 */
	public static String getFormatPath(String path) {
		if (Validator.isEmpty(path)) {
			return "";
		}
		path = path.replaceAll("\\\\", "/");
		path = path.replaceAll("//", "/");
		return path;
	}

	/**
	 * 根据文件路径获取文件名称
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件名称
	 */
	public static String getFileName(String path) throws Exception {
		String newPath = getFormatPath(path);// 修正路径（window/linux的路径差别）
		String fileName = newPath;
		int index = 0;
		if ((index = fileName.lastIndexOf("\\")) != -1
				|| (index = fileName.lastIndexOf("/")) != -1) {
			fileName = fileName.substring(index + 1);
		}
		return fileName;
	}

	/**
	 * 获取默认默认Tomcat服务器下ufcaudit url
	 * 
	 * @return 默认Tomcat服务器下ufcaudit url
	 */
	@SuppressWarnings("static-access")
	public static String getUfcauditUrl() {
		String url = null;
		try {
			url = "http://"
					+ java.net.InetAddress.getLocalHost().getLocalHost()
							.getHostAddress() + ":8080/ufcaudit";
		} catch (UnknownHostException e) {
			url = "http://127.0.0.1:8080/ufcaudit";
			logger.error(e);
		}
		return url;
	}
}
