package com.component.common.spring.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.component.common.dao.BaseDao;

/**
 * 获取系统的数据库连接,与hibernate的事物是同一个
 * 
 * @author LiuKun
 * @date Feb 1, 2012
 */
public class ConnectionUtil {

	/**
	 * 取得数据源
	 * 
	 * @return
	 */
	public static DataSource getDataSource() throws SQLException {
		DataSource ds = (DataSource) SpringUtil.getDataSource();
		return ds;
	}
	
	/**
	 * 从数据源中获取连接
	 * 
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-7 下午4:05:46
	 */
	public static Connection getConnectionForDataSource() throws SQLException {
		DataSource ds = getDataSource();
		if (ds != null) {
			return ds.getConnection();
		}
		return null;
	}
	
	/**
	 * 取得数据库连接(从hibernate的session取Connection)
	 * 
	 * @return
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		BaseDao dao = (BaseDao) SpringUtil.getBean("baseDao");
		conn = dao.getConnection();
		return conn;
	}

	/**
	 * 取得hds数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnectionForHds() throws SQLException {
		Connection conn = null;
		DataSource ds = (DataSource) SpringUtil.getDataSource();
		conn = ds.getConnection();
		return conn;
	}

	public static void close(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
			}
	}

	public static void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
			}
	}

	public static void rollback(Connection conn) {
		if (conn != null)
			try {
				conn.rollback();
			} catch (SQLException e) {
			}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void releaseResource(Connection conn, Statement stmt,
			ResultSet rs) {
		close(conn);
		close(stmt);
		close(rs);
	}
}
