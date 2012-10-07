package com.component.common.spring.util;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.module.monitor.service.BusMonitorResultService;
import com.module.monitor.service.BusMonitorTaskService;
import com.module.monitor.service.BusMonitorThemeService;
import com.module.sysadmin.service.SysCodevalueService;
import com.module.sysadmin.service.SysOperlogService;
import com.module.sysadmin.service.SysRbacEmpService;
import com.module.sysadmin.service.SysRbacFuncService;
import com.module.sysadmin.service.SysRbacMenuService;
import com.module.sysadmin.service.SysRbacOperService;
import com.module.sysadmin.service.SysRbacOperroleRelService;
import com.module.sysadmin.service.SysRbacRoleService;
import com.module.sysadmin.service.SysRbacRolefuncRelService;

/**
 * bean帮助类（主要获取bean对象）
 * 
 * @author LiuKun
 * @date 2012-7-3
 * @time 下午6:16:23
 * 
 */
public class BeanUtil implements Serializable, BeanConstants {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1858931749032743097L;

	private static final Logger logger = Logger.getLogger(BeanUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static SysCodevalueService getSysCodevalueService() {
		return (SysCodevalueService) SpringUtil.getBean(SYSCODEVALUESERVICE);
	}

	public static SysRbacFuncService getSysRbacFuncService() {
		return (SysRbacFuncService) SpringUtil.getBean(SYSRBACFUNCSERVICE);
	}

	public static SysRbacRoleService getSysRbacRoleService() {
		return (SysRbacRoleService) SpringUtil.getBean(SYSRBACROLESERVICE);
	}

	public static SysRbacRolefuncRelService getSysRbacRolefuncRelService() {
		return (SysRbacRolefuncRelService) SpringUtil
				.getBean(SYSRBACROLEFUNCRELSERVICE);
	}

	public static SysRbacMenuService getSysRbacMenuService() {
		return (SysRbacMenuService) SpringUtil.getBean(SYSRBACMENUSERVICE);
	}

	public static SysOperlogService getSysOperlogService() {
		return (SysOperlogService) SpringUtil.getBean(SYSOPERLOGSERVICE);
	}

	public static SysRbacOperService getSysRbacOperService() {
		return (SysRbacOperService) SpringUtil.getBean(SYSRBACOPERSERVICE);
	}

	public static SysRbacEmpService getSysRbacEmpService() {
		return (SysRbacEmpService) SpringUtil.getBean(SYSRBACEMPSERVICE);
	}

	public static SysRbacOperroleRelService getSysRbacOperroleRelService() {
		return (SysRbacOperroleRelService) SpringUtil
				.getBean(SYSRBACOPERROLERELSERVICE);
	}

	// ///////////////////////任务监测/////////////////
	public static BusMonitorResultService getBusMonitorResultService() {
		return (BusMonitorResultService) SpringUtil
				.getBean(BUSMONITORRESULTSERVICE);
	}

	public static BusMonitorTaskService getBusMonitorTaskService() {
		return (BusMonitorTaskService) SpringUtil
				.getBean(BUSMONITORTASKSERVICE);
	}

	public static BusMonitorThemeService getBusMonitorThemeService() {
		return (BusMonitorThemeService) SpringUtil
				.getBean(BUSMONITORTHEMESERVICE);
	}
}
