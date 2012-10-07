package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;
import java.util.Date;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:35 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_RBAC_OPER")
public class SysRbacOperEntity extends AbstractEntity implements ISysRbacOperEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String operId;
	private String empId;
	private String userId;
	private String pwd;
	private Date invalDate;
	private String operStatus;
	private Date unlockTime;
	private Date lastLogin;
	private Integer erroCount;
	private Date validTime;
	private String macCode;
	private String ipAddr;
	private Integer freeCount;
	private Date freeTime;

	/** default constructor */
	public SysRbacOperEntity() {
	}

	/** default constructor */
	public SysRbacOperEntity(String operId) {
				this.operId = operId;
	}


	@javax.persistence.Id
	@Column(name = "OPER_ID", length = 32)
	@Comment("����Ա���")
	public String getOperId() {
		return this.operId;
	}

	public void setOperId(String operId) {
				this.operId = operId;
	}

	@Column(name = "EMP_ID", length = 32)
	@Comment("��Ա���")
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
				this.empId = empId;
	}

	@Column(name = "USER_ID", length = 255)
	@Comment("��¼��")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
				this.userId = userId;
	}

	@Column(name = "PWD", length = 255)
	@Comment("����")
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
				this.pwd = pwd;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "INVAL_DATE", length = 4)
	@Comment("������Чʱ��")
	public Date getInvalDate() {
		return this.invalDate;
	}

	public void setInvalDate(Date invalDate) {
				this.invalDate = invalDate;
	}

	@Column(name = "OPER_STATUS", length = 255)
	@Comment("����Ա״̬")
	public String getOperStatus() {
		return this.operStatus;
	}

	public void setOperStatus(String operStatus) {
				this.operStatus = operStatus;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "UNLOCK_TIME", length = 10)
	@Comment("����ʱ��")
	public Date getUnlockTime() {
		return this.unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
				this.unlockTime = unlockTime;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOGIN", length = 10)
	@Comment("����¼ʱ��")
	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
				this.lastLogin = lastLogin;
	}

	@Column(name = "ERRO_COUNT", length = 5)
	@Comment("����������")
	public Integer getErroCount() {
		return this.erroCount;
	}

	public void setErroCount(Integer erroCount) {
				this.erroCount = erroCount;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "VALID_TIME", length = 4)
	@Comment("��Чʱ��")
	public Date getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Date validTime) {
				this.validTime = validTime;
	}

	@Column(name = "MAC_CODE", length = 80)
	@Comment("MAC��ַ����")
	public String getMacCode() {
		return this.macCode;
	}

	public void setMacCode(String macCode) {
				this.macCode = macCode;
	}

	@Column(name = "IP_ADDR", length = 255)
	@Comment("���ε�¼IP��ַ")
	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
				this.ipAddr = ipAddr;
	}

	@Column(name = "FREE_COUNT", length = 5)
	@Comment("�������")
	public Integer getFreeCount() {
		return this.freeCount;
	}

	public void setFreeCount(Integer freeCount) {
				this.freeCount = freeCount;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "FREE_TIME", length = 10)
	@Comment("����ʱ��")
	public Date getFreeTime() {
		return this.freeTime;
	}

	public void setFreeTime(Date freeTime) {
				this.freeTime = freeTime;
	}

}
