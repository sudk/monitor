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
 * ͨ��ҵ���߼��㣨service������ࣩ
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
	 * ���浥��ʵ��
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 ����9:55:48
	 */
	@Transactional
	public <E extends Entity> void save(E entity) {
		baseDao.save(entity);
	}

	/**
	 * ������޸ĵ���ʵ��
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 ����9:56:08
	 */
	@Transactional
	public <E extends Entity> void saveOrUpdate(E entity) {
		baseDao.saveOrUpdate(entity);
	}

	/**
	 * ����ʵ�弯��
	 * 
	 * @param entitys
	 * @author LiuKun
	 * @created 2012-8-2 ����9:56:27
	 */
	@Transactional
	public <E extends Entity> void save(Collection<E> entitys) {
		if (Validator.isNotEmpty(entitys)) {
			baseDao.save(entitys);
		}
	}

	/**
	 * �޸ĵ���ʵ��
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 ����9:56:55
	 */
	@Transactional
	public <E extends Entity> void update(E entity) {
		baseDao.update(entity);
	}

	/**
	 * ɾ������ʵ��
	 * 
	 * @param entity
	 * @author LiuKun
	 * @created 2012-8-2 ����9:57:16
	 */
	@Transactional
	public <E extends Entity> void delete(E entity) {
		baseDao.delete(entity);

	}

	/**
	 * ����ʵ������ֵ��ɾ��ʵ��
	 * 
	 * @param clazz
	 * @param id
	 * @author LiuKun
	 * @created 2012-8-2 ����9:57:47
	 */
	@Transactional
	public <E extends Entity> void deleteById(Class<E> clazz, Serializable id) {
		baseDao.deleteById(clazz, id);
	}

	/**
	 * ����ʵ���������飬ɾ��ʵ��
	 * 
	 * @param clazz
	 * @param ids
	 * @author LiuKun
	 * @created 2012-8-2 ����9:58:07
	 */
	@Transactional
	public <E extends Entity> void deleteByIds(Class<E> clazz,
			Serializable... ids) {
		baseDao.deleteByIds(clazz, ids);
	}

	/**
	 * ����ʵ���ĳ�����ԣ�ɾ��ʵ��
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @author LiuKun
	 * @created 2012-8-2 ����9:59:04
	 */
	@Transactional
	public <E extends Entity> void deleteByProperty(Class<E> clazz,
			String propertyName, Object value) {
		baseDao.deleteByProperty(clazz, propertyName, value);
	}

	/**
	 * ����ʵ����������ѯʵ��
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:00:04
	 */
	@Transactional
	public <E extends Entity> E findById(Class<E> clazz, Serializable id) {
		return baseDao.findById(clazz, id);
	}

	/**
	 * ����ʵ������󣬲�ѯ����ʵ�弯��
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:00:49
	 */
	@Transactional
	public <E extends Entity> List<E> findAll(Class<E> clazz) {
		return baseDao.findAll(clazz);
	}

	/**
	 * ����ʵ��ĳ�����ԣ���ѯʵ�弯��
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:02:22
	 */
	@Transactional
	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value) {
		return baseDao.findByProperty(clazz, propertyName, value, null, null,
				null);
	}

	// ////////////////////////////////////////////////////////////////////////////////

	/**
	 * ����ʵ��ĳ�����ԣ���ѯʵ�弯��
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:02:22
	 */
	@Transactional
	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value, String operator) {
		return baseDao.findByProperty(clazz, propertyName, value, operator,
				null, null);
	}

	/**
	 * 
	 * ����ʵ��ĳ�����ԣ���ѯʵ�弯��
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @param operator
	 * @param orderMap
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:03:21
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
	 * ����ʵ��ĳ�����ԣ���ѯʵ�弯��
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @param operator
	 * @param orderMap
	 * @param pageInfo
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:03:39
	 */
	@Transactional
	public <E extends Entity> List<E> findByProperty(Class<E> clazz,
			String propertyName, Object value, String operator,
			Map<String, String> orderMap, PageInfo pageInfo) {
		return baseDao.findByProperty(clazz, propertyName, value, operator,
				orderMap, pageInfo);
	}

	/**
	 * ����ʵ�����ԣ���ѯʵ�弯��
	 * 
	 * @param clazz
	 * @param valueMap
	 * @param operatorMap
	 * @param orderMap
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:04:17
	 */
	@Transactional
	public <E extends Entity> List<E> find(Class<E> clazz,
			Map<String, Object> valueMap, Map<String, String> operatorMap,
			Map<String, String> orderMap) {
		return baseDao.find(clazz, valueMap, operatorMap, orderMap, null);
	}

	/**
	 * ����ʵ�����ԣ���ѯʵ�弯��
	 * 
	 * @param clazz
	 * @param valueMap
	 * @param operatorMap
	 * @param orderMap
	 * @param pageInfo
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:04:44
	 */
	@Transactional
	public <E extends Entity> List<E> find(Class<E> clazz,
			Map<String, Object> valueMap, Map<String, String> operatorMap,
			Map<String, String> orderMap, PageInfo pageInfo) {
		return baseDao.find(clazz, valueMap, operatorMap, orderMap, pageInfo);
	}

	// /////////////////////////////////////////////////////////////

	/**
	 * ִ��sql��ѯ���
	 * 
	 * @param sql
	 * @param pageInfo
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:05:23
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
	 * ִ��sql�������
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @author LiuKun
	 * @created 2012-8-2 ����10:05:47
	 */
	@Transactional
	public Integer executeUpdate(String sql, Object... params) {
		Integer count = null;
		count = JDBCUtil.executeUpdate(baseDao.getConnection(), sql, params);
		return count;
	}

	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * ���ĳ������ֵ�Ƿ����
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @return
	 * @author LiuKun
	 * @created 2012-7-28 ����11:36:02
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
	 * �ж����ݿ���Ƿ����
	 * 
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	@Transactional
	public Boolean existTable(String tableName) {
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("���ݿ����Ϊ�գ�");
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
	 * �ж����ݿ���Ƿ����
	 * 
	 * @param schema
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
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
	 * �ж����ݿ���Ƿ����(ֻ������DB2)
	 * 
	 * @param schema
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
	 */
	@Transactional
	public Boolean existTableForDB2(String schema, String tableName) {
		if (Validator.isEmpty(schema)) {
			throw new RuntimeException("schemaΪ�գ�");
		}
		if (Validator.isEmpty(tableName)) {
			throw new RuntimeException("tableNameΪ�գ�");
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
	 * ���ݱ���,ɾ����
	 * 
	 * @param schema
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
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
	 * ���ݱ���,ɾ����
	 * 
	 * @param tableName
	 * @return true:���ڣ�false:������
	 * @author LiuKun
	 * @throws SQLException
	 * @created 2012-8-9 ����7:29:24
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
	@Transactional
	public Integer insert(String tableName, Map<String, Object> valueMap) {
		return getBaseDao().insert(tableName, valueMap);
	}

	/**
	 * �����¼
	 * 
	 * @param tableName
	 *            ����
	 * @param mapList
	 *            List<ֵMap>
	 * @return
	 * @author LiuKun
	 * @created 2012-9-26 ����6:01:29
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
	@Transactional
	public Integer delete(String tableName, Map<String, Object> whereMap) {
		return getBaseDao().delete(tableName, whereMap);
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
	@Transactional
	public Integer update(String tableName, Map<String, Object> setMap,
			Map<String, Object> whereMap) {
		return getBaseDao().update(tableName, setMap, whereMap);
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
		return getBaseDao().getColumns(sql);
	}
}
