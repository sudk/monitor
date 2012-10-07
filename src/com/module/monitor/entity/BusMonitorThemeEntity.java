package com.module.monitor.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;
import java.util.Date;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 17:00:41 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "BUS_MONITOR_THEME")
public class BusMonitorThemeEntity extends AbstractEntity implements IBusMonitorThemeEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String themeName;
	private String themeType;
	private String searchUrl;
	private String state;
	private String oper;
	private Date operTime;

	/** default constructor */
	public BusMonitorThemeEntity() {
	}

	/** default constructor */
	public BusMonitorThemeEntity(String id) {
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

	@Column(name = "THEME_NAME", length = 255)
	@Comment("��������")
	public String getThemeName() {
		return this.themeName;
	}

	public void setThemeName(String themeName) {
				this.themeName = themeName;
	}

	@Column(name = "THEME_TYPE", length = 40)
	@Comment("�������")
	public String getThemeType() {
		return this.themeType;
	}

	public void setThemeType(String themeType) {
				this.themeType = themeType;
	}

	@Column(name = "SEARCH_URL", length = 255)
	@Comment("����URL")
	public String getSearchUrl() {
		return this.searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
				this.searchUrl = searchUrl;
	}

	@Column(name = "STATE", length = 40)
	@Comment("״̬")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
				this.state = state;
	}

	@Column(name = "OPER", length = 32)
	@Comment("������")
	public String getOper() {
		return this.oper;
	}

	public void setOper(String oper) {
				this.oper = oper;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "OPER_TIME", length = 10)
	@Comment("����ʱ��")
	public Date getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Date operTime) {
				this.operTime = operTime;
	}

}
