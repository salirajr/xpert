/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Apr 9, 2015	9:25:59 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xybase.ax.eai.archcomp.common.audit.AuditLog;

/**
 * @note
 *
 */
public class AuditLogDaoStreamImpl implements AuditLogDao {

	private final static Logger log = LogManager.getLogger(AuditLogDao.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.common.audit.dao.AuditLogDao#audit(com.xybase
	 * .ax.eai.archcomp.common.audit.AuditLog)
	 */
	@Override
	public void audit(AuditLog data) {
		// TODO Auto-generated method stub
		log.info(data);
	}

}
