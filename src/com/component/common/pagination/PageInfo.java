package com.component.common.pagination;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * 分页接口
 * 
 * @author LiuKun
 * @version 1.1
 * @created 2012-8-23 上午9:53:39
 */
public interface PageInfo extends Serializable {

	/**
	 * 获取分页当前页号,从第1页开始
	 * 
	 * @return 当前页号
	 */
	public Integer getPageIndex();

	/**
	 * 获取分页当前页号,从第1页开始
	 * 
	 * @param pageIndex
	 *            当前页号
	 */
	public void setPageIndex(Integer pageIndex);

	/**
	 * 获取分页每页记录数
	 * 
	 * @return 分页每页记录数
	 */
	public Integer getPageSize();

	/**
	 * 设置分页每页记录数
	 * 
	 * @param pageSize
	 *            分页每页记录数
	 */
	public void setPageSize(Integer pageSize);

	/**
	 * 获取分页总页数
	 * 
	 * @return 分页总页数
	 */
	public Integer getPageCount();

	// /**
	// * 分页总页数
	// *
	// * @param pageCount
	// */
	// public void setPageCount(Integer pageCount);

	/**
	 * 分页总记录数
	 * 
	 * @return 分页总记录数
	 */
	public Integer getPageTotal();

	/**
	 * 设置分页总记录数
	 * 
	 * @param pageTotal
	 *            分页总记录数
	 */
	public void setPageTotal(Integer pageTotal);

	/**
	 * 获取分页的结果集
	 * 
	 * @return 分页的结果集
	 */
	public <E> Collection<E> getResult();

	/**
	 * 设置分页的结果集
	 * 
	 * @param result
	 *            分页的结果集
	 */
	public <E> void setResult(Collection<E> result);

}
