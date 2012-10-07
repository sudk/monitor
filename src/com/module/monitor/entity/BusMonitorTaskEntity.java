package com.module.monitor.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;
import java.util.Date;

/**
 * 
 * Copyright (c) 112 北京联银通科技有限公司 all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 17:00:41 CST 2012
 * @see 工具提供—张武
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "BUS_MONITOR_TASK")
public class BusMonitorTaskEntity extends AbstractEntity implements IBusMonitorTaskEntity {
	/**  描述  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String keyWord;
	private String frequency;
	private Date startDate;
	private Date endDate;
	private String oper;
	private Date operTime;
	private String state;

	/** default constructor */
	public BusMonitorTaskEntity() {
	}

	/** default constructor */
	public BusMonitorTaskEntity(String id) {
				this.id = id;
	}


	@javax.persistence.Id
	@Column(name = "ID", length = 32)
	@Comment("ID")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
				this.id = id;
	}

	@Column(name = "KEY_WORD", length = 255)
	@Comment("关键字")
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
				this.keyWord = keyWord;
	}

	@Column(name = "FREQUENCY", length = 40)
	@Comment("运行频率")
	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
				this.frequency = frequency;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "START_DATE", length = 4)
	@Comment("开始时间")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
				this.startDate = startDate;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "END_DATE", length = 4)
	@Comment("结束时间")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
				this.endDate = endDate;
	}

	@Column(name = "OPER", length = 32)
	@Comment("操作人")
	public String getOper() {
		return this.oper;
	}

	public void setOper(String oper) {
				this.oper = oper;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "OPER_TIME", length = 10)
	@Comment("操作时间")
	public Date getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Date operTime) {
				this.operTime = operTime;
	}

	@Column(name = "STATE", length = 40)
	@Comment("状态")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
				this.state = state;
	}

}
