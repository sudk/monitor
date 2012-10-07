package com.component.common.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.component.common.entity.Entity;
import com.component.common.pagination.PageInfo;

/**
 * ͨ�ó־ò㣨dao�㸸�ࣩ�ӿ�
 * 
 * @author LiuKun
 * @date 2012-6-12
 * @time 10:14
 */
public interface BaseDao {

	// ////////////////////////////////////////////////////////////////////

	public Connection getConnection();

	// ////////////////////////////////////////////////////////////////////

	public <E extends Entity> void save(E entity);

	public <E extends Entity> void saveOrUpdate(E entity);

	public <E extends Entity> void save(Collection<E> entitys);

	public <E extends Entity> void update(E entity);

	public <E extends Entity> void delete(E entity);

	public <E extends Entity> void deleteById(Class<E> clazz, Serializable id);

	public <E extends Entity> void deleteByIds(Class<E> clazz,
			Serializable... ids);

	public <E extends Entity> Integer deleteByProperty(Class<E> clazz,
			String propertyName, Object value);

	public <E extends Entity> List<E> findAll(Class<E> clazz);

	public <E extends Entity> E findById(Class<E> clazz, Serializable id);

	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value);

	// /////////////////////////////////////////////////////////////////////////

	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value, String operator,
			Map<String, String> orderMap, PageInfo pageInfo);

	public <E extends Entity> List<E> find(Class<E> clazz,
			Map<String, Object> valueMap, Map<String, String> operatorMap,
			Map<String, String> orderMap, PageInfo pageInfo);

	/**
	 * �ж����ݿ���Ƿ����
	 * 
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	public Boolean existTable(String tableName) throws SQLException;

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
			throws SQLException;

	/**
	 * ���ݱ���,ɾ����
	 * 
	 * @param tableName
	 * @return
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	public void dropTable(String tableName) throws SQLException;

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
	public void dropTable(String schema, String tableName) throws SQLException;

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
	public Integer insert(String tableName, Map<String, Object> valueMap);

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
	public Integer delete(String tableName, Map<String, Object> whereMap);

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
			Map<String, Object> whereMap);

	/**
	 * ����sql��䣬��ȡsql����ѯ���������������
	 * 
	 * @param sql
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 ����10:57:12
	 */
	public String[] getColumns(String sql);
}
