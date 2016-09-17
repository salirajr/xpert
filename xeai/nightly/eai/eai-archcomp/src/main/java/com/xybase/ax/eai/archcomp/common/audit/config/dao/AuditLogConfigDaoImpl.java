/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 10, 2015	11:03:58 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.audit.config.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.common.audit.config.AuditLogConfig;

/**
 * @note
 *
 */
public class AuditLogConfigDaoImpl extends JdbcTemplate implements
		AuditLogConfigDao {

	private String query;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.common.audit.config.dao.AuditLogConfigDao#
	 * setQuery(java.lang.String)
	 */
	public void setQuery(String query) {
		// TODO Auto-generated method stub
		this.query = query;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.common.audit.config.dao.AuditLogConfigDao#
	 * getLogConfig(java.lang.String)
	 */
	@Override
	public AuditLogConfig get(final String eventName, final int key,
			final int level) {

		return this.execute(query,
				new PreparedStatementCallback<AuditLogConfig>() {

					@Override
					public AuditLogConfig doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						// TODO Auto-generated method stub
						arg0.setString(1, eventName);
						arg0.setInt(2, key);
						arg0.setInt(3, level);
						ResultSet rs = arg0.executeQuery();
						AuditLogConfig logConfig = new AuditLogConfig();
						logConfig.setEvent(eventName);
						logConfig.setKey(key);
						logConfig.setLevel(level);
						while (rs.next()) {
							logConfig.setParameterized(rs
									.getString("audit_param"));
							logConfig.setCorrelation(rs
									.getString("audit_correlation"));
							logConfig.setConfig(rs.getInt("audit_config"));
							logConfig.setType(rs.getString("audit_type"));
						}
						logConfig.setAuditLogDao(clones());
						return logConfig;
					}
				});
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.common.audit.config.dao.AuditLogConfigDao#
	 * getLogConfig(java.lang.String)
	 */
	@Override
	public AuditLogConfig get(String eventName, int key) {
		return get(eventName, key, 0);
	}

	private AuditLogConfigDao clones() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.common.audit.config.dao.AuditLogConfigDao#
	 * get(java.lang.String, int, int)
	 */
	@Override
	public Map<String, String> getRaw(final String eventName, final int key,
			final int level) {
		return this.execute(query,
				new PreparedStatementCallback<Map<String, String>>() {

					@Override
					public Map<String, String> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						// TODO Auto-generated method stub
						Map<String, String> result = new HashMap<String, String>();
						arg0.setString(1, eventName);
						arg0.setInt(2, key);
						arg0.setInt(3, level);
						ResultSet rs = arg0.executeQuery();
						while (rs.next()) {
							result.put("parameterized",
									rs.getString("audit_param"));
							result.put("correlation",
									rs.getString("audit_correlation"));
							result.put("config", rs.getString("audit_config"));
							result.put("type", rs.getString("audit_type"));
						}
						return result;
					}
				});
	}
}
