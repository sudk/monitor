package com.component.common.jdbc.util;

/**
 * 
 * JDBC帮助类常量
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-9-21 下午2:31:10
 */
public interface JDBCConstants {

	// start:---连接数据库信息---

	/**
	 * jdbc驱动类全名
	 */
	String CLASSNAME = "com.ibm.db2.jcc.DB2Driver";

	/**
	 * jdbc数据库连接url
	 */
	String URL = "jdbc:db2://192.168.1.10:60000/ufcaudit";

	/**
	 * 数据库用户名
	 */
	String USER = "ufcaudit";

	/**
	 * 数据库用户密码
	 */
	String PASSWORD = "ufcaudit";

	// end:---连接数据库信息---

}
