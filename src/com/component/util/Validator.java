package com.component.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * ��֤������
 * Author: LiuKun
 * Date: 2012-6-11
 * Time: 16:44
 */
public class Validator implements Serializable{
	
    /**
	 * ���к�
	 */
	private static final long serialVersionUID = 5911455242813608853L;
	
	private static final Logger logger = Logger.getLogger(Validator.class);
	
	public static Logger getLogger() {
		return logger;
	}


	/**
     * ��֤�����Ƿ�Ϊ��(Object/Integer/String/List/[]/Map ...)
     * 
     * @param value ����֤����
     * @return �������Ϊ�գ�""/" "/"null"/null/number=0/[].length=0/collection.size()=0/map.size()=0����Ϊ�գ�,
     *         ����true,���򷵻�false
     */
    public static boolean isEmpty(Object value) {

        if (   value == null 
        	|| "".equalsIgnoreCase(value.toString().replaceAll(" ", ""))
            || "null".equalsIgnoreCase(value.toString().replaceAll(" ", ""))) {// Object/String
            return true;
        }

        if (value instanceof java.lang.Number) {// BigDecimal/BigInteger/Byte/Double/Float/Long/Integer/Short
            java.lang.Number number = (Number) value;
            if ("0".equalsIgnoreCase(number.toString().replaceAll(" ", "")) 
            	|| number.toString().replaceAll(" ", "").matches("[0]*")
                || number.toString().replaceAll(" ", "").matches("[0]+[.]?[0]+")) {
                return true;
            }
        }

        if (value instanceof Object[]) {// ����
            Object[] objs = (Object[]) value;
            if (objs.length == 0) {
                return true;
            }
        }

        if (value instanceof Collection) {// ����
            Collection<?> coll = (Collection<?>) value;
            if (coll.size() == 0) {
                return true;
            }
        }

        if (value instanceof Map) {// Map
            Map<?,?> map = (Map<?,?>) value;
            if (map.isEmpty() || map.size() == 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }
	
	/**
     * ��֤�����ַ����Ƿ�ֵ���
     *
     * @param s1 ��Ƚϵ��ַ���1
     * @param s2 ��Ƚϵ��ַ���2
     * @return ��������ַ���ֵ��ȣ�����true�����򷵻�false
     */
    public static boolean equals(String s1, String s2) {
        if ((s1 == null) && (s2 == null)) {
            return true;
        } else if ((s1 == null) || (s2 == null)) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }


    /**
     * �жϸ����ַ��Ƿ�Ӣ����ĸ
     *
     * @param c Ҫ��֤���ַ�
     * @return �������ĸ���򷵻�true�����򷵻�false
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     * �жϸ����ַ����Ƿ�����ĸ���ߺ��ֹ���
     *
     * @param s Ҫ��֤���ַ���
     * @return ������Ӣ����ĸ���ߺ��ֹ��ɣ��򷵻�true�����򷵻�false
     */
    public static boolean isLetter(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isLetter(c[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * �жϸ����ַ��Ƿ�����
     *
     * @param c Ҫ��֤���ַ�
     * @return ��������֣��򷵻�true�����򷵻�false
     */
    public static boolean isDigit(char c) {
        int x = (int) c;

        if ((x >= 48) && (x <= 57)) {
            return true;
        }

        return false;
    }

    /**
     * �жϸ����ַ����Ƿ������ֹ���
     *
     * @param s Ҫ��֤���ַ���
     * @return ���������ֹ��ɣ��򷵻�true�����򷵻�false
     */
   /* public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }*/

    /*
    public static boolean isHex(String s) {
		if (isNull(s)) {
			return false;
		}

		return true;
	}
    */

    /**
     * �жϸ����ַ����Ƿ���������html�����
     * ������������html�������ָ����<html></html>�Ĵ��룬��Сд��أ�
     *
     * @param s Ҫ��֤���ַ���
     * @return ��Ϊhtml����Σ��򷵻�true�����򷵻�false
     */
    public static boolean isHTML(String s) {
        if (isNull(s)) {
            return false;
        }

        if (((s.indexOf("<html>") != -1) || (s.indexOf("<HTML>") != -1)) &&
                ((s.indexOf("</html>") != -1) || (s.indexOf("</HTML>") != -1))) {

            return true;
        }

        return false;
    }

    /*
    public static boolean isLUHN(String number) {
		if (number == null) {
			return false;
		}

		number = StringUtil.reverse(number);

		int total = 0;

		for (int i = 0; i < number.length(); i++) {
			int x = 0;

			if (((i + 1) % 2) == 0) {
				x = Integer.parseInt(number.substring(i, i + 1)) * 2;

				if (x >= 10) {
					String s = Integer.toString(x);

					x = Integer.parseInt(s.substring(0, 1)) +
						Integer.parseInt(s.substring(1, 2));
				}
			}
			else {
				x = Integer.parseInt(number.substring(i, i + 1));
			}

			total = total + x;
		}

		if ((total % 10) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
    */

    /**
     * �жϸ����ַ����Ƿ���һ���Ϸ���email��ַ
     *
     * @param email Ҫ������֤���ַ���
     * @return ���ǺϷ���email��ַ���򷵻�true�����򷵻�false
     */
    public static boolean isEmailAddress(String email) {
        if (isNull(email)) {
            return false;
        }

        int eaLength = email.length();

        if (eaLength < 6) {

            // j@j.c

            return false;
        }

        email = email.toLowerCase();

        int at = email.indexOf(StringPool.AT);

        // Unix based email addresses cannot be longer than 24 characters.
        // However, many Windows based email addresses can be longer than 24,
        // so we will set the maximum at 48.

        //int maxEmailLength = 24;
        int maxEmailLength = 48;

        if ((at > maxEmailLength) || (at == -1) || (at == 0) ||
                ((at <= eaLength) && (at > eaLength - 5))) {

            // 123456789012345678901234@joe.com
            // joe.com
            // @joe.com
            // joe@joe
            // joe@jo
            // joe@j

            return false;
        }

        int dot = email.lastIndexOf('.');

        if ((dot == -1) || (dot < at) || (dot > eaLength - 3)) {

            // joe@joecom
            // joe.@joecom
            // joe@joe.c

            return false;
        }

        if (email.indexOf("..") != -1) {

            // joe@joe..com

            return false;
        }

        char[] name = email.substring(0, at).toCharArray();

        for (int i = 0; i < name.length; i++) {
            if ((!isLetter(name[i])) &&
                    (!isDigit(name[i])) &&
                    (name[i] != '.') &&
                    (name[i] != '-') &&
                    (name[i] != '_')) {

                return false;
            }
        }

        if ((name[0] == '.') || (name[name.length - 1] == '.') ||
                (name[0] == '-') || (name[name.length - 1] == '-') ||
                (name[0] == '_')) { // || (name[name.length - 1] == '_')) {

            // .joe.@joe.com
            // -joe-@joe.com
            // _joe_@joe.com

            return false;
        }

        char[] host = email.substring(at + 1, email.length()).toCharArray();

        for (int i = 0; i < host.length; i++) {
            if ((!isLetter(host[i])) &&
                    (!isDigit(host[i])) &&
                    (host[i] != '.') &&
                    (host[i] != '-')) {

                return false;
            }
        }

        if ((host[0] == '.') || (host[host.length - 1] == '.') ||
                (host[0] == '-') || (host[host.length - 1] == '-')) {

            // joe@.joe.com.
            // joe@-joe.com-

            return false;
        }

        // postmaster@joe.com

        if (email.startsWith("postmaster@")) {
            return false;
        }

        // root@.com

        if (email.startsWith("root@")) {
            return false;
        }

        return true;
    }

    /*
     public static boolean isName(String name) {
         if (isNull(name)) {
             return false;
         }

         char[] c = name.trim().toCharArray();

         for (int i = 0; i < c.length; i++) {
             if (((!isLetter(c[i])) &&
                 (!Character.isWhitespace(c[i]))) ||
                     (c[i] == ',')) {

                 return false;
             }
         }

         return true;
     }
     */

    /**
     * �жϸ����ַ����Ƿ�����
     *
     * @param number Ҫ��֤���ַ���
     * @return ��Ϊ���֣��򷵻�true�����򷵻�false
     */
    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        char[] c = number.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * �жϸ��������Ƿ�����飨���������ָnull���߳���Ϊ0�����飩
     *
     * @param o Ҫ��֤������
     * @return ��Ϊ�����飬�򷵻�null�����򷵻�false
     */
    public static boolean isNull(Object[] o) {
        if (o == null || o.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * �жϸ����ַ����Ƿ�մ� ������մ���ָnull���ߡ������������ɸ��ո���ɵĴ���
     *
     * @param s Ҫ��֤���ַ���
     * @return ��Ϊ�մ����򷵻�true�����򷵻�false
     */
    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        s = s.trim();

        if ((s.equals(StringPool.NULL)) || (s.equals(StringPool.BLANK))) {
            return true;
        }

        return false;
    }

    /**
     * �жϸ����ַ����Ƿ�ǿմ� ������մ���ָnull���ߡ������������ɸ��ո���ɵĴ���
     *
     * @param s Ҫ��֤���ַ���
     * @return ��Ϊ�ǿմ����򷵻�true�����򷵻�false
     */
    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    /**
     * �жϸ����ַ����Ƿ�Ϸ�����
     * ������Ϸ�������ָ���ȴ����4�������ֻ�����Ӣ������ɵ��ַ�����
     *
     * @param password Ҫ��֤���ַ���
     * @return ��Ϊ�Ϸ����룬�򷵻�true�����򷵻�false
     */
    public static boolean isPassword(String password) {
        if (isNull(password)) {
            return false;
        }

        if (password.length() < 4) {
            return false;
        }

        char[] c = password.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if ((!isLetter(c[i])) &&
                    (!isDigit(c[i]))) {

                return false;
            }
        }

        return true;
    }

    private static final String regex = "[1-9]\\d*\\.?\\d*|0\\.\\d*|-[1-9]\\d*\\.?\\d*|-0\\.\\d*|0";

    /**
     * �ж��ַ����Ƿ����֣�������и����Լ�С���㣩
     * @param string �����ַ���
     * @return ��Ϊ�Ϸ����֣��򷵻�true�����򷵻�false
     */
    public static boolean isDigital(String string) {
        return string.matches(regex);
    }

    /*
     public static boolean isPhoneNumber(String phoneNumber) {
         return isNumber(StringUtil.extractDigits(phoneNumber));
     }
     */


    /**
     * ��֤�����ַ����Ƿ�Ϊһ����Ч������
     * 
     * @param pattern
     *            ����ģʽ �� yyyy-MM-dd��yyyyMMdd
     * @param value
     *            ����ָ��ģʽ���ַ��� �� 2012-02-29
     * @return �Ƿ�һ����Ч���������ͣ�true:��ʾ�ǣ�false:��ʾ���ǣ�˵������֧�����ڽ�λ �磺2012-02-30
     *         ���ܱ�ʾ2012-03-01��
     * @throws Exception ������ƥ���쳣
     */
    public static boolean validateDate(String pattern, String value) throws Exception {
        if (Validator.isEmpty(pattern)) {
            throw new RuntimeException("����ģʽ����Ϊ�գ�");
        }

        if (Validator.isEmpty(value)) {
            throw new RuntimeException("�����ַ�������Ϊ�գ�");
        }
        boolean flag = false;
        SimpleDateFormat format = null;
        try {
            format = new SimpleDateFormat(pattern);
            Date date = format.parse(value);
            if (format.format(date).equalsIgnoreCase(value)) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            logger.error("���ڸ�ʽ����ȷ��", e);
            return flag;
           // throw new RuntimeException("���ڸ�ʽ����ȷ��");
        }
        return flag;
    }

    /**
     * �ж϶����Ƿ�ΪInteger���ͻ��Ƿ����ת��ΪInteger����
     * 
     * @param value
     * @return ����Ϊtrue������Ϊfalse
     */
    public static boolean validateInteger(Object value)  {
        if (value == null) {
            return false;
        }
        if (value instanceof Integer) {
            return true;
        } else {
            value = value.toString();
        }
        if (value instanceof String) {
            String str = (String) value;
            if (str.matches("\\d+")) {
                return true;
            }
        }
        boolean flag = false;
        return flag;
    }

}