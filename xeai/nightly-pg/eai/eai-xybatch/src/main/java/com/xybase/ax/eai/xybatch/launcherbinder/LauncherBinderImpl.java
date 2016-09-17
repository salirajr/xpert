/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 19, 2016		5:18:57 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.launcherbinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xybase.ax.eai.archcomp.common.util.CustomUtil;
import com.xybase.ax.eai.archcomp.larik.Larik;
import com.xybase.ax.eai.archcomp.lookup.Lookup;
import com.xybase.ax.eai.xybatch.launcher.Launcher;
import com.xybase.ax.eai.xybatch.launcher.LauncherImpl;

/**
 * @author salirajr
 *
 */
public class LauncherBinderImpl implements LauncherBinder {

	private final static Logger log = LogManager
			.getLogger(LauncherBinderImpl.class);

	private Lookup lCtxResources;
	private Larik baseCtxResources;

	static public Map<String, Launcher> launcher;

	static {
		launcher = new HashMap<String, Launcher>();
	}

	public void setBaseCtxResources(Larik baseCtxResources) {
		this.baseCtxResources = baseCtxResources;
	}

	public void setLCtxResources(Lookup contextResource) {
		this.lCtxResources = contextResource;
		for (Map.Entry<String, String> entry : this.lCtxResources.entrySet()) {
			Larik temp = this.baseCtxResources.clones();
			temp.add(entry.getValue());
			launcher.put(entry.getKey(), new LauncherImpl(entry.getKey(),
					CustomUtil.asDomainFileURI(temp.toArray())));
		}
		log.info("bindedLauncher is Set!");
	}

	/* (non-Javadoc)
	 * @see com.xybase.ax.eai.xybatch.launcherbinder.LauncherBinder#execute()
	 */
	@Override
	public long execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.xybase.ax.eai.xybatch.launcherbinder.LauncherBinder#remove(java.lang.String)
	 */
	@Override
	public long remove(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.xybase.ax.eai.xybatch.launcherbinder.LauncherBinder#remove()
	 */
	@SuppressWarnings("static-access")
	@Override
	public long remove() {
		log.info("destroy bindedLauncher invoked");
		for (Entry<String, Launcher> entry : this.launcher.entrySet()) {
			log.info(entry.getKey() + " is STOPPING!");
			launcher.get(entry.getKey()).stop();
			log.info(entry.getKey() + " STOPPED");
		}
		return 0;
	}
}
