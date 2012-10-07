package com.module.fileTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ���ݿ������
 * 
 * Description: ����
 * 
 * @author zhangwu
 * @version 1.0
 * @created 2012-7-24 ����9:26:08
 */
class DB2 {

	public static Statement stmt = null;

	/**
	 * ��ʼ��ʵ�������ö�Ӧ�����ݿ����ӵ�ַ ����
	 * 
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-24 ����9:25:42
	 */
	public static void init() throws SQLException, Exception {

		// ʹ�õ����ݿ�����
		Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		String url = "jdbc:db2://127.0.0.1:50000/monitor";
		String user = "ufcaudit";
		String password = "ufcaudit";
		Connection conn = DriverManager.getConnection(url, user, password);
		stmt = conn.createStatement();

	}

	/**
	 * ����ִ��SQL ����
	 * 
	 * @param sql
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-24 ����9:27:01
	 */
	public static void addBatch(String sql) throws SQLException, Exception {
		if (stmt == null) {
			init();
		}
		stmt.addBatch(sql);

	}

	public static void insertData() throws SQLException, Exception {
		stmt.executeBatch();

	}

	/**
	 * ��ѯ�����ݱ�ṹ ����
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-24 ����9:27:11
	 */
	public static List<ColumnModel> queryColumn(String sql)
			throws SQLException, Exception {
		List<ColumnModel> list = new ArrayList<ColumnModel>();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			ColumnModel data = readResultSet(rs);
			list.add(data);
		}
		return list;
	}

	/**
	 * ת��Ϊ�����ж���
	 */
	public static ColumnModel readResultSet(ResultSet rs) throws SQLException {
		ColumnModel cModel = new ColumnModel();
		Object value = rs.getObject("tabschema".toUpperCase());
		cModel.setTabschema(value.toString());
		value = rs.getObject("tabname".toUpperCase());

		cModel.setTabname(value.toString());
		value = rs.getObject("colname".toUpperCase());
		cModel.setColname(value.toString());
		value = rs.getObject("colno".toUpperCase());
		cModel.setColno(value.toString());
		value = rs.getObject("typename".toUpperCase());
		cModel.setTypaname(value.toString());
		value = rs.getObject("length".toUpperCase());
		cModel.setLength(value.toString());
		value = rs.getObject("nulls".toUpperCase());
		cModel.setNulls(value.toString());
		value = rs.getObject("scale".toUpperCase());
		cModel.setScale(value.toString());
		value = rs.getObject("keyseq".toUpperCase());
		if (value != null) {
			cModel.setKeyseq(value.toString());
		}

		value = rs.getObject("remarks".toUpperCase());
		if (value != null) {
			cModel.setRemarks(value.toString());
		}
		return cModel;
	}

	/**
	 * �����򣬿ɳ�ʼ�����ݿ����� ����
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-24 ����9:27:54
	 */
	public static void main(String args[]) throws SQLException, Exception {

		Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		String url = "jdbc:db2://10.14.128.132:55000/ctaudit";
		String user = "ctaudit";
		String password = "ctaudit";
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.print("connection  Done!OK!!!\n");
		// conn.setAutoCommit(false);
		Statement stmt = conn.createStatement();
		String columnsSql = "select menuaction from AMIS_RBAC_MENU t where t.menuid='10157' ";
		ResultSet rs = stmt.executeQuery(columnsSql);
		while (rs.next()) {
			String menuaction = (String) rs.getString(1);
			System.out.println("menuaction:" + menuaction);
		}
	}

	/**
	 * ִ�в�ѯSQL��䣬�������HashMap���ֶ���Ϊkey����ʽ����List�з���
	 * 
	 * @param sql
	 *            Ҫִ�е�sql���
	 * @return ���ִ�н��
	 * @throws Exception
	 */
	public static List<Map<String, Object>> executeQuery(String sql)
			throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);

		String[] columns = getColumns(rs);
		while (rs.next()) {
			Map<String, Object> rsMap = new LinkedHashMap<String, Object>();
			for (int i = 0; i < columns.length; i++) {
				Object colObj = rs.getObject(columns[i]);
				rsMap.put(columns[i].toUpperCase(), colObj);
			}
			result.add(rsMap);
		}

		return result;
	}

	/**
	 * ��ȡ�ж��� ����
	 * 
	 * @param result
	 * @return
	 * @throws SQLException
	 * @author zhangwu
	 * @created 2012-7-24 ����9:28:22
	 */
	private static String[] getColumns(ResultSet result) throws SQLException {
		ResultSetMetaData rsmd = result.getMetaData();
		int colNum = rsmd.getColumnCount();
		String[] columns = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			columns[i] = rsmd.getColumnName(i + 1).toUpperCase();
		}
		return columns;
	}
}
