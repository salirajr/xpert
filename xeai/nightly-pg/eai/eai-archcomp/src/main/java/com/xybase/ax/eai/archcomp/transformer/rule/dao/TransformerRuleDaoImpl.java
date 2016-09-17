/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * 03-05-2015	----			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.rule.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.transformer.rule.TransformerRule;

public class TransformerRuleDaoImpl extends JdbcTemplate implements
		TransformerRuleDao {

	private String query;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.rule.dao.TransformRuleDao#getRules
	 * (int)
	 */
	@Override
	public List<TransformerRule> gets(final int transformId) {
		// TODO Auto-generated method stub
		return this.execute(query,
				new PreparedStatementCallback<List<TransformerRule>>() {

					@Override
					public List<TransformerRule> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						List<TransformerRule> result = new ArrayList<TransformerRule>();
						arg0.setInt(1, transformId);
						ResultSet rs = arg0.executeQuery();
						TransformerRule temp;
						while (rs.next()) {
							temp = new TransformerRule();
							temp.setTransformId(transformId);
							temp.setTransformRuleId(rs
									.getInt("transform_rule_id"));
							temp.setTransformRuleSource(rs
									.getString("transform_rule_source"));
							temp.setTransformRuleTarget(rs
									.getString("transform_rule_target"));
							temp.setTransformRuleConfig(rs
									.getInt("transform_rule_config"));
							temp.setTransformRuleMatrix(rs
									.getString("transform_rule_matrix"));
							result.add(temp);
						}

						return result;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.rule.dao.TransformRuleDao#setQuery
	 * (java.lang.String)
	 */
	public void setQuery(String query) {
		// TODO Auto-generated method stub
		this.query = query;
	}

}
