/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 11, 2015	10:08:25 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.handler;

import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.control.bus.CrowbarBus;
import com.xybase.ax.eai.archcomp.control.bus.LevergearBus;

/**
 * @param <T>
 * @param <E>
 * @note
 *
 */
public interface IHandler<T, E> extends CrowbarBus, LevergearBus {

	public void handle(GenericMessage<E> messageIn);

}
