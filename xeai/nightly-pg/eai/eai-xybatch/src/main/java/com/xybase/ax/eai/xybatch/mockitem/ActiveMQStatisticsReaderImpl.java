/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 5, 2016		5:06:50 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.mockitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.xybase.ax.eai.xybatch.common.util.ActiveMQStatistics;

/**
 * @author salirajr
 *
 */
public class ActiveMQStatisticsReaderImpl implements
		ItemReader<Map<String, String>> {

	private final static Logger log = LogManager
			.getLogger(ActiveMQStatisticsReaderImpl.class);

	private List<ActiveMQStatistics> statistics;
	private int i;

	public ActiveMQStatisticsReaderImpl(String url, String username,
			String password, List<String> queues) {
		this.i = 0;
		this.statistics = new ArrayList<ActiveMQStatistics>();
		for (String queue : queues) {
			statistics.add(new ActiveMQStatistics(queue, url, username,
					password));
		}
	}

	@Override
	public Map<String, String> read() throws Exception,
			UnexpectedInputException, ParseException,
			NonTransientResourceException {
		// TODO Auto-generated method stub
		log.info("Reading with iteration=" + i);
		if (i < this.statistics.size()) {
			return statistics.get(i++).getStatistics();
		} else {
			this.i = 0;
			return null;
		}
	}

}
