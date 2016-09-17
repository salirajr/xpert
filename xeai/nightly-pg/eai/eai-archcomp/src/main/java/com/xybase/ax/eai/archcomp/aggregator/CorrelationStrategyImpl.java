/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 4, 2016		9:05:25 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.aggregator;

import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.messaging.Message;

/**
 * @author salirajr
 *
 */
public class CorrelationStrategyImpl implements CorrelationStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.integration.aggregator.CorrelationStrategy#
	 * getCorrelationKey(org.springframework.messaging.Message)
	 */
	@Override
	public Object getCorrelationKey(Message<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
