package com.component.common.spring.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.component.common.dao.BaseDao;

/**
 * ��ȡϵͳ�����ݿ�����,��hibernate��������ͬһ��
 * 
 * @author LiuKun
 * @date Feb 1, 2012
 */
public class ConnectionUtil {

	/**
	 * ȡ������Դ
	 * 
	 * @return
	 */
	public static DataSource getDataSource() throws SQLException {
		DataSource ds = (DataSource) SpringUtil.getDataSource();
		return ds;
	}
	
	/**
	 * ������Դ�л�ȡ����
	 * 
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-7 ����4:05:46
	 */
	public static Connection getConnectionForDataSource() throws SQLException {
		DataSource ds = getDataSource();
		if (ds != null) {
			return ds.getConnection();
		}
		return null;
	}
	
	/**
	 * ȡ�����ݿ�����(��hibernate��sessionȡConnection)
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
	 * ȡ��hds���ݿ�����
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
