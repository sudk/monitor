package com.module.webspider.util;

import java.security.Timestamp;

public class Result {
	public String id;
	public String taskid;
	public String keyword;
	public String search_theme;
	public String title;
	public String status;
	public String url;
	public Timestamp time;
	public String html_path;
	public String content;
	public int content_hash;
	

	public boolean isExist(){
		this.content_hash=this.content.hashCode();
		if(this.findByHash()){
			return false;
		}else{
			return true;
		}
	}
	public boolean findByHash(){
		//按hash值进行查找
		
		return false;
	}
	
	public int save(){
		if(this.isExist()){
			return -1;
		}else{
			//保存结果
			
			return 1;
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSearch_theme() {
		return search_theme;
	}
	public void setSearch_theme(String search_theme) {
		this.search_theme = search_theme;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getHtml_path() {
		return html_path;
	}
	public void setHtml_path(String html_path) {
		this.html_path = html_path;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getContent_hash() {
		return content_hash;
	}
	public void setContent_hash(int content_hash) {
		this.content_hash = content_hash;
	}
	
}
