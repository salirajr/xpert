/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 3, 2015	2:14:22 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.context.lookup.javax;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @note
 *
 */
public class ContextLookup {

	private String home;
	private final Context context;

	public ContextLookup(String lookupHome,
			final Hashtable<Object, Object> lookupProperties)
			throws NamingException {
		this.home = lookupHome;
		this.context = new InitialContext(lookupProperties);
	}

	public Object lookup() throws NamingException {
		return context.lookup(home);
	}

	public void close() throws NamingException {
		context.close();
	}

	@SuppressWarnings("rawtypes")
	public Hashtable getEnvironment() throws NamingException {
		return this.context.getEnvironment();
	}

	public String getHome() {
		return this.home;
	}

}
