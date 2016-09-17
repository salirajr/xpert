package com.concept.ruleengine.reference.util;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class Extraction {

	public static class FromJSon {

		public static Gson gSon = new Gson();

		public static class InList {

			public static String getAt(String json, String key, int i) {
				return gSon.fromJson(json, JsonArray.class).get(i).getAsJsonObject().get(key).getAsString();
			}
			public static int getSize(String json){
				return gSon.fromJson(json, JsonArray.class).size();
			}
		}

		public static class InMap {
			public static String get(String json, String key) {
				return gSon.fromJson(json, JsonArray.class).get(0).getAsJsonObject().get(key).getAsString();
			}
			
		}
		

	}

	public static class FromHashList {

		public static Object getAt(List<Map<String, Object>> hashList, String key, int i) {
			return hashList.get(i).get(key);
		}

	}
}
