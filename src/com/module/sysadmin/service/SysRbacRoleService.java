package com.module.sysadmin.service;

import java.util.List;

import com.component.common.service.BaseService;
import com.component.common.spring.util.BeanUtil;
import com.module.sysadmin.entity.SysCodevalueEntity;
import com.module.sysadmin.entity.SysRbacRoleEntity;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

/**
 * 
 * Copyright (c)  北京联银通科技有限公司 all rights reserved.
 * @author zhangwu
 * @version 1.0 2012-10-06 10:27:37.862
 * @see 工具提供—张武
 *
 */

@Service
public class SysRbacRoleService extends BaseService {

	public JSONArray findAllRoleEntitys() {
		List<SysRbacRoleEntity> list = findAll(SysRbacRoleEntity.class);
		JSONArray json = JSONArray.fromObject(list);
		return json;
	}
}
