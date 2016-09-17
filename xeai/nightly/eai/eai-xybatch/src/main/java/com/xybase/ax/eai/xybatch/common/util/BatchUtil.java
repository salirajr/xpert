/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 8, 2016		3:20:47 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.common.util;

import org.joda.time.DateTime;

import com.xybase.ax.eai.archcomp.common.util.DateUtil;

/**
 * @author salirajr
 *
 */
public class BatchUtil {
	final static public long MILLISECOND = 1000;

	static public DateTime adjustTime(DateTime dateTime, int adjsutmentInSecond) {
		long adjustmentInMs = adjsutmentInSecond * MILLISECOND;
		long dtInMs = dateTime.getMillis();
		long diffInMs = dtInMs % adjustmentInMs;
		System.out.println(dtInMs+" = "+diffInMs+" of "+adjustmentInMs);
		dtInMs -= diffInMs;
		System.out.println(dtInMs);
		return new DateTime(dtInMs);
	}

	public static void main(String[] args) {
		DateTime dt = DateUtil.getNow();
		int adjustment = 100000;
		System.out.println(DateUtil.asString(dt) + " adjusted to second("
				+ adjustment + ") "
				+ DateUtil.asString(adjustTime(dt, adjustment)));
	}
}
