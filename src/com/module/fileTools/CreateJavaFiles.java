package com.module.fileTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 生成底层工具类
 * 
 * Description: 描述
 * 
 * @author zhangwu
 * @version 1.0
 * @created 2012-7-18 下午2:27:43
 */
public class CreateJavaFiles {
	public static void main(String args[]) throws Exception {

		// 文件存放的目录--生成文件的本机目录，需要用双反斜杠分割路径
		String filepath = "D:\\Users\\Administrator\\Workspaces\\MyEclipse10\\monitor\\src\\com\\module\\monitor\\";
		;

		// 代码的包路径
		String packagePath = "com.module.monitor";

		// 底层表名，用逗号分割，不允许有其他符号(包含空格)
		String tableName = "BUS_MONITOR_RESULT,BUS_MONITOR_TASK,BUS_MONITOR_THEME";

		String author = "zhangwu";
		String[] table = tableName.split(",");

		for (int i = 0; i < table.length; i++) {
			System.out.print("auto create file start~~~~~!");
			// creatModelFiles(filepath, table[i], "", packagePath, author);
			// creatHibernateFiles(filepath, table[i], "", packagePath, author);
			// creatPersistence(filepath, table[i], "", packagePath,author);
			// CreateServiceJn(filepath, table[i], "", packagePath, author);
			creatUFCModelFiles(filepath, table[i], "", packagePath, author);
			CreateUFCService(filepath, table[i], "", packagePath, author);
			//creatUFCViewFiles(filepath, table[i], "", packagePath, author);
			System.out.print("success~~~~~!");
		}
		System.out.print("END~~~~~!");

	}

	/**
	 * 生成hibernate的model文件 描述
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-23 上午11:20:20
	 */
	@SuppressWarnings("deprecation")
	public static void creatModelFiles(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {
		SimpleDateFormat fullFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		String nowDate = fullFormat.format(new Date());
		String sql = "select * from syscat.columns  where tabname='"
				+ tableName + "'";
		DB2.init();
		List<ColumnModel> columnsList = DB2.queryColumn(sql);
		String modelPath = filePath + "model";
		File file = new File(modelPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String headers = "package " + packagePath + ".model;\n";
		headers += "\n";

		String content = "public class " + modelName + " implements I"
				+ modelName + " {\n";// 包的路径，引入类
		content += "	// Fields\n";

		String fileds = "";// 字段内容
		String getSets = "\n";// getset内容
		getSets += "	/** default constructor */\n";
		getSets += "	public " + modelName + "() {\n";
		getSets += "	}\n";
		getSets += "\n";

		String end = "}\n";

		String interFiled = "";
		boolean isHaveDate = false;
		boolean isHavaBigDecimal = false;
		for (int i = 0; i < columnsList.size(); i++) {
			ColumnModel cModel = columnsList.get(i);
			String type = "";

			String[] colNames = cModel.getColname().toLowerCase().split("_");
			String colName = "";
			for (int x = 0; x < colNames.length; x++) {
				if (x == 0) {
					colName += colNames[x].substring(0, 1).toLowerCase()
							+ colNames[x].substring(1).toLowerCase();
				} else {
					colName += colNames[x].substring(0, 1).toUpperCase()
							+ colNames[x].substring(1).toLowerCase();
				}

			}

			if (cModel.getTypaname().equals("BIGINT")
					|| cModel.getTypaname().equals("INTEGER")
					|| cModel.getTypaname().equals("SMALLINT")) {
				type = "Integer";
			} else if (cModel.getTypaname().equals("CHARACTER")
					|| cModel.getTypaname().equals("CLOB")
					|| cModel.getTypaname().equals("LONG VARCHAR")
					|| cModel.getTypaname().equals("VARCHAR")) {
				type = "String";
			} else if (cModel.getTypaname().equals("DATE")
					|| cModel.getTypaname().equals("TIMESTAMP")) {
				type = "Date";
				isHaveDate = true;
			} else if (cModel.getTypaname().equals("DECIMAL")) {
				type = "Big_Decimal";
				isHavaBigDecimal = true;
			} else if (cModel.getTypaname().equals("DOUBLE")) {
				type = "Double";
			}
			fileds += "	private " + type + " " + colName + ";\n";

			interFiled += "String " + colName.toUpperCase() + " = \"" + colName
					+ "\" ;\n";

			getSets += "	public " + type + " get"
					+ colName.substring(0, 1).toUpperCase()
					+ colName.substring(1) + "() {\n";
			getSets += "		return this." + colName + ";\n";
			getSets += "	}\n";
			getSets += "	public void set"
					+ colName.substring(0, 1).toUpperCase()
					+ colName.substring(1) + "(" + type + " " + colName
					+ ") {\n";
			getSets += "				this." + colName + " = " + colName + ";\n";
			getSets += "	}\n";

		}
		if (isHavaBigDecimal) {
			headers += "import java.math.BigDecimal;\n";
		}
		if (isHaveDate) {
			headers += "import java.util.Date;\n";
		}
		headers += "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + nowDate + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n";
		String contents = headers + content + fileds + getSets + end;

		FileWriter fw = new FileWriter(modelPath + "\\" + modelName + ".java");
		fw.write("");// 重新写文件
		fw.append(contents);// 追加文件内容
		fw.close();

		String interContent = "package " + packagePath + ".model;\n" + "\n"
				+ "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + new Date() + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + "\n"
				+ "import com.component.Entity;\n" + "\n"
				+ "public interface I" + modelName + " extends Entity {\n"
				+ interFiled + "\n" + "}\n";

		FileWriter interFw = new FileWriter(modelPath + "\\I" + modelName
				+ ".java");
		interFw.write("");// 重新写文件
		interFw.append(interContent);// 追加文件内容
		interFw.close();
	}

	/**
	 * 生成hibernate配置文件 描述
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-18 下午2:32:15
	 */
	public static void creatHibernateFiles(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {
		String sql = "select * from syscat.columns  where tabname='"
				+ tableName + "'";
		DB2.init();
		List<ColumnModel> columnsList = DB2.queryColumn(sql);
		String modelPath = filePath + "model";
		File file = new File(modelPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String headers = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n"
				+ "\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n"
				+ "<!-- \n"
				+ "	Mapping file autogenerated by MyEclipse Persistence Tools\n"
				+ "-->\n" + "<hibernate-mapping>\n";
		String classString = "	<class		name=\"" + packagePath + ".model."
				+ modelName + "\"		table=\"" + tableName + "\" >\n";
		String pkString = "";

		String content = "";

		String end = "";

		for (int i = 0; i < columnsList.size(); i++) {
			ColumnModel cModel = columnsList.get(i);

			String[] colNames = cModel.getColname().toLowerCase().split("_");
			String colName = "";
			for (int x = 0; x < colNames.length; x++) {
				if (x == 0) {
					colName += colNames[x].substring(0, 1).toLowerCase()
							+ colNames[x].substring(1).toLowerCase();
				} else {
					colName += colNames[x].substring(0, 1).toUpperCase()
							+ colNames[x].substring(1).toLowerCase();
				}

			}

			String type = "";
			if (cModel.getTypaname().equals("BIGINT")
					|| cModel.getTypaname().equals("INTEGER")
					|| cModel.getTypaname().equals("SMALLINT")) {
				type = "Integer";
			} else if (cModel.getTypaname().equals("CHARACTER")
					|| cModel.getTypaname().equals("CLOB")
					|| cModel.getTypaname().equals("LONG VARCHAR")
					|| cModel.getTypaname().equals("VARCHAR")) {
				type = "String";
			} else if (cModel.getTypaname().equals("DATE")
					|| cModel.getTypaname().equals("TIMESTAMP")) {
				type = "Date";
			} else if (cModel.getTypaname().equals("DECIMAL")) {
				type = "Big_Decimal";
			} else if (cModel.getTypaname().equals("DOUBLE")) {
				type = "Double";
			}

			if (cModel.getKeyseq() != null && !cModel.getKeyseq().equals("")
					&& !seq.equals("")) {
				pkString += "		<id name=\"" + colName + "\" type=\""
						+ type.toLowerCase() + "\">\n" + "			<column name=\""
						+ cModel.getColname() + "\" />\n"
						+ "			<generator class=\"sequence\">\n"
						+ "				<param name=\"sequence\">\n" + "					" + seq
						+ "\n" + "				</param>\n" + "			</generator>\n"
						+ "		</id>\n";
			} else if (cModel.getKeyseq() != null
					&& !cModel.getKeyseq().equals("") && seq.equals("")) {
				pkString += "        <id name=\"" + colName + "\" type=\""
						+ type.toLowerCase() + "\">\n"
						+ "            <column name=\""
						+ cModel.getColname().toUpperCase() + "\" length=\""
						+ cModel.getLength() + "\" />\n" + "        </id>\n";
			} else if (cModel.getKeyseq() == null
					|| cModel.getKeyseq().equals("")) {
				content += "		<property name=\"" + colName + "\" type=\""
						+ type.toLowerCase() + "\">\n" + "			<column name=\""
						+ cModel.getColname().toUpperCase() + "\" length=\""
						+ cModel.getLength() + "\">\n" + "				<comment>"
						+ cModel.getRemarks() + "</comment>\n"
						+ "			</column>\n" + "		</property>	\n";
			}

		}
		end = "	</class>\n" + "</hibernate-mapping>\n";
		String contents = headers + classString + pkString + content + end;

		String path = modelPath + "\\" + modelName + ".hbm.xml";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				path), "UTF-8");
		out.write(contents);
		out.flush();
		out.close();
		// FileWriter fw = new FileWriter(modelPath + "\\" + modelName
		// + ".hbm.xml");
		// fw.write("");// 重新写文件
		// fw.append(contents);// 追加文件内容
		// fw.close();

	}

	/**
	 * 生成persistent文件 描述
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-23 上午11:21:26
	 */
	public static void creatPersistenceFile(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {
		String sql = "select * from syscat.columns  where tabname='"
				+ tableName + "'";
		DB2.init();
		List<ColumnModel> columnsList = DB2.queryColumn(sql);

		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String viewPath = filePath + "view";
		File file = new File(viewPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String viewmodelImplPath = filePath + "view\\viewmodel";
		file = new File(viewmodelImplPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String listenerImplPath = filePath + "view\\listener";
		file = new File(listenerImplPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		SimpleDateFormat fullFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		String nowDate = fullFormat.format(new Date());

		// finder
		@SuppressWarnings("deprecation")
		String finderHeader = "package " + packagePath + ".persistence;\n"
				+ "			\n" + "import java.util.Collection;\n"
				+ "import java.util.Iterator;\n" + "import java.util.List;\n"
				+ "import java.util.Map;\n" + "\n"
				+ "import org.hibernate.Criteria;\n"
				+ "import org.hibernate.criterion.Expression;\n"
				+ "import org.hibernate.criterion.Order;\n"
				+ "import org.hibernate.criterion.Projections;\n" + "\n" + "\n"
				+ "import " + packagePath + ".model." + modelName + ";\n"
				+ "import com.persistence.hibernate.BasePersistence;\n"
				+ "import com.persistence.hibernate.PageInfo;\n"
				+ "import com.persistence.hibernate.QueryConstant;\n"
				+ "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + nowDate + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + " \n"
				+ "public class " + modelName
				+ "Finder extends BasePersistence  {\n";

		String finderFileds = "\n" + "	// property constants\n";
		String columeArray = "\n\n	String[] columeArray = { ";
		//

		String pkType = "";
		for (int i = 0; i < columnsList.size(); i++) {
			ColumnModel cModel = columnsList.get(i);
			String type = "";
			if (cModel.getTypaname().equals("BIGINT")
					|| cModel.getTypaname().equals("INTEGER")
					|| cModel.getTypaname().equals("SMALLINT")) {
				type = "Integer";
			} else if (cModel.getTypaname().equals("CHARACTER")
					|| cModel.getTypaname().equals("CLOB")
					|| cModel.getTypaname().equals("LONG VARCHAR")
					|| cModel.getTypaname().equals("VARCHAR")) {
				type = "String";
			} else if (cModel.getTypaname().equals("DATE")
					|| cModel.getTypaname().equals("TIMESTAMP")) {
				type = "Date";
			} else if (cModel.getTypaname().equals("DECIMAL")) {
				type = "Big_Decimal";
			} else if (cModel.getTypaname().equals("DOUBLE")) {
				type = "Double";
			}

			if (cModel.getKeyseq() != null && !cModel.getKeyseq().equals("")) {
				pkType = type;
			}

			finderFileds += "	public static final String "
					+ cModel.getColname() + " = " + "\""
					+ cModel.getColname().toLowerCase() + "\";\n";
			if (i != columnsList.size() - 1) {
				columeArray += cModel.getColname() + ",";
			} else {
				columeArray += cModel.getColname() + "};\n";
			}

		}

		String finderMothedString = "	\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders) {\n"
				+ "		Criteria ca = createCriteria(params, operators);\n"
				+ "		Iterator iterOrders = orders.keySet().iterator();\n"
				+ "		while (iterOrders.hasNext()) {\n"
				+ "			String name = (String) iterOrders.next();\n"
				+ "			String order = (String) orders.get(name);\n"
				+ "			if (order.equals(QueryConstant.ORDER_ASC)) {\n"
				+ "				ca.addOrder(Order.asc(name));\n"
				+ "			}\n"
				+ "			if (order.equals(QueryConstant.ORDER_DESC)) {\n"
				+ "				ca.addOrder(Order.desc(name));\n"
				+ "			}\n"
				+ "		}\n"
				+ "		return ca.list();\n"
				+ "	}\n"
				+ "\n"
				+ "	\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @param page\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders,\n"
				+ "			PageInfo page) {\n"
				+ "		Criteria ca = createCriteria(params, operators);\n"
				+ "\n"
				+ "		int totalCount = ((Integer) ca.setProjection(Projections.rowCount())\n"
				+ "				.uniqueResult()).intValue();\n"
				+ "		ca.setProjection(null);\n"
				+ "\n"
				+ "		Iterator iterOrders = orders.keySet().iterator();\n"
				+ "		while (iterOrders.hasNext()) {\n"
				+ "			String name = (String) iterOrders.next();\n"
				+ "			String order = (String) orders.get(name);\n"
				+ "			if (order.equals(QueryConstant.ORDER_ASC)) {\n"
				+ "				ca.addOrder(Order.asc(name));\n"
				+ "			}\n"
				+ "			if (order.equals(QueryConstant.ORDER_DESC)) {\n"
				+ "				ca.addOrder(Order.desc(name));\n"
				+ "			}\n"
				+ "		}\n"
				+ "		ca.setMaxResults(page.getPageSize());\n"
				+ "		ca.setFirstResult((page.getPageNO() - 1) * page.getPageSize());\n"
				+ "		page.setTotalRecordNo(totalCount);\n"
				+ "\n"
				+ "		return ca.list();\n"
				+ "	}\n"
				+ "\n"
				+ "	private Criteria createCriteria(Map params, Map operators) {\n"
				+ "		Criteria ca = this.openSession()\n"
				+ "				.createCriteria("
				+ modelName
				+ ".class);\n"
				+ "		for (int i = 0; i < columeArray.length; i++) {\n"
				+ "			String key = columeArray[i];\n"
				+ "			Object value;\n"
				+ "			value = params.get(key);\n"
				+ "			if (value != null) {\n"
				+ "				if (operators.get(key) == null) {\n"
				+ "					ca = ca.add(Expression.eq(key, value));\n"
				+ "				} else if (operators.get(key).equals(QueryConstant.LIKE)) {\n"
				+ "					ca = ca.add(Expression.like(key, \"%\" + value + \"%\"));\n"
				+ "				} else if (operators.get(key).equals(QueryConstant.LESS_THAN)) {\n"
				+ "					ca = ca.add(Expression.lt(key, value));\n"
				+ "				} else if (operators.get(key).equals(\n"
				+ "						QueryConstant.LESS_OR_EQUAL)) {\n"
				+ "					ca = ca.add(Expression.le(key, value));\n"
				+ "				} else if (operators.get(key).equals(\n"
				+ "						QueryConstant.GREATER_OR_EQUAL)) {\n"
				+ "					ca = ca.add(Expression.ge(key, value));\n"
				+ "				} else if (operators.get(key)\n"
				+ "						.equals(QueryConstant.GREATER_THAN)) {\n"
				+ "					ca = ca.add(Expression.gt(key, value));\n"
				+ "				}else if(operators.get(key).equals(\"in\")){\n"
				+ "					ca = ca.add(Expression.in(key, (Collection)value));\n"
				+ "				} else {\n"
				+ "					ca = ca.add(Expression.eq(key, value));\n"
				+ "				}\n"
				+ "			}\n" + "		}\n" + "		return ca;\n" + "	}\n" + "}\n";
		String contents = finderHeader + finderFileds + columeArray
				+ finderMothedString;

		FileWriter fw = new FileWriter(viewPath + "\\" + modelName
				+ "Finder.java");
		fw.write("");// 重新写文件
		fw.append(contents);// 追加文件内容
		fw.close();

		@SuppressWarnings("deprecation")
		String iDaoContent = "package " + packagePath + ".persistence;\n"
				+ "\n" + "import " + packagePath + ".model." + modelName
				+ ";\n" + "\n" + "/**\n" + " * @author 作者:\n"
				+ " * @version 创建时间：" + new Date() + "\n" + " * 类说明\n"
				+ " */\n" + "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + new Date() + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + "\n"
				+ "public interface I" + modelName + "DAO {\n" + "\n"
				+ "	public  void save(" + modelName + " transientInstance);\n"
				+ "\n" + "	public  void update(" + modelName
				+ " transientInstance);\n" + "\n" + "	public  void delete("
				+ modelName + " persistentInstance);\n" + "\n" + "	public  "
				+ modelName + " getById(" + pkType + " Id);\n" + "\n" + "}";
		FileWriter fwIdao = new FileWriter(viewPath + "\\I" + modelName
				+ "DAO.java");
		fwIdao.write("");// 重新写文件
		fwIdao.append(iDaoContent);// 追加文件内容
		fwIdao.close();

		// dao文件
		String daoImpHeader = "package " + packagePath + ".persistence.impl;\n"
				+ "\n" + "import org.apache.commons.logging.Log;\n"
				+ "import org.apache.commons.logging.LogFactory;\n"
				+ "import org.hibernate.LockMode;\n" + "\n" + "import "
				+ packagePath + ".model." + modelName + ";\n" + "import "
				+ packagePath + ".persistence.I" + modelName + "DAO;\n"
				+ "import com.persistence.hibernate.BasePersistence;\n"
				+ "\n" + "\n" + "\n" + "public class " + modelName
				+ "DAO extends BasePersistence implements I" + modelName
				+ "DAO {\n"
				+ "	private static final Log log = LogFactory.getLog("
				+ modelName + "DAO.class);\n" + "	// property constants\n";

		String daoImpEnd = "\n" + "	protected void initDao() {\n"
				+ "		// do nothing\n" + "	}\n" + "\n" + "	\n" + "	/**     \n"
				+ "	 * @param transientInstance    \n" + "	 */\n" + "	\n"
				+ "	public void save("
				+ modelName
				+ " transientInstance) {\n"
				+ "		log.debug(\"saving "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().save(transientInstance);\n"
				+ "			log.debug(\"save successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"save failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param transientInstance    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	public void update("
				+ modelName
				+ " transientInstance) {\n"
				+ "		log.debug(\"saving "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().merge(transientInstance);\n"
				+ "			log.debug(\"save successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"save failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param persistentInstance    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	public void delete("
				+ modelName
				+ " persistentInstance) {\n"
				+ "		log.debug(\"deleting "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().delete(persistentInstance);\n"
				+ "			log.debug(\"delete successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"delete failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param id\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	public "
				+ modelName
				+ " getById("
				+ pkType
				+ " Id) {\n"
				+ "		log.debug(\"getting "
				+ modelName
				+ " instance with id: \" + Id);\n"
				+ "		try {\n"
				+ "			"
				+ modelName
				+ " instance = ("
				+ modelName
				+ ") this.openSession().get(\n"
				+ "					\""
				+ packagePath
				+ ".model."
				+ modelName
				+ "\", Id);\n"
				+ "			return instance;\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"get failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	\n"
				+ "\n"
				+ "	public "
				+ modelName
				+ " merge("
				+ modelName
				+ " detachedInstance) {\n"
				+ "		log.debug(\"merging "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			"
				+ modelName
				+ " result = ("
				+ modelName
				+ ") this.openSession().merge(\n"
				+ "					detachedInstance);\n"
				+ "			log.debug(\"merge successful\");\n"
				+ "			return result;\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"merge failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	public void attachDirty("
				+ modelName
				+ " instance) {\n"
				+ "		log.debug(\"attaching dirty "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().saveOrUpdate(instance);\n"
				+ "			log.debug(\"attach successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"attach failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	public void attachClean("
				+ modelName
				+ " instance) {\n"
				+ "		log.debug(\"attaching clean "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().lock(instance, LockMode.NONE);\n"
				+ "			log.debug(\"attach successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"attach failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n" + "	}\n" + "\n" + "}";

		String daoContent = daoImpHeader + finderFileds + daoImpEnd;
		FileWriter fwdao = new FileWriter(viewPath + "\\" + modelName
				+ "DAO.java");
		fwdao.write("");// 重新写文件
		fwdao.append(daoContent);// 追加文件内容
		fwdao.close();

		// IService文件生成
		String serPath = filePath + "service";
		file = new File(serPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String serImplPath = filePath + "service\\impl";
		file = new File(serImplPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		@SuppressWarnings("deprecation")
		String iserviceContent = "package "
				+ packagePath
				+ ".service;\n"
				+ "\n"
				+ "import java.util.List;\n"
				+ "import java.util.Map;\n"
				+ "\n"
				+ "import "
				+ packagePath
				+ ".model."
				+ modelName
				+ ";\n"
				+ "import com.persistence.hibernate.PageInfo;\n"
				+ "\n"
				+ "/**\n"
				+ " * @author 作者:"
				+ author
				+ "\n"
				+ " * @version 创建时间："
				+ new Date()
				+ "\n"
				+ " * 类说明\n"
				+ " */\n"
				+ "\n"
				+ "/**\n"
				+ " * \n"
				+ " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n"
				+ " * @author "
				+ author
				+ "\n"
				+ " * @version 1.0 "
				+ new Date()
				+ "\n"
				+ " * @see 工具提供—张武\n"
				+ " *\n"
				+ " */\n"
				+ "\n"
				+ "public interface I"
				+ modelName
				+ "Service {\n"
				+ "	public  void save("
				+ modelName
				+ " transientInstance);\n"
				+ "\n"
				+ "	public  void update("
				+ modelName
				+ " transientInstance);\n"
				+ "\n"
				+ "	public  void delete("
				+ modelName
				+ " persistentInstance);\n"
				+ "\n"
				+ "	public  "
				+ modelName
				+ " getById("
				+ pkType
				+ " Id);\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "\n"
				+ "	public  List findByRandomParam(Map params, Map operators, Map orders);\n"
				+ "\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @param page\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "\n"
				+ "	public  List findByRandomParam(Map params, Map operators,\n"
				+ "			Map orders, PageInfo page);\n" + "	\n" + "}";
		FileWriter fwIser = new FileWriter(serPath + "\\I" + modelName
				+ "Service.java");
		fwIser.write("");// 重新写文件
		fwIser.append(iserviceContent);// 追加文件内容
		fwIser.close();

		// service文件
		@SuppressWarnings("deprecation")
		String serviceContent = "package "
				+ packagePath
				+ ".service.impl;\n"
				+ "\n"
				+ "import java.util.HashMap;\n"
				+ "import java.util.List;\n"
				+ "import java.util.Map;\n"
				+ "\n"
				+ "import "
				+ packagePath
				+ ".model."
				+ modelName
				+ ";\n"
				+ "import "
				+ packagePath
				+ ".persistence."
				+ modelName
				+ "Finder;\n"
				+ "import "
				+ packagePath
				+ ".persistence.I"
				+ modelName
				+ "DAO;\n"
				+ "import "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service;\n"
				+ "import com.persistence.hibernate.PageInfo;\n"
				+ "\n"
				+ "/**\n"
				+ " * \n"
				+ " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n"
				+ " * @author "
				+ author
				+ "\n"
				+ " * @version 1.0 "
				+ new Date()
				+ "\n"
				+ " * @see 工具提供—张武\n"
				+ " *\n"
				+ " */\n"
				+ "public class "
				+ modelName
				+ "Service implements I"
				+ modelName
				+ "Service{\n"
				+ "\n"
				+ "	"
				+ modelName
				+ "Finder finder;\n"
				+ "	I"
				+ modelName
				+ "DAO persistence;\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 返回 finder 的值\n"
				+ "	 * \n"
				+ "	 * @return finder\n"
				+ "	 */\n"
				+ "\n"
				+ "	public "
				+ modelName
				+ "Finder getFinder() {\n"
				+ "		return finder;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 设置 finder 的值\n"
				+ "	 * \n"
				+ "	 * @param finder\n"
				+ "	 */\n"
				+ "	public void setFinder("
				+ modelName
				+ "Finder finder) {\n"
				+ "		this.finder = finder;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 返回 persistence 的值\n"
				+ "	 * \n"
				+ "	 * @return persistence\n"
				+ "	 */\n"
				+ "\n"
				+ "	public I"
				+ modelName
				+ "DAO getPersistence() {\n"
				+ "		return persistence;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 设置 persistence 的值\n"
				+ "	 * \n"
				+ "	 * @param persistence\n"
				+ "	 */\n"
				+ "	public void setPersistence(I"
				+ modelName
				+ "DAO persistence) {\n"
				+ "		this.persistence = persistence;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * @param persistentInstance\n"
				+ "	 * @see "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service#delete("
				+ packagePath
				+ ".model."
				+ modelName
				+ ")\n"
				+ "	 */\n"
				+ "\n"
				+ "	public void delete("
				+ modelName
				+ " persistentInstance) {\n"
				+ "		this.persistence.delete(persistentInstance);\n"
				+ "\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @return\n"
				+ "	 * @see "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service#findByRandomParam(java.util.Map,\n"
				+ "	 *      java.util.Map, java.util.Map)\n"
				+ "	 */\n"
				+ "\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders) {\n"
				+ "		// TODO Auto-generated method stub\n"
				+ "		return this.finder.findByRandomParam(params, operators, orders);\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @param page\n"
				+ "	 * @return\n"
				+ "	 * @see "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service#findByRandomParam(java.util.Map,\n"
				+ "	 *      java.util.Map, java.util.Map,\n"
				+ "	 *      com.persistence.hibernate.PageInfo)\n"
				+ "	 */\n"
				+ "\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders,\n"
				+ "			PageInfo page) {\n"
				+ "		// TODO Auto-generated method stub\n"
				+ "		return this.finder.findByRandomParam(params, operators, orders, page);\n"
				+ "	}\n" + "\n" + "	/**\n" + "	 * @param id\n"
				+ "	 * @return\n" + "	 * @see " + packagePath + ".service.I"
				+ modelName + "Service#getById(" + pkType + ")\n" + "	 */\n"
				+ "\n" + "	public " + modelName + " getById(" + pkType
				+ " Id) {\n" + "\n"
				+ "		return this.persistence.getById(Id);\n" + "	}\n" + "\n"
				+ "	/**\n" + "	 * @param transientInstance\n" + "	 * @see "
				+ packagePath + ".service.I" + modelName + "Service#save("
				+ packagePath + ".model." + modelName + ")\n" + "	 */\n" + "\n"
				+ "	public void save(" + modelName + " transientInstance) {\n"
				+ "		this.persistence.save(transientInstance);\n" + "\n"
				+ "	}\n" + "\n" + "	/**\n" + "	 * @param transientInstance\n"
				+ "	 * @see " + packagePath + ".service.I" + modelName
				+ "Service#update(" + packagePath + ".model." + modelName
				+ ")\n" + "	 */\n" + "\n" + "	public void update(" + modelName
				+ " transientInstance) {\n"
				+ "		this.persistence.update(transientInstance);\n" + "\n"
				+ "	}\n" + "}\n";

		FileWriter fwSer = new FileWriter(serImplPath + "\\" + modelName
				+ "Service.java");
		fwSer.write("");// 重新写文件
		fwSer.append(serviceContent);// 追加文件内容
		fwSer.close();

		// spring文件生成
		String springPath = filePath + "spring";
		file = new File(springPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String springHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n"
				+ "<!-- spring bean 配置文件 "
				+ author
				+ " -->\n"
				+ "<beans default-autowire=\"byName\" default-lazy-init=\"false\">\n"
				+ "\n";

		String springContent = "	    <!-- " + author + " " + modelName
				+ "-->\n" + "	<bean id=\"" + modelName + "Finder\"\n"
				+ "		class=\"" + packagePath + ".persistence." + modelName
				+ "Finder\">\n" + "	</bean>\n" + "	<bean id=\"" + modelName
				+ "DAO\"\n" + "		class=\"" + packagePath + ".persistence.impl."
				+ modelName + "DAO\">\n" + "	</bean>\n" + "	<bean id=\""
				+ modelName + "Service\"\n" + "		class=\"" + packagePath
				+ ".service.impl." + modelName + "Service\">\n"
				+ "		<property name=\"persistence\">\n" + "			<ref bean=\""
				+ modelName + "DAO\"></ref>\n" + "		</property>\n"
				+ "		<property name=\"finder\">\n" + "			<ref bean=\""
				+ modelName + "Finder\"></ref>\n" + "		</property>\n"
				+ "	</bean>\n";

		String springEnd = "</beans>";

		FileWriter fwSpr = new FileWriter(springPath + "\\" + modelName
				+ "-spring.xml");
		fwSpr.write("");// 重新写文件
		fwSpr.append(springHeader + springContent + springEnd);// 追加文件内容
		fwSpr.close();
	}

	/**
	 * 生成persistent文件 描述 生成persistent文件
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-23 上午11:22:08
	 */
	public static void creatPersistence(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {
		String sql = "select * from syscat.columns  where tabname='"
				+ tableName + "'";
		DB2.init();
		List<ColumnModel> columnsList = DB2.queryColumn(sql);

		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String perPath = filePath + "persistence";
		File file = new File(perPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String perImplPath = filePath + "persistence\\impl";
		File fileimpl = new File(perImplPath);
		if (!fileimpl.exists()) {
			fileimpl.mkdirs();
		}

		// finder
		@SuppressWarnings("deprecation")
		String finderHeader = "package " + packagePath + ".persistence;\n"
				+ "			\n" + "import java.util.Collection;\n"
				+ "import java.util.Iterator;\n" + "import java.util.List;\n"
				+ "import java.util.Map;\n" + "\n"
				+ "import org.hibernate.Criteria;\n"
				+ "import org.hibernate.criterion.Expression;\n"
				+ "import org.hibernate.criterion.Order;\n"
				+ "import org.hibernate.criterion.Projections;\n" + "\n" + "\n"
				+ "import " + packagePath + ".model." + modelName + ";\n"
				+ "import com.persistence.hibernate.BasePersistence;\n"
				+ "import com.persistence.hibernate.PageInfo;\n"
				+ "import com.persistence.hibernate.QueryConstant;\n"
				+ "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + new Date() + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + " \n"
				+ "public class " + modelName
				+ "Finder extends BasePersistence  {\n";

		String finderFileds = "\n" + "	// property constants\n";
		String columeArray = "\n\n	String[] columeArray = { ";
		//

		String pkType = "";
		for (int i = 0; i < columnsList.size(); i++) {
			ColumnModel cModel = columnsList.get(i);
			String type = "";
			if (cModel.getTypaname().equals("BIGINT")
					|| cModel.getTypaname().equals("INTEGER")
					|| cModel.getTypaname().equals("SMALLINT")) {
				type = "Integer";
			} else if (cModel.getTypaname().equals("CHARACTER")
					|| cModel.getTypaname().equals("CLOB")
					|| cModel.getTypaname().equals("LONG VARCHAR")
					|| cModel.getTypaname().equals("VARCHAR")) {
				type = "String";
			} else if (cModel.getTypaname().equals("DATE")
					|| cModel.getTypaname().equals("TIMESTAMP")) {
				type = "Date";
			} else if (cModel.getTypaname().equals("DECIMAL")) {
				type = "Big_Decimal";
			} else if (cModel.getTypaname().equals("DOUBLE")) {
				type = "Double";
			}

			if (cModel.getKeyseq() != null && !cModel.getKeyseq().equals("")) {
				pkType = type;
			}

			finderFileds += "	public static final String "
					+ cModel.getColname() + " = " + "\""
					+ cModel.getColname().toLowerCase() + "\";\n";
			if (i != columnsList.size() - 1) {
				columeArray += cModel.getColname() + ",";
			} else {
				columeArray += cModel.getColname() + "};\n";
			}

		}

		String finderMothedString = "	\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders) {\n"
				+ "		Criteria ca = createCriteria(params, operators);\n"
				+ "		Iterator iterOrders = orders.keySet().iterator();\n"
				+ "		while (iterOrders.hasNext()) {\n"
				+ "			String name = (String) iterOrders.next();\n"
				+ "			String order = (String) orders.get(name);\n"
				+ "			if (order.equals(QueryConstant.ORDER_ASC)) {\n"
				+ "				ca.addOrder(Order.asc(name));\n"
				+ "			}\n"
				+ "			if (order.equals(QueryConstant.ORDER_DESC)) {\n"
				+ "				ca.addOrder(Order.desc(name));\n"
				+ "			}\n"
				+ "		}\n"
				+ "		return ca.list();\n"
				+ "	}\n"
				+ "\n"
				+ "	\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @param page\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders,\n"
				+ "			PageInfo page) {\n"
				+ "		Criteria ca = createCriteria(params, operators);\n"
				+ "\n"
				+ "		int totalCount = ((Integer) ca.setProjection(Projections.rowCount())\n"
				+ "				.uniqueResult()).intValue();\n"
				+ "		ca.setProjection(null);\n"
				+ "\n"
				+ "		Iterator iterOrders = orders.keySet().iterator();\n"
				+ "		while (iterOrders.hasNext()) {\n"
				+ "			String name = (String) iterOrders.next();\n"
				+ "			String order = (String) orders.get(name);\n"
				+ "			if (order.equals(QueryConstant.ORDER_ASC)) {\n"
				+ "				ca.addOrder(Order.asc(name));\n"
				+ "			}\n"
				+ "			if (order.equals(QueryConstant.ORDER_DESC)) {\n"
				+ "				ca.addOrder(Order.desc(name));\n"
				+ "			}\n"
				+ "		}\n"
				+ "		ca.setMaxResults(page.getPageSize());\n"
				+ "		ca.setFirstResult((page.getPageNO() - 1) * page.getPageSize());\n"
				+ "		page.setTotalRecordNo(totalCount);\n"
				+ "\n"
				+ "		return ca.list();\n"
				+ "	}\n"
				+ "\n"
				+ "	private Criteria createCriteria(Map params, Map operators) {\n"
				+ "		Criteria ca = this.openSession()\n"
				+ "				.createCriteria("
				+ modelName
				+ ".class);\n"
				+ "		for (int i = 0; i < columeArray.length; i++) {\n"
				+ "			String key = columeArray[i];\n"
				+ "			Object value;\n"
				+ "			value = params.get(key);\n"
				+ "			if (value != null) {\n"
				+ "				if (operators.get(key) == null) {\n"
				+ "					ca = ca.add(Expression.eq(key, value));\n"
				+ "				} else if (operators.get(key).equals(QueryConstant.LIKE)) {\n"
				+ "					ca = ca.add(Expression.like(key, \"%\" + value + \"%\"));\n"
				+ "				} else if (operators.get(key).equals(QueryConstant.LESS_THAN)) {\n"
				+ "					ca = ca.add(Expression.lt(key, value));\n"
				+ "				} else if (operators.get(key).equals(\n"
				+ "						QueryConstant.LESS_OR_EQUAL)) {\n"
				+ "					ca = ca.add(Expression.le(key, value));\n"
				+ "				} else if (operators.get(key).equals(\n"
				+ "						QueryConstant.GREATER_OR_EQUAL)) {\n"
				+ "					ca = ca.add(Expression.ge(key, value));\n"
				+ "				} else if (operators.get(key)\n"
				+ "						.equals(QueryConstant.GREATER_THAN)) {\n"
				+ "					ca = ca.add(Expression.gt(key, value));\n"
				+ "				}else if(operators.get(key).equals(\"in\")){\n"
				+ "					ca = ca.add(Expression.in(key, (Collection)value));\n"
				+ "				} else {\n"
				+ "					ca = ca.add(Expression.eq(key, value));\n"
				+ "				}\n"
				+ "			}\n" + "		}\n" + "		return ca;\n" + "	}\n" + "}\n";
		String contents = finderHeader + finderFileds + columeArray
				+ finderMothedString;

		FileWriter fw = new FileWriter(perPath + "\\" + modelName
				+ "Finder.java");
		fw.write("");// 重新写文件
		fw.append(contents);// 追加文件内容
		fw.close();

		@SuppressWarnings("deprecation")
		String iDaoContent = "package " + packagePath + ".persistence;\n"
				+ "\n" + "import " + packagePath + ".model." + modelName
				+ ";\n" + "\n" + "/**\n" + " * @author 作者:\n"
				+ " * @version 创建时间：" + new Date() + "\n" + " * 类说明\n"
				+ " */\n" + "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + new Date() + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + "\n"
				+ "public interface I" + modelName + "DAO {\n" + "\n"
				+ "	public  void save(" + modelName + " transientInstance);\n"
				+ "\n" + "	public  void update(" + modelName
				+ " transientInstance);\n" + "\n" + "	public  void delete("
				+ modelName + " persistentInstance);\n" + "\n" + "	public  "
				+ modelName + " getById(" + pkType + " Id);\n" + "\n" + "}";
		FileWriter fwIdao = new FileWriter(perPath + "\\I" + modelName
				+ "DAO.java");
		fwIdao.write("");// 重新写文件
		fwIdao.append(iDaoContent);// 追加文件内容
		fwIdao.close();

		// dao文件
		String daoImpHeader = "package " + packagePath + ".persistence.impl;\n"
				+ "\n" + "import org.apache.commons.logging.Log;\n"
				+ "import org.apache.commons.logging.LogFactory;\n"
				+ "import org.hibernate.LockMode;\n" + "\n" + "import "
				+ packagePath + ".model." + modelName + ";\n" + "import "
				+ packagePath + ".persistence.I" + modelName + "DAO;\n"
				+ "import com.persistence.hibernate.BasePersistence;\n"
				+ "\n" + "\n" + "\n" + "public class " + modelName
				+ "DAO extends BasePersistence implements I" + modelName
				+ "DAO {\n"
				+ "	private static final Log log = LogFactory.getLog("
				+ modelName + "DAO.class);\n" + "	// property constants\n";

		String daoImpEnd = "\n" + "	protected void initDao() {\n"
				+ "		// do nothing\n" + "	}\n" + "\n" + "	\n" + "	/**     \n"
				+ "	 * @param transientInstance    \n" + "	 */\n" + "	\n"
				+ "	public void save("
				+ modelName
				+ " transientInstance) {\n"
				+ "		log.debug(\"saving "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().save(transientInstance);\n"
				+ "			log.debug(\"save successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"save failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param transientInstance    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	public void update("
				+ modelName
				+ " transientInstance) {\n"
				+ "		log.debug(\"saving "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().merge(transientInstance);\n"
				+ "			log.debug(\"save successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"save failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param persistentInstance    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	public void delete("
				+ modelName
				+ " persistentInstance) {\n"
				+ "		log.debug(\"deleting "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().delete(persistentInstance);\n"
				+ "			log.debug(\"delete successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"delete failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	\n"
				+ "	/**     \n"
				+ "	 * @param id\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "	\n"
				+ "	public "
				+ modelName
				+ " getById("
				+ pkType
				+ " Id) {\n"
				+ "		log.debug(\"getting "
				+ modelName
				+ " instance with id: \" + Id);\n"
				+ "		try {\n"
				+ "			"
				+ modelName
				+ " instance = ("
				+ modelName
				+ ") this.openSession().get(\n"
				+ "					\""
				+ packagePath
				+ ".model."
				+ modelName
				+ "\", Id);\n"
				+ "			return instance;\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"get failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	\n"
				+ "\n"
				+ "	public "
				+ modelName
				+ " merge("
				+ modelName
				+ " detachedInstance) {\n"
				+ "		log.debug(\"merging "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			"
				+ modelName
				+ " result = ("
				+ modelName
				+ ") this.openSession().merge(\n"
				+ "					detachedInstance);\n"
				+ "			log.debug(\"merge successful\");\n"
				+ "			return result;\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"merge failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	public void attachDirty("
				+ modelName
				+ " instance) {\n"
				+ "		log.debug(\"attaching dirty "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().saveOrUpdate(instance);\n"
				+ "			log.debug(\"attach successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"attach failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n"
				+ "	}\n"
				+ "\n"
				+ "	public void attachClean("
				+ modelName
				+ " instance) {\n"
				+ "		log.debug(\"attaching clean "
				+ modelName
				+ " instance\");\n"
				+ "		try {\n"
				+ "			this.openSession().lock(instance, LockMode.NONE);\n"
				+ "			log.debug(\"attach successful\");\n"
				+ "		} catch (RuntimeException re) {\n"
				+ "			log.error(\"attach failed\", re);\n"
				+ "			throw re;\n"
				+ "		}\n" + "	}\n" + "\n" + "}";

		String daoContent = daoImpHeader + finderFileds + daoImpEnd;
		FileWriter fwdao = new FileWriter(perImplPath + "\\" + modelName
				+ "DAO.java");
		fwdao.write("");// 重新写文件
		fwdao.append(daoContent);// 追加文件内容
		fwdao.close();

		// IService文件生成
		String serPath = filePath + "service";
		file = new File(serPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String serImplPath = filePath + "service\\impl";
		fileimpl = new File(serImplPath);
		if (!fileimpl.exists()) {
			fileimpl.mkdirs();
		}

		@SuppressWarnings("deprecation")
		String iserviceContent = "package "
				+ packagePath
				+ ".service;\n"
				+ "\n"
				+ "import java.util.List;\n"
				+ "import java.util.Map;\n"
				+ "\n"
				+ "import "
				+ packagePath
				+ ".model."
				+ modelName
				+ ";\n"
				+ "import com.persistence.hibernate.PageInfo;\n"
				+ "\n"
				+ "/**\n"
				+ " * @author 作者:"
				+ author
				+ "\n"
				+ " * @version 创建时间："
				+ new Date()
				+ "\n"
				+ " * 类说明\n"
				+ " */\n"
				+ "\n"
				+ "/**\n"
				+ " * \n"
				+ " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n"
				+ " * @author "
				+ author
				+ "\n"
				+ " * @version 1.0 "
				+ new Date()
				+ "\n"
				+ " * @see 工具提供—张武\n"
				+ " *\n"
				+ " */\n"
				+ "\n"
				+ "public interface I"
				+ modelName
				+ "Service {\n"
				+ "	public  void save("
				+ modelName
				+ " transientInstance);\n"
				+ "\n"
				+ "	public  void update("
				+ modelName
				+ " transientInstance);\n"
				+ "\n"
				+ "	public  void delete("
				+ modelName
				+ " persistentInstance);\n"
				+ "\n"
				+ "	public  "
				+ modelName
				+ " getById("
				+ pkType
				+ " Id);\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "\n"
				+ "	public  List findByRandomParam(Map params, Map operators, Map orders);\n"
				+ "\n"
				+ "	/**     \n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @param page\n"
				+ "	 * @return    \n"
				+ "	 */\n"
				+ "\n"
				+ "	public  List findByRandomParam(Map params, Map operators,\n"
				+ "			Map orders, PageInfo page);\n" + "	\n" + "}";
		FileWriter fwIser = new FileWriter(serPath + "\\I" + modelName
				+ "Service.java");
		fwIser.write("");// 重新写文件
		fwIser.append(iserviceContent);// 追加文件内容
		fwIser.close();

		// service文件
		@SuppressWarnings("deprecation")
		String serviceContent = "package "
				+ packagePath
				+ ".service.impl;\n"
				+ "\n"
				+ "import java.util.HashMap;\n"
				+ "import java.util.List;\n"
				+ "import java.util.Map;\n"
				+ "\n"
				+ "import "
				+ packagePath
				+ ".model."
				+ modelName
				+ ";\n"
				+ "import "
				+ packagePath
				+ ".persistence."
				+ modelName
				+ "Finder;\n"
				+ "import "
				+ packagePath
				+ ".persistence.I"
				+ modelName
				+ "DAO;\n"
				+ "import "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service;\n"
				+ "import com.persistence.hibernate.PageInfo;\n"
				+ "\n"
				+ "/**\n"
				+ " * \n"
				+ " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n"
				+ " * @author "
				+ author
				+ "\n"
				+ " * @version 1.0 "
				+ new Date()
				+ "\n"
				+ " * @see 工具提供—张武\n"
				+ " *\n"
				+ " */\n"
				+ "public class "
				+ modelName
				+ "Service implements I"
				+ modelName
				+ "Service{\n"
				+ "\n"
				+ "	"
				+ modelName
				+ "Finder finder;\n"
				+ "	I"
				+ modelName
				+ "DAO persistence;\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 返回 finder 的值\n"
				+ "	 * \n"
				+ "	 * @return finder\n"
				+ "	 */\n"
				+ "\n"
				+ "	public "
				+ modelName
				+ "Finder getFinder() {\n"
				+ "		return finder;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 设置 finder 的值\n"
				+ "	 * \n"
				+ "	 * @param finder\n"
				+ "	 */\n"
				+ "	public void setFinder("
				+ modelName
				+ "Finder finder) {\n"
				+ "		this.finder = finder;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 返回 persistence 的值\n"
				+ "	 * \n"
				+ "	 * @return persistence\n"
				+ "	 */\n"
				+ "\n"
				+ "	public I"
				+ modelName
				+ "DAO getPersistence() {\n"
				+ "		return persistence;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * 设置 persistence 的值\n"
				+ "	 * \n"
				+ "	 * @param persistence\n"
				+ "	 */\n"
				+ "	public void setPersistence(I"
				+ modelName
				+ "DAO persistence) {\n"
				+ "		this.persistence = persistence;\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * @param persistentInstance\n"
				+ "	 * @see "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service#delete("
				+ packagePath
				+ ".model."
				+ modelName
				+ ")\n"
				+ "	 */\n"
				+ "\n"
				+ "	public void delete("
				+ modelName
				+ " persistentInstance) {\n"
				+ "		this.persistence.delete(persistentInstance);\n"
				+ "\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @return\n"
				+ "	 * @see "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service#findByRandomParam(java.util.Map,\n"
				+ "	 *      java.util.Map, java.util.Map)\n"
				+ "	 */\n"
				+ "\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders) {\n"
				+ "		// TODO Auto-generated method stub\n"
				+ "		return this.finder.findByRandomParam(params, operators, orders);\n"
				+ "	}\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * @param params\n"
				+ "	 * @param operators\n"
				+ "	 * @param orders\n"
				+ "	 * @param page\n"
				+ "	 * @return\n"
				+ "	 * @see "
				+ packagePath
				+ ".service.I"
				+ modelName
				+ "Service#findByRandomParam(java.util.Map,\n"
				+ "	 *      java.util.Map, java.util.Map,\n"
				+ "	 *      com.persistence.hibernate.PageInfo)\n"
				+ "	 */\n"
				+ "\n"
				+ "	public List findByRandomParam(Map params, Map operators, Map orders,\n"
				+ "			PageInfo page) {\n"
				+ "		// TODO Auto-generated method stub\n"
				+ "		return this.finder.findByRandomParam(params, operators, orders, page);\n"
				+ "	}\n" + "\n" + "	/**\n" + "	 * @param id\n"
				+ "	 * @return\n" + "	 * @see " + packagePath + ".service.I"
				+ modelName + "Service#getById(" + pkType + ")\n" + "	 */\n"
				+ "\n" + "	public " + modelName + " getById(" + pkType
				+ " Id) {\n" + "\n"
				+ "		return this.persistence.getById(Id);\n" + "	}\n" + "\n"
				+ "	/**\n" + "	 * @param transientInstance\n" + "	 * @see "
				+ packagePath + ".service.I" + modelName + "Service#save("
				+ packagePath + ".model." + modelName + ")\n" + "	 */\n" + "\n"
				+ "	public void save(" + modelName + " transientInstance) {\n"
				+ "		this.persistence.save(transientInstance);\n" + "\n"
				+ "	}\n" + "\n" + "	/**\n" + "	 * @param transientInstance\n"
				+ "	 * @see " + packagePath + ".service.I" + modelName
				+ "Service#update(" + packagePath + ".model." + modelName
				+ ")\n" + "	 */\n" + "\n" + "	public void update(" + modelName
				+ " transientInstance) {\n"
				+ "		this.persistence.update(transientInstance);\n" + "\n"
				+ "	}\n" + "}\n";

		FileWriter fwSer = new FileWriter(serImplPath + "\\" + modelName
				+ "Service.java");
		fwSer.write("");// 重新写文件
		fwSer.append(serviceContent);// 追加文件内容
		fwSer.close();

		// spring文件生成
		String springPath = filePath + "spring";
		file = new File(springPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String springHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n"
				+ "<!-- spring bean config "
				+ author
				+ " -->\n"
				+ "<beans default-autowire=\"byName\" default-lazy-init=\"false\">\n"
				+ "\n";

		String springContent = "	    <!-- " + author + " " + modelName
				+ "-->\n" + "	<bean id=\"" + modelName + "Finder\"\n"
				+ "		class=\"" + packagePath + ".persistence." + modelName
				+ "Finder\">\n" + "	</bean>\n" + "	<bean id=\"" + modelName
				+ "DAO\"\n" + "		class=\"" + packagePath + ".persistence.impl."
				+ modelName + "DAO\">\n" + "	</bean>\n" + "	<bean id=\""
				+ modelName + "Service\"\n" + "		class=\"" + packagePath
				+ ".service.impl." + modelName + "Service\">\n"
				+ "		<property name=\"persistence\">\n" + "			<ref bean=\""
				+ modelName + "DAO\"></ref>\n" + "		</property>\n"
				+ "		<property name=\"finder\">\n" + "			<ref bean=\""
				+ modelName + "Finder\"></ref>\n" + "		</property>\n"
				+ "	</bean>\n";

		String springEnd = "</beans>";

		FileWriter fwSpr = new FileWriter(springPath + "\\" + modelName
				+ "-spring.xml");
		fwSpr.write("");// 重新写文件
		fwSpr.append(springHeader + springContent + springEnd);// 追加文件内容
		fwSpr.close();
	}

	/**
	 * 生成service文件（潍坊项目） 描述
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-23 上午11:22:34
	 */
	public static void CreateServiceJn(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {

		SimpleDateFormat fullFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		String nowDate = fullFormat.format(new Date());

		String sql = "select * from syscat.columns  where tabname='"
				+ tableName + "'";
		DB2.init();
		List<ColumnModel> columnsList = DB2.queryColumn(sql);
		String keytype = "";
		for (int u = 0; u < columnsList.size(); u++) {
			ColumnModel cc = columnsList.get(u);
			if (cc.getKeyseq() != null && !cc.getKeyseq().equals("")) {
				keytype = cc.getTypaname();

				if (cc.getTypaname().equals("BIGINT")
						|| cc.getTypaname().equals("INTEGER")
						|| cc.getTypaname().equals("SMALLINT")) {
					keytype = "Integer";
				} else if (cc.getTypaname().equals("CHARACTER")
						|| cc.getTypaname().equals("CLOB")
						|| cc.getTypaname().equals("LONG VARCHAR")
						|| cc.getTypaname().equals("VARCHAR")) {
					keytype = "String";
				} else if (cc.getTypaname().equals("DATE")
						|| cc.getTypaname().equals("TIMESTAMP")) {
					keytype = "Date";
				} else if (cc.getTypaname().equals("DECIMAL")) {
					keytype = "Big_Decimal";
				} else if (cc.getTypaname().equals("DOUBLE")) {
					keytype = "Double";
				}
			}
		}

		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String perPath = filePath + "service";
		File file = new File(perPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String serviceContent = "package " + packagePath + ".service;\n" + "\n"
				+ "import java.util.List;\n" + "import java.util.Map;\n" + "\n"
				+ "import com.component.BaseService;\n" + "import "
				+ packagePath + ".model." + modelName + ";\n"
				+ "import com.persistence.hibernate.PageInfo;\n" + "\n"
				+ "/**\n" + " * \n" + " * Copyright (c) "
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + nowDate + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + "public class "
				+ modelName + "Service extends BaseService {\n" + "\n"
				+ "	public void save(" + modelName + " model) {\n"
				+ "		getCrudDao().saveEntity(model);\n" + "	}\n" + "\n"
				+ "	public void saveorupdate(" + modelName + " model) {\n"
				+ "		getCrudDao().saveorupdateEntity(model);\n" + "	}\n" + "\n"
				+ "	public void updateEntity(" + modelName + " model) {\n"
				+ "		getCrudDao().updateEntity(model);\n" + "	}\n" + "\n"
				+ "	public void deleteEntity(" + modelName + " model) {\n"
				+ "		getCrudDao().deleteEntity(model);\n" + "	}\n" + "\n"
				+ "	public " + modelName + " get" + modelName + "(" + keytype
				+ " pkey) {\n" + "		return getCrudDao().getEntity(" + modelName
				+ ".class, pkey);\n" + "	}\n" + "\n" + "\n" + "\n"
				+ "	// 根据查询条件查询（万能查询）\n" + "	public List<" + modelName
				+ "> find" + modelName
				+ "(Map params, Map operators, Map orders,\n"
				+ "			PageInfo page) {\n" + "		return getCrudDao().find("
				+ modelName + ".class, page, params, operators,\n"
				+ "				orders);\n" + "	}\n" + "\n" + "	// 根据查询条件查询（万能查询）\n"
				+ "	public List<" + modelName + "> find" + modelName
				+ "(Map params, Map operators, Map orders) {\n"
				+ "		return getCrudDao().find(" + modelName
				+ ".class, params, operators, orders);\n" + "	}\n" + "\n"
				+ "}\n";

		FileWriter fwSpr = new FileWriter(perPath + "\\" + modelName
				+ "Service.java");
		fwSpr.write("");// 重新写文件
		fwSpr.append(serviceContent);// 追加文件内容
		fwSpr.close();

	}

	/**
	 * 研发项目生成model，采用注释编程
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void creatUFCModelFiles(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {
		String sql = "select * from syscat.columns  where tabname='"
				+ tableName + "'";
		DB2.init();
		List<ColumnModel> columnsList = DB2.queryColumn(sql);
		String modelPath = filePath + "entity";
		File file = new File(modelPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String headers = "package " + packagePath + ".entity;\n";
		headers += "\n";

		String content = "@javax.persistence.Entity\n"
				+ "@javax.persistence.Table(name = \"" + tableName + "\")\n"
				+ "public class " + modelName
				+ "Entity extends AbstractEntity implements" + " I" + modelName
				+ "Entity {\n";// 包的路径，引入类
		content += "	/**  描述  */       \n" + "\n"
				+ "	private static final long serialVersionUID = 1L;\n"
				+ "	// Fields\n";

		String fileds = "";// 字段内容
		String getSets = "\n";// getset内容
		String funcs = "";// 构造函数
		funcs += "\n" + "	/** default constructor */\n";
		funcs += "	public " + modelName + "Entity() {\n";
		funcs += "	}\n";
		funcs += "\n";

		String end = "}\n";

		String interFiled = "";
		boolean isHaveDate = false;
		boolean isHavaBigDecimal = false;
		for (int i = 0; i < columnsList.size(); i++) {
			ColumnModel cModel = columnsList.get(i);
			String type = "";

			String[] colNames = cModel.getColname().toLowerCase().split("_");
			String colName = "";
			for (int x = 0; x < colNames.length; x++) {
				if (x == 0) {
					colName += colNames[x].substring(0, 1).toLowerCase()
							+ colNames[x].substring(1).toLowerCase();
				} else {
					colName += colNames[x].substring(0, 1).toUpperCase()
							+ colNames[x].substring(1).toLowerCase();
				}

			}

			String entityColumn = "";
			if (cModel.getTypaname().equals("BIGINT")
					|| cModel.getTypaname().equals("INTEGER")
					|| cModel.getTypaname().equals("SMALLINT")) {
				type = "Integer";
				entityColumn = "	@Column(name = \""
						+ cModel.getColname().toUpperCase()
						+ "\", precision = 5, scale = 0)\n";
			} else if (cModel.getTypaname().equals("CHARACTER")
					|| cModel.getTypaname().equals("CLOB")
					|| cModel.getTypaname().equals("LONG VARCHAR")
					|| cModel.getTypaname().equals("VARCHAR")) {
				type = "String";
				entityColumn = "	@Column(name = \""
						+ cModel.getColname().toUpperCase() + "\", length = "
						+ cModel.getLength() + ")\n";
			} else if (cModel.getTypaname().equals("DATE")) {
				type = "Date";
				isHaveDate = true;
				entityColumn = "	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)\n"
						+ "	@Column(name = \""
						+ cModel.getColname().toUpperCase()
						+ "\", length = "
						+ cModel.getLength() + ")\n";
			} else if (cModel.getTypaname().equals("TIMESTAMP")) {
				type = "Date";
				isHaveDate = true;
				entityColumn = "	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)\n"
						+ "	@Column(name = \""
						+ cModel.getColname().toUpperCase()
						+ "\", length = "
						+ cModel.getLength() + ")\n";
			} else if (cModel.getTypaname().equals("DECIMAL")) {
				type = "BigDecimal";
				isHavaBigDecimal = true;
				if (!cModel.getScale().equals("0")) {
					entityColumn = "	@Column(name = \""
							+ cModel.getColname().toUpperCase()
							+ "\", length = " + cModel.getLength()
							+ ", scale = " + cModel.getScale() + ")\n";
					type = "Double";
					isHavaBigDecimal = false;
				} else {
					entityColumn = "	@Column(name = \""
							+ cModel.getColname().toUpperCase()
							+ "\", length = " + cModel.getLength() + ")\n";
					type = "Integer";
					isHavaBigDecimal = false;
				}
			} else if (cModel.getTypaname().equals("DOUBLE")) {
				type = "Double";
				entityColumn = "	@Column(name = \""
						+ cModel.getColname().toUpperCase() + "\", length = "
						+ cModel.getLength() + ", scale = " + cModel.getScale()
						+ ")\n";
			}
			fileds += "	private " + type + " " + colName + ";\n";

			interFiled += "String " + colName.toUpperCase() + " = \"" + colName
					+ "\" ;\n";

			String pkComment = "";

			// 主键
			if (cModel.getKeyseq() != null && cModel.getKeyseq().equals("1")) {
				funcs += "	/** default constructor */\n";
				funcs += "	public " + modelName + "Entity(" + type + " "
						+ colName + ") {\n";
				funcs += "				this." + colName + " = " + colName + ";\n";
				funcs += "	}\n";
				funcs += "\n";
				pkComment = "	@javax.persistence.Id\n";
			}

			getSets += pkComment + entityColumn + "	@Comment(\""
					+ cModel.getRemarks() + "\")\n" + "	public " + type
					+ " get" + colName.substring(0, 1).toUpperCase()
					+ colName.substring(1) + "() {\n";
			getSets += "		return this." + colName + ";\n";
			getSets += "	}\n";
			getSets += "\n";
			getSets += "	public void set"
					+ colName.substring(0, 1).toUpperCase()
					+ colName.substring(1) + "(" + type + " " + colName
					+ ") {\n";
			getSets += "				this." + colName + " = " + colName + ";\n";
			getSets += "	}\n";
			getSets += "\n";

		}
		headers += "\nimport javax.persistence.Column;\n"
				+ "import com.component.common.annotation.Comment;\n"
				+ "import com.component.common.entity.AbstractEntity;\n";
		if (isHavaBigDecimal) {
			headers += "import java.math.BigDecimal;\n";
		}
		if (isHaveDate) {
			headers += "import java.util.Date;\n";
		}
		headers += "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + new Date() + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n";
		String contents = headers + content + fileds + funcs + getSets + end;

		FileWriter fw = new FileWriter(modelPath + "\\" + modelName
				+ "Entity.java");
		fw.write("");// 重新写文件
		fw.append(contents);// 追加文件内容
		fw.close();

		String interContent = "package " + packagePath + ".entity;\n" + "\n"
				+ "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + new Date() + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + "\n" + "\n"
				+ "public interface I" + modelName + "Entity {\n" + interFiled
				+ "\n" + "}\n";

		FileWriter interFw = new FileWriter(modelPath + "\\I" + modelName
				+ "Entity.java");
		interFw.write("");// 重新写文件
		interFw.append(interContent);// 追加文件内容
		interFw.close();
	}

	/**
	 * 研发项目生成Service文件 描述
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-18 下午2:31:13
	 */
	public static void CreateUFCService(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {

		SimpleDateFormat fullFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		String nowDate = fullFormat.format(new Date());

		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String perPath = filePath + "service";
		File file = new File(perPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String serviceContent = "package " + packagePath + ".service;\n" + "\n"
				+ "import com.component.common.service.BaseService;\n"
				+ "import org.springframework.stereotype.Service;\n" + "\n"
				+ "/**\n" + " * \n" + " * Copyright (c) "
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + nowDate + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + "\n" + "@Service\n"
				+ "public class " + modelName
				+ "Service extends BaseService {\n" + "\n" + "}\n";

		FileWriter fwSpr = new FileWriter(perPath + "\\" + modelName
				+ "Service.java");
		fwSpr.write("");// 重新写文件
		fwSpr.append(serviceContent);// 追加文件内容
		fwSpr.close();

	}

	/**
	 * 研发项目生成view调用层文件 描述
	 * 
	 * @param filePath
	 * @param tableName
	 * @param seq
	 * @param packagePath
	 * @param author
	 * @throws SQLException
	 * @throws Exception
	 * @author zhangwu
	 * @created 2012-7-23 上午11:22:58
	 */
	public static void creatUFCViewFiles(String filePath, String tableName,
			String seq, String packagePath, String author) throws SQLException,
			Exception {

		SimpleDateFormat fullFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		String nowDate = fullFormat.format(new Date());

		String sql = "select * from syscat.columns  where tabname='"
				+ tableName + "'";
		DB2.init();
		List<ColumnModel> columnsList = DB2.queryColumn(sql);
		String modelPath = filePath + "view";
		File file = new File(modelPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String modelName = "";// 小写的类名
		String[] names = tableName.split("_");
		for (int i = 0; i < names.length; i++) {
			modelName += names[i].substring(0, 1)
					+ names[i].substring(1).toLowerCase();
		}

		String headers = "package " + packagePath + ".view;\n";
		headers += "\n";

		String conditionString = "";

		boolean isHaveDate = false;
		boolean isHavaBigDecimal = false;
		for (int i = 0; i < columnsList.size(); i++) {
			ColumnModel cModel = columnsList.get(i);
			String type = "";

			String[] colNames = cModel.getColname().toLowerCase().split("_");
			String colName = "";
			for (int x = 0; x < colNames.length; x++) {
				if (x == 0) {
					colName += colNames[x].substring(0, 1).toLowerCase()
							+ colNames[x].substring(1).toLowerCase();
				} else {
					colName += colNames[x].substring(0, 1).toUpperCase()
							+ colNames[x].substring(1).toLowerCase();
				}

			}

			if (cModel.getTypaname().equals("BIGINT")
					|| cModel.getTypaname().equals("INTEGER")
					|| cModel.getTypaname().equals("SMALLINT")) {
				type = "Integer";
			} else if (cModel.getTypaname().equals("CHARACTER")
					|| cModel.getTypaname().equals("CLOB")
					|| cModel.getTypaname().equals("LONG VARCHAR")
					|| cModel.getTypaname().equals("VARCHAR")) {
				type = "String";
			} else if (cModel.getTypaname().equals("DATE")
					|| cModel.getTypaname().equals("TIMESTAMP")) {
				type = "Date";
				isHaveDate = true;
			} else if (cModel.getTypaname().equals("DECIMAL")) {
				type = "BigDecimal";
				isHavaBigDecimal = true;
				if (!cModel.getScale().equals("0")) {
					type = "Double";
					isHavaBigDecimal = false;
				} else {
					type = "Integer";
					isHavaBigDecimal = false;
				}
			} else if (cModel.getTypaname().equals("DOUBLE")) {
				type = "Double";
			}

			conditionString += "			//" + cModel.getRemarks() + "\n" + "			"
					+ type + " " + colName + " = ParseUtil.parse" + type
					+ "(parameter.get(\"" + colName + "\"));\n"
					+ "			if (Validator.isNotEmpty(" + colName + ")) {\n"
					+ "				valueMap.put(" + modelName + "Entity."
					+ colName.toUpperCase() + ", " + colName + ");\n";
			if (type.equals("String")) {
				conditionString += "				operatorMap.put(" + modelName
						+ "Entity." + colName.toUpperCase()
						+ ", QueryConstants.LIKE);\n";
			}
			conditionString += "			}\n";

		}

		String mathodString =

		"	/**\n" + "	 * \n" + "	 * 描述    查询实体\n" + "	 * @param page\n"
				+ "	 * @param parameter\n" + "	 * @throws Exception   \n"
				+ "	 * @author "
				+ author
				+ "\n"
				+ "	 * @created "
				+ new Date()
				+ "\n"
				+ "	 */\n"
				+ "	@DataProvider\n"
				+ "	public void query"
				+ modelName
				+ "Entity(Page<"
				+ modelName
				+ "Entity> page,\n"
				+ "			Map<String, Object> parameter)  throws Exception {\n"
				+ "		Map<String, Object> valueMap = new HashMap<String, Object>();\n"
				+ "		Map<String, String> operatorMap = new HashMap<String, String>();\n"
				+ "		Map<String, String> orderMap = new HashMap<String, String>();\n"
				+ "		if (Validator.isNotEmpty(parameter)) {\n"
				+ conditionString
				+ "\n"
				+ "		}\n"
				+ "\n"
				+ "		BeanUtil.get"
				+ modelName
				+ "Service().find("
				+ modelName
				+ "Entity.class, valueMap, operatorMap,\n"
				+ "				orderMap, page);\n"
				+ "	}\n"
				+ "	\n"
				+ "\n"
				+ "	 /**\n"
				+ "	 * \n"
				+ "	 * 描述    对实体的操作\n"
				+ "	 * @param datas\n"
				+ "	 * @throws Exception   \n"
				+ "	 * @author "
				+ author
				+ "\n"
				+ "	 * @created "
				+ new Date()
				+ "\n"
				+ "	 */\n"
				+ "	@DataResolver\n"
				+ "	public void save"
				+ modelName
				+ "Entity(Collection<"
				+ modelName
				+ "Entity> datas)\n"
				+ "			throws Exception {\n"
				+ "		for (Iterator<"
				+ modelName
				+ "Entity> iter = EntityUtils.getIterator(datas,\n"
				+ "				FilterType.DELETED, "
				+ modelName
				+ "Entity.class); iter.hasNext();) {\n"
				+ "			BeanUtil.get"
				+ modelName
				+ "Service().delete(iter.next());\n"
				+ "		}\n"
				+ "		for (Iterator<"
				+ modelName
				+ "Entity> iter = EntityUtils.getIterator(datas,\n"
				+ "				FilterType.MODIFIED, "
				+ modelName
				+ "Entity.class); iter.hasNext();) {\n"
				+ "			BeanUtil.get"
				+ modelName
				+ "Service().update(iter.next());\n"
				+ "		}\n"
				+ "		for (Iterator<"
				+ modelName
				+ "Entity> iter = EntityUtils.getIterator(datas,\n"
				+ "				FilterType.NEW, "
				+ modelName
				+ "Entity.class); iter.hasNext();) {\n"
				+ "			BeanUtil.get"
				+ modelName
				+ "Service().save(iter.next());\n"
				+ "		}\n"
				+ "	}\n";

		headers = "";
		if (isHavaBigDecimal) {
			headers += "import java.math.BigDecimal;\n";
		}
		if (isHaveDate) {
			headers += "import java.util.Date;\n";
		}

		@SuppressWarnings("deprecation")
		String content = "package "
				+ packagePath
				+ ".view;\n"
				+ "\n"
				+ "import java.util.Collection;\n"
				+ "import java.util.HashMap;\n"
				+ "import java.util.Iterator;\n"
				+ "import java.util.Map;\n"
				+ headers
				+ "\n"
				+ "import org.apache.log4j.Logger;\n"
				+ "import org.springframework.stereotype.Component;\n"
				+ "\n"
				+ "import com.bstek.dorado.annotation.DataProvider;\n"
				+ "import com.bstek.dorado.annotation.DataResolver;\n"
				+ "import com.bstek.dorado.data.entity.EntityUtils;\n"
				+ "import com.bstek.dorado.data.entity.FilterType;\n"
				+ "import com.bstek.dorado.data.provider.Page;\n"
				+ "import com.component.common.hibernate.util.QueryConstants;\n"
				+ "import com.component.common.util.ParseUtil;\n"
				+ "import com.component.common.util.Validator;\n"
				+ "import com.component.common.spring.util.BeanUtil;\n"
				+ "import " + packagePath + ".entity." + modelName
				+ "Entity;\n" + "\n" + "/**\n" + " * \n" + " * Copyright (c) "
				+ (new Date()).getYear()
				+ " 北京联银通科技有限公司 all rights reserved.\n" + " * @author "
				+ author + "\n" + " * @version 1.0 " + nowDate + "\n"
				+ " * @see 工具提供—张武\n" + " *\n" + " */\n" + "@Component\n"
				+ "public class " + modelName + "View {\n" + "\n"
				+ "	private static final Logger logger = Logger.getLogger("
				+ modelName + "View.class);\n" + "\n"
				+ "	public static Logger getLogger() {\n"
				+ "		return logger;\n" + "	}\n" + "\n" + mathodString + "\n"
				+ "}\n";

		FileWriter fw = new FileWriter(modelPath + "\\" + modelName
				+ "View.java");
		fw.write("");// 重新写文件
		fw.append(content);// 追加文件内容
		fw.close();

	}
}
