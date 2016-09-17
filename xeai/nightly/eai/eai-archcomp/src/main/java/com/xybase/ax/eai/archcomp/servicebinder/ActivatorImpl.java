package com.xybase.ax.eai.archcomp.servicebinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;

import com.xybase.ax.eai.archcomp.common.util.CustomUtil;
import com.xybase.ax.eai.archcomp.constant.XeaiConstants;
import com.xybase.ax.eai.archcomp.control.bus.ExecutorBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.BinderConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.exception.ApplicationRuntimeException;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.larik.Larik;
import com.xybase.ax.eai.archcomp.servicebinder.executor.Executor;
import com.xybase.ax.eai.archcomp.servicebinder.executor.ExecutorImpl;

@SuppressWarnings("rawtypes")
public class ActivatorImpl implements Activator<Message>, ExecutorBus,
		DisposableBean {

	private final static Logger log = LogManager.getLogger(ActivatorImpl.class);

	private Executor executor;

	private Larik baseCtxResources;
	private AbstractApplicationContext baseCtx;

	private boolean onDeploy = false;

	@Override
	public Message activate(Message<Message> message) {
		// TODO Auto-generated method stub
		return executor.execute(message);
	}

	@Override
	public void setBaseCtxResources(Larik baseContextResource) {
		// TODO Auto-generated method stub
		this.onDeploy = true;
		this.baseCtxResources = baseContextResource;
		this.baseCtxResources.add(XeaiConstants.LOCALIZED_CONTROLBUS);
		baseCtx = new ClassPathXmlApplicationContext(
				CustomUtil.asDomainFileURI(this.baseCtxResources.toArray()));
		executor = new ExecutorImpl(baseCtx);
		this.onDeploy = false;
	}

	@Override
	public Message inject(String command) {
		// TODO Auto-generated method stub
		if (onDeploy)
			throw new ApplicationRuntimeException(
					"Service in on deployment!, Please Contact your administrator to hit confirmation",
					new InternalErrorRuntimeException(
							"Message throws to Exception handlers"));
		return executor.inject(command);
	}

	@Override
	public String alter(String contextFile) {
		// TODO Auto-generated method stub
		if (!this.baseCtxResources.contains(contextFile))
			this.baseCtxResources.add(contextFile);

		onDeploy = true;

		stop();
		executor.stop();
		setBaseCtxResources(this.baseCtxResources);
		executor = new ExecutorImpl(baseCtx);
		return BusRspUtil.asResponse(BinderConstant.code.ALTERED_SUCCESS,
				BinderConstant.message.ALTERED_SUCCESS);
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		log.info("Initiates for STOPPING: "
				+ Arrays.toString(baseCtxResources.toArray()));
		baseCtx.destroy();
		executor.stop();
		log.info("STOPPED done for "
				+ Arrays.toString(baseCtxResources.toArray()));
		return BusRspUtil.asResponse(BinderConstant.state.DESTROYED,
				BinderConstant.message.DESTROYED);
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		return BusRspUtil
				.asResponse(BinderConstant.code.ERR_START,
						BinderConstant.state.ERR_START,
						BinderConstant.message.DISABLED);
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return executor.isAlive();
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		stop();
	}

	@ManagedOperation
	public String refresh() {
		// TODO Auto-generated method stub
		stop();
		this.baseCtxResources.reinitialized();
		setBaseCtxResources(this.baseCtxResources);
		return BusRspUtil.asResponse(BinderConstant.state.REFRESHED,
				BinderConstant.message.REFRESHED);
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		List<Map<String, String>> infos = new ArrayList<Map<String, String>>();
		infos.add(BusRspUtil.asPair("className", this.getClass().getName()));
		infos.add(BusRspUtil.asPair("state", isAlive() ? "Active" : "Inactive"));
		infos.add(BusRspUtil.asPair("Context", ""));
		for (int i = 0; i < this.baseCtxResources.size(); i++) {
			infos.add(BusRspUtil.asPair("", this.baseCtxResources.get(i)));
		}
		return BusRspUtil.gson.toJson(infos);
	}
}
