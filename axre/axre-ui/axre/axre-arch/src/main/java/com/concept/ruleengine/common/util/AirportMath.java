/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 28092014		Jovi Rengga Salira		Initial Creation, 
 * 										Generic Math Function Class, to simplified Rule code.
 * 										Powered up common Math operation in Airport Base-logic!
 * **************************************************************************************
 */
package com.concept.ruleengine.common.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class AirportMath {

	public static Timestamp leapTimestamp(Timestamp time, int leapMinute) {

		Timestamp leapTimestamp = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());

		int contactOpenTime = leapMinute * 60;
		cal.add(Calendar.SECOND, contactOpenTime);
		leapTimestamp = new Timestamp(cal.getTimeInMillis());

		return leapTimestamp;
	}

	public static Timestamp leapTimestamp(Timestamp time, int leapMinute, int drift) {

		Timestamp leapTimestamp = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());

		// drift : assign -1 to make it backward
		int contactOpenTime = leapMinute * 60 * drift;
		cal.add(Calendar.SECOND, contactOpenTime);
		leapTimestamp = new Timestamp(cal.getTimeInMillis());

		return leapTimestamp;
	}

	public static Date dateOperation(Date date, int leapType, int leapValue) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(leapType, leapValue);
		return cal.getTime();
	}

}
