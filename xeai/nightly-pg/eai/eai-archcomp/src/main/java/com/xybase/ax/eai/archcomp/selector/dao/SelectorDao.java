/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 27, 2015	8:56:59 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.selector.dao;

import java.util.List;

import com.xybase.ax.eai.archcomp.selector.Selector;

/**
 * @note
 *
 */
public interface SelectorDao {

	public List<Selector> get(int id);

}
