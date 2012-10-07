package com.component.common.dao.impl;

import org.hibernate.Session;

import com.component.common.hibernate.util.HibernateUtil;

/**
 * ͨ�ó־ò㣨dao������ࣩ
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
