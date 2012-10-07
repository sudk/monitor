package com.module.fileTools;

/**
 * 数据列对象
 *         
 * Description: 描述        
 * @author zhangwu        
 * @version 1.0      
 * @created 2012-7-24 上午9:29:06
 */
public class ColumnModel {

	private String tabschema;//表所在的schema
	private String tabname;//表名
	private String colname;//列名
	private String colno;//列的顺序
	private String typaname;//数据类型名
	private String length;//字段长度
	private String scale;//精度
	private String nulls;//是否为空（Y，N）
	private String keyseq;//主键位置（为空则不是主键）
	private String remarks;//备注
	
	public String getTabschema() {
		return tabschema;
	}
	public void setTabschema(String tabschema) {
		this.tabschema = tabschema;
	}
	public String getTabname() {
		return tabname;
	}
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	public String getColname() {
		return colname;
	}
	public void setColname(String colname) {
		this.colname = colname;
	}
	public String getColno() {
		return colno;
	}
	public void setColno(String colno) {
		this.colno = colno;
	}
	public String getTypaname() {
		return typaname;
	}
	public void setTypaname(String typaname) {
		this.typaname = typaname;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getNulls() {
		return nulls;
	}
	public void setNulls(String nulls) {
		this.nulls = nulls;
	}
	public String getKeyseq() {
		return keyseq;
	}
	public void setKeyseq(String keyseq) {
		this.keyseq = keyseq;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
}
