package com.component.common.util;

import java.io.File;
import java.util.Comparator;

/**
 * �ļ��Ƚϣ�����
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-17 ����6:56:39
 */
public class FileComparator implements Comparator<File> {
	public int compare(File file1, File file2) {
		return file1.getName().compareTo(file2.getName());
	}
}
