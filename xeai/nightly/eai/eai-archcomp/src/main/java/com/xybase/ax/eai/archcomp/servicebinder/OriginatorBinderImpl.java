/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Nov 3, 2015	8:56:59 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.servicebinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;

import com.xybase.ax.eai.archcomp.common.util.CustomUtil;
import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.constant.XeaiConstants;
import com.xybase.ax.eai.archcomp.control.bus.constant.BinderConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.larik.Larik;
import com.xybase.ax.eai.archcomp.lookup.Lookup;
import com.xybase.ax.eai.archcomp.servicebinder.executor.Producer;
import com.xybase.ax.eai.archcomp.servicebinder.executor.ProducerImpl;

public class OriginatorBinderImpl implements ServiceBinder {

	private final static Logger log = LogManager
			.getLogger(OriginatorBinderImpl.class);

	private Lookup lCtxResources;
	private Larik baseCtxResources;

	private AbstractApplicationContext baseContext;

	private Map<String, Producer> bindedProducer;

	public OriginatorBinderImpl() {
		// TODO Auto-generated constructor stub
		this.bindedProducer = new HashMap<String, Producer>();
	}

	@Override
	public void setBinderKey(String headerKey) {
		// TODO Auto-generated method stub
		log.error(BusRspUtil.asInjection("setBinderKey(" + headerKey
				+ ") failed, " + this.getClass().getName()
				+ " doesn't required header mappings!"));
	}

	@Override
	public void setBaseCtxResources(Larik baseContextResource) {
		// TODO Auto-generated method stub
		this.baseCtxResources = baseContextResource;
		baseContext = new ClassPathXmlApplicationContext(
				CustomUtil.asDomainFileURI(this.baseCtxResources.toArray()));
	}

	@Override
	public void setLCtxResources(Lookup contextResource) {
		// TODO Auto-generated method stub

		this.lCtxResources = contextResource;
		for (Map.Entry<String, String> entry : this.lCtxResources.entrySet()) {
			this.bindedProducer
					.put(entry.getKey(),
							new ProducerImpl(
									baseContext,
									StringUtil.asDomainFileURI(entry.getValue()),
									StringUtil
											.asDomainFileURI(XeaiConstants.LOCALIZED_CONTROLBUS)));

		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Message inject(String binderValue, String command) {
		// TODO Auto-generated method stub
		log.info(BusRspUtil.asInjection(binderValue, "inject(" + command + ")"));
		return this.bindedProducer.get(binderValue).inject(command);
	}

	@Override
	public String alter(String binderValue, String contextFile) {
		// TODO Auto-generated method stub
		log.info(BusRspUtil.asInjection(binderValue, "alter(" + contextFile
				+ ")"));
		return this.bindedProducer.get(binderValue).alter(
				StringUtil.asDomainFileURI(contextFile));
	}

	@Override
	public String info(String binderValue) {
		// TODO Auto-generated method stub
		return this.bindedProducer.get(binderValue).info();
	}

	@Override
	public String alterBase(String contextFile, boolean isBaseAltered) {
		// TODO Auto-generated method stub
		log.info(BusRspUtil.asInjection("alterBase(" + contextFile + ")"));
		baseContext = new ClassPathXmlApplicationContext(
				new String[] { StringUtil.asDomainFileURI(contextFile) }, true,
				baseContext);

		if (isBaseAltered) {
			setLCtxResources(this.lCtxResources);
		}
		return BusRspUtil.asResponse(BinderConstant.code.ALTERED_SUCCESS,
				"Note!!, keep the context clean!, sync. on every changes!");
	}

	@Override
	public String add(String binderValue, String contextFile) {
		// TODO Auto-generated method stub
		log.info(BusRspUtil.asInjection("add(" + binderValue + ", '"
				+ contextFile + "')"));
		if (bindedProducer.containsKey(binderValue))
			return BusRspUtil.asError(BinderConstant.code.ERR_ADDED,
					BinderConstant.state.ERR_ADDED, "Binded value is exist!!");
		else {
			log.info("is baseContext active? " + baseContext.isActive());
			log.info("is baseContext running? " + baseContext.isRunning());
			this.bindedProducer
					.put(binderValue,
							new ProducerImpl(
									baseContext,
									StringUtil.asDomainFileURI(contextFile),
									StringUtil
											.asDomainFileURI(XeaiConstants.LOCALIZED_CONTROLBUS)));
			return BusRspUtil.asError(BinderConstant.code.ADDED_SUCCESS,
					BinderConstant.state.REFRESHED,
					BinderConstant.message.ADD_SUCCESS);
		}

	}

	@Override
	public String stop(String binderValue) {
		// TODO Auto-generated method stub
		log.info(BusRspUtil.asInjection("stop(" + binderValue + ")"));
		return BusRspUtil.asBinderInfo(binderValue,
				bindedProducer.get(binderValue).stop());
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		log.info(BusRspUtil.asInjection("stop()"));
		baseContext.destroy();
		baseContext = null;
		for (Entry<String, Producer> entry : this.bindedProducer.entrySet()) {
			log.info(entry.getKey() + " is STOPPING!");
			entry.getValue().stop();
			log.info(entry.getKey() + " STOPPED");
		}
		return BusRspUtil.asResponse(BinderConstant.state.DESTROYED,
				BinderConstant.message.DESTROYED);
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		stop();
	}

	@Override
	public String refresh() {
		// TODO Auto-generated method stub
		stop();
		baseCtxResources.reinitialized();
		setBaseCtxResources(this.baseCtxResources);
		lCtxResources.reinitialized();
		setLCtxResources(this.lCtxResources);
		return BusRspUtil.asResponse(BinderConstant.state.REFRESHED,
				BinderConstant.message.REFRESHED);
	}

	@Override
	public String refresh(String binderValue) {
		// TODO Auto-generated method stub
		if (baseContext == null)
			return BusRspUtil.asResponse(BinderConstant.code.ERR_REFRESHED,
					BinderConstant.state.ERR_REFRESHED,
					BinderConstant.message.CONTEXT_DESTROYED);
		else {
			return BusRspUtil.asInjection(binderValue,
					bindedProducer.get(binderValue).refresh(this.baseContext));
		}
	}

	@Override
	public String start(String binderValue) {
		// TODO Auto-generated method stub
		if (baseContext == null)
			return BusRspUtil.asResponse(BinderConstant.code.ERR_REFRESHED,
					BinderConstant.state.ERR_REFRESHED,
					BinderConstant.message.CONTEXT_DESTROYED);
		else
			return BusRspUtil.asInjection(binderValue,
					bindedProducer.get(binderValue).start());
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		if (baseContext.isActive())
			return BusRspUtil.asResponse(BinderConstant.state.ERR_START,
					BinderConstant.message.CONTEXT_UP);
		else {
			setBaseCtxResources(this.baseCtxResources);
			setLCtxResources(this.lCtxResources);
			return BusRspUtil.asResponse(BinderConstant.state.REFRESHED,
					BinderConstant.message.REFRESHED);
		}

	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return baseContext.isActive();
	}

	@Override
	public String inject(String command) {
		// TODO Auto-generated method stub
		log.info("Command-In: " + command);
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<String, Producer> entry : this.bindedProducer.entrySet()) {
			entry.getValue().inject(command);
		}
		return StringUtil.asJson(map);
	}

	@Override
	public void setDefaultBinder(String binderKey) {
		// TODO DO NOTHING
		log.info("No binder-router available on this container!");
	}

	@Override
	public String info() {
		List<Map<String, String>> infos = new ArrayList<Map<String, String>>();
		infos.add(BusRspUtil.asPair("className", this.getClass().getName()));
		infos.add(BusRspUtil.asPair("state", isAlive() ? "Active" : "Inactive"));
		infos.add(BusRspUtil.asPair("Base-Context", ""));
		for (int i = 0; i < this.baseCtxResources.size(); i++) {
			infos.add(BusRspUtil.asPair("", this.baseCtxResources.get(i)));
		}
		infos.add(BusRspUtil.asPair("Binder-Key", "Context"));
		for (Map.Entry<String, String> entry : this.lCtxResources.entrySet()) {
			infos.add(BusRspUtil.asPair(entry.getKey(), entry.getValue()));
		}
		return BusRspUtil.gson.toJson(infos);
	}

}
