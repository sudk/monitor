package com.module.webspider.util;

import java.util.ArrayList;

public class Monitor {
	public Result result;
	
	public ArrayList<MonitorTheme> themeList;	

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public ArrayList<MonitorTheme> getThemeList() {
		return themeList;
	}

	public void setThemeList(ArrayList<MonitorTheme> themeList) {
		this.themeList = themeList;
	}
	
	public void addTheme(MonitorTheme monitorTheme){
		this.themeList.add(monitorTheme);
	}	

}
