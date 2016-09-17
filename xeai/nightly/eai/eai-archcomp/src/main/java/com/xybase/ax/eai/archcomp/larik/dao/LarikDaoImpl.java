package com.xybase.ax.eai.archcomp.larik.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.larik.Larik;

public class LarikDaoImpl extends JdbcTemplate implements LarikDao {

	private String query;

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public Larik get(final String... parameters) {
		// TODO Auto-generated method stub
		return this.execute(query, new PreparedStatementCallback<Larik>() {

			@Override
			public Larik doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				Larik result = new Larik(clones(), parameters);

				setArgs(arg0, parameters);
				ResultSet rs = arg0.executeQuery();

				while (rs.next()) {
					result.add(rs.getString(1));
				}
				return result;
			}
		});
	}

	@Override
	public ArrayList<String> getRaw(final String... parameters) {
		// TODO Auto-generated method stub
		return this.execute(query,
				new PreparedStatementCallback<ArrayList<String>>() {

					@Override
					public ArrayList<String> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						ArrayList<String> result = new ArrayList<String>();

						setArgs(arg0, parameters);
						ResultSet rs = arg0.executeQuery();

						while (rs.next()) {
							result.add(rs.getString(1));
						}
						return result;
					}
				});
	}

	private PreparedStatement setArgs(PreparedStatement arg0,
			String... parameters) throws SQLException {
		for (int i = 0; i < parameters.length; i++) {
			arg0.setString(i + 1, parameters[i]);
		}
		return arg0;
	}

	public LarikDao clones() {
		return this;
	}

}
