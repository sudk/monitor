package com.module.webspider.handle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.monitor.Monitor;

public class Handle implements BaseHandle  {
	public String keyword;
	public String url;
	public String htmlstr;
	public String subject;
	public String title;
	public ArrayList<String> subUrl=new ArrayList<String>();
	public Set subject_hash=new HashSet();
	
	public boolean addSubject_hash(String subj){
		int s=this.subject_hash.size();
		this.subject_hash.add(subj.hashCode());
		int sd=this.subject_hash.size();
		if(sd>s){
			return true;
		}else{
			return false;
		}
	}
	
	public Handle(String keyword,String url){
		this.setKeyword(keyword);
		this.setUrl(url);
	}
	
	public Monitor handle() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String adstractSubject(String htmlstr){
		
		
		int keyword_index=htmlstr.indexOf(this.keyword);
		if(keyword_index!=-1){
			int beginIndex=0;
			if(keyword_index>50){				
				beginIndex=keyword_index-50;
			}
			
			//找出关键字前面的不可见字符
			Pattern p1 = Pattern.compile("\t|\r|\n");
			Matcher m1 = p1.matcher(htmlstr.substring(0, keyword_index));
			int repion_end_index=m1.regionEnd();
			//如果最后一个不见字符在截取的范围内则从最后一个不可见字符处开始截取
			if(repion_end_index>beginIndex){
				beginIndex=repion_end_index;
			}		
			
			int endIndex=beginIndex+150;
			if(htmlstr.length()<endIndex){
				endIndex=htmlstr.length();
			}
			
			htmlstr=htmlstr.substring(beginIndex, endIndex);
			//去除不可见字符
			//Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(htmlstr);
			
			htmlstr = m.replaceAll("");
			
			return htmlstr;
			
		}else{
			return null;
		}
	}
	
	public String integrityUrl(String url){
		if(url.indexOf("http:")>=0||url.indexOf("https:")>=0){
			return url;
		}else{
			String h=this.getUrlHost(this.url);
			return h+url;
		}
	}
	
	public String getUrlHost(String url){
		int i=url.indexOf("/", 7);
		return url.substring(0, i);
	}
	
	
	
	public void addSubUrl(String suburl){
		this.subUrl.add(suburl);
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getHtmlstr() {
		return htmlstr;
	}

	public void setHtmlstr(String htmlstr) {
		this.htmlstr = htmlstr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getSubUrl() {
		return subUrl;
	}

	public void setSubUrl(ArrayList<String> subUrl) {
		this.subUrl = subUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
