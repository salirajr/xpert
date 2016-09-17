/** 
 * Modification History
 * Date			Time			Modified By             Comments
 * **************************************************************************************
 * Apr 7, 2016		11:46:37 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */
package com.xybase.ax.eai.xybatch.rowmapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author salirajr
 *
 */
public class MapRowMapperImpl implements RowMapper<Map<String, Object>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 * int)
	 */
	@Override
	public Map<String, Object> mapRow(ResultSet arg0, int arg1)
			throws SQLException {
		// TODO Auto-generated method stub

		ResultSetMetaData meta = arg0.getMetaData();
		Map<String, Object> row = new HashMap<String, Object>();
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			String key = meta.getColumnName(i);
			Object value = arg0.getObject(key);
			row.put(key, value);
		}

		return row;
	}

}
