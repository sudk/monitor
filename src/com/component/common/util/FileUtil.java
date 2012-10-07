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
 * �ļ���������
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-17 ����6:57:17
 */
public class FileUtil {
	private static final Logger logger = Logger.getLogger(FileUtil.class);

	public static Logger getLogger() {
		return logger;
	}

	// ////////////////////////////////////////////////

	/**
	 * ��ԴĿ¼�µ��ļ�����Ŀ¼��������Ŀ��Ŀ¼�£���Ŀ��Ŀ¼�����ڣ��򴴽���
	 * 
	 * @param sourceDirName
	 *            ԴĿ¼·����
	 * @param destinationDirName
	 *            Ŀ��Ŀ¼·����
	 */
	public static void copyDirectory(String sourceDirName,
			String destinationDirName) {
		copyDirectory(new File(sourceDirName), new File(destinationDirName));
	}

	/**
	 * ��ԴĿ¼�µ����ݿ�����Ŀ��Ŀ¼��
	 * 
	 * @param source
	 *            ԴĿ¼����
	 * @param destination
	 *            Ŀ��Ŀ¼����
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
	 * ����Դ�ļ���Ŀ���ļ���
	 * 
	 * @param source
	 *            Դ�ļ�·������
	 * @param destination
	 *            Ŀ���ļ�·������
	 */
	public static void copyFile(String source, String destination) {
		copyFile(source, destination, false);
	}

	/**
	 * ����Դ�ļ���Ŀ���ļ���
	 * 
	 * @param source
	 *            Դ�ļ�·������
	 * @param destination
	 *            Ŀ���ļ�·������
	 * @param lazy
	 *            ��lazyΪtrue�������ж���Դ�ļ���Ŀ���ļ�����һ�����ٽ��п�������LazyΪfalse����ȫ�̿�����
	 */
	public static void copyFile(String source, String destination, boolean lazy) {

		copyFile(new File(source), new File(destination), lazy);
	}

	/**
	 * ����Դ�ļ���Ŀ���ļ���
	 * 
	 * @param source
	 *            Դ�ļ�����
	 * @param destination
	 *            Ŀ���ļ�����
	 */
	public static void copyFile(File source, File destination) {
		copyFile(source, destination, false);
	}

	/**
	 * ����Դ�ļ���Ŀ���ļ���
	 * 
	 * @param source
	 *            Դ�ļ�����
	 * @param destination
	 *            Ŀ���ļ�����
	 * @param lazy
	 *            ��lazyΪtrue�������ж���Դ�ļ���Ŀ���ļ�����һ�����ٽ��п�������LazyΪfalse����ȫ�̿�����
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
	 * ɾ��ָ���ļ�
	 * 
	 * @param file
	 *            Ҫ��ɾ�����ļ�����·��
	 * @return ��ɾ���ɹ����򷵻�true�����򷵻�false
	 */
	public static boolean delete(String file) {
		return delete(new File(file));
	}

	/**
	 * ɾ��ָ���ļ�
	 * 
	 * @param file
	 *            Ҫ��ɾ�����ļ�����
	 * @return ��ɾ���ɹ����򷵻�true�����򷵻�false
	 */
	public static boolean delete(File file) {
		if (file.exists()) {
			return file.delete();
		} else {
			return false;
		}
	}

	/**
	 * ɾ��ָ��Ŀ¼���������������ļ��Լ���Ŀ¼
	 * 
	 * @param directory
	 *            Ҫɾ����Ŀ¼·��
	 */
	public static void deltree(String directory) {
		deltree(new File(directory));
	}

	/**
	 * ɾ��ָ��Ŀ¼���������������ļ��Լ���Ŀ¼
	 * 
	 * @param directory
	 *            Ҫɾ����Ŀ¼����
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
	 * ��ȡ�ļ����ݣ���¼��byte������
	 * 
	 * @param file
	 *            Ҫ��ȡ���ļ�����
	 * @return ��byte������ʽ���ض�ȡ���ļ�����
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
	 * ��ָ�����������ж�ȡ���ݣ���¼��byte������
	 * 
	 * @param in
	 *            ������
	 * @return ��byte������ʽ���ض�ȡ������������
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
	 * ȡ��ָ���ļ��Ĵ��Ŀ¼·��
	 * 
	 * @param fullFileName
	 *            ָ���ļ���ȫ·��
	 * @return �ļ��Ĵ��Ŀ¼·��
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
	 * ȡ��ָ���ļ��Ĵ��ļ���������·���ģ�
	 * 
	 * @param fullFileName
	 *            ָ���ļ���ȫ·��
	 * @return ָ���ļ��Ĵ��ļ���
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
	 * �ж�ָ���ļ��Ƿ����
	 * 
	 * @param fileName
	 *            ָ���ļ���·���ļ���
	 * @return �����ڣ��򷵻�true�����򷵻�false
	 */
	public static boolean exists(String fileName) {
		File file = new File(fileName);

		return file.exists();
	}

	/**
	 * ȡ��ָ��Ŀ¼�µ���Ŀ¼��
	 * 
	 * @param fileName
	 *            ָ��Ŀ¼·������
	 * @return ��Ŀ¼������
	 * @throws IOException
	 */
	public static String[] listDirs(String fileName) throws IOException {
		return listDirs(new File(fileName));
	}

	/**
	 * ȡ��ָ��Ŀ¼�µ���Ŀ¼��
	 * 
	 * @param file
	 *            ָ��Ŀ¼����
	 * @return ��Ŀ¼������
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
	 * ȡ��ָ��Ŀ¼�µ��ļ�����
	 * 
	 * @param fileName
	 *            ָ��Ŀ¼·������
	 * @return �ļ�������
	 * @throws IOException
	 */
	public static String[] listFiles(String fileName) throws IOException {
		if (Validator.isNull(fileName)) {
			return new String[0];
		}

		return listFiles(new File(fileName));
	}

	/**
	 * ȡ��ָ��Ŀ¼�µ��ļ�����
	 * 
	 * @param file
	 *            ָ��Ŀ¼����
	 * @return �ļ�������
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
	 * ��ָ��·���½�����Ŀ¼
	 * 
	 * @param pathName
	 *            ����·��
	 */
	public static void mkdirs(String pathName) {
		File file = new File(pathName);
		file.mkdirs();
	}

	/**
	 * ��Դ�ļ���������Ŀ���ļ�
	 * 
	 * @param sourceFileName
	 *            Դ�ļ�·����
	 * @param destinationFileName
	 *            Ŀ���ļ�·����
	 * @return ����������ɹ�������true�����򷵻�false
	 */
	public static boolean move(String sourceFileName, String destinationFileName) {
		return move(new File(sourceFileName), new File(destinationFileName));
	}

	/**
	 * ��Դ�ļ���������Ŀ���ļ�
	 * 
	 * @param source
	 *            Դ�ļ�����
	 * @param destination
	 *            Ŀ���ļ�����
	 * @return ����������ɹ�������true�����򷵻�false
	 */
	public static boolean move(File source, File destination) {
		if (!source.exists()) {
			return false;
		}

		destination.delete();

		return source.renameTo(destination);
	}

	/**
	 * ��ȡ�ļ����ݣ����ַ�����ʽ����
	 * 
	 * @param fileName
	 *            �ļ�·����
	 * @return �ļ�����
	 * @throws IOException
	 */
	public static String read(String fileName) throws IOException {
		return read(new File(fileName));
	}

	/**
	 * ��ȡ�ļ����ݣ����ַ�����ʽ����
	 * 
	 * @param file
	 *            Ҫ��ȡ���ļ�
	 * @return �ļ�����
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
	 * ���ļ����鰴���ơ���Ŀ¼/�ļ���������
	 * 
	 * @param files
	 *            Դ�ļ�����
	 * @return �������ļ�����
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
	 * ���ļ�·���еġ�\\'�滻Ϊ'/'
	 * 
	 * @param fileName
	 *            �ļ�·��
	 * @return �滻����ļ�·��
	 */
	public static String replaceSeparator(String fileName) {
		return StringUtil.replace(fileName, '\\', "/");
	}

	/**
	 * ��Reader���е��ļ����������з���List��
	 * 
	 * @param reader
	 *            �ļ�������
	 * @return ���ж�ȡ�����ļ�����
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
	 * ��ָ���ļ����ݶ��������з���List��
	 * 
	 * @param fileName
	 *            ָ���ļ���·������
	 * @return ���ж�ȡ�����ļ�����
	 */
	public static List<String> toList(String fileName) {
		try {
			return toList(new FileReader(fileName));
		} catch (IOException ioe) {
			return new ArrayList<String>();
		}
	}

	/**
	 * ���ļ�����ת��ΪProperties����
	 * 
	 * @param fis
	 *            �ļ�������
	 * @return Properties����
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
	 * ��ָ���ļ�����ת��ΪProperties����
	 * 
	 * @param fileName
	 *            ָ���ļ���·������
	 * @return Properties����
	 */
	public static Properties toProperties(String fileName) {
		try {
			return toProperties(new FileInputStream(fileName));
		} catch (IOException ioe) {
			return new Properties();
		}
	}

	/**
	 * ���ļ�����ָ���ַ���������
	 * 
	 * @param fileName
	 *            Ҫ��ȡ���ļ�·������
	 * @param s
	 *            ����ļ����ݵ��ַ���
	 * @throws IOException
	 */
	public static void write(String fileName, String s) {
		write(new File(fileName), s);
	}

	/**
	 * ���ļ�����ָ���ַ���������
	 * 
	 * @param fileName
	 *            Ҫ��ȡ���ļ�·������
	 * @param s
	 *            ����ļ����ݵ��ַ���
	 * @param lazy
	 * @throws IOException
	 */
	public static void write(String fileName, String s, boolean lazy) {
		write(new File(fileName), s, lazy);
	}

	/**
	 * ���ļ�����ָ���ַ���������
	 * 
	 * @param pathName
	 *            Ҫ��ȡ���ļ����Ŀ¼·��
	 * @param fileName
	 *            Ҫ��ȡ���ļ���
	 * @param s
	 *            ����ļ����ݵ��ַ���
	 * @throws IOException
	 */
	public static void write(String pathName, String fileName, String s) {

		write(new File(pathName, fileName), s);
	}

	/**
	 * ���ļ�����ָ���ַ���������
	 * 
	 * @param pathName
	 *            Ҫ��ȡ���ļ����Ŀ¼·��
	 * @param fileName
	 *            Ҫ��ȡ���ļ���
	 * @param s
	 *            ����ļ����ݵ��ַ���
	 * @param lazy
	 * @throws IOException
	 */
	public static void write(String pathName, String fileName, String s,
			boolean lazy) {
		write(new File(pathName, fileName), s, lazy);
	}

	/**
	 * ���ַ���д��ָ���ļ���
	 * 
	 * @param file
	 *            Ҫд����ļ�����
	 * @param s
	 *            ����ļ����ݵ��ַ���
	 * @throws IOException
	 */
	public static void write(File file, String s) {
		write(file, s, false);
	}

	/**
	 * ���ַ���д��ָ���ļ���
	 * 
	 * @param file
	 *            Ҫд����ļ�����
	 * @param s
	 *            ����ļ����ݵ��ַ���
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
	 * ��ָ��byte����д��ָ���ļ���
	 * 
	 * @param fileName
	 *            Ҫд����ļ�·������
	 * @param byteArray
	 *            ����ļ����ݵ�byte����
	 * @throws IOException
	 */
	public static void write(String fileName, byte[] byteArray) {
		write(new File(fileName), byteArray);
	}

	/**
	 * ��ָ��byte����д��ָ���ļ���
	 * 
	 * @param file
	 *            Ҫд����ļ�����
	 * @param byteArray
	 *            ����ļ����ݵ�byte����
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
	 * ����·����ȡĿ¼(Fileӳ���Ŀ¼������ʱ����(��Ŀ¼������ʱҲ�ᴴ��)�������û����ж�дȨ��)
	 * 
	 * @param pathname
	 *            ·��
	 * @return
	 * @throws Exception
	 */
	public static File getDirectory(String pathname) {
		if (Validator.isEmpty(pathname)) {
			throw new RuntimeException("·��Ϊ�գ�");
		}
		File dir = null;
		try {
			dir = new File(pathname);

			if (dir.exists()) {// file����

				if (dir.isFile()) {// fileΪ�ļ�
					throw new RuntimeException("·����[" + pathname
							+ "]��ʾ�����ļ�������Ŀ¼��");
				}

			} else {// file������

				if (dir.mkdirs()) {
					// �����û����ж�Ȩ��
					dir.setReadable(true, false);
					// �����û�����дȨ��
					dir.setWritable(true, false);
				} else {
					throw new RuntimeException("����Ŀ¼[" + pathname + "]ʧ�ܣ�");
				}

			}

			if (!dir.canRead()) {
				// �����û����ж�Ȩ��
				dir.setReadable(true, false);
			}

			if (!dir.canWrite()) {
				// �����û�����дȨ��
				dir.setWritable(true, false);
			}

			if (!dir.canRead()) {
				throw new RuntimeException("Ӧ�ó����ܶ�ȡ·����[" + pathname
						+ "]��ʾ��Ŀ¼��");
			}

			if (!dir.canWrite()) {
				throw new RuntimeException("Ӧ�ó������޸�·����[" + pathname
						+ "]��ʾ��Ŀ¼��");
			}

		} catch (RuntimeException e) {
			logger.error("��ȡ·����[" + pathname + "]���ļ�ʧ�ܣ�", e);
			throw e;
		}

		return dir;
	}

	/**
	 * ����·����ȡ�ļ�(Fileӳ����ļ�������ʱ����(��Ŀ¼������ʱҲ�ᴴ��)�������û����ж�дȨ��)
	 * 
	 * @param pathname
	 *            ·��
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static File getFile(String pathname) {
		if (Validator.isEmpty(pathname)) {
			throw new RuntimeException("·��Ϊ�գ�");
		}
		File file = null;
		try {
			file = new File(pathname);

			if (file.exists()) {// file����

				if (file.isDirectory()) {// fileΪĿ¼
					throw new RuntimeException("·����[" + pathname
							+ "]��ʾ����Ŀ¼�������ļ���");
				}

			} else {// file�����ڣ��ȴ������ļ��У��ٴ����ļ���
					// ���ļ���
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) {// ���ļ��в�����
					if (parentFile.mkdirs()) {
						// �����û����ж�Ȩ��
						parentFile.setReadable(true, false);
						// �����û�����дȨ��
						parentFile.setWritable(true, false);
					} else {
						throw new RuntimeException("������Ŀ¼["
								+ parentFile.getPath() + "]ʧ�ܣ�");
					}
				}

				if (!parentFile.canRead()) {
					// �����û����ж�Ȩ��
					parentFile.setReadable(true, false);
				}

				if (!parentFile.canWrite()) {
					// �����û�����дȨ��
					parentFile.setWritable(true, false);
				}

				if (file.createNewFile()) {
					// �����û����ж�Ȩ��
					file.setReadable(true, false);
					// �����û�����дȨ��
					file.setWritable(true, false);
				} else {
					throw new RuntimeException("�����ļ�[" + pathname + "]ʧ�ܣ�");
				}

			}

			if (!file.canRead()) {
				// �����û����ж�Ȩ��
				file.setReadable(true, false);
			}

			if (!file.canWrite()) {
				// �����û�����дȨ��
				file.setWritable(true, false);
			}

			if (!file.canRead()) {
				throw new RuntimeException("Ӧ�ó����ܶ�ȡ·����[" + pathname
						+ "]��ʾ���ļ���");
			}

			if (!file.canWrite()) {
				throw new RuntimeException("Ӧ�ó������޸�·����[" + pathname
						+ "]��ʾ���ļ���");
			}

		} catch (RuntimeException e) {
			logger.error("��ȡ·����[" + pathname + "]���ļ�ʧ�ܣ�", e);
			throw e;
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}

		return file;
	}

	// /**
	// * copy �ļ�
	// * @param srcFile Դ�ļ�
	// * @param destFile Ŀ���ļ�
	// * @throws IOException
	// * @throws Exception
	// */
	// public static void copyFile(File srcFile, File destFile) throws
	// RuntimeException, IOException {
	//
	// if(srcFile == null){
	// throw new RuntimeException("Դ�ļ�Ϊ�գ�");
	// }
	//
	// if(destFile == null){
	// throw new RuntimeException("Ŀ���ļ�Ϊ�գ�");
	// }
	//
	// if(!srcFile.exists()){
	// throw new RuntimeException("Դ�ļ�������");
	// }
	//
	// if(!srcFile.isFile()){
	// throw new RuntimeException("Դ�ļ�Ϊ��Ŀ¼�������ļ���");
	// }
	// try {
	// if(!srcFile.canRead()){
	// //�����û����ж�Ȩ��
	// srcFile.setReadable(true,false);
	// }
	//
	// if(!srcFile.canRead()){
	// throw new RuntimeException("Դ�ļ�û�ж���Ȩ��");
	// }
	//
	// if(!destFile.exists()){//Ŀ���ļ�������
	// destFile = getFile(destFile.getPath());
	// }
	//
	// if(!destFile.canWrite()){
	// throw new RuntimeException("Ŀ���ļ�û��д��Ȩ�ޣ�");
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
