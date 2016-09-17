/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 13, 2015	2:01:16 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.factory;

import java.util.List;

import com.xybase.ax.eai.archcomp.transformer.TransformerImpl;
import com.xybase.ax.eai.archcomp.transformer.rule.TransformerRule;
import com.xybase.ax.eai.archcomp.transformer.rule.dao.TransformerRuleDao;
import com.xybase.ax.eai.archcomp.transformer.template.TransformerTemplate;
import com.xybase.ax.eai.archcomp.transformer.template.dao.TransformerTemplateDao;

/**
 * @note Factory Container for Transformation.
 *
 */
public class TransfromerFactory {

	private TransformerTemplateDao transformDao;
	private TransformerRuleDao transformRuleDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TransformerImpl createNew(int transformId) {
		TransformerTemplate config = transformDao.get(transformId);
		List<TransformerRule> transfromRule = transformRuleDao.gets(transformId);
		TransformerImpl transform = new TransformerImpl();
		transform.setTransformRuleDao(transformRuleDao);
		transform.setTransform(config);
		transform.setTransformRules(transfromRule);
		return transform;
	}

	public void setTransformDao(TransformerTemplateDao transformDao) {
		this.transformDao = transformDao;
	}

	public void setTransformRuleDao(TransformerRuleDao transformRuleDao) {
		this.transformRuleDao = transformRuleDao;
	}

}
