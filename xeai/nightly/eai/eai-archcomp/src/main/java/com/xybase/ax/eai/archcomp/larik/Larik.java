package com.xybase.ax.eai.archcomp.larik;

import java.util.ArrayList;
import java.util.Arrays;

import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.larik.dao.LarikDao;

public class Larik extends ArrayList<String> implements CrowbarBus {

	private LarikDao factory;
	private String[] parameters;

	private String sets;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Larik(LarikDao factory, String... parameters) {
		this.factory = factory;
		this.parameters = parameters;
	}

	public void setParameters(String... parameters) {
		this.parameters = parameters;
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

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		ArrayList<String> temp = factory.getRaw(this.parameters);
		this.addAll(temp);
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
		this.addAll(new ArrayList<String>());
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
		return Arrays.toString(this.toArray());
	}

	public Larik clones() {
		Larik temp = new Larik(factory, parameters);
		temp.addAll(this);
		return temp;
	}
}
