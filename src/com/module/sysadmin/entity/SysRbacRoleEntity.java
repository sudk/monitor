package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;

/**
 * 
 * Copyright (c) 112 北京联银通科技有限公司 all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:37 CST 2012
 * @see 工具提供—张武
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_RBAC_ROLE")
public class SysRbacRoleEntity extends AbstractEntity implements ISysRbacRoleEntity {
	/**  描述  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String roleId;
	private String roleName;
	private String roleType;
	private String roleStatus;
	private String roleCreator;
	private String roleDesc;

	/** default constructor */
	public SysRbacRoleEntity() {
	}

	/** default constructor */
	public SysRbacRoleEntity(String roleId) {
				this.roleId = roleId;
	}


	@javax.persistence.Id
	@Column(name = "ROLE_ID", length = 32)
	@Comment("角色编号")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
				this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME", length = 64)
	@Comment("角色名称")
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
				this.roleName = roleName;
	}

	@Column(name = "ROLE_TYPE", length = 40)
	@Comment("角色适用范围")
	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
				this.roleType = roleType;
	}

	@Column(name = "ROLE_STATUS", length = 40)
	@Comment("角色状态")
	public String getRoleStatus() {
		return this.roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
				this.roleStatus = roleStatus;
	}

	@Column(name = "ROLE_CREATOR", length = 32)
	@Comment("角色创建者")
	public String getRoleCreator() {
		return this.roleCreator;
	}

	public void setRoleCreator(String roleCreator) {
				this.roleCreator = roleCreator;
	}

	@Column(name = "ROLE_DESC", length = 255)
	@Comment("角色描述")
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
				this.roleDesc = roleDesc;
	}

}
