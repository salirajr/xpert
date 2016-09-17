/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.util;

import java.io.File;
import java.util.List;

import com.xybase.ax.eai.archcomp.constant.XeaiConstants;

public class CustomUtil {

	public static boolean isNull(Object arg0) {
		return arg0 == null;
	}

	public static boolean isAListClass(Object list) {
		if (list instanceof List)
			return true;
		return false;
	}

	public static Object asInstance(String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> classTemplate;
		classTemplate = Class.forName(className);
		return classTemplate.newInstance();

	}

	public static String[] asDomainFileURI(Object[] paths) {
		String[] result = new String[paths.length];
		for (int i = 0; i < paths.length; i++)
			result[i] = "file:" + XeaiConstants.BASEPATH + "/" + paths[i];

		return result;
	}

	public static String asDomainFileURI(String path) {
		return "file:" + XeaiConstants.BASEPATH + "/" + path;

	}

	public static boolean isFileExist(String absoultePath) {
		return new File(absoultePath).isFile();

	}

	@Override
	public String toString() {
		return "customUtil";
	}

}
