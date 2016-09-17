/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * 04-01-2015   ----            Abdul Azis Nur          @Override toString()
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.xybase.ax.eai.archcomp.constant.XeaiConstants;

public class StringUtil {

	public static final String nullValue = null;
	public static final String trueValue = "TRUE";
	public static final String emptyChar = "";
	public static final Gson gson = new Gson();

	public class RegX {
		public static final String doubleQuotes = "\"";
		public static final String singleQuotes = "'";
		public static final String whitespace = "[\\s]+";
		public static final String comma = "[\\s\\,]+";
	}

	public static boolean isNullOrBlank(Object args) {
		return isNull(args) || args.toString().trim().equals("");
	}

	public static boolean isNull(Object args) {
		return args == null;
	}

	public static String toString(@SuppressWarnings("rawtypes") Map data) {
		if (data == null)
			return null;
		return Joiner.on("&").withKeyValueSeparator("=").join(data);
	}

	@SuppressWarnings("rawtypes")
	public static HashMap toMap(String data) {
		if (isNullOrBlank(data))
			return null;
		HashMap<String, String> result = new HashMap<String, String>();
		String[] pairs = data.split("&");
		String[] temp;
		for (String pair : pairs) {
			temp = pair.split("=");
			result.put(temp[0], temp[1]);
		}
		return result;
	}

	public static String concats(String... text) {
		String result = "";

		for (int i = 0; i < text.length; i++)
			result += text[i];

		return result;
	}

	public static String cast(Object args) {
		return isNullOrBlank(args) ? nullValue : args.toString();
	}

	public static <T> String asString(T args) {
		try {
			return (String) args;
		} catch (ClassCastException e) {
			return isNull(args) ? e.toString() : args.toString();
		}
	}

	public static String constraints(String arg0) {
		return RegX.singleQuotes + arg0 + RegX.singleQuotes;
	}

	public static String doubleQuoted(String arg0) {
		return constraints(arg0, RegX.doubleQuotes);
	}

	public static String constraints(String arg0, String comps) {
		return comps + arg0 + comps;
	}

	public static int toInteger(String arg0) {
		return Integer.parseInt(arg0);
	}

	public static String asFileURI(String path) {
		return "file:" + path;
	}

	public static String asDomainFileURI(String path) {
		return "file:" + XeaiConstants.BASEPATH + "/" + path;
	}

	public static String[] toArray(String strToConvert, String regex) {
		String[] result = strToConvert.split(regex);
		return result;
	}

	public static Object tolower(Object text) {
		if (text instanceof String && !isNullOrBlank(text))
			return text.toString().toLowerCase();
		return text;
	}

	public static Object toUPPER(Object text) {
		if (text instanceof String && !isNullOrBlank(text))
			return text.toString().toLowerCase();
		return text;
	}

	public static String asJson(String... keyValue) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < keyValue.length; i += 2) {
			map.put(keyValue[i], keyValue[i + 1]);
		}
		return gson.toJson(map, Map.class).toString();
	}

	public static String asJson(Map<String, String> keyValue) {
		return gson.toJson(keyValue, Map.class).toString();
	}

	public String toString() {
		return "stringUtil";
	}

}
