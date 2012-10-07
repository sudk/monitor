package com.component.common.util;

import java.io.Serializable;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * 
 * ·��������
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-9-10 ����11:07:49
 */
public class PathUtil implements PathConstants, Serializable {
	private PathUtil() {
	}

	/**
	 * ���к�
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
	 * ��ȡ��classesΪ��Ŀ¼��·�����磺������/ufcaudit/WebContent/WEB-INF/classes
	 * 
	 * @return classesΪ��Ŀ¼��·��
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
	 * ��ȡ��WebRootΪ��Ŀ¼��·�����磺������/ufcaudit/WebContent
	 * 
	 * @return ��WebRootΪ��Ŀ¼��·��
	 */
	public static String getWebRootPath() {
		String classpath = getClassPath();
		return classpath.substring(0, classpath.indexOf("/WEB-INF/classes"));
	}

	/**
	 * ��ȡ����ĿΪ��Ŀ¼��·�����磺������/ufcaudit
	 * 
	 * @return ����ĿΪ��Ŀ¼��·��
	 */
	public static String getAppPath() {
		String rootPath = getWebRootPath();
		return rootPath.substring(0, rootPath.lastIndexOf("/"));
	}

	/**
	 * ��������ϵͳ��ͬ���µ�·�����⣨ "\"��"/"�����⣩,ͳһ�޸�Ϊ"/"
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return ��������ļ�·��
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
	 * �����ļ�·����ȡ�ļ�����
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return �ļ�����
	 */
	public static String getFileName(String path) throws Exception {
		String newPath = getFormatPath(path);// ����·����window/linux��·�����
		String fileName = newPath;
		int index = 0;
		if ((index = fileName.lastIndexOf("\\")) != -1
				|| (index = fileName.lastIndexOf("/")) != -1) {
			fileName = fileName.substring(index + 1);
		}
		return fileName;
	}

	/**
	 * ��ȡĬ��Ĭ��Tomcat��������ufcaudit url
	 * 
	 * @return Ĭ��Tomcat��������ufcaudit url
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
