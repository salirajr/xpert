/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 4, 2015	7:59:54 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.lookup.util;

import java.util.Hashtable;
import java.util.Iterator;

import com.xybase.ax.eai.archcomp.lookup.Lookup;

/**
 * @note
 *
 */
public class LookupUtil {

	// Only for Context Lookup JAVAX
	public static Hashtable<Object, Object> toHashtable(Lookup lookup) {
		Hashtable<Object, Object> result = new Hashtable<Object, Object>();
		Iterator<String> keys = lookup.keySet().iterator();
		String key;
		while (keys.hasNext()) {
			key = keys.next();
			result.put(key, lookup.get(key));
		}
		return result;
	}

	@Override
	public String toString() {
		return "lookupUtil";
	}
}
