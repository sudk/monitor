package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.component.common.spring.util.SpringUtil;

public class InitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {

	}

	public void contextInitialized(ServletContextEvent event) {
		SpringUtil.setServletContext(event.getServletContext());
	}

}
