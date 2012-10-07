package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;
import java.util.Date;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:31 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_ORG")
public class SysOrgEntity extends AbstractEntity implements ISysOrgEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String orgId;
	private String parentId;
	private String orgKey;
	private String orgName;
	private String orgFullname;
	private String orgType;
	private String orgSeq;
	private String status;
	private String orgAddr;
	private String phone;
	private String email;
	private String leader;
	private String linkOaTel;
	private String linkHomeTel;
	private Integer sortNo;
	private Date inputDate;
	private String remark;

	/** default constructor */
	public SysOrgEntity() {
	}

	/** default constructor */
	public SysOrgEntity(String orgId) {
				this.orgId = orgId;
	}


	@javax.persistence.Id
	@Column(name = "ORG_ID", length = 32)
	@Comment("�������")
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
				this.orgId = orgId;
	}

	@Column(name = "PARENT_ID", length = 32)
	@Comment("���������")
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
				this.parentId = parentId;
	}

	@Column(name = "ORG_KEY", length = 32)
	@Comment("��������")
	public String getOrgKey() {
		return this.orgKey;
	}

	public void setOrgKey(String orgKey) {
				this.orgKey = orgKey;
	}

	@Column(name = "ORG_NAME", length = 64)
	@Comment("��������")
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
				this.orgName = orgName;
	}

	@Column(name = "ORG_FULLNAME", length = 255)
	@Comment("����ȫ��")
	public String getOrgFullname() {
		return this.orgFullname;
	}

	public void setOrgFullname(String orgFullname) {
				this.orgFullname = orgFullname;
	}

	@Column(name = "ORG_TYPE", length = 40)
	@Comment("��������")
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
				this.orgType = orgType;
	}

	@Column(name = "ORG_SEQ", length = 512)
	@Comment("��������")
	public String getOrgSeq() {
		return this.orgSeq;
	}

	public void setOrgSeq(String orgSeq) {
				this.orgSeq = orgSeq;
	}

	@Column(name = "STATUS", length = 40)
	@Comment("����״̬")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
				this.status = status;
	}

	@Column(name = "ORG_ADDR", length = 255)
	@Comment("������ַ")
	public String getOrgAddr() {
		return this.orgAddr;
	}

	public void setOrgAddr(String orgAddr) {
				this.orgAddr = orgAddr;
	}

	@Column(name = "PHONE", length = 40)
	@Comment("�����绰")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
				this.phone = phone;
	}

	@Column(name = "EMAIL", length = 255)
	@Comment("����email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
				this.email = email;
	}

	@Column(name = "LEADER", length = 32)
	@Comment("������")
	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
				this.leader = leader;
	}

	@Column(name = "LINK_OA_TEL", length = 40)
	@Comment("�����˰칫�绰")
	public String getLinkOaTel() {
		return this.linkOaTel;
	}

	public void setLinkOaTel(String linkOaTel) {
				this.linkOaTel = linkOaTel;
	}

	@Column(name = "LINK_HOME_TEL", length = 40)
	@Comment("�����˼�ͥ�绰")
	public String getLinkHomeTel() {
		return this.linkHomeTel;
	}

	public void setLinkHomeTel(String linkHomeTel) {
				this.linkHomeTel = linkHomeTel;
	}

	@Column(name = "SORT_NO", length = 5)
	@Comment("�����")
	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
				this.sortNo = sortNo;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "INPUT_DATE", length = 4)
	@Comment("¼������")
	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
				this.inputDate = inputDate;
	}

	@Column(name = "REMARK", length = 512)
	@Comment("����˵��")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
				this.remark = remark;
	}

}
