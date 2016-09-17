/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 10, 2015	11:00:59 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.config.dao;

import java.util.Map;

import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;

/**
 * @note
 *
 */
public interface AuditLogConfigDao {

	public AuditLogConfig get(String eventName, int key, int level);

	public AuditLogConfig get(String eventName, int key);

	public Map<String, String> getRaw(String eventName, int key, int level);
}
