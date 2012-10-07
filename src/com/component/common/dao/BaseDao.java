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
 * 通用持久层（dao层父类）接口
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
	 * 判断数据库表是否存在
	 * 
	 * @param tableName
	 * @return true:存在；false:不存在
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	public Boolean existTable(String tableName) throws SQLException;

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
			throws SQLException;

	/**
	 * 根据表名,删除表
	 * 
	 * @param tableName
	 * @return
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	public void dropTable(String tableName) throws SQLException;

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
	public void dropTable(String schema, String tableName) throws SQLException;

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
	public Integer insert(String tableName, Map<String, Object> valueMap);

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
	public Integer delete(String tableName, Map<String, Object> whereMap);

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
			Map<String, Object> whereMap);

	/**
	 * 根据sql语句，获取sql语句查询结果集的列名名称
	 * 
	 * @param sql
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 上午10:57:12
	 */
	public String[] getColumns(String sql);
}
