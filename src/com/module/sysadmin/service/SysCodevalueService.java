package com.module.sysadmin.service;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.component.common.service.BaseService;
import com.component.common.spring.util.BeanUtil;
import com.module.sysadmin.entity.SysCodevalueEntity;

/**
 * 
 * Copyright (c)  ��������ͨ�Ƽ����޹�˾ all rights reserved.
 * @author zhangwu
 * @version 1.0 2012-10-06 10:27:29.541
 * @see �����ṩ������
 *
 */

@Service
public class SysCodevalueService extends BaseService {

	public JSONArray findAllCodeValueEntitys() {
		List<SysCodevalueEntity> list = BeanUtil.getSysCodevalueService()
				.findAll(SysCodevalueEntity.class);
		JSONArray json = JSONArray.fromObject(list);
		return json;
	}
}
