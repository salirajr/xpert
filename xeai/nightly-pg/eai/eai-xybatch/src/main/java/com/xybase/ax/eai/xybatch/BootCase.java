/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 11, 2016		2:02:31 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xybase.ax.eai.archcomp.common.util.CustomUtil;
import com.xybase.ax.eai.archcomp.common.util.DateUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.common.util.TransformUtil;
import com.xybase.ax.eai.xybatch.launcherbinder.LauncherBinderImpl;

/**
 * @author salirajr
 *
 */
public class BootCase {

	private ClassPathXmlApplicationContext applicationContext;

	static private String currentLocation;

	static {
		currentLocation = new File(BootCase.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath()).getParentFile()
				.getAbsolutePath();
		System.setProperty("current.location", currentLocation); 
	}

	/**
	 * 
	 */
	public void createRuntime() {
		// TODO Auto-generated constructor stub
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (applicationContext.isActive()
						&& applicationContext.isRunning()) {
					try {
						applicationContext.getBean(LauncherBinderImpl.class)
								.remove();
						System.out.println("CP-"
								+ DateUtil.getNow()
								+ "-BATCH LAUNCHER['"
								+ TransformUtil.ifNullAssigns(
										System.getProperty("eaixybatch.name"),
										"0") + "'] IS DESTROYED!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		});

		this.applicationContext = new ClassPathXmlApplicationContext(
				CustomUtil.asDomainFileURI("INV-xybatch/local-archs.xml"));

	}

	static private boolean loadSystemProperties() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(currentLocation
					+ "/runtime.properties")));
			for (String key : properties.stringPropertyNames()) {
				if (!StringUtil.isNullOrBlank(key))
					System.setProperty(key, properties.getProperty(key));
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	static private boolean initializeLogger() {
		try {
			LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager
					.getContext(false);

			// this will force a reconfiguration
			loggerContext.setConfigLocation(new File(currentLocation
					+ "/log4j2.xml").toURI());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * [0] : xparam, [1] : xparam, [2] : xparam, [3] : xparam, [4] : xparam
	 * */
	static public void main(String[] args) {

		if (initializeLogger() && loadSystemProperties()) {
			System.out
					.println("CP-" + DateUtil.getNow() + "-BATCH LAUNCHER['"
							+ System.getProperty("eaixybatch.name")
							+ "'] IS STARTING!");
			new BootCase().createRuntime();
		} else {
			System.out.println("CP-"
					+ DateUtil.getNow()
					+ "-BATCH LAUNCHER['"
					+ TransformUtil.ifNullAssigns(
							System.getProperty("eaixybatch.name"), "0")
					+ "'] IS NOT STARTING!");
		}

	}
}
