package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;
import java.util.Date;

/**
 * 
 * Copyright (c) 112 北京联银通科技有限公司 all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:30 CST 2012
 * @see 工具提供—张武
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_OPERLOG")
public class SysOperlogEntity extends AbstractEntity implements ISysOperlogEntity {
	/**  描述  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private Date operDate;
	private String operator;
	private String operType;
	private String operIp;
	private String orgId;
	private String operation;

	/** default constructor */
	public SysOperlogEntity() {
	}

	/** default constructor */
	public SysOperlogEntity(String id) {
				this.id = id;
	}


	@javax.persistence.Id
	@Column(name = "ID", length = 32)
	@Comment("物理主键")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
				this.id = id;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "OPER_DATE", length = 10)
	@Comment("操作日期")
	public Date getOperDate() {
		return this.operDate;
	}

	public void setOperDate(Date operDate) {
				this.operDate = operDate;
	}

	@Column(name = "OPERATOR", length = 32)
	@Comment("操作人")
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
				this.operator = operator;
	}

	@Column(name = "OPER_TYPE", length = 40)
	@Comment("操作类型")
	public String getOperType() {
		return this.operType;
	}

	public void setOperType(String operType) {
				this.operType = operType;
	}

	@Column(name = "OPER_IP", length = 32)
	@Comment("操作人IP")
	public String getOperIp() {
		return this.operIp;
	}

	public void setOperIp(String operIp) {
				this.operIp = operIp;
	}

	@Column(name = "ORG_ID", length = 32)
	@Comment("操作人所属审计机构")
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
				this.orgId = orgId;
	}

	@Column(name = "OPERATION", length = 512)
	@Comment("操作内容")
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
				this.operation = operation;
	}

}
