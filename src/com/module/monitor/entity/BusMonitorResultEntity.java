package com.module.monitor.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;
import java.util.Date;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 17:00:40 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "BUS_MONITOR_RESULT")
public class BusMonitorResultEntity extends AbstractEntity implements IBusMonitorResultEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String taskId;
	private String keyWord;
	private String searchTheme;
	private String url;
	private String title;
	private String state;
	private Date time;
	private String htmlPath;
	private String content;
	private String contentHash;

	/** default constructor */
	public BusMonitorResultEntity() {
	}

	/** default constructor */
	public BusMonitorResultEntity(String id) {
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

	@Column(name = "TASK_ID", length = 32)
	@Comment("������")
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
				this.taskId = taskId;
	}

	@Column(name = "KEY_WORD", length = 255)
	@Comment("�ؼ���")
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
				this.keyWord = keyWord;
	}

	@Column(name = "SEARCH_THEME", length = 32)
	@Comment("��������")
	public String getSearchTheme() {
		return this.searchTheme;
	}

	public void setSearchTheme(String searchTheme) {
				this.searchTheme = searchTheme;
	}

	@Column(name = "URL", length = 512)
	@Comment("URL")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
				this.url = url;
	}

	@Column(name = "TITLE", length = 512)
	@Comment("����")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
				this.title = title;
	}

	@Column(name = "STATE", length = 40)
	@Comment("״̬")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
				this.state = state;
	}

	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "TIME", length = 10)
	@Comment("ʱ��")
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
				this.time = time;
	}

	@Column(name = "HTML_PATH", length = 255)
	@Comment("HTML·��")
	public String getHtmlPath() {
		return this.htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
				this.htmlPath = htmlPath;
	}

	@Column(name = "CONTENT", length = 1000)
	@Comment("����")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
				this.content = content;
	}

	@Column(name = "CONTENT_HASH", length = 1000)
	@Comment("���ݹ�ϣֵ")
	public String getContentHash() {
		return this.contentHash;
	}

	public void setContentHash(String contentHash) {
				this.contentHash = contentHash;
	}

}
