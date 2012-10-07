package com.component.common.pagination.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.component.common.entity.Entity;
import com.component.common.pagination.PageInfo;

/**
 * 分页实现
 * 
 * @author LiuKun
 * @version 1.1
 * @created 2012-8-23 上午9:53:00
 */
public class PageInfoImpl implements PageInfo {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3159687728763381382L;
	/**
	 * 分页当前页号
	 */
	private Integer pageIndex = 1;
	/**
	 * 分页每页记录数
	 */
	private Integer pageSize = 30;
	/**
	 * 分页总页数
	 */
	private Integer pageCount = 0;
	/**
	 * 分页总记录数
	 */
	private Integer pageTotal = 0;
	/**
	 * 分页的结果集
	 */
	private Collection<?> result = new ArrayList<Entity>();

	public Integer getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex < 1 ? 1 : pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize < 1 ? 1 : pageSize;
	}

	public Integer getPageCount() {
		this.pageCount = (this.pageTotal + this.pageSize - 1) / this.pageSize;
		return this.pageCount;
	}

	// public void setPageCount(Integer pageCount) {
	// this.pageCount = pageCount;
	// }

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	@SuppressWarnings("unchecked")
	public <E> Collection<E> getResult() {
		return (Collection<E>) this.result;
	}

	public <E> void setResult(Collection<E> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append("PageInfo{pageIndex=")
				.append(pageIndex).append(", pageSize=").append(pageSize)
				.append(", pageCount=").append(getPageCount())
				.append(", pageTotal=").append(pageTotal).append('}')
				.toString();
	}
}
