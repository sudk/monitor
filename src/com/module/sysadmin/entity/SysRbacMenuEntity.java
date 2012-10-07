package com.module.sysadmin.entity;


import javax.persistence.Column;
import com.component.common.annotation.Comment;
import com.component.common.entity.AbstractEntity;

/**
 * 
 * Copyright (c) 112 ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 Sat Oct 06 10:27:34 CST 2012
 * @see �����ṩ������
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SYS_RBAC_MENU")
public class SysRbacMenuEntity extends AbstractEntity implements ISysRbacMenuEntity {
	/**  ����  */       

	private static final long serialVersionUID = 1L;
	// Fields
	private String menuId;
	private String funcId;
	private String menuParentId;
	private String menuName;
	private String menuLabel;
	private String isLeaf;
	private String menuUrl;
	private String menuParam;
	private Integer menuLevel;
	private Integer menuOrder;
	private String menuIcon;
	private String menuSeq;

	/** default constructor */
	public SysRbacMenuEntity() {
	}

	/** default constructor */
	public SysRbacMenuEntity(String menuId) {
				this.menuId = menuId;
	}


	@javax.persistence.Id
	@Column(name = "MENU_ID", length = 32)
	@Comment("�˵����")
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
				this.menuId = menuId;
	}

	@Column(name = "FUNC_ID", length = 32)
	@Comment("�˵��������ܱ��")
	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
				this.funcId = funcId;
	}

	@Column(name = "MENU_PARENT_ID", length = 32)
	@Comment("���˵����")
	public String getMenuParentId() {
		return this.menuParentId;
	}

	public void setMenuParentId(String menuParentId) {
				this.menuParentId = menuParentId;
	}

	@Column(name = "MENU_NAME", length = 100)
	@Comment("�˵�����")
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
				this.menuName = menuName;
	}

	@Column(name = "MENU_LABEL", length = 100)
	@Comment("�˵���ǩ")
	public String getMenuLabel() {
		return this.menuLabel;
	}

	public void setMenuLabel(String menuLabel) {
				this.menuLabel = menuLabel;
	}

	@Column(name = "IS_LEAF", length = 40)
	@Comment("�Ƿ�Ҷ��")
	public String getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
				this.isLeaf = isLeaf;
	}

	@Column(name = "MENU_URL", length = 255)
	@Comment("�˵�URL")
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
				this.menuUrl = menuUrl;
	}

	@Column(name = "MENU_PARAM", length = 255)
	@Comment("�˵�URL����")
	public String getMenuParam() {
		return this.menuParam;
	}

	public void setMenuParam(String menuParam) {
				this.menuParam = menuParam;
	}

	@Column(name = "MENU_LEVEL", length = 5)
	@Comment("�˵��㼶")
	public Integer getMenuLevel() {
		return this.menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
				this.menuLevel = menuLevel;
	}

	@Column(name = "MENU_ORDER", length = 5)
	@Comment("ͬ���˵���ʾ˳��")
	public Integer getMenuOrder() {
		return this.menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
				this.menuOrder = menuOrder;
	}

	@Column(name = "MENU_ICON", length = 255)
	@Comment("�˵�ͼ��·��")
	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
				this.menuIcon = menuIcon;
	}

	@Column(name = "MENU_SEQ", length = 512)
	@Comment("�˵�SEQ")
	public String getMenuSeq() {
		return this.menuSeq;
	}

	public void setMenuSeq(String menuSeq) {
				this.menuSeq = menuSeq;
	}

}
