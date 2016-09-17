package com.xybase.ax.eai.archcomp.control.bus.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class BusRspUtil {

	public static final Gson gson = new Gson();

	public static String asResponse(String state, String message) {
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		temp.add(asPair("state", state));
		temp.add(asPair("message", message));
		return gson.toJson(temp);
	}

	public static String asResponse(String statusCode, String state,
			String message) {
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		temp.add(asPair("statusCode", statusCode));
		temp.add(asPair("state", state));
		temp.add(asPair("message", message));
		return gson.toJson(temp);
	}

	public static String asResponse(String statusCode, String state,
			String message, String... operands) {
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		temp.add(asPair("statusCode", statusCode));
		temp.add(asPair("state", state));
		temp.add(asPair("message", message));
		temp.add(asPair("operands", ""));
		for (int i = 0; i < operands.length; i++) {
			temp.add(asPair("", operands[i]));
		}
		return gson.toJson(temp);
	}

	public static String asInfo(String className, String info,
			String... controlBus) {
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		temp.add(asPair("className", className));
		temp.add(asPair("info", info));
		temp.add(asPair("controlBus", ""));
		for (int i = 0; i < controlBus.length; i++) {
			temp.add(asPair("", controlBus[i]));
		}
		return gson.toJson(temp);
	}

	public static String asInjection(String componentName, String commands) {
		return "BinderBus[componentName: " + componentName + ", commands: "
				+ commands + "]";
	}

	public static String asInjection(String commands) {
		return "BinderBus[ commands: " + commands + "]";
	}

	public static String asError(String statusCode, String state, String message) {
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		temp.add(asPair("statusCode", statusCode));
		temp.add(asPair("state", state));
		temp.add(asPair("message", message));
		return gson.toJson(temp);
	}

	public static String asBinderInfo(String binderValue, String message) {
		return "{\"binderValue\": \"" + binderValue + "\", \"message\": \""
				+ message + "\"}";
	}

	public static String asBaseInfo(String className, String infoKey,
			String... infos) {
		return null;
	}

	public static Map<String, String> asPair(String name, String value) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("value", value);
		return map;
	}

}
