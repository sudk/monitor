package com.component.common.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

/**
 * 服务器帮助类
 * 
 * @author LiuKun
 * @date 2012-07-09
 * @time 15:20
 * 
 */
public class ServerUtil implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6077690721364179906L;

	private ServerUtil() {

	}

	private static final Logger logger = Logger.getLogger(ServerUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	private static ServerUtil instance;

	public static ServerUtil getInstance() {
		if (null == instance) {
			synchronized (ServerUtil.class) {
				if (null == instance) {
					instance = new ServerUtil();
				}
			}
		}

		return instance;
	}

	// ///////////////////////////////////////////////////////////////////////

	/**
	 * 根据协议和scheme获取服务端口号:如：{protocol=HTTP/1.1,scheme=http},{protocol=AJP/1.3,
	 * scheme=http}
	 * 
	 * @return 端口号
	 */
	private static String getPort(String protocol, String scheme)
			throws Exception {
		MBeanServer mBeanServer = null;
		if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
			mBeanServer = (MBeanServer) MBeanServerFactory
					.findMBeanServer(null).get(0);
		}

		Set<ObjectName> names = null;
		try {
			names = mBeanServer.queryNames(new ObjectName(
					"Catalina:type=Connector,*"), null);
		} catch (Exception e) {
			return "";
		}

		Iterator<ObjectName> it = names.iterator();
		ObjectName oname = null;
		while (it.hasNext()) {
			oname = it.next();
			String pvalue = (String) mBeanServer
					.getAttribute(oname, "protocol");
			String svalue = (String) mBeanServer.getAttribute(oname, "scheme");
			String port = ((Integer) mBeanServer.getAttribute(oname, "port"))
					.toString();
			if (logger.isInfoEnabled()) {
				logger.info("-----------------------------");
				logger.info(" protocol = " + pvalue);
				logger.info(" scheme = " + svalue);
				logger.info(" port = " + port);
				logger.info("-----------------------------");
			}
			if (null != protocol && protocol.equalsIgnoreCase(pvalue)
					&& null != scheme && scheme.equals(svalue)) {
				return port;
			}
		}

		return "";
	}

	/**
	 * 获取应用服务器端口号
	 * 
	 * @return 端口号
	 */
	public static String getPort() {
		try {
			return getPort("HTTP/1.1", "http");
		} catch (Exception e) {
			logger.error(e);
		}
		return "";
	}
}
