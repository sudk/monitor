package com.component.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.exception.ProcudureExecuteException;

/**
 * 
 * 
 * Description: 存储过程执行器
 * 
 * @author guo_lei
 * @version 1.0
 * @created 2012-8-3 下午4:17:18
 */
public class ProcedureExecutor {
	private static final Logger logger = Logger
			.getLogger(ProcedureExecutor.class);
	private static final int CURSOR_TYPE = -10;

	private static int cursorType = CURSOR_TYPE;

	public static int getCursorType() {
		return cursorType;
	}

	public static void setCursorType(int cursorType) {
		ProcedureExecutor.cursorType = cursorType;
	}

	/**
	 * 
	 * 描述 :执行存储过程,进行数据库修改处理
	 * 
	 * @param procName
	 *            存储过程名称
	 * @param procPrts
	 *            存储过程中的参数列表
	 * @param conn
	 *            数据库链接
	 * @return 如果执行失败，则返回失败信息，否则返回空串“”
	 * @throws Exception
	 * @author guo_lei
	 * @created 2012-8-3 下午4:19:38
	 */
	public String executeUpdateProcedure(String procName,
			List<Object> procPrts, Connection conn) throws Exception {
		String pResult = "";
		CallableStatement stmt = null;
		List<Object> returnTypeList = new ArrayList<Object>();
		returnTypeList.add(String.valueOf(Types.INTEGER));
		returnTypeList.add(String.valueOf(Types.VARCHAR));

		try {
			int iPrtsLen = 0;
			if (procPrts != null) {
				iPrtsLen = procPrts.size();
			}
			stmt = executeProcedureSQL(procName, procPrts, returnTypeList,
					conn, stmt);
			if (logger.isDebugEnabled()) {
				logger.debug("execute the update procedure:" + procName);
				logger.debug("the result of executing the update procedure:"
						+ stmt.getInt(iPrtsLen + 1));
				logger.debug("the result message of executing the update procedure:"
						+ stmt.getString(iPrtsLen + 2));
			}

			// 如果执行失败，则返回失败信息，否则返回空串“”
			if (stmt.getInt(iPrtsLen + 1) < 0) {
				String errorMsg = stmt.getString((iPrtsLen + 2));
				logger.error("error in executing the update procedure:"
						+ errorMsg);
				int errorCode = stmt.getInt(iPrtsLen + 1);
				pResult = "错误码:" + errorCode;
				throw new ProcudureExecuteException(pResult);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return pResult;
	}

	/**
	 * 
	 * 描述 :执行存储过程,自定义返回值
	 * 
	 * @param procName
	 * @param procPrts
	 * @param returnTypeList
	 *            返回值类型列表：每个元素是个 String对象(来自java.sql.Type的类型)
	 * @param conn
	 * @return 如果执行失败，则返回失败信息，否则返回空串“”
	 * @throws Exception
	 * @author guo_lei
	 * @created 2012-8-3 下午4:21:31
	 */
	public static List<Object> executeCommonProcedure(String procName,
			List<Object> procPrts, List<Object> returnTypeList, Connection conn)
			throws Exception {
		logger.debug("==To execute the update procedure:" + procName);
		List<Object> resultList = new ArrayList<Object>();
		CallableStatement stmt = null;

		try {
			int iPrtsLen = 0;
			if (procPrts != null) {
				iPrtsLen = procPrts.size();
			}
			stmt = executeProcedureSQL(procName, procPrts, returnTypeList,
					conn, stmt);
			if (returnTypeList != null) {
				for (int i = 0; i < returnTypeList.size(); i++) {
					Object result = stmt.getObject(iPrtsLen + i + 1);
					resultList.add(result);
					logger.debug("the result of executing the update procedure: "
							+ (i + 1) + "===" + result);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return resultList;
	}

	/**
	 * 
	 * 描述
	 * 
	 * @param procName
	 * @param procPrts
	 * @param returnTypeList
	 * @param conn
	 * @param stmt
	 * @return
	 * @throws SQLException
	 * @author guo_lei
	 * @created 2012-8-3 下午4:22:56
	 */

	private static CallableStatement executeProcedureSQL(String procName,
			List<Object> procPrts, List<Object> returnTypeList,
			Connection conn, CallableStatement stmt) throws SQLException {
		int iPrtsLen = 0;
		if (procPrts != null) {
			iPrtsLen = procPrts.size();
		}
		String sql = formatProcedureExecuteSql(procName, returnTypeList,
				iPrtsLen);

		stmt = conn.prepareCall(sql);
		for (int i = 0; i < iPrtsLen; i++) {
			logger.info("---PARAM:" + procPrts.get(i));
			stmt.setString((i + 1), procPrts.get(i).toString());
		}
		if (returnTypeList != null) {
			for (int i = 0; i < returnTypeList.size(); i++) {
				String type = (String) returnTypeList.get(i);
				stmt.registerOutParameter((iPrtsLen + i + 1),
						Integer.valueOf(type));
			}
		}
		stmt.executeUpdate();
		return stmt;
	}

	/**
	 * 
	 * 描述 :格式化执行的sql语句
	 * 
	 * @param procName
	 * @param returnTypeList
	 * @param iPrtsLen
	 * @return
	 * @author guo_lei
	 * @created 2012-8-3 下午4:23:03
	 */
	private static String formatProcedureExecuteSql(String procName,
			List<Object> returnTypeList, int iPrtsLen) {
		StringBuffer sbSql = new StringBuffer("{call ").append(procName)
				.append("(");
		for (int i = 0; i < iPrtsLen; i++) {
			if (i > 0) {
				sbSql.append(",");
			}
			sbSql.append("?");
		}

		if (returnTypeList != null) {
			if (iPrtsLen > 0) {
				sbSql.append(",");
			}
			for (int i = 0; i < returnTypeList.size(); i++) {
				if (i > 0) {
					sbSql.append(",");
				}
				sbSql.append("?");
			}
		}
		sbSql.append(")}"); // 返回的2个参数 procedure out

		logger.info("---sql:" + sbSql.toString());

		return sbSql.toString();
	}

	/**
	 * 
	 * 描述 :执行查询的存储过程
	 * 
	 * @param procName
	 * @param procPrts
	 * @param conn
	 * @return
	 * @throws Exception
	 * @author guo_lei
	 * @created 2012-8-3 下午4:23:18
	 */
	public static ResultSet executeQueryProcedure(String procName,
			List<Object> procPrts, Connection conn) throws Exception {
		CallableStatement stmt = null;
		List<Object> returnTypeList = new ArrayList<Object>();
		returnTypeList.add(String.valueOf(Types.INTEGER));
		returnTypeList.add(String.valueOf(getCursorType()));
		try {
			int iPrtsLen = 0;
			if (procPrts != null) {
				iPrtsLen = procPrts.size();
			}
			stmt = executeProcedureSQL(procName, procPrts, returnTypeList,
					conn, stmt);

			logger.info("execute the update procedure:" + procName);
			logger.info("the result of executing the update procedure:"
					+ stmt.getInt(iPrtsLen + 1));
			// 如果执行失败，则返回失败信息，否则返回空串“”
			if (stmt.getInt(iPrtsLen + 1) < 0) {
				throw new ProcudureExecuteException("Procedure executed error!");
			}

			return (ResultSet) stmt.getObject(2);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex1) {
			}
		}

	}

}
