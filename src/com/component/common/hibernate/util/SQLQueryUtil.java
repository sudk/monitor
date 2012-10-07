package com.component.common.hibernate.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.component.common.pagination.PageInfo;
import com.component.common.util.ParseUtil;
import com.component.common.util.Validator;

/**
 * hibernate SQLQuery 查询帮助类(hibernate native sql 查询)
 * 
 * @author LiuKun
 * @date 2012-07-14
 * @time 15:28
 */
public class SQLQueryUtil implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6308179770929082749L;

	private static final Logger logger = Logger.getLogger(SQLQueryUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	// ////////////////////////////////////////////////////////////

	private static void setParameter(SQLQuery sqlQuery, Object... params) {
		if (Validator.isNotEmpty(params)) {
			for (int i = 0; i < params.length; i++) {
				sqlQuery.setParameter(i, params[i]);
			}
		}
	}

	public static Integer getCount(Session session, String sql,
			Object... params) {
		Integer total = 0;
		String realSql = "select count(*) from ( " + sql + " )";
		SQLQuery sqlQuery = session.createSQLQuery(realSql);
		setParameter(sqlQuery, params);
		Object result = sqlQuery.uniqueResult();
		total = ParseUtil.parseInteger(result);
		return total;
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> find(Session session, String sql, Class<E> clazz,
			PageInfo pageInfo, Object... params) {
		List<E> list = new ArrayList<E>();
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		setParameter(sqlQuery, params);
		if (pageInfo != null) {
			Integer first = (pageInfo.getPageIndex() - 1)
					* pageInfo.getPageSize();
//			if(ValidateUtil.isEmpty(first)){
//				first = 1;
//			}
			sqlQuery.setFirstResult(first);
			@SuppressWarnings("unused")
			Integer pageSize = pageInfo.getPageSize();
//			if(Validator.isEmpty(pageSize)){
//				pageSize = PageInfoUtil.getDefaultPagesize();
//			}
			sqlQuery.setMaxResults(pageInfo.getPageSize());
			Integer total = getCount(session, sql, params);
			pageInfo.setPageTotal(total);
		}
		sqlQuery = sqlQuery.addEntity("model",clazz);
		list = sqlQuery.list();
		return list;
	}

}
