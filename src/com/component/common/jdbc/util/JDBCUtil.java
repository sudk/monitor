package com.component.common.jdbc.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.component.common.pagination.PageInfo;
import com.component.common.spring.util.SpringUtil;
import com.component.common.util.Validator;

/**
 * jdbc ������
 * 
 * @author LiuKun
 * @date 2012-7-17
 * @time ����2:30:35
 * 
 */
public class JDBCUtil implements Serializable, JDBCConstants {
	/**
	 * ���к�
	 */
	private static final long serialVersionUID = -2069873359827367717L;

	private JDBCUtil() {

	}

	private static final Logger logger = Logger.getLogger(JDBCUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static JDBCUtil instance;

	public static JDBCUtil getInstance() {
		if (null == instance) {
			synchronized (JDBCUtil.class) {
				if (null == instance) {
					instance = new JDBCUtil();
				}
			}
		}
		return instance;
	}

	// /////////////////////////////////////////////////////////////////

	/**
	 * ��ȡ���ݿ����Ӷ���
	 * 
	 * @param className
	 *            jdbc������ȫ��
	 * @param url
	 *            jdbc���ݿ�����url
	 * @param user
	 *            ���ݿ��û���
	 * @param password
	 *            ���ݿ��û�����
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����3:35:00
	 */
	public static Connection getConnection(String className, String url,
			String user, String password) {
		if (null == className || "".equals(className)) {
			throw new RuntimeException("jdbc������ȫ��Ϊ�գ�");
		}
		if (null == url || "".equals(url)) {
			throw new RuntimeException("jdbc���ݿ�����urlΪ�գ�");
		}
		if (null == user || "".equals(user)) {
			throw new RuntimeException("���ݿ��û���Ϊ�գ�");
		}
		if (null == password || "".equals(password)) {
			throw new RuntimeException("���ݿ��û�����Ϊ�գ�");
		}
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}

	/**
	 * ��ȡ���ݿ����Ӷ���
	 * 
	 * @param url
	 *            jdbc���ݿ�����url
	 * @param user
	 *            ���ݿ��û���
	 * @param password
	 *            ���ݿ��û�����
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����3:35:00
	 */
	public static Connection getConnection(String url, String user,
			String password) {
		if (null == url || "".equals(url)) {
			throw new RuntimeException("jdbc���ݿ�����urlΪ�գ�");
		}
		if (null == user || "".equals(user)) {
			throw new RuntimeException("���ݿ��û���Ϊ�գ�");
		}
		if (null == password || "".equals(password)) {
			throw new RuntimeException("���ݿ��û�����Ϊ�գ�");
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}

	/**
	 * ��ȡ����Դ
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����3:43:07
	 */
	public static DataSource getDataSource() {
		DataSource ds = (DataSource) SpringUtil.getDataSource();
		return ds;
	}

	/**
	 * ������Դ�л�ȡ����
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����3:43:19
	 */
	public static Connection getConnectionForDataSource() {
		DataSource ds = getDataSource();
		if (ds != null) {
			try {
				return ds.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * ����jdbc�������ݿ���Ϣ����ȡ���Ӷ���
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����3:48:36
	 */
	public static Connection getConnectionForJDBC() {
		Connection conn = getConnection(CLASSNAME, URL, USER, PASSWORD);
		return conn;
	}

	/**
	 * �رղ������ݿ�����ͷ���Դ
	 * 
	 * @param conn
	 * @param stat
	 * @param rs
	 * @author LiuKun
	 * @created 2012-9-21 ����10:05:12
	 */
	public static void close(Connection conn, Statement stat, ResultSet rs) {

		try {
			if (null != rs) {
				// if (!rs.isClosed()) {
				rs.close();
				// }
				rs = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			if (null != stat) {
				// if (!stat.isClosed()) {
				stat.close();
				// }
				stat = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			if (null != conn) {
				if (!conn.isClosed()) {
					// if (conn.getAutoCommit() == false) {// �ֶ��ύ����
					// if (logger.isInfoEnabled()) {
					// logger.info("---�ֶ��ύ����---");
					// }
					// conn.commit();
					// }
					conn.close();
				}
				conn = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���ݽ��������ȡ��������
	 * 
	 * @param result
	 * @return String[] = ����(����д)[]
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-9-21 ����10:07:03
	 */
	private static String[] getColumns(ResultSet result) {
		if (null == result) {
			throw new RuntimeException("�����Ϊ�գ�");
		}
		try {
			if (result.isClosed()) {
				throw new RuntimeException("������Ѿ��رգ�");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		String[] columns = null;
		try {
			ResultSetMetaData rsmd = result.getMetaData();
			int colNum = rsmd.getColumnCount();
			columns = new String[colNum];
			for (int i = 0; i < colNum; i++) {
				columns[i] = rsmd.getColumnName(i + 1).toUpperCase();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return columns;
	}

	/**
	 * ���ݽ��������ȡ��������
	 * 
	 * @param result
	 * @return List<����(����д)>
	 * @author LiuKun
	 * @created 2012-9-21 ����10:21:18
	 */
	@SuppressWarnings("unused")
	private static List<String> getColumnList(ResultSet result) {
		String[] columns = getColumns(result);
		if (null == columns || columns.length == 0) {
			throw new RuntimeException("��ȡ����Ϊ��");
		}
		List<String> columnList = Arrays.asList(columns);
		return columnList;
	}

	/**
	 * ���ò���ֵ
	 * 
	 * @param ps
	 * @param params
	 * @author LiuKun
	 * @created 2012-9-21 ����10:23:43
	 */
	private static void setParameter(PreparedStatement ps, Object... params) {
		if (null == ps) {
			throw new RuntimeException("Ԥ����PreparedStatementΪ�գ�");
		}
		try {
			for (int i = 0; null != params && i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���ò���ֵ
	 * 
	 * @param ps
	 * @param paramList
	 * @author LiuKun
	 * @created 2012-9-21 ����2:04:26
	 */
	private static void setParameter(PreparedStatement ps,
			List<Object> paramList) {
		if (null == ps) {
			throw new RuntimeException("Ԥ����PreparedStatementΪ�գ�");
		}
		try {
			for (int i = 0; null != paramList && i < paramList.size(); i++) {
				ps.setObject(i + 1, paramList.get(i));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����sql�Ͳ���Map����ȡԤ�������PreparedStatement
	 * 
	 * @param sql
	 * @param parameter
	 * @author LiuKun
	 * @created 2012-9-21 ����10:30:49
	 */
	private static PreparedStatement getPreparedStatement(Connection conn,
			String sql, Map<String, Object> parameter) {
		if (null == conn) {
			throw new RuntimeException("���ݿ����Ӷ���Ϊ�գ�");
		}
		try {
			if (conn.isClosed()) {

			}
		} catch (SQLException e) {
			throw new RuntimeException("���ݿ����Ӷ����Ѿ��رգ�");
		}
		if (null == sql || "".equals(sql)) {
			throw new RuntimeException("sqlΪ�գ�");
		}
		if (null == parameter) {
			parameter = new HashMap<String, Object>();
		}
		// start:------
		String oldSql = sql;
		String newSql = sql;
		List<Object> paramList = new ArrayList<Object>();
		int start = 0;
		int end = 0;
		String key = null;
		Object value = null;
		while (true) {
			start = newSql.indexOf(":", start);
			if (start == -1) {
				break;
			}
			end = newSql.indexOf(" ", start);
			if (end == -1) {
				end = newSql.length();
			}
			key = newSql.substring(start + 1, end);
			value = parameter.get(key);
			paramList.add(value);
			newSql = newSql.replace(":" + key, "?");
		}
		// end:------
		if (logger.isInfoEnabled()) {
			logger.info("ԭsql[" + oldSql + "]");
			logger.info("��sql[" + newSql + "]");
		}
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(newSql);
			setParameter(ps, paramList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ps;
	}

	/**
	 * ִ��sql��ѯ
	 * 
	 * @param conn
	 * @param sql
	 * @param pageInfo
	 * @param paramList
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����2:19:07
	 */
	public static List<Map<String, Object>> executeQuery(Connection conn,
			String sql, PageInfo pageInfo, List<Object> paramList) {
		if (null != paramList && paramList.size() > 0) {
			Object[] params = paramList.toArray(new Object[0]);
			return executeQuery(conn, sql, pageInfo, params);
		} else {
			return executeQuery(conn, sql, pageInfo);
		}
	}

	/**
	 * ִ��sql��ѯ
	 * 
	 * @param conn
	 * @param sql
	 * @param pageInfo
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����10:36:06
	 */
	public static List<Map<String, Object>> executeQuery(Connection conn,
			String sql, PageInfo pageInfo, Object... params) {
		if (conn == null) {
			throw new RuntimeException("���ݿ����Ӳ���Ϊ�գ�");
		}
		if (null == sql || "".equals(sql)) {
			throw new RuntimeException("sql��䲻��Ϊ�գ�");
		}
		if (logger.isInfoEnabled()) {
			if (pageInfo != null) {
				logger.info(pageInfo.toString());
			}
			if (null != params && params.length > 0) {
				String message = "";
				for (int i = 0; i < params.length; i++) {
					message += "," + params[i].toString();
				}
				message = message.replaceFirst("[,]", "");
				logger.info("����=[" + message + "]");
			}
			logger.info("sql={" + sql + "}");
		}

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (null != pageInfo) {
			try {
				String countSql = " SELECT COUNT(*) AS MYCOUNT FROM ( " + sql
						+ " ) MYTABLE ";
				Integer start = pageInfo.getPageSize()
						* (pageInfo.getPageIndex() - 1) + 1;
				Integer end = pageInfo.getPageSize()
						* (pageInfo.getPageIndex() - 1)
						+ pageInfo.getPageSize();
				sql = " SELECT * FROM "
						+ " ( "
						+ " SELECT INPUTSQL.*, ROWNUMBER() OVER() ROWID FROM ( "
						+ sql + " ) INPUTSQL " + " ) A " + " WHERE "
						+ " A.ROWID >=  " + start + " AND " + " A.ROWID <=  "
						+ end;
				ps = conn.prepareStatement(countSql);
				setParameter(ps, params);
				rs = ps.executeQuery();
				if (rs.next()) {
					Object obj = rs.getObject("MYCOUNT");
					Integer pageTotal = Integer.valueOf(obj.toString());
					pageInfo.setPageTotal(pageTotal);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				close(null, ps, rs);
			}
		}

		try {
			ps = conn.prepareStatement(sql);
			setParameter(ps, params);
			rs = ps.executeQuery();
			String[] columns = getColumns(rs);
			while (rs.next()) {
				Map<String, Object> valueMap = new HashMap<String, Object>();
				for (int i = 0; i < columns.length; i++) {
					valueMap.put(columns[i], rs.getObject(columns[i]));
				}
				mapList.add(valueMap);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, rs);
		}
		return mapList;
	}

	/**
	 * ִ��sql�޸�
	 * 
	 * @param conn
	 * @param sql
	 * @param paramList
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����2:25:36
	 */
	public static Integer executeUpdate(Connection conn, String sql,
			List<Object> paramList) {
		if (null != paramList && paramList.size() > 0) {
			Object[] params = paramList.toArray(new Object[0]);
			return executeUpdate(conn, sql, params);
		} else {
			return executeUpdate(conn, sql);
		}
	}

	/**
	 * ִ��sql�޸�
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����10:37:29
	 */
	public static Integer executeUpdate(Connection conn, String sql,
			Object... params) {
		if (conn == null) {
			throw new RuntimeException("���ݿ����Ӳ���Ϊ�գ�");
		}

		if (null == sql || "".equals(sql)) {
			throw new RuntimeException("sql��䲻��Ϊ�գ�");
		}

		if (logger.isInfoEnabled()) {
			if (null != params && params.length > 0) {
				String message = "";
				for (int i = 0; i < params.length; i++) {
					message += "," + params[i].toString();
				}
				message = message.replaceFirst("[,]", "");
				logger.info("����=[" + message + "]");
			}
			logger.info("sql={" + sql + "}");
		}

		PreparedStatement ps = null;
		Integer result = null;
		try {
			ps = conn.prepareStatement(sql);
			setParameter(ps, params);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, null);
		}
		return result;
	}

	/**
	 * �ж����ݿ���Ƿ����
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-9 ����7:36:35
	 */
	public static Boolean existTable(Connection conn, String tableName)
			throws SQLException {
		return existTable(conn, "UFCAUDIT", tableName);
	}

	/**
	 * �ж����ݿ���Ƿ����
	 * 
	 * @param conn
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-9 ����7:36:35
	 */
	public static Boolean existTable(Connection conn, String schema,
			String tableName) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("���ݿ�����Ϊ�գ�");
		}
		if (Validator.isEmpty(schema)) {
			throw new RuntimeException("schemaΪ�գ�");
		}
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("����Ϊ�գ�");
		}
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getTables(null, schema.toUpperCase(),
				tableName.toUpperCase(), new String[] { "TABLE" });
		Boolean flag = Boolean.FALSE;
		if (rs.next()) {
			flag = Boolean.TRUE;
		} else {
			flag = Boolean.FALSE;
		}
		JDBCUtil.close(conn, null, rs);
		return flag;
	}

	/**
	 * ���ݱ���,ɾ����
	 * 
	 * @param conn
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-10 ����9:11:52
	 */
	public static void dropTable(Connection conn, String schema,
			String tableName) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("���ݿ�����Ϊ�գ�");
		}
		if (Validator.isEmpty(schema)) {
			throw new RuntimeException("schemaΪ�գ�");
		}
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("����Ϊ�գ�");
		}
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getTables(null, schema.toUpperCase(),
				tableName.toUpperCase(), new String[] { "TABLE" });
		Statement stat = null;
		Boolean flag = Boolean.FALSE;
		if (rs.next()) {
			flag = Boolean.TRUE;
		} else {
			flag = Boolean.FALSE;
		}
		if (!flag) {
			JDBCUtil.close(conn, stat, rs);
			throw new RuntimeException("��[" + tableName + "]������!");
		}
		stat = conn.createStatement();
		// ��ȫ��
		String tableFullName = (schema + "." + tableName).toUpperCase();
		// ��ִ�п����������
		stat.execute(" ALTER TABLE " + tableFullName
				+ " ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE ");
		// ��drop table
		stat.execute(" DROP TABLE " + tableFullName);
		JDBCUtil.close(conn, stat, rs);
	}

	/**
	 * ���ݱ���,ɾ����
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-10 ����9:11:52
	 */
	public static void dropTable(Connection conn, String tableName)
			throws SQLException {
		dropTable(conn, "UFCAUDIT", tableName);
	}

	/**
	 * �����¼
	 * 
	 * @param conn
	 *            ���ݿ����Ӷ���
	 * @param tableName
	 *            ����
	 * @param parameter
	 *            Map<����,��ֵ>
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����2:33:12
	 */
	public static Integer insert(Connection conn, String tableName,
			Map<String, Object> parameter) {
		if (null == conn) {
			throw new RuntimeException("���ݿ����Ӷ���Ϊ�գ�");
		}
		if (null == tableName || "".equals(tableName)) {
			throw new RuntimeException("����Ϊ�գ�");
		}
		if (null == parameter || parameter.size() == 0) {
			throw new RuntimeException("����Ϊ�գ�");
		}
		// start:-------
		String sql1 = "";
		String sql2 = "";
		for (java.util.Map.Entry<String, Object> entry : parameter.entrySet()) {
			String columnName = entry.getKey();
			sql1 += " , " + columnName;
			sql2 += " , :" + columnName;
		}
		sql1 = sql1.replaceFirst(",", "");
		sql2 = sql2.replaceFirst(",", "");
		sql1 += " ";
		sql2 += " ";
		String insertSql = " INSERT INTO " + tableName + "(" + sql1 + ")"
				+ " VALUES(" + sql2 + ") ";
		// end:-------
		PreparedStatement ps = getPreparedStatement(conn, insertSql, parameter);
		Integer result = null;
		try {
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, null);
		}
		return result;
	}

	/**
	 * ɾ����¼
	 * 
	 * @param conn
	 * @param tableName
	 * @param parameter
	 *            Map<����,��ֵ> where����������ÿ����������=
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����3:03:51
	 */
	public static Integer delete(Connection conn, String tableName,
			Map<String, Object> parameter) {
		if (null == conn) {
			throw new RuntimeException("���ݿ����Ӷ���Ϊ�գ�");
		}
		if (null == tableName || "".equals(tableName)) {
			throw new RuntimeException("����Ϊ�գ�");
		}
		if (null == parameter || parameter.size() == 0) {
			throw new RuntimeException("����Ϊ�գ�");
		}
		// start:-------
		String whereSql = "";
		for (java.util.Map.Entry<String, Object> entry : parameter.entrySet()) {
			String columnName = entry.getKey();
			whereSql += " AND " + columnName + " = :" + columnName;
		}
		whereSql = whereSql.replaceFirst("AND", "");
		whereSql += " ";
		String deleteSql = " DELETE FROM " + tableName + " WHERE " + whereSql;
		// end:-------
		PreparedStatement ps = getPreparedStatement(conn, deleteSql, parameter);
		Integer result = null;
		try {
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, null);
		}
		return result;
	}

	/**
	 * �޸ļ�¼
	 * 
	 * @param conn
	 * @param tableName
	 *            ����
	 * @param setMap
	 *            Map<����,��ֵ>
	 * @param whereMap
	 *            Map<����,��ֵ> where����������ÿ����������=
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 ����3:26:57
	 */
	public static Integer update(Connection conn, String tableName,
			Map<String, Object> setMap, Map<String, Object> whereMap) {
		if (null == conn) {
			throw new RuntimeException("���ݿ����Ӷ���Ϊ�գ�");
		}
		if (null == tableName || "".equals(tableName)) {
			throw new RuntimeException("����Ϊ�գ�");
		}
		if (null == setMap || setMap.size() == 0) {
			throw new RuntimeException("set����Ϊ�գ�");
		}
		if (null == whereMap || whereMap.size() == 0) {
			throw new RuntimeException("where����Ϊ�գ�");
		}
		// start:------
		List<Object> paramList = new ArrayList<Object>();
		String setSql = "";
		for (java.util.Map.Entry<String, Object> entry : setMap.entrySet()) {
			String columnName = entry.getKey();
			Object value = entry.getValue();
			setSql += " , " + columnName + " = ? ";
			paramList.add(value);
		}
		setSql = setSql.replaceFirst(",", "");

		String whereSql = "";
		for (java.util.Map.Entry<String, Object> entry : whereMap.entrySet()) {
			String columnName = entry.getKey();
			Object value = entry.getValue();
			whereSql += " AND " + columnName + " = ? ";
			paramList.add(value);
		}
		whereSql = whereSql.replaceFirst("AND", "");
		String updateSql = " UPDATE " + tableName + " SET " + setSql
				+ " WHERE " + whereSql + " ";
		// end:------
		PreparedStatement ps = null;
		Integer result = null;
		try {
			ps = conn.prepareStatement(updateSql);
			setParameter(ps, paramList);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, null);
		}
		return result;
	}

	/**
	 * ����sql��䣬��ȡsql����ѯ���������������
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 ����10:50:45
	 */
	public static String[] getColumns(Connection conn, String sql) {
		if (null == conn) {
			throw new RuntimeException("���ݿ����Ӷ���");
		}
		if (Validator.isEmpty(sql)) {
			throw new RuntimeException("sql���Ϊ�գ�");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] columns = null;
		try {
			// �����ѯ��ͷsql
			String newSql = " SELECT * FROM (" + sql + ") MYTABLE WHERE 0 = 1 ";
			ps = conn.prepareStatement(newSql);
			rs = ps.executeQuery();
			columns = getColumns(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, rs);
		}
		return columns;
	}
}
