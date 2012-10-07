package com.component.common.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.component.common.dao.BaseDao;
import com.component.common.entity.Entity;
import com.component.common.jdbc.util.JDBCUtil;
import com.component.common.pagination.PageInfo;
import com.component.common.util.Validator;

/**
 * 
 * 通用业务逻辑层（service层抽象父类）
 * 
 * @author LiuKun
 * @date 2012-07-16
 * @time 15:44
 */
@Transactional
public abstract class BaseService {

	@Resource
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 保存单个实体
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 上午9:55:48
	 */
	@Transactional
	public <E extends Entity> void save(E entity) {
		baseDao.save(entity);
	}

	/**
	 * 保存或修改单个实体
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 上午9:56:08
	 */
	@Transactional
	public <E extends Entity> void saveOrUpdate(E entity) {
		baseDao.saveOrUpdate(entity);
	}

	/**
	 * 保存实体集合
	 * 
	 * @param entitys
	 * @author LiuKun
	 * @created 2012-8-2 上午9:56:27
	 */
	@Transactional
	public <E extends Entity> void save(Collection<E> entitys) {
		if (Validator.isNotEmpty(entitys)) {
			baseDao.save(entitys);
		}
	}

	/**
	 * 修改单个实体
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 上午9:56:55
	 */
	@Transactional
	public <E extends Entity> void update(E entity) {
		baseDao.update(entity);
	}

	/**
	 * 删除单个实体
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 上午9:57:16
	 */
	@Transactional
	public <E extends Entity> void delete(E entity) {
		baseDao.delete(entity);

	}

	/**
	 * 根据实体主键值，删除实体
	 * 
	 * @param clazz
	 * @param id
	 * @author LiuKun
	 * @created 2012-8-2 上午9:57:47
	 */
	@Transactional
	public <E extends Entity> void deleteById(Class<E> clazz, Serializable id) {
		baseDao.deleteById(clazz, id);
	}

	/**
	 * 根据实体主键数组，删除实体
	 * 
	 * @param clazz
	 * @param ids
	 * @author LiuKun
	 * @created 2012-8-2 上午9:58:07
	 */
	@Transactional
	public <E extends Entity> void deleteByIds(Class<E> clazz,
			Serializable... ids) {
		baseDao.deleteByIds(clazz, ids);
	}

	/**
	 * 根据实体的某个属性，删除实体
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @author LiuKun
	 * @created 2012-8-2 上午9:59:04
	 */
	@Transactional
	public <E extends Entity> void deleteByProperty(Class<E> clazz,
			String propertyName, Object value) {
		baseDao.deleteByProperty(clazz, propertyName, value);
	}

	/**
	 * 根据实体主键，查询实体
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:00:04
	 */
	@Transactional
	public <E extends Entity> E findById(Class<E> clazz, Serializable id) {
		return baseDao.findById(clazz, id);
	}

	/**
	 * 根据实体类对象，查询所有实体集合
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:00:49
	 */
	@Transactional
	public <E extends Entity> List<E> findAll(Class<E> clazz) {
		return baseDao.findAll(clazz);
	}

	/**
	 * 根据实体某个属性，查询实体集合
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:02:22
	 */
	@Transactional
	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value) {
		return baseDao.findByProperty(clazz, propertyName, value, null, null,
				null);
	}

	// ////////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据实体某个属性，查询实体集合
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:02:22
	 */
	@Transactional
	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value, String operator) {
		return baseDao.findByProperty(clazz, propertyName, value, operator,
				null, null);
	}

	/**
	 * 
	 * 根据实体某个属性，查询实体集合
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @param operator
	 * @param orderMap
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:03:21
	 */
	@Transactional
	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value, String operator,
			Map<String, String> orderMap) {
		return baseDao.findByProperty(clazz, propertyName, value, operator,
				orderMap, null);
	}

	/**
	 * 
	 * 根据实体某个属性，查询实体集合
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @param operator
	 * @param orderMap
	 * @param pageInfo
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:03:39
	 */
	@Transactional
	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value, String operator,
			Map<String, String> orderMap, PageInfo pageInfo) {
		return baseDao.findByProperty(clazz, propertyName, value, operator,
				orderMap, pageInfo);
	}

	/**
	 * 根据实体属性，查询实体集合
	 * 
	 * @param clazz
	 * @param valueMap
	 * @param operatorMap
	 * @param orderMap
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:04:17
	 */
	@Transactional
	public <E extends Entity> List<E> find(Class<E> clazz,
			Map<String, Object> valueMap, Map<String, String> operatorMap,
			Map<String, String> orderMap) {
		return baseDao.find(clazz, valueMap, operatorMap, orderMap, null);
	}

	/**
	 * 根据实体属性，查询实体集合
	 * 
	 * @param clazz
	 * @param valueMap
	 * @param operatorMap
	 * @param orderMap
	 * @param pageInfo
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:04:44
	 */
	@Transactional
	public <E extends Entity> List<E> find(Class<E> clazz,
			Map<String, Object> valueMap, Map<String, String> operatorMap,
			Map<String, String> orderMap, PageInfo pageInfo) {
		return baseDao.find(clazz, valueMap, operatorMap, orderMap, pageInfo);
	}

	// /////////////////////////////////////////////////////////////

	/**
	 * 执行sql查询语句
	 * 
	 * @param sql
	 * @param pageInfo
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:05:23
	 */
	@Transactional
	public List<Map<String, Object>> executeQuery(String sql,
			PageInfo pageInfo, Object... params) {
		List<Map<String, Object>> mapList = null;
		mapList = JDBCUtil.executeQuery(baseDao.getConnection(), sql, pageInfo,
				params);
		return mapList;
	}

	/**
	 * 执行sql更新语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 上午10:05:47
	 */
	@Transactional
	public Integer executeUpdate(String sql, Object... params) {
		Integer count = null;
		count = JDBCUtil.executeUpdate(baseDao.getConnection(), sql, params);
		return count;
	}

	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * 检测某个属性值是否存在
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-7-28 上午11:36:02
	 */
	@Transactional
	public <E extends Entity> Boolean checkExist(Class<E> clazz,
			String propertyName, Object value) {
		List<E> list = findByProperty(clazz, propertyName, value);
		if (Validator.isEmpty(list)) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
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
	@Transactional
	public Boolean existTable(String tableName) {
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("数据库表名为空！");
		}
		Boolean flag = Boolean.FALSE;
		try {
			flag = getBaseDao().existTable(tableName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return flag;
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
	@Transactional
	public Boolean existTable(String schema, String tableName) {
		try {
			return getBaseDao().existTable(schema, tableName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断数据库表是否存在(只适用于DB2)
	 * 
	 * @param schema
	 * @param tableName
	 * @return true:存在；false:不存在
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	@Transactional
	public Boolean existTableForDB2(String schema, String tableName) {
		if (Validator.isEmpty(schema)) {
			throw new RuntimeException("schema为空！");
		}
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("tableName为空！");
		}
		String sql = " SELECT NAME FROM SYSIBM.SYSTABLES WHERE CREATOR = ? AND NAME = ?  ";
		List<Map<String, Object>> mapList = executeQuery(sql, null,
				schema.toUpperCase(), tableName.toUpperCase());
		if (Validator.isNotEmpty(mapList)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 根据表名,删除表
	 * 
	 * @param schema
	 * @param tableName
	 * @return true:存在；false:不存在
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	@Transactional
	public void dropTable(String schema, String tableName) {
		try {
			getBaseDao().dropTable(schema, tableName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据表名,删除表
	 * 
	 * @param tableName
	 * @return true:存在；false:不存在
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 下午7:29:24
	 */
	@Transactional
	public void dropTable(String tableName) {
		try {
			getBaseDao().dropTable(tableName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
	@Transactional
	public Integer insert(String tableName, Map<String, Object> valueMap) {
		return getBaseDao().insert(tableName, valueMap);
	}

	/**
	 * 插入记录
	 * 
	 * @param tableName
	 *            表名
	 * @param mapList
	 *            List<值Map>
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 下午6:01:29
	 */
	@Transactional
	public Integer insert(String tableName, List<Map<String, Object>> mapList) {
		Integer count = 0;
		for (int i = 0; null != mapList && i < mapList.size(); i++) {
			Map<String, Object> valueMap = mapList.get(i);
			int temp = getBaseDao().insert(tableName, valueMap);
			count += temp;
		}
		return count;
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
	@Transactional
	public Integer delete(String tableName, Map<String, Object> whereMap) {
		return getBaseDao().delete(tableName, whereMap);
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
	@Transactional
	public Integer update(String tableName, Map<String, Object> setMap,
			Map<String, Object> whereMap) {
		return getBaseDao().update(tableName, setMap, whereMap);
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
		return getBaseDao().getColumns(sql);
	}
}
