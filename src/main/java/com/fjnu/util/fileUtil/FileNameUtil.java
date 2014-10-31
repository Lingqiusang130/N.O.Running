package com.fjnu.util.fileUtil;

import java.io.File;

public class FileNameUtil {

	public static String[] getFileNameInfo(String fileWithDirName) {
		String[] names = new String[3];
		String internalName[] = fileWithDirName.split("/");
		String directorName = "";
		String fileName = "";
		String fileExtensionName = "";
		int len = internalName.length;
		for (int j = 0; j < len; j++) {
			if (j < len - 1) {
				if (j == 0) {
					directorName += internalName[j];
				} else {
					directorName += "/" + internalName[j];
				}
			} else {
				directorName += "/";
				String tempStr1 = internalName[j];
				String tempStrs[] = tempStr1.split("\\.");
				fileName += tempStrs[0];
				fileExtensionName = tempStrs[1];
			}
		}
		names[0] = directorName;
		names[1] = fileName;
		names[2] = fileExtensionName;
		return names;
	}

	public static String getNewFileName(String oldFileNameWithPath, int fileNum) {
		String newFileName = "";
		String[] fileNameInfos = getFileNameInfo(oldFileNameWithPath);

		if (fileNum == 1) {
			newFileName = fileNameInfos[0] + fileNameInfos[1] + "_" + "1."
					+ fileNameInfos[2];
		} else {
			StringBuffer tempStrBuffer = new StringBuffer(fileNameInfos[1]);
			int idx = tempStrBuffer.lastIndexOf("_");
			int len = tempStrBuffer.length();
			if(idx==-1){//文件名不含"_"
				tempStrBuffer.append("_").append(String.valueOf(fileNum));
			}
			else{
				tempStrBuffer.replace(idx + 1, len, String.valueOf(fileNum));
			}
			
			newFileName = fileNameInfos[0] + tempStrBuffer.toString() + "."
					+ fileNameInfos[2];
		}
		return newFileName;
	}

	/**
	 * 返回指定文件名中的文件号
	 * 
	 * @param fileName
	 *            :文件名，形如a/b/c/xxxx_2.yyy
	 * @return：文件号的字符串，如"2"
	 */
	public static String getFileNumStr(String fileName) {
		String[] internalNames = getFileNameInfo(fileName);
		String fileNameWithoutPath = internalNames[1];
		int pos = fileNameWithoutPath.indexOf("_");
		String fileNumStr = "";
		if (pos == -1) {
			fileNumStr = "0";
		} else {
			int len = fileNameWithoutPath.length();
			fileNumStr = fileNameWithoutPath.substring(pos + 1, len);
		}
		return fileNumStr;
	}

	/**
	 * 返回指定文件中的文件号
	 * 
	 * @param file
	 *            :文件，其名形如a/b/c/xxxx_2.yyy
	 * @return：文件号的字符串，如"2"
	 */
	public static String getFileNumStr(File file) {
		String fileName = file.getName();
		int pos = fileName.indexOf("_");
		String fileNumStr = "";
		if (pos == -1) {
			fileNumStr = "0";
		} else {
			int pos2 = fileName.indexOf('.');
			fileNumStr = fileName.substring(pos + 1, pos2);
		}
		return fileNumStr;
	}

	/**
	 * 返回指定文件名中的文件号
	 * 
	 * @param fileName
	 *            :文件名，形如a/b/c/xxxx_2.yyy
	 * @return：文件号，如 2
	 */
	public static int getFileNum(String fileName) {
		String[] internalNames = getFileNameInfo(fileName);
		String fileNameWithoutPath = internalNames[1];
		int pos = fileNameWithoutPath.indexOf("_");
		int fileNum = 0;
		if (pos != -1) {
			int len = fileNameWithoutPath.length();
			fileNum = Integer.parseInt(fileNameWithoutPath.substring(pos + 1,
					len));
		}
		return fileNum;
	}

	/**
	 * 返回指定文件中的文件号
	 * 
	 * @param fileName
	 *            :文件，其名形如a/b/c/xxxx_2.yyy
	 * @return：文件号，如 2
	 */
	public static int getFileNum(File file) {

		String fileName = file.getName();
		int pos = fileName.indexOf("_");
		int fileNum = 0;
		if (pos != -1) {
			int pos2 = fileName.indexOf('.');
			fileNum = Integer.parseInt(fileName.substring(pos + 1, pos2));
		}
		return fileNum;
	}

	public static String getProjectAbsolutePathName() {
		return System.getProperty("user.dir") + "\\";
	}
}
