/**
 * 
 */
package com.xybase.ax.eai.waconsole.archcomp.handler;

import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.transformer.TransformerImpl;
import com.xybase.ax.eai.archcomp.transformer.extract.Extractor;
import com.xybase.ax.eai.archcomp.transformer.inject.Injector;
import com.xybase.ax.eai.archcomp.transformer.util.SpELProcessor;

/**
 * 			@author xybase-007
 *
 * =============================================================
 * =
 * =          Created By Abdul Azis Nur
 * =          On Feb 23, 2016 11:02:38 AM
 * =          TransformHandler.java
 * =
 * =============================================================
 */
@SuppressWarnings("rawtypes")
public class TransformHandler extends com.xybase.ax.eai.archcomp.handler.TransformHandler {
	
	// add by azis 20160222
		public GenericMessage setTransformRule(GenericMessage<TransformerImpl> messageIn) {
			Injector ij = (Injector) this.transformer.getInjector();
			Extractor ej = (Extractor) this.transformer.getExtractor();
			SpELProcessor sp = (SpELProcessor) this.transformer.gettProcessor();
			this.transformer =  messageIn.getPayload();
			this.transformer.setInjector(ij);
			this.transformer.setExtractor(ej);
			this.transformer.setProcessor(sp);
			GenericMessage messageOut = new GenericMessage<String>((String) messageIn.getPayload().getPayloadData());
			return messageOut;
		}

}
