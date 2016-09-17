/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Aug 25, 2015		9:10:19 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.matrix.dao.MatrixDao;

public class Matrix extends HashMap<String, Map<String, String>> implements
		CrowbarBus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private String sets;
	private MatrixDao factory;
	private List<String> columns;

	private String[] parameters;

	public Matrix(String key, String... parameters) {
		columns = new ArrayList<String>();
		this.key = key;
		this.parameters = parameters;
	}

	public Matrix(MatrixDao factory, String key, String... parameters) {
		columns = new ArrayList<String>();
		this.factory = factory;
		this.key = key;
		this.parameters = parameters;
	}

	public void setSets(String sets) {
		this.sets = sets;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String reinitialized() {
		this.putAll(factory.getRaw(this.key, this.parameters));
		return BusRspUtil.asResponse(CrowbarConstant.state.INITIALIZATION_SUCCEED,
				toString());
	}

	@Override
	public String reinitialized(String operand) {
		// TODO Auto-generated method stub
		if (columns.contains(operand)) {
			this.putAll(factory.getRaw(operand, this.parameters));
			return BusRspUtil.asResponse(
					CrowbarConstant.code.CREINITIALIZATIONPSUCCEED,
					CrowbarConstant.state.CREINITIALIZATIONPSUCCEED,
					"Matrix refreshed with key: " + operand + "!");
		} else {
			return BusRspUtil.asResponse(CrowbarConstant.code.DESCRIBEINFO,
					CrowbarConstant.state.CREINITIALIZATIONP,
					columns.toString());
		}
	}

	@Override
	public String destroy() {
		// TODO Auto-generated method stub
		this.putAll(new HashMap<String, Map<String, String>>());
		return BusRspUtil.asResponse(CrowbarConstant.state.DESTROYED, toString());
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
		return null;
	}

	@Override
	public String toString() {
		return this.sets;
	}
}
