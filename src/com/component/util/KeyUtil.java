package com.component.util;

import java.io.Serializable;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.component.common.key.KeyGenUtil;

/**
 * key������
 * 
 * @author LiuKun
 * @date 2012-7-19
 * @time ����9:01:20
 * 
 */
public class KeyUtil implements Serializable, KeyConstants {

	/**
	 * ���к�
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
	 * ��ȡuuidhex��Ψһ���ֵ
	 * 
	 * @return
	 */
	public static synchronized String getUUIDhex() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	// ///////////////////////////////ϵͳ����/////////////////////////////////////////////////////

	/**
	 * ������ţ�������ƻ���������źͱ���ƻ���������ţ�
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-21 ����3:43:14
	 */
	public static synchronized String getOrgId() {
		return KeyGenUtil.getStringKey(SEQ_ORG_ID);
	}

	/**
	 * ϵͳ��������
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-22 ����11:01:37
	 */
	public static synchronized String getSysParamId() {
		return KeyGenUtil.getStringKey(SEQ_SYS_PARAM_ID);
	}

	/**
	 * ����Ա���
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getOperId() {
		return KeyGenUtil.getStringKey(SEQ_OPER_ID);
	}

	/**
	 * ��ɫ���
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getRoleId() {
		return KeyGenUtil.getStringKey(SEQ_ROLE_ID);
	}

	/**
	 * ��ɫ�빦�ܹ�ϵ����
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getRoleFuncRelId() {
		return KeyGenUtil.getStringKey(SEQ_ROLE_FUNC_REL_ID);
	}

	/**
	 * ��ɫ�����Ա��ϵ����
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getOperRoleRelId() {
		return KeyGenUtil.getStringKey(SEQ_RBAC_OPER_ROLE_REL_ID);
	}

	/**
	 * �û�������
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getGroupId() {
		return KeyGenUtil.getStringKey(SEQ_GROUP_ID);
	}

	/**
	 * �û������û���ϵ����
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getUserGroupRelId() {
		return KeyGenUtil.getStringKey(SEQ_USER_GROUP_REL_ID);
	}

	/**
	 * �û��������ݹ�ϵ����
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getGroupDataRelId() {
		return KeyGenUtil.getStringKey(SEQ_GROUP_DATA_REL_ID);
	}

	// /**
	// * ���ܱ������
	// *
	// * @return
	// * @author LiuKun
	// * @created 2012-7-23 ����4:05:36
	// */
	// public static synchronized String getFuncId() {
	// return KeyGenUtil.getStringKey(SEQ_FUNC_ID);
	// }
	//
	// /**
	// * �˵��������
	// *
	// * @return
	// * @author LiuKun
	// * @created 2012-7-23 ����4:05:36
	// */
	// public static synchronized String getMenuId() {
	// return KeyGenUtil.getStringKey(SEQ_MENU_ID);
	// }

	/**
	 * ���ܲ˵����������Ϊ�˱��⹦����˵���������ͻ���⣬���ܱ�źͲ˵����ʹ��ͬһ��������ɣ�
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-23 ����4:05:36
	 */
	public static synchronized String getFuncMenuId() {
		return KeyGenUtil.getStringKey(SEQ_FUNC_MENU_ID);
	}

	/**
	 * ��ɫ�����ϵ����
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-28 ����2:13:26
	 */
	public static synchronized String getRoleRelId() {
		return KeyGenUtil.getStringKey(SEQ_ROLE_REL_ID);
	}

	/**
	 * ��������
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-31 ����11:27:54
	 */
	public static synchronized String getAttaId() {
		return KeyGenUtil.getStringKey(SEQ_ATTA_ID);
	}

	/**
	 * ϵͳ��־����
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-8-20 ����10:40:34
	 */
	public static synchronized String getSysOperlogId() {
		return KeyGenUtil.getStringKey(SEQ_SYS_OPERLOG_ID);
	}

	// end:---��������---
}
