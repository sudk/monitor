package com.module.monitor.service;

import com.component.common.service.BaseService;
import com.module.monitor.entity.BusMonitorResultEntity;

import org.springframework.stereotype.Service;

/**
 * 
 * Copyright (c)  北京联银通科技有限公司 all rights reserved.
 * @author zhangwu
 * @version 1.0 2012-10-06 17:00:40.661
 * @see 工具提供—张武
 *
 */

@Service
public class BusMonitorResultService extends BaseService {
     public void saveMonitor(String name){
    	 BusMonitorResultEntity entity = new BusMonitorResultEntity();
    	 entity.setKeyWord(name);
    	 save(entity);
     }
}
