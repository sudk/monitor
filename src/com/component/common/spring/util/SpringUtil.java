package com.component.common.spring.util;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Spring帮助类
 * 
 * @author LiuKun
 * @date 2012-07-09
 * @time 15:20
 * 
 */
public class SpringUtil implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6077690721364179906L;

	private SpringUtil() {

	}

	private static final Logger logger = Logger.getLogger(SpringUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	private static SpringUtil instance;

	public static SpringUtil getInstance() {
		if (null == instance) {
			synchronized (SpringUtil.class) {
				if (null == instance) {
					instance = new SpringUtil();
				}
			}
		}

		return instance;
	}

	// ///////////////////////////////////////////////////////////////////////

	private static ServletContext servletContext;

	private static ApplicationContext context = null;

	public static ApplicationContext getContext() {
		if (null == context) {
			synchronized (SpringUtil.class) {
				if (null == context) {
					// context = DoradoUtil.getAttachedWebApplicationContext();
					context = WebApplicationContextUtils
							.getWebApplicationContext(servletContext);
				}
			}
			// 方法一：
			// context = DoradoContext.getAttachedWebApplicationContext();
			// 方法二：
			// try {
			// // spring主配置文件路径
			// // context = new FileSystemXmlApplicationContext(path);
			// // context = new ClassPathXmlApplicationContext(path);
			// } catch (Exception e) {
			// logger.error(e);
			// }
			// 方法三：
			// WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}

		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringUtil.context = context;
	}

	public static void setServletContext(ServletContext servletContext) {
		SpringUtil.servletContext = servletContext;
	}

	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * 获取bean
	 * 
	 * @param beanName
	 *            bean名称
	 * @return bean 对象
	 */
	public static Object getBean(String beanName) {
		return getContext().getBean(beanName);
	}

	/**
	 * 获取spring中的数据源
	 * 
	 * @return 数据源对象
	 */
	public static DataSource getDataSource() {
		return (DataSource) getBean("dataSource");
	}

	/**
	 * 获取spring中注册的bean的名字数组
	 * 
	 * @return bean的名字数组
	 */
	public static String[] getBeanDefinitionNames() {
		return getContext().getBeanDefinitionNames();
	}

	/**
	 * 获取spring中标识注解对象的bean的Map对象集合
	 * 
	 * @param clazz
	 *            注解类类对象
	 * @return 实现注解对象的bean对象Map集合
	 */
	public static Map<String, Object> getBeansWithAnnotation(
			Class<? extends java.lang.annotation.Annotation> clazz) {
		return getContext().getBeansWithAnnotation(clazz);
	}

	// /////////////////////////////////////////////////////////////

}
