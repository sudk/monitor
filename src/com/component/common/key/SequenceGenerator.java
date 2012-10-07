package com.component.common.key;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.component.common.spring.util.ConnectionUtil;

/**
 * 表主键生成器，代替原来的sequence写法(单类)
 * @author LiuKun
 * @date Feb 1, 2012
 */
public class SequenceGenerator {
	private Log logger = LogFactory.getLog(SequenceGenerator.class);
	
	private static SequenceGenerator pkGen = new SequenceGenerator();
	/**
	 * 缓存的主键个数
	 */
	private static long cacheKeys = 20;
	/**
	 * 行锁
	 */
	private static String forUpdateString=" for update";
	/**
	 * 主键存放器
	 */
	private HashMap<String, PKInfo> keyList = new HashMap<String, PKInfo>();
	private ThreadLocal<Boolean> IS_AUTO_COMMIT = new ThreadLocal<Boolean>();
	
	public static SequenceGenerator getInstance(){
		String dbType = "DB2";//可配置
		if("ORACLE".equalsIgnoreCase(dbType))
			forUpdateString = " for update";
		else if("DB2".equalsIgnoreCase(dbType))
			forUpdateString = " for read only with rs";
		
		cacheKeys = Long.parseLong("5");//可配置，一次取多少个值
		return pkGen;
	}
	
	public long getNextSequence(String name){
		PKInfo pkInfo;
		pkInfo = keyList.get(name);
		if(pkInfo == null){
			synchronized (keyList){
				pkInfo = (PKInfo)keyList.get(name);
				if (pkInfo == null){
					pkInfo = new PKInfo(name, cacheKeys);
					keyList.put(name, pkInfo);
				}
			}
		}
		return pkInfo.getNextId();
	}

	private void close(Connection conn) {
		if (conn != null) {
			try {
				Boolean isAutoCommit = (Boolean) IS_AUTO_COMMIT.get();
				IS_AUTO_COMMIT.remove();
				if (isAutoCommit != null)
					conn.setAutoCommit(isAutoCommit.booleanValue());
			} catch (Exception e) {
			}
			ConnectionUtil.close(conn);
		}
	}

	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnectionForDataSource();
			IS_AUTO_COMMIT.set(Boolean.valueOf(conn.getAutoCommit()));
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	class PKInfo {
		private long maxId;
		private long currentId;
		private long poolSize;
		private String tabName;
		
		public PKInfo(String name, long poolSize){
			currentId = 1L;
			tabName = name;
			this.poolSize = poolSize;
		}
		
		public synchronized long getNextId(){
			if (currentId > maxId)
				updateMaxId();
			return currentId++;
		}
		
		private void updateMaxId() {
			Connection conn = null;
			try {
				boolean isResetCurrentId = false;
				conn = getConnection();
				long dbExistedCode = getDBExistedValue(conn, tabName, true);
				long newValue = 0L;
				if (dbExistedCode > -1L) {
					if (dbExistedCode > maxId)
						isResetCurrentId = true;
					newValue = dbExistedCode + poolSize;
					update(conn, tabName, newValue);
				} else {
					newValue = poolSize;
					insert(conn, tabName, newValue);
				}
				conn.commit();

				maxId = newValue;
				if (isResetCurrentId)
					currentId = (maxId - poolSize) + 1L;
			} catch (Throwable e) {
				try {
					ConnectionUtil.rollback(conn);
				} catch (Exception e1) {
				}
			} finally {
				close(conn);
			}
		}
		
		private long getDBExistedValue(Connection conn, String seqName, boolean update) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			long result = -1L;

			String querySql = "select VAL from AC_KEY where KEY=? " + forUpdateString;
			try {
				if(logger.isDebugEnabled()){
					logger.debug("exec sql: " + querySql);
				}
				pstmt = conn.prepareStatement(querySql);
				pstmt.setString(1, seqName);
				pstmt.setFetchSize(1);
				for (rs = pstmt.executeQuery(); rs.next();)
					result = rs.getLong(1);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ConnectionUtil.close(rs);
				ConnectionUtil.close(pstmt);
			}
			return result;
		}
		
		private void update(Connection conn, String seqName, long value) {
			String updateSql = "update AC_KEY set VAL = ? where KEY=?";
			PreparedStatement pstmt = null;
			try {
				if(logger.isDebugEnabled()){
					logger.debug("exec sql: " + updateSql);
				}
				pstmt = conn.prepareStatement(updateSql);
				pstmt.setLong(1, value);
				pstmt.setString(2, seqName);
				pstmt.execute();
			} catch (Exception e) {
			} finally {
				ConnectionUtil.close(pstmt);
			}
		}

		private void insert(Connection conn, String seqName, long value) {
			String insertSql = "insert into AC_KEY(KEY,VAL) values(?,?)";
			PreparedStatement pstmt = null;
			try {
				if(logger.isDebugEnabled()){
					logger.debug("exec sql: " + insertSql);
				}
				pstmt = conn.prepareStatement(insertSql);
				pstmt.setString(1, seqName);
				pstmt.setLong(2, value);
				pstmt.execute();
			} catch (Exception e) {
			} finally {
				ConnectionUtil.close(pstmt);
			}
		}
	}
}

