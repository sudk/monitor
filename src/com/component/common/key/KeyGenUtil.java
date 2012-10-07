package com.component.common.key;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.component.common.util.DateUtil;

/**
 * 导入导出时，防止主键冲突，生成唯一主键
 * 
 * @author LiuKun
 * @date 2010-4-16
 */
public final class KeyGenUtil {
	/**
	 * 格式:当前时间 + 用户编号
	 * 
	 * @return
	 */
	public synchronized static final String getKey() {
		String userid = "";//SystemUtil.getCurrentUser().getCompanyId();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssS");

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}

		return format.format(new Date()) + userid;
	}
	/**
	 * 提取uuidhex的唯一编号值
	 * 
	 * @return 32位字符码
	 */
	public synchronized static String getUUIDhex() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 表主键生成器，代替原来的sequence写法(单类)
	 * @return 主键（long类型）
	 */
	public static Long getKey(String name){
		return SequenceGenerator.getInstance().getNextSequence(name);
	}
	
	/**
	 * 表主键生成器，代替原来的sequence写法(单类)
	 * @return 主键（long类型）
	 */
	public static Integer getIntegerKey(String name){
		return Integer.valueOf(Long.toString(getKey(name)));
	}
	
	/**
	 * 表主键生成器，代替原来的sequence写法(单类)
	 * @return 主键（字符串类型）
	 */
	public static String getStringKey(String name){
		long id = SequenceGenerator.getInstance().getNextSequence(name);
		return Long.toString(id);
	}
	
	/**
	 * 主键规则：按照前缀 + 顺序号, 如果字符长度小于length，则顺序号前补0,eg. DOC001
	 * @param name
	 * @param prefix
	 * @param length 为0时不做0补充处理
	 * @return DOC001
	 */
	public static String getKey(String name, String prefix, int length){
		String key = prefix;
		String tmp = String.valueOf(getKey(name));
		if ((key + tmp).length() < length) {
			int len = length - (key + tmp).length();
			for (int i = 0; i < len; i++)
				tmp = "0" + tmp;
		}
		return key + tmp;
	}
	
	/**
	 * 主键规则：按照前缀 + 顺序号, 顺序号自增，没有长度约束
	 * @param name
	 * @param prefix
	 * @return DOC1
	 */
	public static String getKey(String name, String prefix){
		String key = prefix;
		String tmp = String.valueOf(getKey(name));
		return key + tmp;
	}
	
	
	/**
	 * 主键规则：按照前缀 + 年份 + 顺序号, 如果字符长度小于length，则顺序号前补0,eg. DOC2012001
	 * @param name
	 * @param prefix
	 * @param length 为0时不做0补充处理
	 * @return DOC2012001
	 */
	public static String getYearKey(String name, String prefix, int length) {
		String key = prefix + DateUtil.formatDate("yyyy", new Date());
		String tmp = String.valueOf(getKey(name));
		if ((key + tmp).length() < length) {
			int len = length - (key + tmp).length();
			for (int i = 0; i < len; i++)
				tmp = "0" + tmp;
		}
		return key + tmp;
	}
	
	/**
	 * 主键规则：按照年份+前缀+ 顺序号, 如果字符长度小于length，则顺序号前补0,eg. 2012DOC001
	 * @param name
	 * @param prefix
	 * @param length 为0时不做0补充处理
	 * @return DOC2012001
	 */
	public static String getYearKeyYearFirst(String name, String prefix, int length) {
		String key =   DateUtil.formatDate("yyyy", new Date())+prefix;
		String tmp = String.valueOf(getKey(name));
		if ((key + tmp).length() < length) {
			int len = length - (key + tmp).length();
			for (int i = 0; i < len; i++)
				tmp = "0" + tmp;
		}
		return key + tmp;
	}
	
	/**
	 * 主键规则：按照前缀 + 顺序号, 如果字符长度小于length，则顺序号前补0,eg. DOC2012001
	 * @param name
	 * @param prefix
	 * @param length 为0时不做0补充处理
	 * @return DOC2012001
	 */
	public static String getKeyByPrefix(String name, String prefix, int length) {
		String key = prefix ;
		String tmp = String.valueOf(getKey(name));
		if ((key + tmp).length() < length) {
			int len = length - (key + tmp).length();
			for (int i = 0; i < len; i++)
				tmp = "0" + tmp;
		}
		return key + tmp;
	}

}
