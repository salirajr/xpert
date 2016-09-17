/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * 04-01-2015   ----            Abdul Azis Nur          @Override toString() sets
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.lookup.dao.LookupDao;

public class Lookup extends HashMap<String, String> implements CrowbarBus {

	@SuppressWarnings("unused")
	private final static Logger log = LogManager.getLogger(Lookup.class);
	private LookupDao factory;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2375209295637084560L;
	private String[] key;
	private String sets;

	public Lookup() {
		// Do nothing here!
		// defined only for Spring-Bean
	}

	public Lookup(String... key) {
		this.key = key;

	}

	public Lookup(LookupDao dao, String... key) {
		this.key = key;
		this.factory = dao;

	}

	public void setKey(String... key) {
		this.key = key;
	}

	public String toString() {
		return this.sets;
	}

	/**
	 * @return the sets
	 */
	public String getSets() {
		return sets;
	}

	/**
	 * @param sets
	 *            the sets to set
	 */
	public void setSets(String sets) {
		this.sets = sets;
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return this.isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		Map<String, String> temp = factory.getRaw(this.key);
		this.putAll(temp);
		return BusRspUtil.asResponse(
				CrowbarConstant.state.INITIALIZATION_SUCCEED, toString());
	}

	@Override
	public String reinitialized(String operand) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATIONP,
				CrowbarConstant.state.CREINITIALIZATIONP,
				CrowbarConstant.message.CREINITIALIZATIONP);
	}

	@Override
	public String destroy() {
		// TODO Auto-generated method stub
		this.putAll(new HashMap<String, String>());
		return BusRspUtil.asResponse(CrowbarConstant.state.DESTROYED,
				toString());
	}

	@Override
	public String destroy(String operand) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREDESTROYINGP,
				CrowbarConstant.state.CREDESTROYINGP,
				CrowbarConstant.message.CREDESTROYINGP);
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		List<Map<String, String>> infos = new ArrayList<Map<String, String>>();
		infos.add(BusRspUtil.asPair("className", this.getClass().getName()));
		infos.add(BusRspUtil.asPair("state",
				isinitialized() ? "Empty" : this.size() + " sets"));
		infos.add(BusRspUtil.asPair("Lookup-value", "Lookup-key"));
		for (Map.Entry<String, String> entry : this.entrySet()) {
			infos.add(BusRspUtil.asPair(entry.getKey(), entry.getValue()));
		}
		return BusRspUtil.gson.toJson(infos);
	}
}
