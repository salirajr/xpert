package com.xybase.ax.eai.waconsole.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xybase.ax.eai.archcomp.common.util.DateUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.waconsole.appliance.Context;

@Controller
public class FileUtils {

	private final String prefixMap = "/assets";
	private Gson gson = new Gson();
	private String basepath = System
			.getProperty("xybase.ax.eai.domain.basepath");

	private static final Set<String> VALUES = new HashSet<String>(
			Arrays.asList(new String[] { "commons", "repressors", "activators",
					"originators" }));

	private final static Logger log = LogManager.getLogger(FileUtils.class);

	@RequestMapping(prefixMap + "/lslrt")
	public @ResponseBody String lslrt(
			@RequestParam("pwd") String pwd,
			@RequestParam("name") String name,
			@RequestParam(value = "processor", required = false) String processor) {
		List<Map<String, String>> tlmap;
		List<Map<String, String>> lmap;
		log.info("execution-processor of " + processor);
		if (!StringUtil.isNullOrBlank(processor) && processor.equals("svctx")) {
			tlmap = lsLrt(basepath, pwd + "/" + name);
			lmap = new ArrayList<Map<String, String>>();
			for (Map<String, String> map : tlmap) {
				String[] tpwd = map.get("pwd").split("/");
				if (tpwd.length > 2 && VALUES.contains(tpwd[2])) {
					lmap.add(map);
				} else if (map.get("type").equals("DIR")
						&& VALUES.contains(map.get("name"))) {
					lmap.add(map);
				}
			}
			return gson.toJson(lmap).toString();
		}
		lmap = lsLrt(basepath, pwd + "/" + name);
		return gson.toJson(lmap).toString();
	}

	private List<Map<String, String>> lsLrt(String prefixDirectory,
			String currentDirectory) {
		File fCurrentDirectory = new File(prefixDirectory + currentDirectory);
		List<Map<String, String>> result = new ArrayList<>();
		File[] filesList = fCurrentDirectory.listFiles();
		Map<String, Map<String, String>> dmap = new TreeMap<String, Map<String, String>>();
		Map<String, Map<String, String>> fmap = new TreeMap<String, Map<String, String>>();
		Map<String, String> map;
		for (File f : filesList) {
			map = new HashMap<>();
			map.put("name", f.getName());
			map.put("pwd", currentDirectory);
			if (f.isDirectory()) {
				map.put("type", "DIR");
				dmap.put(f.getName(), map);
			} else if (f.isFile() && f.getName().toUpperCase().endsWith("XML")) {
				map.put("type", "XML");
				fmap.put(f.getName(), map);
			}

		}
		for (Map<String, String> evalue : dmap.values()) {
			result.add(evalue);
		}
		for (Map<String, String> evalue : fmap.values()) {
			result.add(evalue);
		}
		return result;
	}

	@RequestMapping(prefixMap + "/cat")
	public @ResponseBody String concatenation(
			@RequestParam("asset") String asset) {
		return concatenation(basepath, asset);
	}

	private String concatenation(String prefixDirectory, String asset) {
		try {
			return new String(Files.readAllBytes(Paths.get(basepath + asset)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return e.toString();

		}
	}

	@RequestMapping(value = prefixMap + "/{action}/file", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String modify(@PathVariable int action,
			@RequestBody Context context) {
		File file = null;

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("action", action);

		try {

			boolean result = false;
			switch (action) {
			case 111:
				file = new File(basepath + context.getPath()
						+ context.getName());
				if (!file.exists()) {
					map.put("state", 0);
					map.put("remarks",
							"File: '" + context.getPath() + context.getName()
									+ "' is not exist!!");
				} else {
					result = file.delete();
					map.put("state", 1);
					map.put("remarks", context.getPath() + context.getName()
							+ " deleted!");
				}
				break;
			case 110:
				context.setName(context.getName().toLowerCase());
				file = new File(basepath + context.getPath()
						+ context.getName());
				if (!file.exists()) {
					result = save(file.getAbsolutePath(), context.getContent());
					if (result) {
						map.put("state", 1);
						map.put("remarks",
								context.getPath() + context.getName()
										+ " created!");
					} else {
						map.put("state", 0);
						map.put("remarks",
								context.getPath() + context.getName()
										+ " creation was failed!");
					}

				} else {
					map.put("state", 0);
					map.put("remarks",
							"File: '" + context.getPath() + context.getName()
									+ "' is exist!!");
				}

				break;
			case 101:
				context.setName(context.getName().toLowerCase());
				if ((!StringUtil.isNullOrBlank(context.getSourcePath()) && !context
						.getSourcePath().equals(context.getPath()))
						|| (!StringUtil.isNullOrBlank(context.getSourceName()) && !context
								.getSourceName().equals(context.getName()))) {

					File sourceFile = new File(basepath
							+ context.getSourcePath() + context.getSourceName());
					file = new File(basepath + context.getPath()
							+ context.getName());

					if (!sourceFile.exists() || file.exists()) {
						map.put("state", 0);
						if (file.exists()) {
							map.put("remarks", "File: '" + context.getPath()
									+ context.getName() + "' is exist!!");
						} else {
							map.put("remarks",
									"File: '" + context.getSourcePath()
											+ context.getSourceName()
											+ "' is exist!!");
						}

					} else {
						result = sourceFile.renameTo(file);
						if (result) {
							map.put("state", 1);
							map.put("remarks",
									"Rename from: '" + context.getSourcePath()
											+ context.getSourceName()
											+ "' to: '" + context.getPath()
											+ context.getName() + "'");
						} else {
							map.put("state", 0);
							map.put("remarks",
									"Failed Rename to: '" + context.getPath()
											+ context.getName() + "'");
						}
					}

				}

				file = new File(basepath + context.getPath()
						+ context.getName());

				System.out.println(file.getAbsolutePath());

				if (!file.exists()) {
					System.out.println(5);
					map.put("state", 0);
					map.put("remarks",
							"File: '" + context.getPath() + context.getName()
									+ "' is not exist!!");
				} else {
					if (concatenation(context.getPath(), context.getName())
							.equals(context.getContent())) {
						map.put("state", 1);
						map.put("remarks", "Content of '" + context.getPath()
								+ context.getName() + "' is equals!");
					} else {
						save(file.getAbsolutePath(), context.getContent());
					}
				}

				break;
			default:
				map.put("state", 0);
				map.put("remarks", "UNKNOWN ACTION " + action);
				break;
			}
		} catch (Exception e) {
			map.put("state", 0);
			map.put("remarks", e.getLocalizedMessage());
			e.printStackTrace();
		}
		map.put("timestamp", DateUtil.getTimestamp());
		return gson.toJson(map).toString();
	}

	private boolean save(String absolutePath, String content)
			throws IOException {
		FileWriter fw = new FileWriter(absolutePath);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
		return true;
	}

}
