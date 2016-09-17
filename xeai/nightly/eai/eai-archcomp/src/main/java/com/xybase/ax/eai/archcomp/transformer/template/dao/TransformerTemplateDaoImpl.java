/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 13, 2015	1:17:38 PM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.archcomp.transformer.template.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.xybase.ax.eai.archcomp.constant.ErrorConstants;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.transformer.template.TransformerTemplate;

/**
 * @note
 *
 */
public class TransformerTemplateDaoImpl extends JdbcTemplate implements TransformerTemplateDao {

	private String query;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xybase.ax.eai.archcomp.transform.model.dao.TransformDao#get(java.
	 * lang.String)
	 */
	@Override
	public TransformerTemplate get(final int transfromId) {
		return this.execute(query, new PreparedStatementCallback<TransformerTemplate>() {

			@Override
			public TransformerTemplate doInPreparedStatement(PreparedStatement arg0)
					throws SQLException, DataAccessException {
				arg0.setInt(1, transfromId);
				ResultSet rs = arg0.executeQuery();
				TransformerTemplate transform = new TransformerTemplate();
				transform.setTransformDao(clones());
				transform.setId(transfromId);
				if (rs.next()) {
					transform.setTemplate(rs.getString("transform_template"));
					transform.setType(rs.getString("transform_type"));
					return transform;
				} else {
					throw new InternalErrorRuntimeException(
							"There's no transformation id for:"
									+ transfromId
									+ "!!, Please check your message-handler definition .xml",
							null, ErrorConstants.Code.TRANSFORMATION_EXCEPTION);
				}

			}
		});
	}

	public void setQuery(String query) {
		this.query = query;
	}

	private TransformerTemplateDao clones() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xybase.ax.eai.archcomp.transform.dao.TransformDao#getMap(int)
	 */
	@Override
	public Map<String, Object> getRaw(final int transfromId) {
		// TODO Auto-generated method stub
		return this.execute(query,
				new PreparedStatementCallback<Map<String, Object>>() {

					@Override
					public Map<String, Object> doInPreparedStatement(
							PreparedStatement arg0) throws SQLException,
							DataAccessException {
						arg0.setInt(1, transfromId);
						ResultSet rs = arg0.executeQuery();
						Map<String, Object> transform = new HashMap<String, Object>();
						transform.put("id", transfromId);
						if (rs.next()) {
							transform.put("template",
									rs.getString("transform_template"));
							transform.put("type",
									rs.getString("transform_type"));
							return transform;
						} else {
							throw new InternalErrorRuntimeException(
									"There's no transformation id for:"
											+ transfromId
											+ "!!, Please check your message-handler definition .xml",
									null,
									ErrorConstants.Code.TRANSFORMATION_EXCEPTION);
						}

					}
				});
	}
}
