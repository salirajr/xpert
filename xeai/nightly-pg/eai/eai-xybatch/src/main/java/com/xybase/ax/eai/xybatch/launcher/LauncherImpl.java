/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 19, 2016		5:28:52 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.launcher;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xybase.ax.eai.archcomp.common.util.CustomUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.XeaiConstants;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.xybatch.constant.BatchContstant;
import com.xybase.ax.eai.xybatch.launcher.detail.LauncherDetail;
import com.xybase.ax.eai.xybatch.launcher.job.DefaultLauncherJob;

/**
 * @author salirajr
 *
 */
public class LauncherImpl implements Launcher {

	private final static Logger log = LogManager.getLogger(LauncherImpl.class);

	private String[] launcherCtx;
	private ClassPathXmlApplicationContext baseContext;
	private JobLauncher jobLauncher;
	private Job job;
	private String name;
	private LauncherDetail launcherDetail;

	/**
	 * 
	 */
	public LauncherImpl() {
		// TODO Auto-generated constructor stub
		this.launcherDetail = new LauncherDetail();
		this.name = null;
	}

	/**
	 * 
	 */
	public LauncherImpl(String name, String[] ctx) {
		// TODO Auto-generated constructor stub

		this.name = name;
		setContext(ctx);
	}

	public void setLauncherDetail(LauncherDetail detail) {
		this.launcherDetail = detail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.xybatch.joblauncher.JobLauncher#execute()
	 */
	@Override
	public BatchStatus execute() {
		// TODO Auto-generated method stub
		JobExecution execution;
		try {
			log.debug(this.name + " is executing..");
			execution = jobLauncher.run(job, retrieveParameters());
			return execution.getStatus();
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BatchStatus.UNKNOWN;

	}

	public void setContext(String... launcherCtx) {
		// TODO Auto-generated method stub
		this.launcherCtx = launcherCtx;
		if (StringUtil.isNullOrBlank(this.name)) {
			throw new InternalErrorRuntimeException("Null Launcher Name");
		} else {
			try {
				log.info("bindedLauncher[" + name + "] initialize..");
				baseContext = new ClassPathXmlApplicationContext(
						this.launcherCtx);

				this.launcherDetail = this.baseContext.getBean(
						"launcherDetail", LauncherDetail.class);

				this.jobLauncher = this.baseContext.getBean(
						BatchContstant.LAUNCHER_NAME, JobLauncher.class);
				this.job = (Job) this.baseContext.getBean(this.name);
				activate();
			} catch (Exception e) {
				log.error("Launcher[" + this.name + "] :" + e.getMessage());
			}
		}

	}

	public void activate() {
		Trigger trigger;
		if (!StringUtil.isNullOrBlank(launcherDetail.getCronSchedule())) {
			trigger = TriggerBuilder
					.newTrigger()
					.withSchedule(
							CronScheduleBuilder.cronSchedule(launcherDetail
									.getCronSchedule())).build();

		} else if (launcherDetail.getInterval() > 0) {
			trigger = TriggerBuilder
					.newTrigger()
					.withSchedule(
							SimpleScheduleBuilder
									.simpleSchedule()
									.withIntervalInSeconds(
											launcherDetail.getInterval())
									.repeatForever()).build();
		} else
			throw new InternalErrorRuntimeException("Launcher[" + this.name
					+ "] is not starting.. Schedule Cron/Interval is not set..");

		JobDetail job = JobBuilder.newJob(DefaultLauncherJob.class)
				.withIdentity(name, BatchContstant.GROUP_IDENTITY).build();

		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			log.info("bindedLauncher[" + name + "] activated..");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("Launcher[" + this.name + "] :" + e.getMessage());
		}

	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.xybatch.launcher.Launcher#status()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		if (baseContext != null
				&& (baseContext.isActive() || baseContext.isRunning()))
			baseContext.destroy();
		baseContext = null;
		log.error("Launcher[" + this.name + "] is stopped..");
	}

	private JobParameters retrieveParameters() {

		JobParametersBuilder builder = new JobParametersBuilder();

		String xparPath = System.getProperty("eaixybatch.xpar.basepath") + "/"
				+ this.name.toLowerCase() + "-xpar.xml";
		log.info(xparPath + " [XPAR] loaded!");
		if (CustomUtil.isFileExist(XeaiConstants.BASEPATH + xparPath)) {
			try {
				@SuppressWarnings({ "unchecked", "resource" })
				Map<String, Object> xpar = new ClassPathXmlApplicationContext(
						StringUtil.asDomainFileURI(xparPath)).getBean("xPar",
						Map.class);
				for (Entry<String, Object> entry : xpar.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					log.debug(key + ":" + value);
					if (value instanceof String) {
						builder.addString(key, (String) value);
					} else if (value instanceof Float
							|| value instanceof Double) {
						builder.addDouble(key, ((Number) value).doubleValue());
					} else if (value instanceof Integer
							|| value instanceof Long) {
						builder.addLong(key, ((Number) value).longValue());
					}
				}
			} catch (Exception e) {
				log.error(this.name + " XPAR failed to load.. cause: "
						+ e.getMessage());
			}

		} else
			log.error(this.name
					+ " job is assigned to have [XPAR], context of [XPAR] is not exist..");

		return builder.toJobParameters();

	}

}
