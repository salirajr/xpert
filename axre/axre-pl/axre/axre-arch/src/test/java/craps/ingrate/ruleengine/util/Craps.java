package craps.ingrate.ruleengine.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.concept.ruleengine.common.util.AirportMath;
import com.concept.ruleengine.common.util.DateUtil;
import com.concept.ruleengine.common.util.StringUtil;

public class Craps {
	public static void mainConvert(String[] args) {
		int dayOfOpt = DateUtil.getDayOfWeek();
		System.out.println(dayOfOpt);

		Object timestamp = "2015-08-31T13:41:00.000Z";
		Date dt = StringUtil.toDate(timestamp, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
		System.out.println(DateUtil.getDayOfWeek(dt));
	}

	public static void main(String[] args) {

		Object timestamp = "2015-05-26T13:41:00.000Z";
		timestamp = "2015-05-31T12:10:00.000Z";

		Timestamp ts = StringUtil.toTimestamp(timestamp,
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		String s = DateUtil.toString(AirportMath.leapTimestamp(ts, 2),
				"yyyy-MM-dd HH:mm:ss");

		s = DateUtil.toString(StringUtil.toTimestamp(timestamp,
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd HH:mm:ss");
		System.out.println(s);
		// String eta = DateUtil.toString(
		// StringUtil.toTimestamp(timestamp,
		// "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd HH:mm:ss.SSS");
		//
		// System.out.println(eta);

	}

	public static void mainLeaps(String[] args) {

		Object timestamp = "2015-05-26 13:41:00";

		Timestamp etd = StringUtil
				.toTimestamp(timestamp, "yyyy-MM-dd HH:mm:ss");

		String d = DateUtil.toString(AirportMath.leapTimestamp(etd, 20, -1),
				"yyyy-MM-dd HH:mm:ss");

		System.out.println(d);

	}

	public static void mainSubstring(String[] args) {
		String stand = "ABC";
		System.out.println(stand.substring(1, 3));
		System.out.println(stand.substring(0, 1));
	}

	public static void mainMath(String[] args) {
		String num = "9.0";
		int inum = (int) Math.floor(Double.valueOf(num) * 60);
		System.out.println(inum);
	}

	public static void mainStringMisc(String[] args) {
		"'A','B'".contains("A");
	}

	public static void mainMisc(String[] args) {

		int x = 1;
		if (x < 1)
			System.out.println("zero!!");
	}

	public static void mainCalender(String[] args) {

		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

		System.out.println(now.getTime());
	}
}
