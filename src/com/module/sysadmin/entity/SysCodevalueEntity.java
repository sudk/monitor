package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:29 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_CODEVALUE")
public class SysCodevalueEntity extends AbstractEntity implements ISysCodevalueEntity {
	/**  ����  */       

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
	@Comment("����ֵID")
	public String getCodeValueId() {
		return this.codeValueId;
	}

	public void setCodeValueId(String codeValueId) {
				this.codeValueId = codeValueId;
	}

	@Column(name = "PARENT_ID", length = 40)
	@Comment("�ϼ�����ֵID")
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
				this.parentId = parentId;
	}

	@Column(name = "CODE_ID", length = 40)
	@Comment("����ID")
	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
				this.codeId = codeId;
	}

	@Column(name = "VALUE", length = 40)
	@Comment("����ֵ")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
				this.value = value;
	}

	@Column(name = "ORDER_ID", length = 5)
	@Comment("�����")
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
				this.orderId = orderId;
	}

	@Column(name = "FLAG", length = 40)
	@Comment("�Ƿ���Ч")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
				this.flag = flag;
	}

	@Column(name = "LEAF", length = 40)
	@Comment("�Ƿ�Ҷ�ӽڵ�")
	public String getLeaf() {
		return this.leaf;
	}

	public void setLeaf(String leaf) {
				this.leaf = leaf;
	}

	@Column(name = "CODE_VALUE_DESC", length = 255)
	@Comment("��������")
	public String getCodeValueDesc() {
		return this.codeValueDesc;
	}

	public void setCodeValueDesc(String codeValueDesc) {
				this.codeValueDesc = codeValueDesc;
	}

	@Column(name = "SEQ", length = 512)
	@Comment("��SEQ")
	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
				this.seq = seq;
	}

}
