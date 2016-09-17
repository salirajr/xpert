/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.rule.dao;

import java.util.List;

import com.xybase.ax.eai.archcomp.transformer.rule.TransformerRule;

public interface TransformerRuleDao {
	
	public List<TransformerRule> gets(int transformId);
	
}
