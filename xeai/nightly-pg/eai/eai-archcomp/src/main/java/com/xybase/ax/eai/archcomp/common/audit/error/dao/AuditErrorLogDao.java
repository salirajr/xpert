/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * May 21, 2015	10:28:56 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.error.dao;

import com.xybase.ax.eai.archcomp.common.audit.error.AuditErrorLog;

/**
 * @note
 *
 */
public interface AuditErrorLogDao {
	
	public void logError(AuditErrorLog log);
}
