package com.component.common.spring.util;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Spring������
 * 
 * @author LiuKun
 * @date 2012-07-09
 * @time 15:20
 * 
 */
public class SpringUtil implements Serializable {
	/**
	 * ���к�
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
			// ����һ��
			// context = DoradoContext.getAttachedWebApplicationContext();
			// ��������
			// try {
			// // spring�������ļ�·��
			// // context = new FileSystemXmlApplicationContext(path);
			// // context = new ClassPathXmlApplicationContext(path);
			// } catch (Exception e) {
			// logger.error(e);
			// }
			// ��������
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
	 * ��ȡbean
	 * 
	 * @param beanName
	 *            bean����
	 * @return bean ����
	 */
	public static Object getBean(String beanName) {
		return getContext().getBean(beanName);
	}

	/**
	 * ��ȡspring�е�����Դ
	 * 
	 * @return ����Դ����
	 */
	public static DataSource getDataSource() {
		return (DataSource) getBean("dataSource");
	}

	/**
	 * ��ȡspring��ע���bean����������
	 * 
	 * @return bean����������
	 */
	public static String[] getBeanDefinitionNames() {
		return getContext().getBeanDefinitionNames();
	}

	/**
	 * ��ȡspring�б�ʶע������bean��Map���󼯺�
	 * 
	 * @param clazz
	 *            ע���������
	 * @return ʵ��ע������bean����Map����
	 */
	public static Map<String, Object> getBeansWithAnnotation(
			Class<? extends java.lang.annotation.Annotation> clazz) {
		return getContext().getBeansWithAnnotation(clazz);
	}

	// /////////////////////////////////////////////////////////////

}
