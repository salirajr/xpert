/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 20, 2016		9:56:02 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.launcher.detail;

/**
 * @author salirajr
 *
 */
public class LauncherDetail {

	private String cronSchedule;
	private int interval;
	private boolean xparVariable;
	private String xparContextName;

	/**
	 * 
	 */
	public LauncherDetail() {
		// TODO Auto-generated constructor stub
		this.cronSchedule = null;
		this.interval = -1;
		this.xparVariable = false;
		this.xparContextName = null;
	}

	public String getCronSchedule() {
		return cronSchedule;
	}

	public void setCronSchedule(String cronSchedule) {
		this.cronSchedule = cronSchedule;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public boolean isXparVariable() {
		return xparVariable;
	}

	public void setXparVariable(boolean xparVariable) {
		this.xparVariable = xparVariable;
	}

	public String getXparContextName() {
		return xparContextName;
	}

	public void setXparContextName(String xparContextName) {
		this.xparContextName = xparContextName;
	}

}
