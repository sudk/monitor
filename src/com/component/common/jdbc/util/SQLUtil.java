package com.component.common.jdbc.util;

import java.io.Serializable;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.component.common.util.Validator;

/**
 * sql ������
 * 
 * @author LiuKun
 * @date 2012-7-17
 * @time ����2:30:35
 * 
 */
public class SQLUtil implements Serializable {
	/**
	 * ���к�
	 */
	private static final long serialVersionUID = -2069873359827367717L;

	private SQLUtil() {

	}

	private static final Logger logger = Logger.getLogger(SQLUtil.class);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static SQLUtil instance;

	public static SQLUtil getInstance() {
		if (null == instance) {
			synchronized (SQLUtil.class) {
				if (null == instance) {
					instance = new SQLUtil();
				}
			}
		}
		return instance;
	}

	// /////////////////////////////////////////////////////////////////

	
	/**
	 * ��������װΪSQL�����in�����е����ݵģ�'','','','',������ʽ
	 * ���["1","2","3"] ת��Ϊ('1','2','3')
	 * @return
	 */
	public static String in(Collection<String> collection){
		if(Validator.isEmpty(collection)){
			return "''";
		}
		StringBuilder temp = new StringBuilder("");
		java.util.Iterator<String> it = collection.iterator();
		while (it.hasNext()) {
			String str = (String) it.next();
			temp.append("'"+str+"',");
		}
		temp.deleteCharAt(temp.length()-1);
		return temp.toString();
	}
	
	/**
	 * ������װΪSQL�����in�����е����ݵģ�'','','','',������ʽ
	 * ���["1","2","3"] ת��Ϊ('1','2','3')
	 * @return
	 */
	public static String in(String... values){
		if(Validator.isEmpty(values)){
			return "''";
		}
		
		StringBuilder temp = new StringBuilder("");
		for (int i = 0;values!=null && i < values.length; i++) {
			String str = values[i];
			temp.append("'"+str+"',");
		}
		temp.deleteCharAt(temp.length()-1);
		return temp.toString();
	}
	
	/**
	 * ������װΪSQL�����in�����е����ݵģ�'','','','',������ʽ
	 * ���[1,2,3] ת��Ϊ(1,2,3)
	 * @return
	 */
	public static String in(Integer... values){
		if(Validator.isEmpty(values)){
			return "";
		}
		StringBuilder temp = new StringBuilder("");
		for (int i = 0;values!=null && i < values.length; i++) {
			Integer value = values[i];
			temp.append(value+",");
		}
		temp.deleteCharAt(temp.length()-1);
		return temp.toString();
	}
	
}
