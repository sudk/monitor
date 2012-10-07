package com.module.webspider.util;

import java.security.Timestamp;

public class MonitorTheme {
	public String id;
	public String themename;
	public String themetype;
	public String searchurl;
	public String status;
	public String oper;
	public Timestamp opertime;
	public int isSub;
	
	
	
	public int getIsSub() {
		return isSub;
	}
	public void setIsSub(int isSub) {
		this.isSub = isSub;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getThemename() {
		return themename;
	}
	public void setThemename(String themename) {
		this.themename = themename;
	}
	public String getThemetype() {
		return themetype;
	}
	public void setThemetype(String themetype) {
		this.themetype = themetype;
	}
	public String getSearchurl() {
		return searchurl;
	}
	public void setSearchurl(String searchurl) {
		this.searchurl = searchurl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public Timestamp getOpertime() {
		return opertime;
	}
	public void setOpertime(Timestamp opertime) {
		this.opertime = opertime;
	}
	
	
	
}
