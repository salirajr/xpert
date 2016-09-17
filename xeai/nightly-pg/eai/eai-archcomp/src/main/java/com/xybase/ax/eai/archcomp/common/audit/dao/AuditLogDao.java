/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.dao;

import com.xybase.ax.eai.archcomp.common.audit.AuditLog;


public interface AuditLogDao {

	public void audit(AuditLog data);


}