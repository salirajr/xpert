/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 10, 2015	1:40:37 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @note
 *
 */
public class DateUtil {

	final static public String DEFAULT_TPATTERN = "MM-dd-yyyy HH:mm:ss.SSS";

	public static String getTimestamp() {
		DateTimeFormatter formater = DateTimeFormat
				.forPattern(DEFAULT_TPATTERN);
		return formater.print(getNow());
	}

	public static String getTimestamp(String format) {
		DateTimeFormatter formater = DateTimeFormat.forPattern(format);
		return formater.print(new DateTime());
	}

	public static String format(String time, String format, String toFormat) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		DateTime jTime = formatter.parseDateTime(time);
		DateTimeFormatter convertFormatter = DateTimeFormat
				.forPattern(toFormat);
		return convertFormatter.print(jTime);
	}

	public static DateTime getNow() {
		return DateTime.now();
	}

	public static DateTime format(String time, String format) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		return formatter.parseDateTime(time);
	}

	public static String asString(DateTime time, String format) {
		DateTimeFormatter convertFormatter = DateTimeFormat.forPattern(format);
		return convertFormatter.print(time);
	}

	public static String asString(DateTime time) {
		DateTimeFormatter convertFormatter = DateTimeFormat
				.forPattern(DEFAULT_TPATTERN);
		return convertFormatter.print(time);
	}

	public static String asString(DateTime time, String format,
			String timezoneId) {
		DateTimeFormatter convertFormatter = DateTimeFormat.forPattern(format);
		convertFormatter.withZone(DateTimeZone.forID(timezoneId));
		return convertFormatter.print(time);
	}

	public static int getMinuteGap(DateTime dateTime) {
		DateTime now = DateTime.now();
		Minutes minutes = Minutes.minutesBetween(dateTime, now);
		return minutes.getMinutes();
	}

	@Override
	public String toString() {
		return "dateUtil";
	}
}
