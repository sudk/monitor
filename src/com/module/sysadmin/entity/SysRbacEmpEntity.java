package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;
import java.util.Date;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:32 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_RBAC_EMP")
public class SysRbacEmpEntity extends AbstractEntity implements ISysRbacEmpEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String empId;
	private String operId;
	private String empName;
	private String orgId;
	private String gender;
	private Date birthdate;
	private String empStatus;
	private String cardType;
	private String cardNo;
	private Date inDate;
	private Date createtime;
	private String pemail;
	private String telNum;
	private String qq;
	private String uNative;
	private String remark;

	/** default constructor */
	public SysRbacEmpEntity() {
	}

	/** default constructor */
	public SysRbacEmpEntity(String empId) {
				this.empId = empId;
	}


	@javax.persistence.Id
	@Column(name = "EMP_ID", length = 32)
	@Comment("��Ա���")
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
				this.empId = empId;
	}

	@Column(name = "OPER_ID", length = 32)
	@Comment("����Ա���")
	public String getOperId() {
		return this.operId;
	}

	public void setOperId(String operId) {
				this.operId = operId;
	}

	@Column(name = "EMP_NAME", length = 255)
	@Comment("��Ա����")
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
				this.empName = empName;
	}

	@Column(name = "ORG_ID", length = 32)
	@Comment("�����������")
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
				this.orgId = orgId;
	}

	@Column(name = "GENDER", length = 40)
	@Comment("�Ա�")
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
				this.gender = gender;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "BIRTHDATE", length = 4)
	@Comment("��������")
	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
				this.birthdate = birthdate;
	}

	@Column(name = "EMP_STATUS", length = 40)
	@Comment("��Ա״̬")
	public String getEmpStatus() {
		return this.empStatus;
	}

	public void setEmpStatus(String empStatus) {
				this.empStatus = empStatus;
	}

	@Column(name = "CARD_TYPE", length = 40)
	@Comment("֤������")
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
				this.cardType = cardType;
	}

	@Column(name = "CARD_NO", length = 40)
	@Comment("֤������")
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
				this.cardNo = cardNo;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "IN_DATE", length = 4)
	@Comment("��ְ����")
	public Date getInDate() {
		return this.inDate;
	}

	public void setInDate(Date inDate) {
				this.inDate = inDate;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", length = 10)
	@Comment("����ʱ��")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
				this.createtime = createtime;
	}

	@Column(name = "PEMAIL", length = 128)
	@Comment("˽�˵�������")
	public String getPemail() {
		return this.pemail;
	}

	public void setPemail(String pemail) {
				this.pemail = pemail;
	}

	@Column(name = "TEL_NUM", length = 25)
	@Comment("�ֻ�����")
	public String getTelNum() {
		return this.telNum;
	}

	public void setTelNum(String telNum) {
				this.telNum = telNum;
	}

	@Column(name = "QQ", length = 25)
	@Comment("QQ")
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
				this.qq = qq;
	}

	@Column(name = "U_NATIVE", length = 25)
	@Comment("����")
	public String getUNative() {
		return this.uNative;
	}

	public void setUNative(String uNative) {
				this.uNative = uNative;
	}

	@Column(name = "REMARK", length = 512)
	@Comment("��ע")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
				this.remark = remark;
	}

}
