package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;

/**
 * 
 * Copyright (c) 112 北京联银通科技有限公司 all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:29 CST 2012
 * @see 工具提供—张武
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_CODEVALUE")
public class SysCodevalueEntity extends AbstractEntity implements ISysCodevalueEntity {
	/**  描述  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String codeValueId;
	private String parentId;
	private String codeId;
	private String value;
	private Integer orderId;
	private String flag;
	private String leaf;
	private String codeValueDesc;
	private String seq;

	/** default constructor */
	public SysCodevalueEntity() {
	}

	/** default constructor */
	public SysCodevalueEntity(String codeValueId) {
				this.codeValueId = codeValueId;
	}


	@javax.persistence.Id
	@Column(name = "CODE_VALUE_ID", length = 40)
	@Comment("代码值ID")
	public String getCodeValueId() {
		return this.codeValueId;
	}

	public void setCodeValueId(String codeValueId) {
				this.codeValueId = codeValueId;
	}

	@Column(name = "PARENT_ID", length = 40)
	@Comment("上级代码值ID")
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
				this.parentId = parentId;
	}

	@Column(name = "CODE_ID", length = 40)
	@Comment("代码ID")
	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
				this.codeId = codeId;
	}

	@Column(name = "VALUE", length = 40)
	@Comment("代码值")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
				this.value = value;
	}

	@Column(name = "ORDER_ID", length = 5)
	@Comment("排序号")
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
				this.orderId = orderId;
	}

	@Column(name = "FLAG", length = 40)
	@Comment("是否有效")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
				this.flag = flag;
	}

	@Column(name = "LEAF", length = 40)
	@Comment("是否叶子节点")
	public String getLeaf() {
		return this.leaf;
	}

	public void setLeaf(String leaf) {
				this.leaf = leaf;
	}

	@Column(name = "CODE_VALUE_DESC", length = 255)
	@Comment("中文描述")
	public String getCodeValueDesc() {
		return this.codeValueDesc;
	}

	public void setCodeValueDesc(String codeValueDesc) {
				this.codeValueDesc = codeValueDesc;
	}

	@Column(name = "SEQ", length = 512)
	@Comment("树SEQ")
	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
				this.seq = seq;
	}

}
