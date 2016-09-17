/** 
 * Modification History
 * Date         Modified By             Comments
 * **************************************************************************************
 * 24092014		Jovi Rengga Salira		Initial Creation,
 * 										Custom JdbcTempate implementation class,
 * 										Basic function.
 * **************************************************************************************
 */
package com.concept.ruleengine.reference.dao;

import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.concept.ruleengine.archcomp.exception.ApplicationRuntimeException;

public class ReferenceDAOImpl extends JdbcTemplate implements ReferenceDAO {

	private static Logger logger = LogManager.getLogger(ReferenceDAOImpl.class);

	public ReferenceDAOImpl() {
		super();
	}

	public ReferenceDAOImpl(String jndiName) {
		super();
		Context initialContext = null;
		DataSource ds = null;
		try {
			initialContext = new InitialContext();
			ds = (DataSource) initialContext.lookup(jndiName);
			initialContext.close();
			this.setDataSource(ds);
			logger.info("XYBASE Airport Rule Engine Data Source Initiation done!!");
		} catch (NamingException e) {
			throw new ApplicationRuntimeException("XYBASE Airport Rule Engine Data Source Exception, [JNDI: "
					+ jndiName + " doesnot exist!! ]");

		}
	}

	@Override
	public Object get(String query) {
		List<Object> result = this.queryForList(query, Object.class);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<Object> getList(String query) {
		List<Object> result = this.queryForList(query, Object.class);
		return result;
	}

	// Common Extraction!
	@Override
	public Map<String, Object> getMap(String query) {
		try {
			List<Map<String, Object>> result = this.queryForList(query);
			return result.get(0);
		} catch (BadSqlGrammarException ege) {
			ege.printStackTrace();
			return null;
		} catch (NullPointerException npe) {
			return null;
		}
	}

	// Common Extraction!
	@Override
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
			return null;
		}
	}

	@Override
	public boolean modify(String query) {
		try {
			this.execute(query);
			return true;
		} catch (BadSqlGrammarException ege) {
			throw new ApplicationRuntimeException(
					"XYBASE Airport Rule Engine Exception, [DML Operation(modify), caused: Bad SQL Grammer, query: "
							+ query + "]");
		}

	}

	@Override
	public double getNumeric(String query) {
		// TODO Auto-generated method stub
		return this.queryForObject(query, Double.class);
	}

	@Override
	public int getInt(String query) {
		// TODO Auto-generated method stub
		return this.queryForObject(query, Integer.class);
	}

}
