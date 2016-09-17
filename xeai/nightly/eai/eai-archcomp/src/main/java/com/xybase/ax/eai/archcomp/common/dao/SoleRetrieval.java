/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 18, 2015	4:36:04 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.common.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

/**
 * @note
 *
 */
public class SoleRetrieval extends JdbcTemplate {

	private String query;
	private String key;
	

	public SoleRetrieval(String query, String key) {
		this.query = query;
		this.key = key;
	}

	public String retrieve() {
		return this.execute(query, new PreparedStatementCallback<String>() {
			@Override
			public String doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				ResultSet rs = arg0.executeQuery();
				while (rs.next()) {
					return rs.getString(key);
				}
				return null;
			}
		});
	}

}
