/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Jun 18, 2015	3:06:43 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.util;

import com.google.common.base.Objects;
import com.xybase.ax.eai.archcomp.constant.InternalConstant;

/**
 * @note
 *
 */
public class TransformUtil {

	public static String asString(Object object) {
		return StringUtil.RegX.doubleQuotes + object
				+ StringUtil.RegX.doubleQuotes;
	}

	public static Object validAssigns(boolean valid, Object object) {
		if (valid)
			return object;
		return InternalConstant.XEAI_TRANSFORM_NO_INJECTION;
	}

	public static boolean isEquals(Object object, Object comparison) {
		return Objects.equal(object, comparison);
	}

	public String toString() {
		return "util";
	}

	public static Object asInteger(Object value) {
		Integer objectValue = Integer.parseInt((String) value);
		return objectValue;
	}

	public static Object ifNullAssigns(Object args, Object throwedObject) {
		return !StringUtil.isNull(args) ? args : throwedObject;
	}

	public static Object ifNullOrBlankAssigns(Object args, Object throwedObject) {
		return !StringUtil.isNull(args) && args.toString().equals("") ? args
				: throwedObject;
	}
}
