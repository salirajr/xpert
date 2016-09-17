/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 18, 2016		3:16:18 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.mockitem;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author salirajr
 *
 */
public class MapPrintProcessorConsole implements
		ItemProcessor<Map<String, Object>, Map<String, Object>> {

	private final static Logger log = LogManager
			.getLogger(MapPrintProcessorConsole.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public Map<String, Object> process(Map<String, Object> item)
			throws Exception {
		// TODO Auto-generated method stub
		log.info(item.toString());
		return item;
	}

}
