/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * May 21, 2015	10:30:04 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.error.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.common.audit.error.AuditErrorLog;

/**
 * @note
 *
 */
public class AuditErrorLogDaoImpl extends JdbcTemplate implements
		AuditErrorLogDao {

	private String query;

	public void setQuery(String query) {
		this.query = query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.common.audit.error.dao.AuditErrorLogDao#systemLog
	 * (com.xybase.ax.eai.archcomp.common.audit.error.AuditErrorLog)
	 */
	@Override
	public void logError(final AuditErrorLog logs) {
		// TODO Auto-generated method stub
		this.execute(query, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				arg0.setString(1, logs.getAuditTime());
				arg0.setString(2, logs.getErrorId());
				arg0.setString(3, logs.getXeaiId());
				arg0.setString(4, logs.getEventName());
				arg0.setString(5, logs.getCause());
				arg0.setString(6, logs.getSeverity());
				arg0.setString(7, logs.getEndpoint());
				return arg0.execute();
			}
		});

	}

}
