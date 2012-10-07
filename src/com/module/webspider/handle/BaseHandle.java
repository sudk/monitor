package com.module.webspider.handle;

import javax.management.monitor.Monitor;

public interface BaseHandle {
	public Monitor handle();
	public String adstractSubject(String htmlstr);
}
