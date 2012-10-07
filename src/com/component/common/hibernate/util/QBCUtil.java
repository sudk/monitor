package com.component.common.hibernate.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.component.common.pagination.PageInfo;

/**
 * Query by Criteria
 * @author LiuKun
 * @version 1.1
 */
public class QBCUtil implements Serializable{
	
	private static final long serialVersionUID = 9061454872262819112L;

	private QBCUtil(){
	}
	private static final Logger logger = Logger.getLogger(QBCUtil.class);
	
	private static QBCUtil instance;
	public static QBCUtil getInstance(){
		if(instance == null){
			synchronized (QBCUtil.class) {
				if(instance == null){
					instance = new QBCUtil();
				}
			}
		}
		return instance;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}
	
	//****************************************************************************************************************************
	

	public static Criteria addCriterion(Criteria criteria,Map<String, Object> valueMap, Map<String, String> operatorMap) {
		
		if (criteria == null || valueMap == null) {
			return criteria;
		}
		
		if (operatorMap == null) {
			operatorMap = new LinkedHashMap<String, String>();
		}
		
		Object value = null;
		for (String propertyName : valueMap.keySet()) {
			value = valueMap.get(propertyName);
			addCriterion(criteria, propertyName, value, operatorMap.get(propertyName));
		}
		return criteria;
	}

	public static Criteria addCriterion(Criteria criteria, String propertyName,	Object value, String operator) {
		if (criteria == null || propertyName == null || value == null) {
			return criteria;
		}
		if (operator == null) {
			criteria = criteria.add(Restrictions.eq(propertyName, value));
		} else if ("EQ".equalsIgnoreCase(operator)
				|| "=".equalsIgnoreCase(operator)
				|| "==".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.eq(propertyName, value));
		} else if ("%LIKE".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.like(propertyName,	(String) value, MatchMode.START));
		} else if ("LIKE%".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.like(propertyName, (String) value, MatchMode.END));
		} else if ("LIKE".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.like(propertyName,	(String) value, MatchMode.ANYWHERE));
		} else if ("<".equalsIgnoreCase(operator)
				|| "LT".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.lt(propertyName, value));
		} else if ("<=".equalsIgnoreCase(operator)
				|| "LE".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.le(propertyName, value));
		} else if (">=".equalsIgnoreCase(operator)
				|| "GE".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.ge(propertyName, value));
		} else if (">".equalsIgnoreCase(operator)
				|| "GT".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.gt(propertyName, value));
		} else if ("<>".equalsIgnoreCase(operator)
				|| "!=".equalsIgnoreCase(operator)
				|| "NE".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.ne(propertyName, value));
		} else if ("IN".equalsIgnoreCase(operator)) {
			if (value instanceof Collection) {
				criteria = criteria.add(Restrictions.in(propertyName,(Collection<?>) value));
			} else if (value instanceof Object[]) {
				criteria = criteria.add(Restrictions.in(propertyName,(Object[]) value));
			}
		} else if ("between".equalsIgnoreCase(operator)) {
			if (value instanceof List) {
				List<?> list = (List<?>) value;
				criteria = criteria.add(Restrictions.between(propertyName, list.get(0), list.get(1)));
			} else if (value instanceof Object[]) {
				Object[] objs = (Object[]) value;
				criteria = criteria.add(Restrictions.between(propertyName, objs[0], objs[1]));
			}
		} else if ("sql".equalsIgnoreCase(operator)) {
			criteria = criteria.add(Restrictions.sqlRestriction(value.toString()));
		} else {
			criteria = criteria.add(Restrictions.eq(propertyName, value));
		}
		return criteria;
	}

	public static void addOrders(Criteria criteria, Map<String, String> orders) {
		if (orders == null) {
			return;
		}
		Iterator<String> iterOrders = orders.keySet().iterator();
		String name = "";
		String order = "";
		while (iterOrders.hasNext()) {
			name = iterOrders.next();
			order = orders.get(name);
			addOrder(criteria, name, order);
		}
	}

	public static void addOrder(Criteria criteria, String propertyName, String order) {
		if (propertyName == null || order == null) {
			return;
		}
		if ("ASC".equalsIgnoreCase(order)) {
			criteria.addOrder(Order.asc(propertyName));
		} else if ("DESC".equalsIgnoreCase(order)) {
			criteria.addOrder(Order.desc(propertyName));
		} else {
			criteria.addOrder(Order.asc(propertyName));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <E> List<E> find(Session session, Class<E> clazz,	Map<String, Object> valueMap, Map<String, String> operatorMap,Map<String, String> orderMap, PageInfo pageInfo) {
		Criteria criteria = session.createCriteria(clazz);
		addCriterion(criteria, valueMap, operatorMap);
		int totalCount = 0;
		// 添加分页
		if (pageInfo != null) {
			criteria.setProjection(null);
			List<?> tempList = criteria.setProjection(Projections.rowCount())
					.list();
			Object obj = null;
			if (tempList != null && tempList.size() != 0) {
				for (int i = 0; i < tempList.size(); i++) {
					obj = tempList.get(i);
					int temp = ((Integer) obj).intValue();
					;
					if (temp > totalCount) {
						totalCount = temp;
					}
				}
			}
			criteria.setProjection(null);
		}
		// 添加排序条件
		addOrders(criteria, orderMap);
		// 排序后分页
		if (pageInfo != null) {
			criteria.setMaxResults(pageInfo.getPageSize());
			criteria.setFirstResult((pageInfo.getPageIndex() - 1) * pageInfo.getPageSize());
			pageInfo.setPageTotal(totalCount);
		}
		
		return criteria.list();
	}
}
