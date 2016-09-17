package com.xybase.ax.eai.waconsole.appliance.qdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

public class QDaoImpl extends JdbcTemplate implements QDao {

	private Map<String, String> map;

	public QDaoImpl(Map<String, String> map) {
		// TODO Auto-generated constructor stub
		this.map = map;
	}

	@Override
	public boolean hasKey(String key) {
		// TODO Auto-generated method stub
		return this.map.containsKey(key);
	}

	@Override
	public boolean update(String key, String... parameters) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String get(String key, final String... parameters) {
		// TODO Auto-generated method stub
		System.out.println(map.get(key));
		return this.execute(map.get(key),
				new PreparedStatementCallback<String>() {

					@Override
					public String doInPreparedStatement(PreparedStatement arg0)
							throws SQLException, DataAccessException {
						String result = null;
						setArgs(arg0, parameters);
						ResultSet rs = arg0.executeQuery();
						while (rs.next()) {
							result = rs.getString(1);
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

}
