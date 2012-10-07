package com.component.common.pagination.util;

import java.io.Serializable;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.component.common.pagination.PageInfo;
import com.component.common.pagination.impl.PageInfoImpl;

/**
 * 
 * ∑÷“≥∞Ô÷˙¿‡
 * 
 * @author LiuKun
 * @version 1.1
 * @created 2012-8-23 …œŒÁ9:54:24
 */
public class PageInfoUtil implements Serializable {
	
	static Integer pageSize = 20 ;

	private PageInfoUtil() {

	}

	private static final long serialVersionUID = 3961398573001745572L;

	private static final Logger logger = Logger.getLogger(PageInfoUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static PageInfoUtil instance;

	public static PageInfoUtil getInstance() {
		if (instance == null) {
			synchronized (PageInfoUtil.class) {
				if (instance == null) {
					instance = new PageInfoUtil();
				}
			}
		}
		return instance;
	}

	// /////////////////////////////////////////////////////////////////

	public static PageInfo getPageInfo() {
		PageInfo pageInfo = new PageInfoImpl();
		pageInfo.setPageSize(pageSize);
		return pageInfo;
	}

	public static PageInfo getPageInfo(Integer pageIndex, Integer pageSize) {
		PageInfo pageInfo = new PageInfoImpl();
		pageInfo.setPageIndex(pageIndex);
		// pageInfo.setPageSize(pageSize);
		pageInfo.setPageSize(pageSize);
		return pageInfo;
	}


}
