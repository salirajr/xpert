/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 13, 2015	1:16:59 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.template.dao;

import java.util.Map;

import com.xybase.ax.eai.archcomp.transformer.template.TransformerTemplate;

/**
 * @note
 *
 */
public interface TransformerTemplateDao {
	
	public TransformerTemplate get(int transfromId);
	
	public Map<String, Object> getRaw(int transfromId);
}
