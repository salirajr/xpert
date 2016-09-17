/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 30092014		Jovi Rengga Salira		Initial Creation, 
 * 										Powered up common casting operation in Airport Base-logic!,
 * 										Only object based operation gene heres.
 * **************************************************************************************
 */
package com.concept.ruleengine.common.util;

import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;

public class CustomUtil {

	// JS, useful on LHS Rules
	public static boolean validate(Object boolean_) {
		return Boolean.valueOf(boolean_.toString());

	}
	
	public static boolean isEqualed(Object dividen, Object division){
		return true;
	}

	public static int toNumber(Object number_) {
		return Integer.valueOf(number_.toString());

	}

	public static double toDouble(Object number_) {
		return Double.valueOf(number_.toString());

	}

	public static boolean isNull(Object objctToTest) {
		return objctToTest == null;
	}

	public static boolean isNull(Object objctToTest, boolean revokeException,
			String key) {
		boolean iNoB = objctToTest == null;
		if (iNoB && revokeException)
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, Null Pointer [input key:"
							+ key + "]");
		return iNoB;
	}

	public static Object NVL(Object objctToTest, Object nvlReference) {
		return objctToTest == null ? nvlReference : objctToTest;
	}

	public static Object ifNullOrZeroed(Object objctToTest, int zeroedReference) {
		return isNull(objctToTest) ? objctToTest
				: toNumber(objctToTest) == 0 ? zeroedReference : objctToTest;
	}

	public static boolean isNullOrBlank(Object objectToTest) {
		return objectToTest == null || objectToTest.toString().equals("");
	}

}
