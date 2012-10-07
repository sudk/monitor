package com.component.common.entity;

import javax.persistence.Transient;

import com.component.common.annotation.Comment;

/**
 * ����ʵ��
 * 
 * @author LiuKun
 * @date 2012-07-16
 * @time 17:19
 * 
 */
public abstract class AbstractEntity implements Entity {

	/**
	 * ���к�
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// //////////////////////��������/////////////////////////////////
	private Boolean checked = Boolean.FALSE;
	private Boolean hasChild = Boolean.FALSE;
	
	@Transient
	@Comment("ѡ��")
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Transient
	@Comment("�Ƿ�����ӽڵ�")
	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}
	
}
