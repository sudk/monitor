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
 * ͨ�ó־ò㣨dao�㸸�ࣩ
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
	 * �ж����ݿ���Ƿ����
	 * 
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	public Boolean existTable(String tableName) throws SQLException {
		return JDBCUtil.existTable(getConnection(), tableName);
	}

	/**
	 * �ж����ݿ���Ƿ����
	 * 
	 * @param schema
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	public Boolean existTable(String schema, String tableName)
			throws SQLException {
		return JDBCUtil.existTable(getConnection(), schema, tableName);
	}

	/**
	 * ���ݱ���,ɾ����
	 * 
	 * @param tableName
	 * @return
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	public void dropTable(String tableName) throws SQLException {
		JDBCUtil.dropTable(getConnection(), tableName);
	}

	/**
	 * ���ݱ���,ɾ����
	 * 
	 * @param schema
	 * @param tableName
	 * @return
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	public void dropTable(String schema, String tableName) throws SQLException {
		JDBCUtil.dropTable(getConnection(), schema, tableName);
	}

	/**
	 * �����¼
	 * 
	 * @param tableName
	 *            ����
	 * @param valueMap
	 *            ֵMap
	 * @return
	 * @author LiuKun
	 * @created 2012-9-24 ����10:59:31
	 */
	public Integer insert(String tableName, Map<String, Object> valueMap) {
		return JDBCUtil.insert(getConnection(), tableName, valueMap);
	}

	/**
	 * ɾ����¼
	 * 
	 * @param tableName
	 *            ����
	 * @param whereMap
	 *            where��ѯ����Map
	 * @return
	 * @author LiuKun
	 * @created 2012-9-24 ����10:57:11
	 */
	public Integer delete(String tableName, Map<String, Object> whereMap) {
		return JDBCUtil.delete(getConnection(), tableName, whereMap);
	}

	/**
	 * �޸ļ�¼
	 * 
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
	public Integer update(String tableName, Map<String, Object> setMap,
			Map<String, Object> whereMap) {
		return JDBCUtil.update(getConnection(), tableName, setMap, whereMap);
	}

	/**
	 * ����sql��䣬��ȡsql����ѯ���������������
	 * 
	 * @param sql
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 ����10:57:12
	 */
	public String[] getColumns(String sql) {
		return JDBCUtil.getColumns(getConnection(), sql);
	}
}
