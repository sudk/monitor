package com.module.fileTools;

/**
 * �����ж���
 *         
 * Description: ����        
 * @author zhangwu        
 * @version 1.0      
 * @created 2012-7-24 ����9:29:06
 */
public class ColumnModel {

	private String tabschema;//�����ڵ�schema
	private String tabname;//����
	private String colname;//����
	private String colno;//�е�˳��
	private String typaname;//����������
	private String length;//�ֶγ���
	private String scale;//����
	private String nulls;//�Ƿ�Ϊ�գ�Y��N��
	private String keyseq;//����λ�ã�Ϊ������������
	private String remarks;//��ע
	
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
