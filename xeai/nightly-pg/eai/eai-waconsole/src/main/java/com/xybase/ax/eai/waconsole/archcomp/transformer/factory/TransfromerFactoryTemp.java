/**
 * 
 */
package com.xybase.ax.eai.waconsole.archcomp.transformer.factory;

/**
 * 			@author xybase-007
 *
 * =============================================================
 * =
 * =          Created By Abdul Azis Nur
 * =          On Feb 23, 2016 11:00:19 AM
 * =          TransfromerFactoryTemp.java
 * =
 * =============================================================
 */

import java.util.List;

import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.transformer.TransformerImpl;
import com.xybase.ax.eai.archcomp.transformer.rule.TransformerRule;
import com.xybase.ax.eai.archcomp.transformer.template.TransformerTemplate;
import com.xybase.ax.eai.waconsole.appliance.Transform;

/**
 * @note Factory Container for Transformation.
 *
 */

@SuppressWarnings("rawtypes")
public class TransfromerFactoryTemp {

	TransformerImpl transform = new TransformerImpl();

	/**
	 * @return the transform
	 */
	public TransformerImpl getTransform() {
		return transform;
	}

	/**
	 * @param transform the transform to set
	 */
	public void setTransform(TransformerImpl transform) {
		this.transform = transform;
	}

	@SuppressWarnings("unchecked")
	public GenericMessage handle(GenericMessage<Transform> messageIn) {
		
		Transform transform = messageIn.getPayload();
		
		TransformerImpl transformerImpl = new TransformerImpl();
		
		TransformerTemplate config = new TransformerTemplate();
		config.setTemplate(transform.getTransformTemplate());		
		config.setType(transform.getTransformType());
		
		List<TransformerRule> transfromRule = transform.getTransformData() ;
		
		transformerImpl.setTransformRuleDao(null);
		transformerImpl.setTransform(config);
		transformerImpl.setTransformRules(transfromRule);
		transformerImpl.setPayloadData(transform.getPayload());
		
		GenericMessage messageOut = new GenericMessage<TransformerImpl>(transformerImpl);
		
		return messageOut;
	}
	
	public TransformerImpl clone() {
		
		return getTransform();
	}

}

