package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:33 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_RBAC_FUNC")
public class SysRbacFuncEntity extends AbstractEntity implements ISysRbacFuncEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String funcId;
	private String funcName;
	private String funcParentId;
	private String funcSeq;
	private String funcType;
	private Integer funcLevel;
	private Integer funcOrder;
	private String funcDesc;
	private String funcAction;
	private String funcRemark;

	/** default constructor */
	public SysRbacFuncEntity() {
	}

	/** default constructor */
	public SysRbacFuncEntity(String funcId) {
				this.funcId = funcId;
	}


	@javax.persistence.Id
	@Column(name = "FUNC_ID", length = 32)
	@Comment("���ܱ��")
	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
				this.funcId = funcId;
	}

	@Column(name = "FUNC_NAME", length = 255)
	@Comment("��������")
	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
				this.funcName = funcName;
	}

	@Column(name = "FUNC_PARENT_ID", length = 32)
	@Comment("�����ܱ��")
	public String getFuncParentId() {
		return this.funcParentId;
	}

	public void setFuncParentId(String funcParentId) {
				this.funcParentId = funcParentId;
	}

	@Column(name = "FUNC_SEQ", length = 512)
	@Comment("����SEQ")
	public String getFuncSeq() {
		return this.funcSeq;
	}

	public void setFuncSeq(String funcSeq) {
				this.funcSeq = funcSeq;
	}

	@Column(name = "FUNC_TYPE", length = 40)
	@Comment("��������")
	public String getFuncType() {
		return this.funcType;
	}

	public void setFuncType(String funcType) {
				this.funcType = funcType;
	}

	@Column(name = "FUNC_LEVEL", length = 5)
	@Comment("�������㼶")
	public Integer getFuncLevel() {
		return this.funcLevel;
	}

	public void setFuncLevel(Integer funcLevel) {
				this.funcLevel = funcLevel;
	}

	@Column(name = "FUNC_ORDER", length = 5)
	@Comment("ͬһ�㼶����˳��")
	public Integer getFuncOrder() {
		return this.funcOrder;
	}

	public void setFuncOrder(Integer funcOrder) {
				this.funcOrder = funcOrder;
	}

	@Column(name = "FUNC_DESC", length = 255)
	@Comment("��������")
	public String getFuncDesc() {
		return this.funcDesc;
	}

	public void setFuncDesc(String funcDesc) {
				this.funcDesc = funcDesc;
	}

	@Column(name = "FUNC_ACTION", length = 255)
	@Comment("���ܵ��õ�ַ")
	public String getFuncAction() {
		return this.funcAction;
	}

	public void setFuncAction(String funcAction) {
				this.funcAction = funcAction;
	}

	@Column(name = "FUNC_REMARK", length = 512)
	@Comment("���ܱ�ע")
	public String getFuncRemark() {
		return this.funcRemark;
	}

	public void setFuncRemark(String funcRemark) {
				this.funcRemark = funcRemark;
	}

}
