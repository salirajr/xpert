/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 30092014		Jovi Rengga Salira		Initial Creation, 
 * 										Powered up common casting operation in Airport Base-logic!,
 * 										Only String based operation gene heres.
 * **************************************************************************************
 */
package com.concept.ruleengine.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;

public class StringUtil {

	public class CustomRegex {
		public static final String WHITESPACE = "[\\s]+";
		public static final String COMMA = "[\\s\\,]+";
		public static final String ISNUMERIC = "[-|.]?[0-9]+([.]{1}[0-9]+)?";
	}

	public static final String valueNull = null;

	public static Date toDate(Object date, String format) {

		isNullOrBlank(date, true, "date");
		isNullOrBlank(format, true, "format");

		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse((String) date);
		} catch (ParseException e) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, String to Date Format Exception[input:" + date
							+ ", format: " + format.toString() + "]");
		} catch (IllegalArgumentException iae) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Inacceptable Time Format Exception[format: "
							+ format.toString() + "]");
		}

	}

	public static Timestamp toTimestamp(Object timestamp, String format) {

		isNullOrBlank(timestamp, true, "date");
		isNullOrBlank(format, true, "format");

		DateFormat df = new SimpleDateFormat(format);
		try {
			Date dateActual = df.parse(timestamp.toString());
			return new Timestamp(dateActual.getTime());
		} catch (ParseException e) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, String to Timestamp Format Exception[input:" + timestamp
							+ ", format: " + format.toString() + "]");
		} catch (IllegalArgumentException iae) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Inacceptable Timestamp Format Exception[format: "
							+ format.toString() + "]");
		}

	}

	public static String fromDate(Date date, String format) {
		isNullOrBlank(format, true, "format");
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);

	}

	// JS, revokeException: is exception handler, for mandatory constrains!
	public static boolean isNullOrBlank(Object strToTest, boolean revokeException, String key) {
		boolean iNoB = strToTest == null || strToTest.toString().trim().equals("");
		if (iNoB && revokeException)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Null or Blank Value Exception[input key:" + key + "]");
		return iNoB;
	}

	public static boolean isNullOrBlank(String strToTest) {
		return (strToTest == null || strToTest.trim().equals(""));
	}

	public static String ifNullOrBlank(String strToTest, String nullOrBlankReference) {
		return (strToTest == null || strToTest.trim().equals("")) ? nullOrBlankReference : strToTest;
	}

	public static String[] toArray(String strToConvert, String regex) {
		String[] result = strToConvert.split(regex);
		return result;
	}

	public static List<String> toList(String strToConvert, String regex) {
		if (strToConvert.trim().equals(""))
			return new ArrayList<String>();
		return Arrays.asList(strToConvert.split(regex));
	}

	public static String toString(Object obj) {
		return CustomUtil.isNull(obj) ? StringUtil.valueNull : obj.toString();
	}

	public static boolean isNumeric(String numeric) {
		// \\d+ ^[+-]?((\\d+(\\.\\d*)?)|(\\.\\d+))
		return java.util.regex.Pattern.matches(CustomRegex.ISNUMERIC, numeric);
	}

	public static int toInt(String ints) {
		return (int) Math.floor(Double.valueOf(ints));
	}

}
