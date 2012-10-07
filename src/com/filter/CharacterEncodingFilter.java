package com.filter;

import java.io.IOException;
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


/**
 * 字符编码过滤器（解决中文乱码）
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-3 下午5:07:12
 */
public class CharacterEncodingFilter implements Filter {

	private static final Logger logger = Logger
			.getLogger(CharacterEncodingFilter.class);

	public static Logger getLogger() {
		return logger;
	}

	// /////////////////////////////////////
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest serveltRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (logger.isInfoEnabled()) {
			logger.info("---字符编码过滤器（解决中文乱码）---");
		}
		HttpServletRequest request = (HttpServletRequest) serveltRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		request.setCharacterEncoding(CharsetUtil.JVM_DEFAULT_CHARSET);
		response.setCharacterEncoding(CharsetUtil.JVM_DEFAULT_CHARSET);
		String method = request.getMethod();
		if ("get".equalsIgnoreCase(method) || "post".equalsIgnoreCase(method)) {
			Map<String, String[]> map = request.getParameterMap();
			CharsetUtil.changeCharsetParameterMap(map);
		}
		filterChain.doFilter(request, response);
	}

	public void destroy() {

	}

}
