package com.xybase.ax.eai.waconsole.appliance.qmap;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import com.xybase.ax.eai.archcomp.common.util.StringUtil;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;

public class QMap {

	private final static Logger log = LogManager.getLogger(QMap.class);

	class CriteriaType {
		static final String VALUE_ASSIGN = ":va";
		static final String NULLVALUE_ASSIGN = ":nva";
		static final String NULLVALUE_NOASSIGN = ":nvna";
	}

	/* plain query, PLAIN QUERY GROUP BY A ORDER BY B LIMIT 0 OFFSET 1 */
	private String template;
	private String orderBy;
	private String groupBy;
	private String limit;
	private String limitValue;
	private String offset;
	private String offsetValue;

	private List<String> keyCriterias;
	private List<String> valueCriterias;
	// operand, leftCriteria, rightCriteria, rightCriteria, reassignedCriteria
	private List<String[]> criterias;

	private int fxdCriteria;

	public QMap(String query) {
		this.template = query;
		this.fxdCriteria = StringUtils.countOccurrencesOf(this.template, "?");
		
		this.keyCriterias = new ArrayList<String>();
		this.valueCriterias = new ArrayList<String>();
		this.criterias = new ArrayList<String[]>();
		this.groupBy = null;
		this.orderBy = null;
		this.limit = null;
		this.offset = null;
		this.limitValue = null;
		this.offsetValue = null;
	}

	public QMap(String query, List<String> criteria) {
		this.criterias = new ArrayList<String[]>();
		for (int i = 0; i < criteria.size(); i++) {
			this.criterias.add(StringUtil.toArray(criteria.get(i), ";"));
		}
		this.template = query;
		this.fxdCriteria = StringUtils.countOccurrencesOf(this.template, "?");
		this.keyCriterias = new ArrayList<String>();
		this.valueCriterias = new ArrayList<String>();
		this.groupBy = null;
		this.orderBy = null;
		this.limit = null;
		this.offset = null;
	}

	public String getQuery() {

		StringBuilder query = new StringBuilder(this.template);
		for (String criteria : this.keyCriterias) {
			query.append(" ").append(criteria);
		}

		if (!StringUtil.isNullOrBlank(groupBy))
			query.append(" ").append("group by ").append(groupBy);

		if (!StringUtil.isNullOrBlank(orderBy))
			query.append(" ").append("order by ").append(orderBy);

		if (!StringUtil.isNullOrBlank(limit)) {
			if (limit.contains("?"))
				query.append(" limit ").append(limitValue.trim());
			else
				query.append(" limit ").append(limit.trim());
		}

		if (!StringUtil.isNullOrBlank(offset)) {
			if (limit.contains("?"))
				query.append(" offset ").append(offsetValue.trim());
			else
				query.append(" offset ").append(offset.trim());
		}

		log.info(query);
		return query.toString();
	}

	public String getPlain() {
		StringBuilder query = new StringBuilder(this.template);
		for (String criteria : this.keyCriterias) {
			query.append(" ").append(criteria);
		}
		if (!StringUtil.isNullOrBlank(groupBy))
			query.append(" ").append("group by ").append(groupBy);
		log.info(query);
		return query.toString();
	}

	public String[] getValueCriterias() {
		return valueCriterias.toArray(new String[valueCriterias.size()]);
	}

	public void addCriteria(String operand, String leftCriteria,
			String rightCriteria, String reassignedCriteria, String value) {
		String criteria = "";
		if (operand.equals(CriteriaType.VALUE_ASSIGN)) {
			criteria = leftCriteria.concat(" ").concat(rightCriteria);
		} else if (operand.equals(CriteriaType.NULLVALUE_ASSIGN)) {
			if (StringUtil.isNullOrBlank(value)) {
				criteria = leftCriteria.concat(" ").concat(reassignedCriteria);
			} else {
				criteria = leftCriteria.concat(" ").concat(rightCriteria);
			}
		} else if (operand.equals(CriteriaType.NULLVALUE_NOASSIGN)) {
			if (!StringUtil.isNullOrBlank(value)) {
				criteria = leftCriteria.concat(" ").concat(rightCriteria);
			}
		}
		if (!criteria.equals("")) {
			keyCriterias.add(criteria);
			if (criteria.contains("?"))
				valueCriterias.add(value);
		}

	}

	public void assign(String... values) {
		System.out.println("===========+>"+this.fxdCriteria);
		// offset and null
		int oNL = 0;
		if (!StringUtil.isNullOrBlank(limit) && limit.contains("?"))
			oNL++;
		if (!StringUtil.isNullOrBlank(offset) && offset.contains("?"))
			oNL++;

		if (values.length - oNL - this.fxdCriteria == criterias.size()) {
			int i;
			for (i = 0; i < values.length - oNL; i++) {
				if (i < this.fxdCriteria)
					valueCriterias.add(values[i]);
				else
					addCriteria(this.criterias.get(i - this.fxdCriteria)[0],
							this.criterias.get(i - this.fxdCriteria)[1],
							this.criterias.get(i - this.fxdCriteria)[2],
							this.criterias.get(i - this.fxdCriteria)[3],
							values[i]);

			}
			if (!StringUtil.isNullOrBlank(this.limit)
					&& this.limit.contains("?")) {
				this.limitValue = values[i];
			}
			if (!StringUtil.isNullOrBlank(this.offset)
					&& this.offset.contains("?")) {
				this.offsetValue = values[i + 1];
			}

		} else {
			throw new InternalErrorRuntimeException(
					"Parameterized is not fit!!, expected "
							+ (criterias.size() + oNL) + ", instead of "
							+ values.length);
		}
	}

	public void assignPlain(String... values) {

		// offset and null
		int oNL = 0;
		if (!StringUtil.isNullOrBlank(this.limit) && this.limit.contains("?"))
			oNL++;
		if (!StringUtil.isNullOrBlank(this.offset) && this.offset.contains("?"))
			oNL++;

		if (values.length - oNL - fxdCriteria == this.criterias.size()) {
			for (int i = 0; i < values.length - oNL; i++) {
				if (i < fxdCriteria)
					this.valueCriterias.add(values[i]);
				else
					addCriteria(this.criterias.get(i - this.fxdCriteria)[0],
							this.criterias.get(i - this.fxdCriteria)[1],
							this.criterias.get(i - this.fxdCriteria)[2],
							this.criterias.get(i - this.fxdCriteria)[3],
							values[i]);
			}
		} else {
			log.error("Parameterized is not fit!!, expected "
					+ (criterias.size() + oNL) + ", instead of "
					+ values.length);
		}
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public void setOrderBy(String value) {
		this.orderBy = value;

	}

	public void setLimit(String value) {
		this.limit = value;
	}

	public void setOffset(String value) {
		this.offset = value;
	}

	public void setCriterias(List<String[]> criterias) {
		this.criterias = criterias;
	}

	public QMap clones() {
		QMap map = new QMap(this.template);
		map.setCriterias(this.criterias);
		map.setLimit(this.limit);
		map.setOffset(this.offset);
		map.setOrderBy(this.orderBy);
		map.setGroupBy(this.groupBy);
		return map;
	}
}
