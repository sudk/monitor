package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:38 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_RBAC_ROLEFUNC_REL")
public class SysRbacRolefuncRelEntity extends AbstractEntity implements ISysRbacRolefuncRelEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String roleId;
	private String funcId;

	/** default constructor */
	public SysRbacRolefuncRelEntity() {
	}

	/** default constructor */
	public SysRbacRolefuncRelEntity(String id) {
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

	@Column(name = "ROLE_ID", length = 32)
	@Comment("��ɫ���")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
				this.roleId = roleId;
	}

	@Column(name = "FUNC_ID", length = 32)
	@Comment("���ܱ��")
	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
				this.funcId = funcId;
	}

}
