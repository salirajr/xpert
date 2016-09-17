/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 19, 2016		5:02:06 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.launcher.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xybase.ax.eai.xybatch.constant.BatchContstant;
import com.xybase.ax.eai.xybatch.launcher.LauncherImpl;
import com.xybase.ax.eai.xybatch.launcherbinder.LauncherBinderImpl;

/**
 * @author salirajr
 *
 */
public class DefaultLauncherJob implements Job {

	private final static Logger log = LogManager.getLogger(LauncherImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail detail = context.getJobDetail();
		if (detail.getKey().getGroup().equals(BatchContstant.GROUP_IDENTITY)) {
			log.debug(detail.getKey().getName()
					+ " exist in launcher: "
					+ LauncherBinderImpl.launcher.containsKey(detail.getKey()
							.getName()));
			LauncherBinderImpl.launcher.get(detail.getKey().getName())
					.execute();

		}

	}

}
