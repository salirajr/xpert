/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Aug 25, 2015		9:10:19 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.matrix.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.matrix.Matrix;

public class MatrixDaoImpl extends JdbcTemplate implements MatrixDao {

	private String query;

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public Matrix get(final String key, final String... parameters) {
		// TODO Auto-generated method stub
		return this.execute(query, new PreparedStatementCallback<Matrix>() {

			@Override
			public Matrix doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				Matrix result = new Matrix(clones(), key, parameters);
				Map<String, String> map = new HashMap<String, String>();
				
				setArgs(arg0, parameters);
				ResultSet rs = arg0.executeQuery();
				List<String> columns = getColumns(rs.getMetaData());
				result.setColumns(columns);
				
				while (rs.next()) {
					for (int i = 0; i < columns.size(); i++) {
						map.put(columns.get(i), rs.getString(columns.get(i)));
					}
					result.put(rs.getString(key), map);
					map = new HashMap<String, String>();
				}
				return result;
			}
		});
	}

	@Override
	public Map<String, Map<String, String>> getRaw(final String key,
			final String... parameters) {
		// TODO Auto-generated method stub
		return this
				.execute(
						query,
						new PreparedStatementCallback<Map<String, Map<String, String>>>() {

							@Override
							public Map<String, Map<String, String>> doInPreparedStatement(
									PreparedStatement arg0)
									throws SQLException, DataAccessException {
								Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
								Map<String, String> map = new HashMap<String, String>();
								
								setArgs(arg0, parameters);
								ResultSet rs = arg0.executeQuery();
								List<String> columns = getColumns(rs.getMetaData());
								
								while (rs.next()) {
									for (int i = 0; i < columns.size(); i++) {
										map.put(columns.get(i), rs.getString(columns.get(i)));
									}
									result.put(rs.getString(key), map);
									map = new HashMap<String, String>();
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

	private List<String> getColumns(ResultSetMetaData metadata)
			throws SQLException {
		List<String> columns = new ArrayList<String>();

		for (int i = 0; i < metadata.getColumnCount(); i++) {
			columns.add(metadata.getColumnName(i + 1));
		}
		return columns;
	}

	public MatrixDao clones() {
		return this;
	}

}
