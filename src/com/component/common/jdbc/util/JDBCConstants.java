package com.component.common.jdbc.util;

/**
 * 
 * JDBC�����ೣ��
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-9-21 ����2:31:10
 */
public interface JDBCConstants {

	// start:---�������ݿ���Ϣ---

	/**
	 * jdbc������ȫ��
	 */
	String CLASSNAME = "com.ibm.db2.jcc.DB2Driver";

	/**
	 * jdbc���ݿ�����url
	 */
	String URL = "jdbc:db2://192.168.1.10:60000/ufcaudit";

	/**
	 * ���ݿ��û���
	 */
	String USER = "ufcaudit";

	/**
	 * ���ݿ��û�����
	 */
	String PASSWORD = "ufcaudit";

	// end:---�������ݿ���Ϣ---

}
