package com.component.common.hibernate.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.component.common.entity.Entity;
import com.component.common.pagination.PageInfo;
import com.component.common.spring.util.SpringUtil;
import com.component.common.util.ParseUtil;
import com.component.common.util.Validator;

/**
 * hibernate ������
 * 
 * @author LiuKun
 * @date 2012-07-17
 * @time 15:31
 * 
 */
public class HibernateUtil implements QueryConstants, Serializable {
	/**
	 * ���к�
	 */
	private static final long serialVersionUID = -2607688491120241480L;

	private HibernateUtil() {

	}

	private static final Logger logger = Logger.getLogger(HibernateUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static HibernateUtil instance;

	public static HibernateUtil getInstance() {
		if (null == instance) {
			synchronized (HibernateUtil.class) {
				if (null == instance) {
					instance = new HibernateUtil();
				}
			}
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////////////////////
	public static void addOrder(Criteria criteria, String propertyName,
			String order) {
		if (propertyName == null || order == null) {
			return;
		}
		if (ASC.equalsIgnoreCase(order)) {
			criteria = criteria.addOrder(Order.asc(propertyName));
		} else if (DESC.equalsIgnoreCase(order)) {
			criteria = criteria.addOrder(Order.desc(propertyName));
		} else {// Ĭ������
			criteria = criteria.addOrder(Order.asc(propertyName));
		}
	}

	public static void addOrders(Criteria criteria, Map<String, String> orderMap) {
		if (orderMap == null) {
			return;
		}
		Iterator<String> iterOrders = orderMap.keySet().iterator();
		String name = "";
		String order = "";
		while (iterOrders.hasNext()) {
			name = iterOrders.next();
			order = orderMap.get(name);
			addOrder(criteria, name, order);
		}
	}

	public static Criteria addCriterion(Criteria criteria, String propertyName,
			Object value, String operator) {
		if (criteria == null || propertyName == null) {
			return criteria;
		}

		if (value == null) {
			return criteria.add(Restrictions.or(Restrictions.eq(propertyName,
					""), org.hibernate.criterion.Property.forName(propertyName)
					.isNull()));
		}

		if (operator == null) {
			criteria = criteria.add(Restrictions.eq(propertyName, value));
		} else if (EQ.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.eq(propertyName, value));
		} else if (LIKE_START.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.like(propertyName,
					(String) value, MatchMode.START));
		} else if (LIKE_END.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.like(propertyName,
					(String) value, MatchMode.END));
		} else if (LIKE.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.like(propertyName,
					(String) value, MatchMode.ANYWHERE));
		} else if (LT.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.lt(propertyName, value));
		} else if (LE.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.le(propertyName, value));
		} else if (GE.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.ge(propertyName, value));
		} else if (GT.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.gt(propertyName, value));
		} else if (NE.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.ne(propertyName, value));
		} else if (IN.equalsIgnoreCase(operator)) {
			if (value instanceof Collection) {
				criteria = criteria.add(Restrictions.in(propertyName,
						(Collection<?>) value));
			} else if (value instanceof Object[]) {
				criteria = criteria.add(Restrictions.in(propertyName,
						(Object[]) value));
			}
		} else if (NOT_IN.equalsIgnoreCase(operator)) {
			if (value instanceof Collection) {
				criteria = criteria.add(Restrictions.not(Restrictions.in(
						propertyName, (Collection<?>) value)));
			} else if (value instanceof Object[]) {
				criteria = criteria.add(Restrictions.not(Restrictions.in(
						propertyName, (Object[]) value)));
			}
		} else if (BETWEEN.equalsIgnoreCase(operator)) {
			if (value instanceof List) {
				List<?> list = (List<?>) value;
				criteria = criteria.add(Restrictions.between(propertyName,
						list.get(0), list.get(1)));
			} else if (value instanceof Object[]) {
				Object[] objs = (Object[]) value;
				criteria = criteria.add(Restrictions.between(propertyName,
						objs[0], objs[1]));
			}
		} else if (SQL.equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.sqlRestriction(value
					.toString()));
		} else {
			criteria = criteria.add(Restrictions.eq(propertyName, value));
		}
		return criteria;
	}

	public static Criteria addCriterion(Criteria criteria,
			Map<String, Object> valueMap, Map<String, String> operatorMap) {
		if (criteria == null || valueMap == null) {
			return criteria;
		}

		if (operatorMap == null) {
			operatorMap = new LinkedHashMap<String, String>();
		}
		Object value = null;
		for (String propertyName : valueMap.keySet()) {
			value = valueMap.get(propertyName);
			addCriterion(criteria, propertyName, value,
					operatorMap.get(propertyName));
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public static <E extends Entity> List<E> findAll(Session session,
			Class<E> clazz) {
		Criteria criteria = session.createCriteria(clazz);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public static <E extends Entity> List<E> find(Session session,
			Class<E> clazz, Map<String, Object> valueMap,
			Map<String, String> operatorMap, Map<String, String> orderMap,
			PageInfo pageInfo) {
		if (logger.isInfoEnabled()) {
			if (Validator.isNotEmpty(valueMap)) {
				String params = "";
				for (String key : valueMap.keySet()) {
					params += "," + key + "=" + valueMap.get(key);
				}
				params = params.replaceFirst(",", "");
				logger.info("params={" + params + "}");
			}
		}
		Criteria criteria = session.createCriteria(clazz);
		addCriterion(criteria, valueMap, operatorMap);
		// ��ӷ�ҳ
		if (pageInfo != null) {
			Integer pageTotal = 0;
			criteria.setProjection(null);
			List<?> tempList = criteria.setProjection(Projections.rowCount())
					.list();
			Object obj = null;
			if (tempList != null && tempList.size() != 0) {
				for (int i = 0; i < tempList.size(); i++) {
					obj = tempList.get(i);
					Integer temp = ParseUtil.parseInteger(obj);
					if (temp > pageTotal) {
						pageTotal = temp;
					}
				}
			}
			criteria.setProjection(null);
			pageInfo.setPageTotal(pageTotal);
		}

		// �����������
		addOrders(criteria, orderMap);

		// ������ҳ
		if (pageInfo != null) {
			criteria.setFirstResult((pageInfo.getPageIndex() - 1)
					* pageInfo.getPageSize());
			criteria.setMaxResults(pageInfo.getPageSize());
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public static <E extends Entity> List<E> findByProperty(Session session,
			Class<E> clazz, String propertyName, Object value, String operator,
			Map<String, String> orderMap, PageInfo pageInfo) {
		Criteria criteria = session.createCriteria(clazz);
		HibernateUtil.addCriterion(criteria, propertyName, value, operator);
		int totalCount = 0;
		if (pageInfo != null) {
			Object rowCountObject = criteria.setProjection(
					Projections.rowCount()).uniqueResult();
			if (rowCountObject != null) {
				totalCount = Integer.valueOf(rowCountObject.toString());
			}
		}
		HibernateUtil.addOrders(criteria, orderMap);
		if (pageInfo != null) {
			criteria.setProjection(null)
					.setFirstResult(
							(pageInfo.getPageIndex() - 1)
									* pageInfo.getPageSize())
					.setMaxResults(pageInfo.getPageSize());
			pageInfo.setPageTotal(totalCount);
		}
		return criteria.list();
	}

	// ////////////////////////////////////////////////////////

	public static final String SESSION_FACTORY_NAME = "&sessionFactory";

	/**
	 * ��ȡSessionFactory
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����9:35:38
	 */
	public static SessionFactory getSessionFactory() {
		// LocalSessionFactoryBean lsfb = (LocalSessionFactoryBean) SpringUtil
		// .getBean(SESSION_FACTORY_NAME);
		// return (SessionFactory) lsfb.getObject();

		AnnotationSessionFactoryBean asfb = (AnnotationSessionFactoryBean) SpringUtil
				.getBean(SESSION_FACTORY_NAME);// ������ʵ�ǻ�ȡ��SessionFactory����һ������
		return (SessionFactory) asfb.getObject();
	}

	/**
	 * ��ȡHibernate��Configuration
	 * 
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����9:48:58
	 */
	public static Configuration getConfiguration() {
		// LocalSessionFactoryBean lsfb = (LocalSessionFactoryBean) SpringUtil
		// .getBean(SESSION_FACTORY_NAME);
		// return lsfb.getConfiguration();
		AnnotationSessionFactoryBean asfb = (AnnotationSessionFactoryBean) SpringUtil
				.getBean(SESSION_FACTORY_NAME);// ������ʵ�ǻ�ȡ��SessionFactory����һ������
		return asfb.getConfiguration();
	}

	// private static Configuration hibernateConf;
	//
	// private static Configuration getHibernateConf() {
	// if (hibernateConf == null) {
	// // hibernateConf=new Configuration();//*.hbm.xml��ʽ
	// hibernateConf = new AnnotationConfiguration().configure();// ע�ⷽʽ
	// hibernateConf.buildSessionFactory();// ע�ⷽʽ�����
	// }
	// return hibernateConf;
	// }

	/**
	 * ��ȡMap<����,ʵ��ȫ��>
	 * 
	 * @return Map<String,String> = Map<����,ʵ��ȫ��>
	 * @author LiuKun
	 * @created 2012-7-27 ����9:38:40
	 */
	public static Map<String, String> getEntityclassnamefortablenamemap() {
		Map<String, ClassMetadata> metaMap = getSessionFactory()
				.getAllClassMetadata();
		Map<String, String> nameMap = new HashMap<String, String>();
		for (String key : metaMap.keySet()) {
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap
					.get(key);
			String tableName = classMetadata.getTableName();
			String className = classMetadata.getEntityMetamodel().getName();
			nameMap.put(tableName, className);
		}
		return nameMap;
	}

	/**
	 * ʵ��־û�����
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����9:53:19
	 */
	public static <E> PersistentClass getPersistentClass(Class<E> clazz) {
		synchronized (HibernateUtil.class) {
			PersistentClass persistentClass = getConfiguration()
					.getClassMapping(clazz.getName());
			if (null == persistentClass) {
				persistentClass = getConfiguration().addClass(clazz)
						.getClassMapping(clazz.getName());// *.hbm.xml��ʽ
				persistentClass = getConfiguration().getClassMapping(
						clazz.getName());
			}
			return persistentClass;
		}
	}

	/**
	 * ��ȡʵ�����ӳ�����������
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����10:01:20
	 */
	public static <E> SingleTableEntityPersister getSingleTableEntityPersister(
			Class<E> clazz) {
		return (SingleTableEntityPersister) getClassMetadata(clazz);
	}

	/**
	 * ��ȡʵ�����ӳ�����������
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����10:01:32
	 */
	public static <E> ClassMetadata getClassMetadata(Class<E> clazz) {
		ClassMetadata classMetadata = getSessionFactory().getClassMetadata(
				clazz);
		return classMetadata;
	}

	/**
	 * ��ȡʵ���Ӧ�ı���
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����10:24:09
	 */
	public static <E> String getTableName(Class<E> clazz) {
		return getSingleTableEntityPersister(clazz).getTableName();
	}

	/**
	 * ��ȡʵ���Ӧ�ı���
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����10:42:55
	 */
	public static <E> String getTableNameByPersistentClass(Class<E> clazz) {
		return getPersistentClass(clazz).getTable().getName();
	}

	/**
	 * ��ȡʵ���Ӧ�������
	 * 
	 * @param clazz
	 * @return ��������primaryKey ������primaryKey.getColumn(i).getName()
	 * @author LiuKun
	 * @created 2012-7-27 ����10:45:43
	 */
	public static <E> PrimaryKey getPrimaryKeys(Class<E> clazz) {
		return getPersistentClass(clazz).getTable().getPrimaryKey();
	}

	/**
	 * ��ȡʵ���Ӧ��������ֶ����ƣ�ֻ������Ψһ���������
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����10:47:20
	 */
	public static <E> String getPrimaryKey(Class<E> clazz) {
		return getPrimaryKeys(clazz).getColumn(0).getName();
	}

	/**
	 * ����ʵ��������ԣ���ȡʵ�������Զ�Ӧ�ı��ֶ�����
	 * 
	 * @param clazz
	 * @param propertyName
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����10:49:56
	 */
	public static <E> String getColumnName(Class<E> clazz, String propertyName) {
		PersistentClass persistentClass = getPersistentClass(clazz);
		Property property = persistentClass.getProperty(propertyName);
		Iterator<?> it = property.getColumnIterator();
		if (it.hasNext()) {
			Column column = (Column) it.next();
			return column.getName();
		}
		return null;
	}

	/**
	 * ����ʵ�壬��ȡʵ���Ӧ���������������
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����10:55:10
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<String> getColumnList(Class<E> clazz) {
		List<String> columnList = new ArrayList<String>();
		Iterator<Column> it = getPersistentClass(clazz).getTable()
				.getColumnIterator();
		while (it.hasNext()) {
			Column column = it.next();
			columnList.add(column.getName());
		}
		return columnList;
	}

	/**
	 * ��ȡʵ�������ж�Ӧ�����Ե�����������(��������������)
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����11:00:36
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<String> getPropertyList(Class<E> clazz) {
		List<String> propertyList = new ArrayList<String>();
		Iterator<Property> it = getPersistentClass(clazz).getPropertyIterator();
		while (it.hasNext()) {
			Property property = (Property) it.next();
			propertyList.add(property.getName());
		}
		return propertyList;
	}

	/**
	 * ��ȡʵ�������ж�Ӧ�����Ե�����������(������������)
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����11:00:36
	 */
	public static <E> List<String> getPropertysList(Class<E> clazz) {
		List<String> propertyList = getPropertyList(clazz);
		propertyList.add(getIdentifierPropertyName(clazz));
		return propertyList;
	}

	/**
	 * ��ȡʵ��־���
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����1:38:01
	 */
	public static <E> AbstractEntityPersister getEntityPersister(Class<E> clazz) {
		return (AbstractEntityPersister) getSessionFactory().getClassMetadata(
				clazz);
	}

	/**
	 * ��ȡʵ�������ݿ�������Ӧ�����������˷���ֻ�����ڵ�һ����
	 * 
	 * @param clazz
	 * @return
	 * @author LiuKun
	 * @created 2012-7-27 ����1:39:44
	 */
	public static <E> String getIdentifierPropertyName(Class<E> clazz) {
		return getEntityPersister(clazz).getIdentifierPropertyName();
	}

	/**
	 * ��ȡʵ���Ӧ�������� Map<����,������>
	 * 
	 * @param clazz
	 * @return Map<����,������>
	 * @author LiuKun
	 * @created 2012-8-20 ����11:19:20
	 */
	public static <E> Map<String, String> getColumnMap(Class<E> clazz) {
		Map<String, String> columnMap = new HashMap<String, String>();
		Iterator<?> propertyIt = getPersistentClass(clazz)
				.getPropertyIterator();
		while (propertyIt.hasNext()) {
			Property property = (Property) propertyIt.next();
			String propertyName = property.getName();
			String columnName = null;
			Iterator<?> columnIt = property.getColumnIterator();
			if (columnIt.hasNext()) {
				Column column = (Column) columnIt.next();
				columnName = column.getName();
			}
			columnMap.put(columnName, propertyName);
		}
		return columnMap;
	}

}
