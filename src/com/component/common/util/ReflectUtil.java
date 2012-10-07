package com.component.common.util;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.component.common.hibernate.util.HibernateUtil;

/**
 * ������صİ�����
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 ����9:46:18
 */
public class ReflectUtil implements Serializable {
	private ReflectUtil() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	/**
	 * ���к�
	 */
	private static final long serialVersionUID = -7823719392758990749L;
	private static final Logger logger = Logger.getLogger(ReflectUtil.class);
	private static ReflectUtil instance;

	public static ReflectUtil getInstance() {
		if (instance == null) {
			synchronized (ReflectUtil.class) {
				if (instance == null) {
					instance = new ReflectUtil();
				}
			}
		}
		return instance;
	}

	// ////////////////////////////////////

	/**
	 * ִ��get����
	 * 
	 * @param setMethod
	 * @param obj
	 * @param args
	 * @author LiuKun
	 * @created 2012-8-20 ����9:47:09
	 */
	public static void executeSetMethod(Method setMethod, Object obj,
			Object... args) throws Exception {
		if (setMethod == null || obj == null) {
			return;
		}

		if (Validator.isNotEmpty(args)) {
			// set������������
			Class<?>[] argsClassArr = setMethod.getParameterTypes();
			Object[] newArgs = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					// ���Ͳ�һ��
					if (!PackageUtil.equalsIgnorePackageClass(args[i]
							.getClass().getName(), argsClassArr[i].getName())) {
						if (PackageUtil.equalsIgnorePackageClass(
								String.class.getName(),
								argsClassArr[i].getName())) {// set��������ΪString����
							newArgs[i] = ParseUtil.parseString(args[i]);
						} else if (PackageUtil.equalsIgnorePackageClass(
								Integer.class.getName(),
								argsClassArr[i].getName())) {// set��������ΪInteger����
							newArgs[i] = ParseUtil.parseInteger(args[i]);
						} else if (PackageUtil.equalsIgnorePackageClass(
								Boolean.class.getName(),
								argsClassArr[i].getName())// set��������ΪBolean����
								&& PackageUtil.equalsIgnorePackageClass(
										String.class.getName(), args[i]
												.getClass().getName())// ����ֵ������ΪString����
						) {
							String value = ParseUtil.parseString(args[i]);
							if ("0".equalsIgnoreCase(value)) {
								newArgs[i] = Boolean.FALSE;
							} else if ("1".equalsIgnoreCase(value)) {
								newArgs[i] = Boolean.TRUE;
							}
						} else {
							newArgs[i] = args[i];
						}

					} else {// ����һ��
						newArgs[i] = args[i];
					}
				} else {
					newArgs[i] = args[i];
				}
			}
			try {
				setMethod.invoke(obj, newArgs);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	/**
	 * ִ��set����
	 * 
	 * @param getMethod
	 * @param obj
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object executeGetMethod(Method getMethod, Object obj,
			Object... args) throws Exception {
		return getMethod.invoke(obj, args);
	}

	/**
	 * ��������󣬻�ȡ�������������Map<��������ԭ�������򴿴�д����Сд��,���Զ���>
	 * 
	 * @param clazz
	 *            �����
	 * @return Map<��������ԭ�������򴿴�д����Сд��,���Զ���>
	 */
	public static <E> Map<String, Field> getFieldMapByEntityClass(Class<E> clazz) {
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		for (int i = 0; fields != null && i < fields.length; i++) {
			Field field = fields[i];
			fieldMap.put(fields[i].getName(), field);
			fieldMap.put(fields[i].getName().toLowerCase(), field);
			fieldMap.put(fields[i].getName().toUpperCase(), field);
		}

		fields = clazz.getFields();
		AccessibleObject.setAccessible(fields, true);
		for (int i = 0; fields != null && i < fields.length; i++) {
			Field field = fields[i];
			fieldMap.put(fields[i].getName(), field);
			fieldMap.put(fields[i].getName().toLowerCase(), field);
			fieldMap.put(fields[i].getName().toUpperCase(), field);
		}
		return fieldMap;
	}

	/**
	 * һ��get/set����ת��Ϊһ���������� �� �����������Ƽ���
	 * 
	 * @param clazz
	 * @return
	 */
	public static <E> Set<String> getFieldNameSetByEntityClassAndGetMethodAndSetMethod(
			Class<E> clazz) {
		Set<String> fieldNameSet = new HashSet<String>();

		Map<String, String> getMethodNameMap = new HashMap<String, String>();
		Map<String, String> setMethodNameMap = new HashMap<String, String>();

		String fieldName = "";
		String methodName = "";
		String getMethodName = "";
		String setMethodName = "";

		Method[] methods = clazz.getDeclaredMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			methodName = method.getName();
			if (methodName.startsWith("get")) {// get����
				getMethodNameMap.put(methodName, null);
			} else if (methodName.startsWith("set")) {// set����
				setMethodNameMap.put(methodName, null);
			}
		}

		methods = clazz.getMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			methodName = method.getName();
			if (methodName.startsWith("get")) {// get����
				getMethodNameMap.put(methodName, null);
			} else if (methodName.startsWith("set")) {// set����
				setMethodNameMap.put(methodName, null);
			}
		}

		for (Entry<String, String> entry : getMethodNameMap.entrySet()) {
			getMethodName = entry.getKey();
			setMethodName = "set" + getMethodName.substring(3);

			if (setMethodNameMap.containsKey(setMethodName)) {// get�����������Ӧ��set����������Թ���һ����������
				fieldName = getMethodName.substring(3);
				fieldName = Character.toString(Character.toLowerCase(fieldName
						.charAt(0))) + fieldName.substring(1);
				fieldNameSet.add(fieldName);
			}
		}

		return fieldNameSet;
	}

	/**
	 * ��������󣬻�ȡ��������з���Map<��������ԭ�������򴿴�д����Сд��,��������>
	 * 
	 * @param clazz
	 *            �����
	 * @return Map<��������ԭ�������򴿴�д����Сд��,��������>
	 */
	public static <E> Map<String, Method> getMethodMapByEntityClass(
			Class<E> clazz) {
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = clazz.getDeclaredMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			methodMap.put(methods[i].getName(), method);
			methodMap.put(methods[i].getName().toLowerCase(), method);
			methodMap.put(methods[i].getName().toUpperCase(), method);
		}

		methods = clazz.getMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			methodMap.put(methods[i].getName(), method);
			methodMap.put(methods[i].getName().toLowerCase(), method);
			methodMap.put(methods[i].getName().toUpperCase(), method);
		}
		return methodMap;
	}

	/**
	 * ��������󣬻�ȡ�����get����Map<��������ԭ�������򴿴�д����Сд��,��������>
	 * 
	 * @param clazz
	 *            �����
	 * @return get����Map<��������ԭ�������򴿴�д����Сд��,��������>
	 */
	public static <E> Map<String, Method> getGetMethodMapByEntityClass(
			Class<E> clazz) {
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = clazz.getDeclaredMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith("get")) {
				methodMap.put(methods[i].getName(), method);
				methodMap.put(methods[i].getName().toLowerCase(), method);
				methodMap.put(methods[i].getName().toUpperCase(), method);
			}
		}

		methods = clazz.getMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith("get")) {
				methodMap.put(methods[i].getName(), method);
				methodMap.put(methods[i].getName().toLowerCase(), method);
				methodMap.put(methods[i].getName().toUpperCase(), method);
			}
		}
		return methodMap;
	}

	/**
	 * ��������󣬻�ȡ�����set����Map<��������ԭ�������򴿴�д����Сд��,��������>
	 * 
	 * @param clazz
	 *            �����
	 * @return set����Map<��������ԭ�������򴿴�д����Сд��,��������>
	 */
	public static <E> Map<String, Method> getSetMethodMapByEntityClass(
			Class<E> clazz) {
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = clazz.getDeclaredMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith("set")) {
				methodMap.put(methods[i].getName(), method);
				methodMap.put(methods[i].getName().toLowerCase(), method);
				methodMap.put(methods[i].getName().toUpperCase(), method);
			}
		}

		methods = clazz.getMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith("set")) {
				methodMap.put(methods[i].getName(), method);
				methodMap.put(methods[i].getName().toLowerCase(), method);
				methodMap.put(methods[i].getName().toUpperCase(), method);
			}
		}
		return methodMap;
	}

	/**
	 * List<Map<String,Object>> ת List<E>
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> map2Model(List<Map<String, Object>> list,
			Class<E> clazz) throws Exception {
		List<E> modelList = new ArrayList<E>();
		if (Validator.isEmpty(list)) {
			return modelList;
		}

		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator
				.hasNext();) {
			Map<String, Object> map = iterator.next();
			E t = (E) map2Model(map, clazz);
			modelList.add(t);
		}
		return modelList;
	}

	/**
	 * List<Map<String,Object>> ת List<E> ( ��� hibernate �� ���� �� ��������һ�µ����)
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> map2ModelByHibernate(
			List<Map<String, Object>> list, Class<E> clazz) throws Exception {
		Map<String, String> columnMap = HibernateUtil.getColumnMap(clazz);
		for (int i = 0; list != null && i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Map<String, Object> fieldMap = new HashMap<String, Object>();
			for (String key : map.keySet()) {
				String columnName = key;
				Object value = map.get(key);
				String fieldName = columnMap.get(columnName);
				if (Validator.isNotEmpty(fieldName)) {
					fieldMap.put(fieldName, value);
				}
			}
			map.putAll(fieldMap);
			list.set(i, map);
		}
		return map2Model(list, clazz);
	}

	/**
	 * List<Map<String,Object>> ת List<E> ( ��� map�е�key����ʵ�����������һ�µ����)
	 * 
	 * @param list
	 * @param clazz
	 * @param convertMap
	 *            Map<map��key��,ʵ��������>
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> map2ModelByHibernate(
			List<Map<String, Object>> list, Class<E> clazz,
			Map<String, String> convertMap) throws Exception {
		if (Validator.isNotEmpty(convertMap)) {
			for (int i = 0; list != null && i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				Map<String, Object> fieldMap = new HashMap<String, Object>();
				for (String key : map.keySet()) {
					String columnName = key;
					Object value = map.get(key);
					String fieldName = convertMap.get(columnName);
					if (Validator.isNotEmpty(fieldName)) {
						fieldMap.put(fieldName, value);
					}
				}
				map.putAll(fieldMap);
				list.set(i, map);
			}
		}

		return map2Model(list, clazz);
	}

	/**
	 * mapת��Ϊʵ�����
	 * 
	 * @param map
	 *            Map<��������ԭ�������򴿴�д����Сд��,ֵ>
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <E> E map2Model(Map<String, Object> map, Class<E> clazz)
			throws Exception {
		E model = clazz.newInstance();
		String fieldName = "";
		Object value = null;
		Method[] methods = clazz.getDeclaredMethods();
		AccessibleObject.setAccessible(methods, true);
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set")
					&& methodName.getBytes().length >= 3) {
				fieldName = methodName.substring(3);
				fieldName = Character.toString(Character.toLowerCase(fieldName
						.charAt(0))) + fieldName.substring(1);
				value = map.get(fieldName);
				if (Validator.isNotEmpty(value)) {
					executeSetMethod(method, model, value);
				} else {
					fieldName = fieldName.toLowerCase();
					value = map.get(fieldName);
					if (Validator.isNotEmpty(value)) {
						executeSetMethod(method, model, value);
					} else {
						fieldName = fieldName.toUpperCase();
						value = map.get(fieldName);
						if (Validator.isNotEmpty(value)) {
							executeSetMethod(method, model, value);
						}
					}
				}
			}
		}

		methods = clazz.getMethods();
		AccessibleObject.setAccessible(methods, true);
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set")
					&& methodName.getBytes().length >= 3) {
				fieldName = methodName.substring(3);
				fieldName = Character.toString(Character.toLowerCase(fieldName
						.charAt(0))) + fieldName.substring(1);
				value = map.get(fieldName);
				if (Validator.isNotEmpty(value)) {
					executeSetMethod(method, model, value);
				} else {
					fieldName = fieldName.toLowerCase();
					value = map.get(fieldName);
					if (Validator.isNotEmpty(value)) {
						executeSetMethod(method, model, value);
					} else {
						fieldName = fieldName.toUpperCase();
						value = map.get(fieldName);
						if (Validator.isNotEmpty(value)) {
							executeSetMethod(method, model, value);
						}
					}
				}
			}
		}

		return model;
	}

	/**
	 * mapת��Ϊʵ�����( ��� hibernate �� ���� �� ��������һ�µ����)
	 * 
	 * @param map
	 *            Map<��������ԭ�������򴿴�д����Сд��,ֵ>
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <E> E map2ModelByHibernate(Map<String, Object> map,
			Class<E> clazz) throws Exception {

		Map<String, String> columnMap = HibernateUtil.getColumnMap(clazz);
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			String columnName = key;
			Object value = map.get(key);
			String fieldName = columnMap.get(columnName);
			if (Validator.isNotEmpty(fieldName)) {
				fieldMap.put(fieldName, value);
			}
		}
		map.putAll(fieldMap);

		return map2Model(map, clazz);
	}

	/**
	 * mapת��Ϊʵ�����
	 * 
	 * @param map
	 *            Map<��������ԭ�������򴿴�д����Сд��,ֵ>
	 * @param clazz
	 * @param convertMap
	 *            Map<map��key��,ʵ��������>
	 * @return
	 * @throws Exception
	 */
	public static <E> E map2Model(Map<String, Object> map, Class<E> clazz,
			Map<String, String> convertMap) throws Exception {
		if (Validator.isNotEmpty(convertMap)) {
			Map<String, Object> fieldMap = new HashMap<String, Object>();
			for (String key : map.keySet()) {
				String columnName = key;
				Object value = map.get(key);
				String fieldName = convertMap.get(columnName);
				if (Validator.isNotEmpty(fieldName)) {
					fieldMap.put(fieldName, value);
				}
			}
			map.putAll(fieldMap);
		}

		return map2Model(map, clazz);
	}

	/**
	 * ʵ�����ת��ΪMap<��������ԭ�������򴿴�д����Сд��,ֵ>
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> model2Map(Object model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (Validator.isEmpty(model)) {
			return map;
		}

		Method[] methods = model.getClass().getMethods();
		String fieldName = "";
		AccessibleObject.setAccessible(methods, true);
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("get")
					&& methodName.getBytes().length >= 3) {
				fieldName = methodName.substring(3);
				fieldName = Character.toString(Character.toLowerCase(fieldName
						.charAt(0))) + fieldName.substring(1);
				Object value = executeGetMethod(method, model);
				map.put(fieldName, value);
				map.put(fieldName.toLowerCase(), value);
				map.put(fieldName.toUpperCase(), value);
			}
		}

		methods = model.getClass().getDeclaredMethods();
		AccessibleObject.setAccessible(methods, true);
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("get")
					&& methodName.getBytes().length >= 3) {
				fieldName = methodName.substring(3);
				fieldName = Character.toString(Character.toLowerCase(fieldName
						.charAt(0))) + fieldName.substring(1);
				Object value = executeGetMethod(method, model);
				map.put(fieldName, value);
				map.put(fieldName.toLowerCase(), value);
				map.put(fieldName.toUpperCase(), value);
			}
		}
		return map;
	}

	/**
	 * List<E> ת List<Map<��������ԭ�������򴿴�д����Сд��,ֵ>>
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static <E> List<Map<String, Object>> model2Map(List<E> list)
			throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (Validator.isEmpty(list)) {
			return mapList;
		}
		for (int i = 0; list != null && i < list.size(); i++) {
			E e = list.get(i);
			Map<String, Object> map = model2Map(e);
			mapList.add(map);
		}

		return mapList;
	}

	/**
	 * ��ȡʵ���ĳ�����Ե�����ֵ
	 * 
	 * @param entity
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static <E> Object getValue(E entity, String name) throws Exception {
		Method getMethod = getGetMethodMapByEntityClass(entity.getClass()).get(
				name);
		if (getMethod != null) {
			return executeGetMethod(getMethod, entity);
		}
		return null;
	}

	/**
	 * ����ʵ���ĳ�����Ե�ֵ
	 * 
	 * @param model
	 * @param name
	 * @param value
	 * @throws Exception
	 */
	public static void setValue(Object model, String name, Object value)
			throws Exception {
		if (Validator.isEmpty(model) || Validator.isEmpty(name)) {
			return;
		}
		Class<?> clazz = model.getClass();
		Method setMethod = (Method) getSetMethodMapByEntityClass(clazz).get(
				name);
		if (setMethod != null) {
			executeSetMethod(setMethod, model, value);
		}
	}

	/**
	 * copy��������
	 * 
	 * @param source
	 * @param target
	 * @author LiuKun
	 * @created 2012-8-20 ����11:32:48
	 */
	public static void copyProperties(Object source, Object target) {
		if (source == null || target == null) {
			return;
		}
		try {
			org.springframework.beans.BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ������orig��ͬ��������ֵ���Ƶ�����dest��ͬ��������
	 * 
	 * @param dest
	 *            Ŀ�ĸ�ֵ����
	 * @param orig
	 *            Դͷȡֵ����
	 * @throws Exception
	 */
	public static void copyPropertiesForCustom(Object dest, Object orig)
			throws Exception {
		if (orig == null || dest == null) {
			return;
		}
		Class<?> origClass = orig.getClass();
		Class<?> destClass = dest.getClass();

		Map<String, Method> getMethodMap = getGetMethodMapByEntityClass(origClass);

		Method[] setMethods = destClass.getDeclaredMethods();
		AccessibleObject.setAccessible(setMethods, true);
		for (int i = 0; setMethods != null && i < setMethods.length; i++) {
			Method setMethod = setMethods[i];
			String setMethodName = setMethod.getName();
			if (setMethodName.startsWith("set")) {
				String getMethodName = "get" + setMethodName.substring(3);
				Method getMethod = getMethodMap.get(getMethodName);
				if (getMethod != null) {
					Object value = executeGetMethod(getMethod, orig);
					executeSetMethod(setMethod, dest, value);
				} else {
					getMethod = getMethodMap.get(getMethodName.toLowerCase());
					if (getMethod != null) {
						Object value = executeGetMethod(getMethod, orig);
						executeSetMethod(setMethod, dest, value);
					} else {
						getMethod = getMethodMap.get(getMethodName
								.toUpperCase());
						if (getMethod != null) {
							Object value = executeGetMethod(getMethod, orig);
							executeSetMethod(setMethod, dest, value);
						}
					}
				}
			}
		}

		setMethods = destClass.getMethods();
		AccessibleObject.setAccessible(setMethods, true);
		for (int i = 0; setMethods != null && i < setMethods.length; i++) {
			Method setMethod = setMethods[i];
			String setMethodName = setMethod.getName();
			if (setMethodName.startsWith("set")) {
				String getMethodName = "get" + setMethodName.substring(3);
				Method getMethod = getMethodMap.get(getMethodName);
				if (getMethod != null) {
					Object value = executeGetMethod(getMethod, orig);
					executeSetMethod(setMethod, dest, value);
				} else {
					getMethod = getMethodMap.get(getMethodName.toLowerCase());
					if (getMethod != null) {
						Object value = executeGetMethod(getMethod, orig);
						executeSetMethod(setMethod, dest, value);
					} else {
						getMethod = getMethodMap.get(getMethodName
								.toUpperCase());
						if (getMethod != null) {
							Object value = executeGetMethod(getMethod, orig);
							executeSetMethod(setMethod, dest, value);
						}
					}
				}
			}
		}

	}

}
