/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 4, 2016		9:04:29 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.aggregator;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;

/**
 * @author salirajr
 *
 */
public class ReleaseStrategyImpl implements ReleaseStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.integration.aggregator.ReleaseStrategy#canRelease
	 * (org.springframework.integration.store.MessageGroup)
	 */
	@Override
	public boolean canRelease(MessageGroup arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
