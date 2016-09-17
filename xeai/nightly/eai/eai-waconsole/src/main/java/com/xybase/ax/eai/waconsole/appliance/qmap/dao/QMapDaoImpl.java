package com.xybase.ax.eai.waconsole.appliance.qmap.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.waconsole.appliance.qmap.QMap;

public class QMapDaoImpl extends JdbcTemplate implements QMapDao {

	private Map<String, QMap> qmaps;
	private Map<String, String> conceal;

	private String concealCount = "count";

	public QMapDaoImpl(Map<String, QMap> map) {
		// TODO Auto-generated constructor stub
		this.qmaps = map;
	}

	@Override
	public boolean hasKey(String key) {
		// TODO Auto-generated method stub
		return this.qmaps.containsKey(key);
	}

	@Override
	public boolean update(String key, String... parameters) {
		// TODO Auto-generated method stub

		if (!qmaps.containsKey(key))
			throw new InternalErrorRuntimeException("key[" + key
					+ "] is not exist!!");
		if (!conceal.containsKey("plain"))
			throw new InternalErrorRuntimeException(
					"Conceal[ plain ] is not exist!!");
		QMap qmap = this.qmaps.get(key).clones();
		qmap.assign(parameters);
		final String[] tCriteria = qmap.getValueCriterias();

		return (boolean) this.execute(
				conceal.get("plain").replaceAll("#QUERY#", qmap.getQuery()),
				new PreparedStatementCallback<Object>() {

					@Override
					public Object doInPreparedStatement(
							final PreparedStatement arg0) throws SQLException,
							DataAccessException {
						setArgs(arg0, tCriteria);
						boolean result = false;
						if (arg0.executeUpdate() > 0)
							result = true;
						return result;
					}
				});
	}

	@Override
	public String retrieve(String concealKey, String key, String... parameters) {
		// TODO Auto-generated method stub
		if (!qmaps.containsKey(key))
			throw new InternalErrorRuntimeException("key[" + key
					+ "] is not exist!!");
		if (!conceal.containsKey(concealKey))
			throw new InternalErrorRuntimeException("Conceal[" + concealKey
					+ "] is not exist!!");
		QMap qmap = this.qmaps.get(key).clones();
		qmap.assign(parameters);
		final String[] tCriteria = qmap.getValueCriterias();

		return this.execute(
				conceal.get(concealKey).replaceAll("#QUERY#", qmap.getQuery()),
				new PreparedStatementCallback<String>() {

					@Override
					public String doInPreparedStatement(PreparedStatement arg0)
							throws SQLException, DataAccessException {
						String result = null;
						setArgs(arg0, tCriteria);
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

	@Override
	public void setConceal(Map<String, String> conceal) {
		// TODO Auto-generated method stub
		this.conceal = conceal;
	}

	@Override
	public String count(String key, String... parameters) {
		// TODO Auto-generated method stub

		if (!qmaps.containsKey(key))
			throw new InternalErrorRuntimeException("key[" + key
					+ "] is not exist!!");
		if (!conceal.containsKey(concealCount))
			throw new InternalErrorRuntimeException("Conceal[" + concealCount
					+ "] is not exist!!");
		QMap qmap = this.qmaps.get(key).clones();
		qmap.assignPlain(parameters);
		final String[] tCriteria = qmap.getValueCriterias();

		return this.execute(
				conceal.get(concealCount)
						.replaceAll("#QUERY#", qmap.getPlain()),
				new PreparedStatementCallback<String>() {

					@Override
					public String doInPreparedStatement(PreparedStatement arg0)
							throws SQLException, DataAccessException {
						String result = null;
						setArgs(arg0, tCriteria);
						ResultSet rs = arg0.executeQuery();
						while (rs.next()) {
							result = rs.getString(1);
						}
						return result;
					}
				});
	}

	public Map<String, Object> get() {

		return this.execute("select * from xeai_lookup_archcomp where rownum < 2",
				new PreparedStatementCallback<Map<String, Object>>() {

					@Override
					public Map<String, Object> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						// TODO Auto-generated method stub
						return null;
					}
				});
	}

	public Map<String, Object> getAll() {

		return this.execute("select * from xeai_lookup_archcomp",
				new PreparedStatementCallback<Map<String, Object>>() {

					@Override
					public Map<String, Object> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						// TODO Auto-generated method stub
						return null;
					}
				});
	}
}
