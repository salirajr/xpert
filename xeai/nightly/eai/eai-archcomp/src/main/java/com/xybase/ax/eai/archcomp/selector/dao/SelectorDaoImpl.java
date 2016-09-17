/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 27, 2015	8:56:59 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.selector.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.selector.Selector;

/**
 * @note
 *
 */
public class SelectorDaoImpl extends JdbcTemplate implements SelectorDao {

	private String query;

	public void setQuery(String query) {
		this.query = query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.selector.dao.ISelectorDao#get(int)
	 */
	@Override
	public List<Selector> get(final int id) {
		// TODO Auto-generated method stub
		return this.execute(query,
				new PreparedStatementCallback<List<Selector>>() {

					@Override
					public List<Selector> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						arg0.setInt(1, id);
						ResultSet rs = arg0.executeQuery();
						List<Selector> result = new ArrayList<Selector>();
						Selector is;
						while (rs.next()) {
							is = new Selector();
							is.setId(id);
							is.setSequence(rs.getInt("selector_sequence"));
							is.setExpression(rs
									.getString("selector_expression"));
							is.setDiscardMessage(rs
									.getString("selector_discard_message"));
							is.setConfig(rs.getInt("selector_config"));
							result.add(is);
						}
						return result;
					}

				});
	}

}
