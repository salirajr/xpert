/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 11, 2015	10:46:34 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xystub.stub.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.xystub.stub.Stub;

/**
 * @note
 *
 */
public class StubDaoImpl extends JdbcTemplate implements StubDao {

	private String query;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.xystub.config.dao.StubConfigDao#setQuery(java.lang.
	 * String)
	 */
	@Override
	public void setQuery(String query) {
		// TODO Auto-generated method stub
		this.query = query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.xystub.config.dao.StubConfigDao#lookup(java.lang.String
	 * )
	 */
	@Override
	public Stub lookup(final String stubRqi) {
		// TODO Auto-generated method stub
		return this.execute(query, new PreparedStatementCallback<Stub>() {

			@Override
			public Stub doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				arg0.setString(1, stubRqi);
				ResultSet rs = arg0.executeQuery();
				Stub result = new Stub();
				while(rs.next()){
					result.setRequestIn(stubRqi);
					result.setResponseOut(rs.getString("stub_rpo"));
					result.setType(rs.getString("stub_type"));
				}
				return result;
			}
		});
	}

}
