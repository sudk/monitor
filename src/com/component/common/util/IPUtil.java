package com.component.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * 
 * IP������(��ȡ������IP��ַ��ͻ���IP��ַ)
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 ����6:20:47
 */
public class IPUtil implements Serializable {
	/**
	 * ���к�
	 */
	private static final long serialVersionUID = 6077690721364179906L;

	private IPUtil() {
	}

	private static final Logger logger = Logger.getLogger(IPUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	private static IPUtil instance;

	public static IPUtil getInstance() {
		if (null == instance) {
			synchronized (IPUtil.class) {
				if (null == instance) {
					instance = new IPUtil();
				}
			}
		}

		return instance;
	}

	// ///////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * ��ȡ�ͻ�����ʵIP��ַ��ע�⣺���ͨ��Squid�����������������squid.conf�����ļ�forwarded_for��Ĭ����Ϊon��
	 * �����ȡ����IPΪunknown��
	 * 
	 * @param request
	 * @return
	 * @author LiuKun
	 * @created 2012-8-20 ����6:51:05
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static String getIpAddr(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		if (logger.isInfoEnabled()) {
			logger.info("====================================================");
		}
		if (logger.isInfoEnabled()) {
			StringBuffer buf = new StringBuffer("---all head info:---\n");
			Enumeration enumeration = request.getHeaderNames();
			while (enumeration.hasMoreElements()) {
				Object head = enumeration.nextElement();
				if (null != head) {
					String value = request.getHeader(String.valueOf(head));
					buf.append(head + "=" + value + "\n");
				}
			}
			buf.append("\n--------------");
			logger.info(buf.toString());
		}

		String ip = request.getHeader("x-forwarded-for");
		if (logger.isInfoEnabled()) {
			logger.info("request.getHeader(\"x-forwarded-for\")=" + ip);
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			if (logger.isInfoEnabled()) {
				logger.info("request.getHeader(\"X-Forwarded-For\")=" + ip);
			}
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (logger.isInfoEnabled()) {
				logger.info("request.getHeader(\"Proxy-Client-IP\")=" + ip);
			}
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			if (logger.isInfoEnabled()) {
				logger.info("request.getHeader(\"WL-Proxy-Client-IP\")=" + ip);
			}
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			if (logger.isInfoEnabled()) {
				logger.info("request.getHeader(\"HTTP_CLIENT_IP\")=" + ip);
			}
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			if (logger.isInfoEnabled()) {
				logger.info("request.getHeader(\"HTTP_X_FORWARDED_FOR\")=" + ip);
			}
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (logger.isInfoEnabled()) {
				logger.info("request.getRemoteAddr()=" + ip);
			}
		}

		if (Validator.isNotEmpty(ip) && ip.indexOf(',') != -1) {
			// ���ͨ���˶༶�������Ļ���X-Forwarded-For��ֵ����ֹһ��������һ�� IP ֵ
			// ȡX-Forwarded-For�е�һ����unknown����ЧIP�ַ���
			// �磺X-Forwarded-For��192.168.1.110, 192.168.1.120, 192.168.1.130,
			// 192.168.1.100
			// �û���ʵIPΪ�� 192.168.1.110
			// ע��:�����ʵ�ַΪ localhost ʱ ��ַ��ʽΪ 0:0:0:0:0:0:1
			if (logger.isInfoEnabled()) {
				logger.info("ip=" + ip);
			}
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				if (null != ips[i] && !"unknown".equalsIgnoreCase(ips[i])) {
					ip = ips[i];
					break;
				}
			}
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			if (logger.isEnabledFor(Priority.WARN)) {
				logger.warn("���ڿͻ��˷��ʵ�ַʹ�� localhost����ȡ�ͻ�����ʵIP��ַ������ʹ��IP��ʽ����");
			}
			//��ʱ��Ϊ�ͻ��˺ͷ���������ͬһ̨����
			ip = getServerIp();
		}

		if ("unknown".equalsIgnoreCase(ip)) {
			if (logger.isEnabledFor(Priority.WARN)) {
				logger.warn("���ڿͻ���ͨ��Squid�������������ʣ���ȡ�ͻ�����ʵIP��ַ���������squid.conf�����ļ�forwarded_for��Ĭ����Ϊon���");
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("====================================================");
		}
		return ip;
	}

	/**
	 * ��ȡ������IP��ַ(δ����)
	 * 
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 * @author LiuKun
	 * @created 2012-8-20 ����7:13:41
	 */
	public static byte[] getIp() throws UnknownHostException, SocketException {
		byte[] b = InetAddress.getLocalHost().getAddress();
		Enumeration<NetworkInterface> allNetInterfaces = null;
		allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		NetworkInterface netInterface = null;
		while (allNetInterfaces.hasMoreElements()) {
			netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			if (netInterface.getName().trim().equals("eth0")) {
				Enumeration<InetAddress> addresses = netInterface
						.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
				}
				break;
			}
		}
		if (ip != null && ip instanceof Inet4Address) {
			return b = ip.getAddress();
		}
		return b;
	}

	/**
	 * ��ȡ������IP��ַ
	 */
	@SuppressWarnings("static-access")
	public static String getServerIp() {
		try {
			return java.net.InetAddress.getLocalHost().getLocalHost()
					.getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		//
		URL url = new URL("");
		URLConnection conn = url.openConnection();
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.15) Gecko/20110303 Firefox/3.6.15");
		conn.setRequestProperty("Content-Type", "text/html");
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		InputStream is = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				"GB2312"));
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.contains("����IP��ַ��")) {
				// System.out.println(line);
				int start = line.indexOf('[') + 1;
				int end = line.indexOf(']');
				System.out.println(line.substring(start, end));
			}
		}
		br.close();
	}
}
