package com.component.common.entity;

import javax.persistence.Transient;

import com.component.common.annotation.Comment;

/**
 * 抽象实体
 * 
 * @author LiuKun
 * @date 2012-07-16
 * @time 17:19
 * 
 */
public abstract class AbstractEntity implements Entity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// //////////////////////虚拟属性/////////////////////////////////
	private Boolean checked = Boolean.FALSE;
	private Boolean hasChild = Boolean.FALSE;
	
	@Transient
	@Comment("选择")
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Transient
	@Comment("是否存在子节点")
	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}
	
}
