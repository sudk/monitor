package com.component.common.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.component.common.dao.BaseDao;
import com.component.common.entity.Entity;
import com.component.common.hibernate.util.HibernateUtil;
import com.component.common.jdbc.util.JDBCUtil;
import com.component.common.pagination.PageInfo;

/**
 * 通用持久层（dao层父类）
 * 
 * @author LiuKun
 * @date 2012-6-12
 * @time 10:14
 */
@Repository("baseDao")
public class BaseDaoImpl extends AbstractBaseDaoImpl implements BaseDao {

	// ////////////////////////////////////////////////////////////////////

	@SuppressWarnings("deprecation")
	public Connection getConnection() {
		return getSession().connection();
	}

	// ////////////////////////////////////////////////////////////////////

	public <E extends Entity> void save(E entity) {
		getSession().save(entity);
	}

	public <E extends Entity> void saveOrUpdate(E entity) {
		getSession().saveOrUpdate(entity);
	}

	public <E extends Entity> void save(Collection<E> entitys) {
		for (Iterator<E> iterator = entitys.iterator(); iterator.hasNext();) {
			E e = iterator.next();
			getSession().save(e);
		}
	}

	public <E extends Entity> void update(E entity) {
		getSession().update(entity);
	}

	public <E extends Entity> void delete(E entity) {
		getSession().delete(entity);
	}

	public <E extends Entity> Integer deleteByProperty(Class<E> clazz,
			String propertyName, Object value) {
		String hql = "delete from " + clazz.getName() + " where "
				+ propertyName + " = :" + propertyName;
		Session session = getSession();
		Query query = null;
		query = session.createQuery(hql.toString());
		query.setParameter(propertyName, value);
		return query.executeUpdate();
	}

	public <E extends Entity> void deleteById(Class<E> clazz, Serializable id) {
		getSession().delete(findById(clazz, id));
	}

	public <E extends Entity> void deleteByIds(Class<E> clazz,
			Serializable... ids) {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				getSession().delete(findById(clazz, ids[i]));
			}
		}
	}

	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value) {
		return this
				.findByProperty(clazz, propertyName, value, null, null, null);
	}

	public <E extends Entity> List<E> findAll(Class<E> clazz) {
		return HibernateUtil.findAll(getSession(), clazz);
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity> E findById(Class<E> clazz, Serializable id) {
		return (E) getSession().get(clazz, id);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////

	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value, String operator,
			Map<String, String> orderMap, PageInfo pageInfo) {
		List<E> list = null;
		if (pageInfo != null) {
			list = HibernateUtil.findByProperty(getSession(), clazz,
					propertyName, value, operator, orderMap, pageInfo);
			pageInfo.setResult(list);
		} else {
			list = HibernateUtil.findByProperty(getSession(), clazz,
					propertyName, value, operator, orderMap, null);
		}
		return list;
	}

	public <E extends Entity> List<E> find(Class<E> clazz,
			Map<String, Object> valueMap, Map<String, String> operatorMap,
			Map<String, String> orderMap, PageInfo pageInfo) {
		List<E> list = null;
		if (pageInfo != null) {
			list = HibernateUtil.find(getSession(), clazz, valueMap,
					operatorMap, orderMap, pageInfo);
			pageInfo.setResult(list);
		} else {
			list = HibernateUtil.find(getSession(), clazz, valueMap,
					operatorMap, orderMap, null);
		}
		return list;
	}

	/**
	 * 判断数据库表是否存在
	 * 
	 * @param tableName
	 * @return true:存在；false:不存在
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	public Boolean existTable(String tableName) throws SQLException {
		return JDBCUtil.existTable(getConnection(), tableName);
	}

	/**
	 * 判断数据库表是否存在
	 * 
	 * @param schema
	 * @param tableName
	 * @return true:存在；false:不存在
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	public Boolean existTable(String schema, String tableName)
			throws SQLException {
		return JDBCUtil.existTable(getConnection(), schema, tableName);
	}

	/**
	 * 根据表名,删除表
	 * 
	 * @param tableName
	 * @return
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	public void dropTable(String tableName) throws SQLException {
		JDBCUtil.dropTable(getConnection(), tableName);
	}

	/**
	 * 根据表名,删除表
	 * 
	 * @param schema
	 * @param tableName
	 * @return
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	public void dropTable(String schema, String tableName) throws SQLException {
		JDBCUtil.dropTable(getConnection(), schema, tableName);
	}

	/**
	 * 插入记录
	 * 
	 * @param tableName
	 *            表名
	 * @param valueMap
	 *            值Map
	 * @return
	 * @author LiuKun
	 * @created 2012-9-24 上午10:59:31
	 */
	public Integer insert(String tableName, Map<String, Object> valueMap) {
		return JDBCUtil.insert(getConnection(), tableName, valueMap);
	}

	/**
	 * 删除记录
	 * 
	 * @param tableName
	 *            表名
	 * @param whereMap
	 *            where查询条件Map
	 * @return
	 * @author LiuKun
	 * @created 2012-9-24 上午10:57:11
	 */
	public Integer delete(String tableName, Map<String, Object> whereMap) {
		return JDBCUtil.delete(getConnection(), tableName, whereMap);
	}

	/**
	 * 修改记录
	 * 
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
	public Integer update(String tableName, Map<String, Object> setMap,
			Map<String, Object> whereMap) {
		return JDBCUtil.update(getConnection(), tableName, setMap, whereMap);
	}

	/**
	 * 根据sql语句，获取sql语句查询结果集的列名名称
	 * 
	 * @param sql
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 上午10:57:12
	 */
	public String[] getColumns(String sql) {
		return JDBCUtil.getColumns(getConnection(), sql);
	}
}
