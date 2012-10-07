package com.component.common.pagination.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.component.common.entity.Entity;
import com.component.common.pagination.PageInfo;

/**
 * ��ҳʵ��
 * 
 * @author LiuKun
 * @version 1.1
 * @created 2012-8-23 ����9:53:00
 */
public class PageInfoImpl implements PageInfo {

	/**
	 * ���к�
	 */
	private static final long serialVersionUID = -3159687728763381382L;
	/**
	 * ��ҳ��ǰҳ��
	 */
	private Integer pageIndex = 1;
	/**
	 * ��ҳÿҳ��¼��
	 */
	private Integer pageSize = 30;
	/**
	 * ��ҳ��ҳ��
	 */
	private Integer pageCount = 0;
	/**
	 * ��ҳ�ܼ�¼��
	 */
	private Integer pageTotal = 0;
	/**
	 * ��ҳ�Ľ����
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
