package com.component.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 文件处理工具类
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-17 下午6:57:17
 */
public class FileUtil {
	private static final Logger logger = Logger.getLogger(FileUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	// ////////////////////////////////////////////////

	/**
	 * 将源目录下的文件及子目录，拷贝到目标目录下（若目标目录不存在，则创建）
	 * 
	 * @param sourceDirName
	 *            源目录路径名
	 * @param destinationDirName
	 *            目标目录路径名
	 */
	public static void copyDirectory(String sourceDirName,
			String destinationDirName) {
		copyDirectory(new File(sourceDirName), new File(destinationDirName));
	}

	/**
	 * 将源目录下的内容拷贝到目标目录下
	 * 
	 * @param source
	 *            源目录对象
	 * @param destination
	 *            目标目录对象
	 */
	public static void copyDirectory(File source, File destination) {
		if (source.exists() && source.isDirectory()) {
			if (!destination.exists()) {
				destination.mkdirs();
			}

			File[] fileArray = source.listFiles();

			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isDirectory()) {
					copyDirectory(fileArray[i], new File(destination.getPath()
							+ File.separator + fileArray[i].getName()));
				} else {
					copyFile(fileArray[i], new File(destination.getPath()
							+ File.separator + fileArray[i].getName()));
				}
			}
		}
	}

	/**
	 * 拷贝源文件到目标文件。
	 * 
	 * @param source
	 *            源文件路径名称
	 * @param destination
	 *            目标文件路径名称
	 */
	public static void copyFile(String source, String destination) {
		copyFile(source, destination, false);
	}

	/**
	 * 拷贝源文件到目标文件。
	 * 
	 * @param source
	 *            源文件路径名称
	 * @param destination
	 *            目标文件路径名称
	 * @param lazy
	 *            若lazy为true，则先判断若源文件与目标文件内容一致则不再进行拷贝；若Lazy为false，则全盘拷贝。
	 */
	public static void copyFile(String source, String destination, boolean lazy) {

		copyFile(new File(source), new File(destination), lazy);
	}

	/**
	 * 拷贝源文件到目标文件。
	 * 
	 * @param source
	 *            源文件对象
	 * @param destination
	 *            目标文件对象
	 */
	public static void copyFile(File source, File destination) {
		copyFile(source, destination, false);
	}

	/**
	 * 拷贝源文件到目标文件。
	 * 
	 * @param source
	 *            源文件对象
	 * @param destination
	 *            目标文件对象
	 * @param lazy
	 *            若lazy为true，则先判断若源文件与目标文件内容一致则不再进行拷贝；若Lazy为false，则全盘拷贝。
	 */
	public static void copyFile(File source, File destination, boolean lazy) {
		if (!source.exists()) {
			return;
		}

		if (lazy) {
			String oldContent = null;

			try {
				oldContent = read(source);
			} catch (Exception e) {
				return;
			}

			String newContent = null;

			try {
				newContent = read(destination);
			} catch (Exception e) {
			}

			if (oldContent == null || !oldContent.equals(newContent)) {
				copyFile(source, destination, false);
			}
		} else {
			if ((destination.getParentFile() != null)
					&& (!destination.getParentFile().exists())) {

				destination.getParentFile().mkdirs();
			}

			try {
				FileChannel srcChannel = new FileInputStream(source)
						.getChannel();
				FileChannel dstChannel = new FileOutputStream(destination)
						.getChannel();

				dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

				srcChannel.close();
				dstChannel.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param file
	 *            要被删除的文件名称路径
	 * @return 若删除成果，则返回true；否则返回false
	 */
	public static boolean delete(String file) {
		return delete(new File(file));
	}

	/**
	 * 删除指定文件
	 * 
	 * @param file
	 *            要被删除的文件对象
	 * @return 若删除成果，则返回true；否则返回false
	 */
	public static boolean delete(File file) {
		if (file.exists()) {
			return file.delete();
		} else {
			return false;
		}
	}

	/**
	 * 删除指定目录，包括其下所有文件以及子目录
	 * 
	 * @param directory
	 *            要删除的目录路径
	 */
	public static void deltree(String directory) {
		deltree(new File(directory));
	}

	/**
	 * 删除指定目录，包括其下所有文件以及子目录
	 * 
	 * @param directory
	 *            要删除的目录对象
	 */
	public static void deltree(File directory) {
		if (directory.exists() && directory.isDirectory()) {
			File[] fileArray = directory.listFiles();

			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isDirectory()) {
					deltree(fileArray[i]);
				} else {
					fileArray[i].delete();
				}
			}

			directory.delete();
		}
	}

	/**
	 * 读取文件内容，记录在byte数组中
	 * 
	 * @param file
	 *            要读取的文件对象
	 * @return 以byte数组形式返回读取的文件内容
	 * @throws IOException
	 */
	public static byte[] getBytes(File file) throws IOException {
		if (file == null || !file.exists()) {
			return null;
		}

		FileInputStream in = new FileInputStream(file);

		byte[] bytes = getBytes(in);

		in.close();

		return bytes;
	}

	/**
	 * 从指定的输入流中读取内容，记录在byte数组中
	 * 
	 * @param in
	 *            输入流
	 * @return 以byte数组形式返回读取的输入流内容
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int c = in.read();

		while (c != -1) {
			out.write(c);
			c = in.read();
		}

		out.close();

		return out.toByteArray();
	}

	/**
	 * 取得指定文件的存放目录路径
	 * 
	 * @param fullFileName
	 *            指定文件的全路径
	 * @return 文件的存放目录路径
	 */
	public static String getPath(String fullFileName) {
		int pos = fullFileName.lastIndexOf("/");

		if (pos == -1) {
			pos = fullFileName.lastIndexOf("\\");
		}

		String shortFileName = fullFileName.substring(0, pos);

		if (Validator.isNull(shortFileName)) {
			return "/";
		}

		return shortFileName;
	}

	/**
	 * 取得指定文件的纯文件名（不带路径的）
	 * 
	 * @param fullFileName
	 *            指定文件的全路径
	 * @return 指定文件的纯文件名
	 */
	public static String getShortFileName(String fullFileName) {
		int pos = fullFileName.lastIndexOf("/");

		if (pos == -1) {
			pos = fullFileName.lastIndexOf("\\");
		}

		String shortFileName = fullFileName.substring(pos + 1,
				fullFileName.length());

		return shortFileName;
	}

	/**
	 * 判断指定文件是否存在
	 * 
	 * @param fileName
	 *            指定文件的路径文件名
	 * @return 若存在，则返回true；否则返回false
	 */
	public static boolean exists(String fileName) {
		File file = new File(fileName);

		return file.exists();
	}

	/**
	 * 取得指定目录下的子目录名
	 * 
	 * @param fileName
	 *            指定目录路径名称
	 * @return 子目录名数组
	 * @throws IOException
	 */
	public static String[] listDirs(String fileName) throws IOException {
		return listDirs(new File(fileName));
	}

	/**
	 * 取得指定目录下的子目录名
	 * 
	 * @param file
	 *            指定目录对象
	 * @return 子目录名数组
	 * @throws IOException
	 */
	public static String[] listDirs(File file) throws IOException {
		List<String> dirs = new ArrayList<String>();
		File[] fileArray = file.listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].isDirectory()) {
				dirs.add(fileArray[i].getName());
			}
		}
		return (String[]) dirs.toArray(new String[0]);
	}

	/**
	 * 取得指定目录下的文件名称
	 * 
	 * @param fileName
	 *            指定目录路径名称
	 * @return 文件名数组
	 * @throws IOException
	 */
	public static String[] listFiles(String fileName) throws IOException {
		if (Validator.isNull(fileName)) {
			return new String[0];
		}

		return listFiles(new File(fileName));
	}

	/**
	 * 取得指定目录下的文件名称
	 * 
	 * @param file
	 *            指定目录对象
	 * @return 文件名数组
	 * @throws IOException
	 */
	public static String[] listFiles(File file) throws IOException {
		List<String> files = new ArrayList<String>();
		File[] fileArray = file.listFiles();
		for (int i = 0; (fileArray != null) && (i < fileArray.length); i++) {
			if (fileArray[i].isFile()) {
				files.add(fileArray[i].getName());
			}
		}
		return (String[]) files.toArray(new String[0]);
	}

	/**
	 * 在指定路径下建立子目录
	 * 
	 * @param pathName
	 *            建立路径
	 */
	public static void mkdirs(String pathName) {
		File file = new File(pathName);
		file.mkdirs();
	}

	/**
	 * 将源文件，拷贝到目标文件
	 * 
	 * @param sourceFileName
	 *            源文件路径名
	 * @param destinationFileName
	 *            目标文件路径名
	 * @return 拷贝结果，成功，返回true；否则返回false
	 */
	public static boolean move(String sourceFileName, String destinationFileName) {
		return move(new File(sourceFileName), new File(destinationFileName));
	}

	/**
	 * 将源文件，拷贝到目标文件
	 * 
	 * @param source
	 *            源文件对象
	 * @param destination
	 *            目标文件对象
	 * @return 拷贝结果，成功，返回true；否则返回false
	 */
	public static boolean move(File source, File destination) {
		if (!source.exists()) {
			return false;
		}

		destination.delete();

		return source.renameTo(destination);
	}

	/**
	 * 读取文件内容，以字符串形式返回
	 * 
	 * @param fileName
	 *            文件路径名
	 * @return 文件内容
	 * @throws IOException
	 */
	public static String read(String fileName) throws IOException {
		return read(new File(fileName));
	}

	/**
	 * 读取文件内容，以字符串形式返回
	 * 
	 * @param file
	 *            要读取的文件
	 * @return 文件内容
	 * @throws IOException
	 */
	public static String read(File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));

			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
		} finally {
			br.close();
		}

		return sb.toString().trim();
	}

	/**
	 * 将文件数组按名称、按目录/文件进行排序。
	 * 
	 * @param files
	 *            源文件数组
	 * @return 排序后的文件数组
	 */
	public static File[] sortFiles(File[] files) {
		Arrays.sort(files, new FileComparator());
		List<File> directoryList = new ArrayList<File>();
		List<File> fileList = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				directoryList.add(files[i]);
			} else {
				fileList.add(files[i]);
			}
		}
		directoryList.addAll(fileList);
		return (File[]) directoryList.toArray(new File[0]);
	}

	/**
	 * 将文件路径中的‘\\'替换为'/'
	 * 
	 * @param fileName
	 *            文件路径
	 * @return 替换后的文件路径
	 */
	public static String replaceSeparator(String fileName) {
		return StringUtil.replace(fileName, '\\', "/");
	}

	/**
	 * 将Reader流中的文件读出，逐行放入List中
	 * 
	 * @param reader
	 *            文件输入流
	 * @return 逐行读取出的文件内容
	 */
	public static List<String> toList(Reader reader) {
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			br.close();
		} catch (IOException ioe) {
		}

		return list;
	}

	/**
	 * 将指定文件内容读出，逐行放入List中
	 * 
	 * @param fileName
	 *            指定文件的路径名称
	 * @return 逐行读取出的文件内容
	 */
	public static List<String> toList(String fileName) {
		try {
			return toList(new FileReader(fileName));
		} catch (IOException ioe) {
			return new ArrayList<String>();
		}
	}

	/**
	 * 将文件内容转换为Properties对象
	 * 
	 * @param fis
	 *            文件输入流
	 * @return Properties对象
	 */
	public static Properties toProperties(FileInputStream fis) {
		Properties props = new Properties();

		try {
			props.load(fis);
		} catch (IOException ioe) {
		}

		return props;
	}

	/**
	 * 将指定文件内容转换为Properties对象
	 * 
	 * @param fileName
	 *            指定文件的路径名称
	 * @return Properties对象
	 */
	public static Properties toProperties(String fileName) {
		try {
			return toProperties(new FileInputStream(fileName));
		} catch (IOException ioe) {
			return new Properties();
		}
	}

	/**
	 * 将文件读入指定字符串对象中
	 * 
	 * @param fileName
	 *            要读取的文件路径名称
	 * @param s
	 *            存放文件内容的字符串
	 * @throws IOException
	 */
	public static void write(String fileName, String s) {
		write(new File(fileName), s);
	}

	/**
	 * 将文件读入指定字符串对象中
	 * 
	 * @param fileName
	 *            要读取的文件路径名称
	 * @param s
	 *            存放文件内容的字符串
	 * @param lazy
	 * @throws IOException
	 */
	public static void write(String fileName, String s, boolean lazy) {
		write(new File(fileName), s, lazy);
	}

	/**
	 * 将文件读入指定字符串对象中
	 * 
	 * @param pathName
	 *            要读取的文件存放目录路径
	 * @param fileName
	 *            要读取的文件名
	 * @param s
	 *            存放文件内容的字符串
	 * @throws IOException
	 */
	public static void write(String pathName, String fileName, String s) {

		write(new File(pathName, fileName), s);
	}

	/**
	 * 将文件读入指定字符串对象中
	 * 
	 * @param pathName
	 *            要读取的文件存放目录路径
	 * @param fileName
	 *            要读取的文件名
	 * @param s
	 *            存放文件内容的字符串
	 * @param lazy
	 * @throws IOException
	 */
	public static void write(String pathName, String fileName, String s,
			boolean lazy) {
		write(new File(pathName, fileName), s, lazy);
	}

	/**
	 * 将字符串写入指定文件中
	 * 
	 * @param file
	 *            要写入的文件对象
	 * @param s
	 *            存放文件内容的字符串
	 * @throws IOException
	 */
	public static void write(File file, String s) {
		write(file, s, false);
	}

	/**
	 * 将字符串写入指定文件中
	 * 
	 * @param file
	 *            要写入的文件对象
	 * @param s
	 *            存放文件内容的字符串
	 * @param lazy
	 * @throws IOException
	 */
	public static void write(File file, String s, boolean lazy) {
		if (file.getParent() != null) {
			mkdirs(file.getParent());
		}
		if (file.exists()) {
			String content = null;
			try {
				content = read(file);
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.flush();
				bw.write(s);
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (content.equals(s)) {
				return;
			}
		}
	}

	/**
	 * 将指定byte数组写入指定文件中
	 * 
	 * @param fileName
	 *            要写入的文件路径名称
	 * @param byteArray
	 *            存放文件内容的byte数组
	 * @throws IOException
	 */
	public static void write(String fileName, byte[] byteArray) {
		write(new File(fileName), byteArray);
	}

	/**
	 * 将指定byte数组写入指定文件中
	 * 
	 * @param file
	 *            要写入的文件对象
	 * @param byteArray
	 *            存放文件内容的byte数组
	 * @throws IOException
	 */
	public static void write(File file, byte[] byteArray) {
		if (file.getParent() != null) {
			mkdirs(file.getParent());
		}

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(byteArray);
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 根据路径获取目录(File映射的目录不存在时创建(父目录不存在时也会创建)，所有用户都有读写权限)
	 * 
	 * @param pathname
	 *            路径
	 * @return
	 * @throws Exception
	 */
	public static File getDirectory(String pathname) {
		if (Validator.isEmpty(pathname)) {
			throw new RuntimeException("路径为空！");
		}
		File dir = null;
		try {
			dir = new File(pathname);

			if (dir.exists()) {// file存在

				if (dir.isFile()) {// file为文件
					throw new RuntimeException("路径名[" + pathname
							+ "]表示的是文件，不是目录！");
				}

			} else {// file不存在

				if (dir.mkdirs()) {
					// 所有用户都有读权限
					dir.setReadable(true, false);
					// 所有用户都有写权限
					dir.setWritable(true, false);
				} else {
					throw new RuntimeException("创建目录[" + pathname + "]失败！");
				}

			}

			if (!dir.canRead()) {
				// 所有用户都有读权限
				dir.setReadable(true, false);
			}

			if (!dir.canWrite()) {
				// 所有用户都有写权限
				dir.setWritable(true, false);
			}

			if (!dir.canRead()) {
				throw new RuntimeException("应用程序不能读取路径名[" + pathname
						+ "]表示的目录！");
			}

			if (!dir.canWrite()) {
				throw new RuntimeException("应用程序不能修改路径名[" + pathname
						+ "]表示的目录！");
			}

		} catch (RuntimeException e) {
			logger.error("获取路径名[" + pathname + "]的文件失败！", e);
			throw e;
		}

		return dir;
	}

	/**
	 * 根据路径获取文件(File映射的文件不存在时创建(父目录不存在时也会创建)，所有用户都有读写权限)
	 * 
	 * @param pathname
	 *            路径
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static File getFile(String pathname) {
		if (Validator.isEmpty(pathname)) {
			throw new RuntimeException("路径为空！");
		}
		File file = null;
		try {
			file = new File(pathname);

			if (file.exists()) {// file存在

				if (file.isDirectory()) {// file为目录
					throw new RuntimeException("路径名[" + pathname
							+ "]表示的是目录，不是文件！");
				}

			} else {// file不存在（先创建父文件夹，再创建文件）
					// 父文件夹
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) {// 父文件夹不存在
					if (parentFile.mkdirs()) {
						// 所有用户都有读权限
						parentFile.setReadable(true, false);
						// 所有用户都有写权限
						parentFile.setWritable(true, false);
					} else {
						throw new RuntimeException("创建父目录["
								+ parentFile.getPath() + "]失败！");
					}
				}

				if (!parentFile.canRead()) {
					// 所有用户都有读权限
					parentFile.setReadable(true, false);
				}

				if (!parentFile.canWrite()) {
					// 所有用户都有写权限
					parentFile.setWritable(true, false);
				}

				if (file.createNewFile()) {
					// 所有用户都有读权限
					file.setReadable(true, false);
					// 所有用户都有写权限
					file.setWritable(true, false);
				} else {
					throw new RuntimeException("创建文件[" + pathname + "]失败！");
				}

			}

			if (!file.canRead()) {
				// 所有用户都有读权限
				file.setReadable(true, false);
			}

			if (!file.canWrite()) {
				// 所有用户都有写权限
				file.setWritable(true, false);
			}

			if (!file.canRead()) {
				throw new RuntimeException("应用程序不能读取路径名[" + pathname
						+ "]表示的文件！");
			}

			if (!file.canWrite()) {
				throw new RuntimeException("应用程序不能修改路径名[" + pathname
						+ "]表示的文件！");
			}

		} catch (RuntimeException e) {
			logger.error("获取路径名[" + pathname + "]的文件失败！", e);
			throw e;
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}

		return file;
	}

	// /**
	// * copy 文件
	// * @param srcFile 源文件
	// * @param destFile 目标文件
	// * @throws IOException
	// * @throws Exception
	// */
	// public static void copyFile(File srcFile, File destFile) throws
	// RuntimeException, IOException {
	//
	// if(srcFile == null){
	// throw new RuntimeException("源文件为空！");
	// }
	//
	// if(destFile == null){
	// throw new RuntimeException("目标文件为空！");
	// }
	//
	// if(!srcFile.exists()){
	// throw new RuntimeException("源文件不存在");
	// }
	//
	// if(!srcFile.isFile()){
	// throw new RuntimeException("源文件为是目录，不是文件！");
	// }
	// try {
	// if(!srcFile.canRead()){
	// //所有用户都有读权限
	// srcFile.setReadable(true,false);
	// }
	//
	// if(!srcFile.canRead()){
	// throw new RuntimeException("源文件没有读的权限");
	// }
	//
	// if(!destFile.exists()){//目标文件不存在
	// destFile = getFile(destFile.getPath());
	// }
	//
	// if(!destFile.canWrite()){
	// throw new RuntimeException("目标文件没有写的权限！");
	// }
	//
	// FileUtils.copyFile(srcFile, destFile);
	//
	// } catch (RuntimeException e) {
	// logger.error(e);
	// throw e;
	// } catch (IOException e) {
	// logger.error(e);
	// throw e;
	// }
	// }
}
