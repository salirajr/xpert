package com.ptxti.concept.ruleengine.ui.util;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoUtil extends JdbcTemplate {

	public DaoUtil() {
		super();
	}

	public String getJson(String query) {
		try {
			// PostgreSQL Feature only
			query = "SELECT array_to_json(array_agg(temptbl_axre)) from (" + query + ") temptbl_axre";
			String result = this.queryForObject(query, String.class);
			return result;
		} catch (BadSqlGrammarException ege) {
			ege.printStackTrace();
			return null;
		} catch (NullPointerException npe) {
			return "[]";
		}
	}

}
