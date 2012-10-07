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
 * jdbc 帮助类
 * 
 * @author LiuKun
 * @date 2012-7-17
 * @time 下午2:30:35
 * 
 */
public class JDBCUtil implements Serializable, JDBCConstants {
	/**
	 * 序列号
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
	 * 获取数据库连接对象
	 * 
	 * @param className
	 *            jdbc驱动类全名
	 * @param url
	 *            jdbc数据库连接url
	 * @param user
	 *            数据库用户名
	 * @param password
	 *            数据库用户密码
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午3:35:00
	 */
	public static Connection getConnection(String className, String url,
			String user, String password) {
		if (null == className || "".equals(className)) {
			throw new RuntimeException("jdbc驱动类全名为空！");
		}
		if (null == url || "".equals(url)) {
			throw new RuntimeException("jdbc数据库连接url为空！");
		}
		if (null == user || "".equals(user)) {
			throw new RuntimeException("数据库用户名为空！");
		}
		if (null == password || "".equals(password)) {
			throw new RuntimeException("数据库用户密码为空！");
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
	 * 获取数据库连接对象
	 * 
	 * @param url
	 *            jdbc数据库连接url
	 * @param user
	 *            数据库用户名
	 * @param password
	 *            数据库用户密码
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午3:35:00
	 */
	public static Connection getConnection(String url, String user,
			String password) {
		if (null == url || "".equals(url)) {
			throw new RuntimeException("jdbc数据库连接url为空！");
		}
		if (null == user || "".equals(user)) {
			throw new RuntimeException("数据库用户名为空！");
		}
		if (null == password || "".equals(password)) {
			throw new RuntimeException("数据库用户密码为空！");
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
	 * 获取数据源
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午3:43:07
	 */
	public static DataSource getDataSource() {
		DataSource ds = (DataSource) SpringUtil.getDataSource();
		return ds;
	}

	/**
	 * 从数据源中获取连接
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午3:43:19
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
	 * 根据jdbc连接数据库信息，获取连接对象
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午3:48:36
	 */
	public static Connection getConnectionForJDBC() {
		Connection conn = getConnection(CLASSNAME, URL, USER, PASSWORD);
		return conn;
	}

	/**
	 * 关闭操作数据库对象，释放资源
	 * 
	 * @param conn
	 * @param stat
	 * @param rs
	 * @author LiuKun
	 * @created 2012-9-21 上午10:05:12
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
					// if (conn.getAutoCommit() == false) {// 手动提交事务
					// if (logger.isInfoEnabled()) {
					// logger.info("---手动提交事务---");
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
	 * 根据结果集，获取列名数组
	 * 
	 * @param result
	 * @return String[] = 列名(纯大写)[]
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-9-21 上午10:07:03
	 */
	private static String[] getColumns(ResultSet result) {
		if (null == result) {
			throw new RuntimeException("结果集为空！");
		}
		try {
			if (result.isClosed()) {
				throw new RuntimeException("结果集已经关闭！");
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
	 * 根据结果集，获取列名集合
	 * 
	 * @param result
	 * @return List<列名(纯大写)>
	 * @author LiuKun
	 * @created 2012-9-21 上午10:21:18
	 */
	@SuppressWarnings("unused")
	private static List<String> getColumnList(ResultSet result) {
		String[] columns = getColumns(result);
		if (null == columns || columns.length == 0) {
			throw new RuntimeException("获取列名为空");
		}
		List<String> columnList = Arrays.asList(columns);
		return columnList;
	}

	/**
	 * 设置参数值
	 * 
	 * @param ps
	 * @param params
	 * @author LiuKun
	 * @created 2012-9-21 上午10:23:43
	 */
	private static void setParameter(PreparedStatement ps, Object... params) {
		if (null == ps) {
			throw new RuntimeException("预处理PreparedStatement为空！");
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
	 * 设置参数值
	 * 
	 * @param ps
	 * @param paramList
	 * @author LiuKun
	 * @created 2012-9-21 下午2:04:26
	 */
	private static void setParameter(PreparedStatement ps,
			List<Object> paramList) {
		if (null == ps) {
			throw new RuntimeException("预处理PreparedStatement为空！");
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
	 * 根据sql和参数Map，获取预处理对象PreparedStatement
	 * 
	 * @param sql
	 * @param parameter
	 * @author LiuKun
	 * @created 2012-9-21 上午10:30:49
	 */
	private static PreparedStatement getPreparedStatement(Connection conn,
			String sql, Map<String, Object> parameter) {
		if (null == conn) {
			throw new RuntimeException("数据库连接对象为空！");
		}
		try {
			if (conn.isClosed()) {

			}
		} catch (SQLException e) {
			throw new RuntimeException("数据库连接对象已经关闭！");
		}
		if (null == sql || "".equals(sql)) {
			throw new RuntimeException("sql为空！");
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
			logger.info("原sql[" + oldSql + "]");
			logger.info("新sql[" + newSql + "]");
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
	 * 执行sql查询
	 * 
	 * @param conn
	 * @param sql
	 * @param pageInfo
	 * @param paramList
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午2:19:07
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
	 * 执行sql查询
	 * 
	 * @param conn
	 * @param sql
	 * @param pageInfo
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 上午10:36:06
	 */
	public static List<Map<String, Object>> executeQuery(Connection conn,
			String sql, PageInfo pageInfo, Object... params) {
		if (conn == null) {
			throw new RuntimeException("数据库连接不能为空！");
		}
		if (null == sql || "".equals(sql)) {
			throw new RuntimeException("sql语句不能为空！");
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
				logger.info("参数=[" + message + "]");
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
	 * 执行sql修改
	 * 
	 * @param conn
	 * @param sql
	 * @param paramList
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午2:25:36
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
	 * 执行sql修改
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 上午10:37:29
	 */
	public static Integer executeUpdate(Connection conn, String sql,
			Object... params) {
		if (conn == null) {
			throw new RuntimeException("数据库连接不能为空！");
		}

		if (null == sql || "".equals(sql)) {
			throw new RuntimeException("sql语句不能为空！");
		}

		if (logger.isInfoEnabled()) {
			if (null != params && params.length > 0) {
				String message = "";
				for (int i = 0; i < params.length; i++) {
					message += "," + params[i].toString();
				}
				message = message.replaceFirst("[,]", "");
				logger.info("参数=[" + message + "]");
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
	 * 判断数据库表是否存在
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-9 下午7:36:35
	 */
	public static Boolean existTable(Connection conn, String tableName)
			throws SQLException {
		return existTable(conn, "UFCAUDIT", tableName);
	}

	/**
	 * 判断数据库表是否存在
	 * 
	 * @param conn
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-9 下午7:36:35
	 */
	public static Boolean existTable(Connection conn, String schema,
			String tableName) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("数据库连接为空！");
		}
		if (Validator.isEmpty(schema)) {
			throw new RuntimeException("schema为空！");
		}
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("表名为空！");
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
	 * 根据表名,删除表
	 * 
	 * @param conn
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-10 上午9:11:52
	 */
	public static void dropTable(Connection conn, String schema,
			String tableName) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("数据库连接为空！");
		}
		if (Validator.isEmpty(schema)) {
			throw new RuntimeException("schema为空！");
		}
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("表名为空！");
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
			throw new RuntimeException("表[" + tableName + "]不存在!");
		}
		stat = conn.createStatement();
		// 表全名
		String tableFullName = (schema + "." + tableName).toUpperCase();
		// 先执行快速清理操作
		stat.execute(" ALTER TABLE " + tableFullName
				+ " ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE ");
		// 再drop table
		stat.execute(" DROP TABLE " + tableFullName);
		JDBCUtil.close(conn, stat, rs);
	}

	/**
	 * 根据表名,删除表
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author LiuKun
	 * @created 2012-8-10 上午9:11:52
	 */
	public static void dropTable(Connection conn, String tableName)
			throws SQLException {
		dropTable(conn, "UFCAUDIT", tableName);
	}

	/**
	 * 插入记录
	 * 
	 * @param conn
	 *            数据库连接对象
	 * @param tableName
	 *            表名
	 * @param parameter
	 *            Map<列名,列值>
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午2:33:12
	 */
	public static Integer insert(Connection conn, String tableName,
			Map<String, Object> parameter) {
		if (null == conn) {
			throw new RuntimeException("数据库连接对象为空！");
		}
		if (null == tableName || "".equals(tableName)) {
			throw new RuntimeException("表名为空！");
		}
		if (null == parameter || parameter.size() == 0) {
			throw new RuntimeException("参数为空！");
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
	 * 删除记录
	 * 
	 * @param conn
	 * @param tableName
	 * @param parameter
	 *            Map<列名,列值> where条件参数，每个条件都是=
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午3:03:51
	 */
	public static Integer delete(Connection conn, String tableName,
			Map<String, Object> parameter) {
		if (null == conn) {
			throw new RuntimeException("数据库连接对象为空！");
		}
		if (null == tableName || "".equals(tableName)) {
			throw new RuntimeException("表名为空！");
		}
		if (null == parameter || parameter.size() == 0) {
			throw new RuntimeException("参数为空！");
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
	 * 修改记录
	 * 
	 * @param conn
	 * @param tableName
	 *            表名
	 * @param setMap
	 *            Map<列名,列值>
	 * @param whereMap
	 *            Map<列名,列值> where条件参数，每个条件都是=
	 * @return
	 * @author LiuKun
	 * @created 2012-9-21 下午3:26:57
	 */
	public static Integer update(Connection conn, String tableName,
			Map<String, Object> setMap, Map<String, Object> whereMap) {
		if (null == conn) {
			throw new RuntimeException("数据库连接对象为空！");
		}
		if (null == tableName || "".equals(tableName)) {
			throw new RuntimeException("表名为空！");
		}
		if (null == setMap || setMap.size() == 0) {
			throw new RuntimeException("set参数为空！");
		}
		if (null == whereMap || whereMap.size() == 0) {
			throw new RuntimeException("where参数为空！");
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
	 * 根据sql语句，获取sql语句查询结果集的列名名称
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 上午10:50:45
	 */
	public static String[] getColumns(Connection conn, String sql) {
		if (null == conn) {
			throw new RuntimeException("数据库连接对象！");
		}
		if (Validator.isEmpty(sql)) {
			throw new RuntimeException("sql语句为空！");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] columns = null;
		try {
			// 构造查询表头sql
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
