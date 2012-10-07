package com.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.component.common.util.CharsetUtil;
import com.component.common.util.URLUtil;

/**
 * 
 * 解码url中转码的字符串过滤器
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-10 下午4:27:37
 */
public class URLDecoderFilter implements Filter {

	private static final Logger logger = Logger
			.getLogger(URLDecoderFilter.class);

	public static Logger getLogger() {
		return logger;
	}

	// /////////////////////////////////////
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest serveltRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (logger.isInfoEnabled()) {
			logger.info("---解码url中转码的字符串过滤器---");
		}
		HttpServletRequest request = (HttpServletRequest) serveltRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		request.setCharacterEncoding(CharsetUtil.JVM_DEFAULT_CHARSET);
		response.setCharacterEncoding(CharsetUtil.JVM_DEFAULT_CHARSET);
		String method = request.getMethod();
		if ("get".equalsIgnoreCase(method) || "post".equalsIgnoreCase(method)) {
			@SuppressWarnings("unchecked")
			Map<String, String[]> map = request.getParameterMap();
			for (Iterator<String> iterator = map.keySet().iterator(); iterator
					.hasNext();) {
				String key = iterator.next();
				String[] value = map.get(key);
				for (int i = 0; i < value.length; i++) {
					if (logger.isInfoEnabled()) {
						logger.info("---转码之前[" + key + "=" + value[i] + "]---");
					}
					value[i] = URLUtil.decode(value[i]);
					if (logger.isInfoEnabled()) {
						logger.info("---转码之后[" + key + "=" + value[i] + "]---");
					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	public void destroy() {

	}

}
