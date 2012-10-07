package com.component.common.pagination;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * ��ҳ�ӿ�
 * 
 * @author LiuKun
 * @version 1.1
 * @created 2012-8-23 ����9:53:39
 */
public interface PageInfo extends Serializable {

	/**
	 * ��ȡ��ҳ��ǰҳ��,�ӵ�1ҳ��ʼ
	 * 
	 * @return ��ǰҳ��
	 */
	public Integer getPageIndex();

	/**
	 * ��ȡ��ҳ��ǰҳ��,�ӵ�1ҳ��ʼ
	 * 
	 * @param pageIndex
	 *            ��ǰҳ��
	 */
	public void setPageIndex(Integer pageIndex);

	/**
	 * ��ȡ��ҳÿҳ��¼��
	 * 
	 * @return ��ҳÿҳ��¼��
	 */
	public Integer getPageSize();

	/**
	 * ���÷�ҳÿҳ��¼��
	 * 
	 * @param pageSize
	 *            ��ҳÿҳ��¼��
	 */
	public void setPageSize(Integer pageSize);

	/**
	 * ��ȡ��ҳ��ҳ��
	 * 
	 * @return ��ҳ��ҳ��
	 */
	public Integer getPageCount();

	// /**
	// * ��ҳ��ҳ��
	// *
	// * @param pageCount
	// */
	// public void setPageCount(Integer pageCount);

	/**
	 * ��ҳ�ܼ�¼��
	 * 
	 * @return ��ҳ�ܼ�¼��
	 */
	public Integer getPageTotal();

	/**
	 * ���÷�ҳ�ܼ�¼��
	 * 
	 * @param pageTotal
	 *            ��ҳ�ܼ�¼��
	 */
	public void setPageTotal(Integer pageTotal);

	/**
	 * ��ȡ��ҳ�Ľ����
	 * 
	 * @return ��ҳ�Ľ����
	 */
	public <E> Collection<E> getResult();

	/**
	 * ���÷�ҳ�Ľ����
	 * 
	 * @param result
	 *            ��ҳ�Ľ����
	 */
	public <E> void setResult(Collection<E> result);

}
