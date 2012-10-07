package com.component.common.key;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.component.common.util.DateUtil;

/**
 * ���뵼��ʱ����ֹ������ͻ������Ψһ����
 * 
 * @author LiuKun
 * @date 2010-4-16
 */
public final class KeyGenUtil {
	/**
	 * ��ʽ:��ǰʱ�� + �û����
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
	 * ��ȡuuidhex��Ψһ���ֵ
	 * 
	 * @return 32λ�ַ���
	 */
	public synchronized static String getUUIDhex() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * ������������������ԭ����sequenceд��(����)
	 * @return ������long���ͣ�
	 */
	public static Long getKey(String name){
		return SequenceGenerator.getInstance().getNextSequence(name);
	}
	
	/**
	 * ������������������ԭ����sequenceд��(����)
	 * @return ������long���ͣ�
	 */
	public static Integer getIntegerKey(String name){
		return Integer.valueOf(Long.toString(getKey(name)));
	}
	
	/**
	 * ������������������ԭ����sequenceд��(����)
	 * @return �������ַ������ͣ�
	 */
	public static String getStringKey(String name){
		long id = SequenceGenerator.getInstance().getNextSequence(name);
		return Long.toString(id);
	}
	
	/**
	 * �������򣺰���ǰ׺ + ˳���, ����ַ�����С��length����˳���ǰ��0,eg. DOC001
	 * @param name
	 * @param prefix
	 * @param length Ϊ0ʱ����0���䴦��
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
	 * �������򣺰���ǰ׺ + ˳���, ˳���������û�г���Լ��
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
	 * �������򣺰���ǰ׺ + ��� + ˳���, ����ַ�����С��length����˳���ǰ��0,eg. DOC2012001
	 * @param name
	 * @param prefix
	 * @param length Ϊ0ʱ����0���䴦��
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
	 * �������򣺰������+ǰ׺+ ˳���, ����ַ�����С��length����˳���ǰ��0,eg. 2012DOC001
	 * @param name
	 * @param prefix
	 * @param length Ϊ0ʱ����0���䴦��
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
	 * �������򣺰���ǰ׺ + ˳���, ����ַ�����С��length����˳���ǰ��0,eg. DOC2012001
	 * @param name
	 * @param prefix
	 * @param length Ϊ0ʱ����0���䴦��
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
