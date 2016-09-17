/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 13, 2015	1:16:33 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.template;

import java.util.Map;

import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.CrowbarConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.transformer.template.dao.TransformerTemplateDao;

/**
 * @note
 *
 */
public class TransformerTemplate implements CrowbarBus {

	private int Id;
	private String template;
	private String type;

	private TransformerTemplateDao dao;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Transform [Id=" + Id + ", template=" + template + ", type="
				+ type + "]";
	}

	/**
	 * @param clones
	 */
	public void setTransformDao(TransformerTemplateDao dao) {
		// TODO Auto-generated method stub
		this.dao = dao;
	}

	@Override
	public boolean isinitialized() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String reinitialized() {
		// TODO Auto-generated method stub
		Map<String, Object> raw = dao.getRaw(this.Id);
		this.template = StringUtil.cast(raw.get("template"));
		this.type = StringUtil.cast(raw.get("type"));
		return BusRspUtil.asResponse(CrowbarConstant.state.INITIALIZATION_SUCCEED,
				toString());
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
		return BusRspUtil.asResponse(CrowbarConstant.code.CDESTROYOFF,
				CrowbarConstant.state.CDESTROYOFF,
				CrowbarConstant.message.CDESTROYOFF);
	}

	@Override
	public String destroy(String operand) {
		// TODO Auto-generated method stub
		return BusRspUtil.asResponse(CrowbarConstant.code.CREINITIALIZATIONP,
				CrowbarConstant.state.CREINITIALIZATIONP,
				CrowbarConstant.message.CREINITIALIZATIONP);
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return BusRspUtil.asInfo(this.getClass().getName(), "", CrowbarBus.class.getName());
	}

}
