package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:36 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_RBAC_OPERROLE_REL")
public class SysRbacOperroleRelEntity extends AbstractEntity implements ISysRbacOperroleRelEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String operId;
	private String roleId;

	/** default constructor */
	public SysRbacOperroleRelEntity() {
	}

	/** default constructor */
	public SysRbacOperroleRelEntity(String id) {
				this.id = id;
	}


	@javax.persistence.Id
	@Column(name = "ID", length = 32)
	@Comment("��������")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
				this.id = id;
	}

	@Column(name = "OPER_ID", length = 32)
	@Comment("����Ա���")
	public String getOperId() {
		return this.operId;
	}

	public void setOperId(String operId) {
				this.operId = operId;
	}

	@Column(name = "ROLE_ID", length = 32)
	@Comment("��ɫ���")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
				this.roleId = roleId;
	}

}
