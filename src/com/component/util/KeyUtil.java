package com.component.util;

import java.io.Serializable;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.component.common.key.KeyGenUtil;

/**
 * key帮助类
 * 
 * @author LiuKun
 * @date 2012-7-19
 * @time 上午9:01:20
 * 
 */
public class KeyUtil implements Serializable, KeyConstants {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8274788131389295956L;

	private KeyUtil() {

	}

	private static final Logger logger = Logger.getLogger(KeyUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static KeyUtil instance;

	public static KeyUtil getInstance() {
		if (null == instance) {
			synchronized (KeyUtil.class) {
				if (null == instance) {
					instance = new KeyUtil();
				}
			}
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////////

	/**
	 * 提取uuidhex的唯一编号值
	 * 
	 * @return
	 */
	public static synchronized String getUUIDhex() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	// ///////////////////////////////系统管理/////////////////////////////////////////////////////

	/**
	 * 机构编号（包括审计机构机构编号和被审计机构机构编号）
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-21 下午3:43:14
	 */
	public static synchronized String getOrgId() {
		return KeyGenUtil.getStringKey(SEQ_ORG_ID);
	}

	/**
	 * 系统参数主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-22 下午11:01:37
	 */
	public static synchronized String getSysParamId() {
		return KeyGenUtil.getStringKey(SEQ_SYS_PARAM_ID);
	}

	/**
	 * 操作员编号
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getOperId() {
		return KeyGenUtil.getStringKey(SEQ_OPER_ID);
	}

	/**
	 * 角色编号
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getRoleId() {
		return KeyGenUtil.getStringKey(SEQ_ROLE_ID);
	}

	/**
	 * 角色与功能关系主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getRoleFuncRelId() {
		return KeyGenUtil.getStringKey(SEQ_ROLE_FUNC_REL_ID);
	}

	/**
	 * 角色与操作员关系主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getOperRoleRelId() {
		return KeyGenUtil.getStringKey(SEQ_RBAC_OPER_ROLE_REL_ID);
	}

	/**
	 * 用户组主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getGroupId() {
		return KeyGenUtil.getStringKey(SEQ_GROUP_ID);
	}

	/**
	 * 用户组与用户关系主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getUserGroupRelId() {
		return KeyGenUtil.getStringKey(SEQ_USER_GROUP_REL_ID);
	}

	/**
	 * 用户组与数据关系主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getGroupDataRelId() {
		return KeyGenUtil.getStringKey(SEQ_GROUP_DATA_REL_ID);
	}

	// /**
	// * 功能编号主键
	// *
	// * @return
	// * @author LiuKun
	// * @created 2012-7-23 下午4:05:36
	// */
	// public static synchronized String getFuncId() {
	// return KeyGenUtil.getStringKey(SEQ_FUNC_ID);
	// }
	//
	// /**
	// * 菜单编号主键
	// *
	// * @return
	// * @author LiuKun
	// * @created 2012-7-23 下午4:05:36
	// */
	// public static synchronized String getMenuId() {
	// return KeyGenUtil.getStringKey(SEQ_MENU_ID);
	// }

	/**
	 * 功能菜单编号主键（为了避免功能与菜单的主键冲突问题，功能编号和菜单编号使用同一个编号生成）
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 下午4:05:36
	 */
	public static synchronized String getFuncMenuId() {
		return KeyGenUtil.getStringKey(SEQ_FUNC_MENU_ID);
	}

	/**
	 * 角色互斥关系主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-28 下午2:13:26
	 */
	public static synchronized String getRoleRelId() {
		return KeyGenUtil.getStringKey(SEQ_ROLE_REL_ID);
	}

	/**
	 * 附件主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-31 上午11:27:54
	 */
	public static synchronized String getAttaId() {
		return KeyGenUtil.getStringKey(SEQ_ATTA_ID);
	}

	/**
	 * 系统日志主键
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-8-20 下午10:40:34
	 */
	public static synchronized String getSysOperlogId() {
		return KeyGenUtil.getStringKey(SEQ_SYS_OPERLOG_ID);
	}

	// end:---数据配置---
}
