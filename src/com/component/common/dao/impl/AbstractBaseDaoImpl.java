package com.component.common.dao.impl;

import org.hibernate.Session;

import com.component.common.hibernate.util.HibernateUtil;

/**
 * 通用持久层（dao层抽象父类）
 * 
 * @author LiuKun
 * @date 2012-07-16
 * @time 17:03
 * 
 */
public class AbstractBaseDaoImpl {
	public Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
}
