/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.lookup.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.lookup.Lookup;

public class LookupDaoImpl extends JdbcTemplate implements LookupDao {

	private String query;

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public Lookup get(final String... parameters) {
		return this.execute(query, new PreparedStatementCallback<Lookup>() {

			@Override
			public Lookup doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				Lookup result = new Lookup(clones(), parameters);
				setArgs(arg0, parameters);
				ResultSet rs = arg0.executeQuery();
				while (rs.next()) {
					result.put(rs.getString(1), rs.getString(2));
				}
				return result;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.lookup.dao.LookupDao#get(java.lang.String)
	 */
	@Override
	public Map<String, String> getRaw(final String... parameters) {
		return this.execute(query,
				new PreparedStatementCallback<Map<String, String>>() {

					@Override
					public Map<String, String> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						Map<String, String> result = new HashMap<String, String>();
						setArgs(arg0, parameters);
						ResultSet rs = arg0.executeQuery();
						while (rs.next()) {
							result.put(rs.getString(1), rs.getString(2));
						}
						return result;
					}
				});
	}

	public LookupDao clones() {
		return this;
	}

	private PreparedStatement setArgs(PreparedStatement arg0,
			String... parameters) throws SQLException {
		for (int i = 0; i < parameters.length; i++) {
			arg0.setString(i + 1, parameters[i]);
		}
		return arg0;
	}

}
