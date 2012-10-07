package com.component.util;

/**
 * 主键常量接口
 * 
 * @author LiuKun
 * @date 2012-7-3
 * @time 下午4:41:21
 * 
 */
public interface KeyConstants {


	// ///////////////////////////////系统管理/////////////////////////////////////////////////////

	/**
	 * 机构编号（包括审计机构机构编号和被审计机构机构编号）
	 */
	String SEQ_ORG_ID = "SEQ_ORG_ID";

	/**
	 * 系统参数主键
	 */
	String SEQ_SYS_PARAM_ID = "SEQ_SYS_PARAM_ID";

	/**
	 * 操作员编号
	 */
	String SEQ_OPER_ID = "SEQ_OPER_ID";

	/**
	 * 角色编号
	 */
	String SEQ_ROLE_ID = "SEQ_ROLE_ID";

	/**
	 * 角色与功能关系主键
	 */
	String SEQ_ROLE_FUNC_REL_ID = "SEQ_ROLE_FUNC_REL_ID";

	/**
	 * 角色与操作员关系主键
	 */
	String SEQ_RBAC_OPER_ROLE_REL_ID = "SEQ_RBAC_OPER_ROLE_REL_ID";

	/**
	 * 用户组主键
	 */
	String SEQ_GROUP_ID = "SEQ_GROUP_ID";

	/**
	 * 用户组与用户关系主键
	 */
	String SEQ_USER_GROUP_REL_ID = "SEQ_USER_GROUP_REL_ID";

	/**
	 * 用户组与数据关系主键
	 */
	String SEQ_GROUP_DATA_REL_ID = "SEQ_GROUP_DATA_REL_ID";

	// /**
	// * 功能编号主键
	// */
	// String SEQ_FUNC_ID = "SEQ_FUNC_ID";
	//
	// /**
	// * 菜单编号主键
	// */
	// String SEQ_MENU_ID = "SEQ_MENU_ID";

	/**
	 * 功能菜单编号主键（为了避免功能与菜单的主键冲突问题，功能编号和菜单编号使用同一个编号生成）
	 */
	String SEQ_FUNC_MENU_ID = "SEQ_FUNC_MENU_ID";


	/**
	 * 角色互斥关系主键
	 */
	String SEQ_ROLE_REL_ID = "SEQ_ROLE_REL_ID";

	/**
	 * 附件主键
	 */
	String SEQ_ATTA_ID = "SEQ_ATTA_ID";

	/**
	 * 系统日志主键
	 */
	String SEQ_SYS_OPERLOG_ID = "SEQ_SYS_OPERLOG_ID";

	
}
