/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.common.audit.AuditLog;

public class AuditLogDaoImpl extends JdbcTemplate implements AuditLogDao {

	private String query;

	@Override
	public void audit(final AuditLog data) {
		this.execute(query, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				arg0.setString(1, data.getAuditTime());
				arg0.setString(2, data.getEndpoint());
				arg0.setString(3, data.getSeverity());
				arg0.setString(4, data.getPayload());
				arg0.setString(5, data.getAuditParam());
				arg0.setString(6, data.getXeaiId());
				arg0.setString(7, data.getCorrelationId());
				arg0.setString(8, data.getEventName());
				arg0.setString(9, data.getAuditType());
				return arg0.execute();
			}
		});
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
