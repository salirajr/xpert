package com.ptxti.concept.ruleengine.ui.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

	public static List<Map<String, String>> lsILrt(String prefixDirectory,
			String currentDirectory) {
		File fCurrentDirectory = new File(prefixDirectory + currentDirectory);
		List<Map<String, String>> result = new ArrayList<>();
		File[] filesList = fCurrentDirectory.listFiles();
		Map<String, String> map;
		for (File f : filesList) {
			map = new HashMap<>();
			map.put("name", f.getName());
			map.put("directory", currentDirectory);
			result.add(map);

		}
		return result;
	}

	public static File getFile(String absolutePath) {
		return new File(absolutePath);
	}

	public static String getFilePlain(String getFile) {
		try {
			return new String(Files.readAllBytes(Paths.get(getFile)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return e.toString();

		}
	}

	public static void fileScreening(String directoryName,
			ArrayList<File> files, String containsWith, String endsWith) {
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile() && file.getAbsolutePath().contains(containsWith)
					&& file.getName().endsWith(endsWith)) {
//				System.out.println(file.getAbsolutePath() + " - " + containsWith);
				files.add(file);
			} else if (file.isDirectory()) {
				fileScreening(file.getAbsolutePath(), files, containsWith,
						endsWith);
			}
		}
	}

	public static String filesFilter(ArrayList<File> files) {
		String result = "[", path;
		File file;
		for (int i = 0; i < files.size(); i++) {
			file = files.get(i);
			path = file.getAbsolutePath().substring(
					System.getProperty("axre.archcomp.basepath").length(),
					file.getAbsolutePath().length());
			result += "{\"file_location\":\""
					+ path.replace("\\", "/").substring(0,
							path.length() - file.getName().length())
					+ "\", \"file_name\":\"" + file.getName() + "\"}";
			if (i < files.size() - 1)
				result += ",";
		}
		result += "]";
		return result;
	}

}
