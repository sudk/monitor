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
 * 反射相关的帮助类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 上午9:46:18
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
	 * 序列号
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
	 * 执行get方法
	 * 
	 * @param setMethod
	 * @param obj
	 * @param args
	 * @author LiuKun
	 * @created 2012-8-20 上午9:47:09
	 */
	public static void executeSetMethod(Method setMethod, Object obj,
			Object... args) throws Exception {
		if (setMethod == null || obj == null) {
			return;
		}

		if (Validator.isNotEmpty(args)) {
			// set方法参数类型
			Class<?>[] argsClassArr = setMethod.getParameterTypes();
			Object[] newArgs = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					// 类型不一致
					if (!PackageUtil.equalsIgnorePackageClass(args[i]
							.getClass().getName(), argsClassArr[i].getName())) {
						if (PackageUtil.equalsIgnorePackageClass(
								String.class.getName(),
								argsClassArr[i].getName())) {// set参数类型为String类型
							newArgs[i] = ParseUtil.parseString(args[i]);
						} else if (PackageUtil.equalsIgnorePackageClass(
								Integer.class.getName(),
								argsClassArr[i].getName())) {// set参数类型为Integer类型
							newArgs[i] = ParseUtil.parseInteger(args[i]);
						} else if (PackageUtil.equalsIgnorePackageClass(
								Boolean.class.getName(),
								argsClassArr[i].getName())// set参数类型为Bolean类型
								&& PackageUtil.equalsIgnorePackageClass(
										String.class.getName(), args[i]
												.getClass().getName())// 传递值的类型为String类型
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

					} else {// 类型一致
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
	 * 执行set方法
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
	 * 根据类对象，获取该类的所有属性Map<属性名（原属性名或纯大写，或纯小写）,属性对象>
	 * 
	 * @param clazz
	 *            类对象
	 * @return Map<属性名（原属性名或纯大写，或纯小写）,属性对象>
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
	 * 一对get/set方法转换为一个虚拟属性 的 虚拟属性名称集合
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
			if (methodName.startsWith("get")) {// get方法
				getMethodNameMap.put(methodName, null);
			} else if (methodName.startsWith("set")) {// set方法
				setMethodNameMap.put(methodName, null);
			}
		}

		methods = clazz.getMethods();
		AccessibleObject.setAccessible(methods, true);
		for (int i = 0; methods != null && i < methods.length; i++) {
			Method method = methods[i];
			methodName = method.getName();
			if (methodName.startsWith("get")) {// get方法
				getMethodNameMap.put(methodName, null);
			} else if (methodName.startsWith("set")) {// set方法
				setMethodNameMap.put(methodName, null);
			}
		}

		for (Entry<String, String> entry : getMethodNameMap.entrySet()) {
			getMethodName = entry.getKey();
			setMethodName = "set" + getMethodName.substring(3);

			if (setMethodNameMap.containsKey(setMethodName)) {// get方法存在相对应得set方法，则可以构建一个虚拟属性
				fieldName = getMethodName.substring(3);
				fieldName = Character.toString(Character.toLowerCase(fieldName
						.charAt(0))) + fieldName.substring(1);
				fieldNameSet.add(fieldName);
			}
		}

		return fieldNameSet;
	}

	/**
	 * 根据类对象，获取该类的所有方法Map<方法名（原方法名或纯大写，或纯小写）,方法对象>
	 * 
	 * @param clazz
	 *            类对象
	 * @return Map<方法名（原方法名或纯大写，或纯小写）,方法对象>
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
	 * 根据类对象，获取该类的get方法Map<方法名（原方法名或纯大写，或纯小写）,方法对象>
	 * 
	 * @param clazz
	 *            类对象
	 * @return get方法Map<方法名（原方法名或纯大写，或纯小写）,方法对象>
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
	 * 根据类对象，获取该类的set方法Map<方法名（原方法名或纯大写，或纯小写）,方法对象>
	 * 
	 * @param clazz
	 *            类对象
	 * @return set方法Map<方法名（原方法名或纯大写，或纯小写）,方法对象>
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
	 * List<Map<String,Object>> 转 List<E>
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
	 * List<Map<String,Object>> 转 List<E> ( 解决 hibernate 中 列名 跟 属性名不一致的情况)
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
	 * List<Map<String,Object>> 转 List<E> ( 解决 map中的key名与实体的属性名不一致的情况)
	 * 
	 * @param list
	 * @param clazz
	 * @param convertMap
	 *            Map<map的key名,实体属性名>
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
	 * map转换为实体对象
	 * 
	 * @param map
	 *            Map<属性名（原属性名或纯大写，或纯小写）,值>
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
	 * map转换为实体对象( 解决 hibernate 中 列名 跟 属性名不一致的情况)
	 * 
	 * @param map
	 *            Map<属性名（原属性名或纯大写，或纯小写）,值>
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
	 * map转换为实体对象
	 * 
	 * @param map
	 *            Map<属性名（原属性名或纯大写，或纯小写）,值>
	 * @param clazz
	 * @param convertMap
	 *            Map<map的key名,实体属性名>
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
	 * 实体对象转换为Map<属性名（原属性名或纯大写，或纯小写）,值>
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
	 * List<E> 转 List<Map<属性名（原属性名或纯大写，或纯小写）,值>>
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
	 * 获取实体的某个属性的属性值
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
	 * 设置实体的某个属性的值
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
	 * copy对象属性
	 * 
	 * @param source
	 * @param target
	 * @author LiuKun
	 * @created 2012-8-20 上午11:32:48
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
	 * 将对象orig相同属性名的值复制到对象dest相同属性名中
	 * 
	 * @param dest
	 *            目的赋值对象
	 * @param orig
	 *            源头取值对象
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
