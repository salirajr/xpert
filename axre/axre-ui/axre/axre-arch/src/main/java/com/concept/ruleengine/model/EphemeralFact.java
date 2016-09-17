package com.concept.ruleengine.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EphemeralFact {

	private static Logger logger = LogManager.getLogger(EphemeralFact.class);

	private Map<String, Object> plainFacts;

	@SuppressWarnings("rawtypes")
	private Map<String, Stack> stackFacts;

	@SuppressWarnings("rawtypes")
	private Map<String, List> listFacts;

	@SuppressWarnings("rawtypes")
	private Map<String, Map> mapFacts;

	@SuppressWarnings("rawtypes")
	public EphemeralFact() {
		plainFacts = new HashMap<String, Object>();
		stackFacts = new HashMap<String, Stack>();
		listFacts = new HashMap<String, List>();
		mapFacts = new HashMap<String, Map>();
	}

	@SuppressWarnings("unchecked")
	public void put(EphemeralFactType type, String key, Object value) {
		if (type == EphemeralFactType.list)
			listFacts.get(key).add(value);
		else if (type == EphemeralFactType.stack)
			stackFacts.get(key).push(value);
		else if (type == EphemeralFactType.plain)
			plainFacts.put(key, value);
	}

	public void remap(String key, String targetKey) {
		plainFacts.put(targetKey, plainFacts.get(key));
		plainFacts.remove(key);
		if (logger.isDebugEnabled())
			logger.debug("key: " + key + " remapped to " + targetKey
					+ ", value: " + plainFacts.get(targetKey));
	}

	public void put(String key, Object value) {
		plainFacts.put(key, value);
	}

	@SuppressWarnings("rawtypes")
	public void putList(String key, List value) {
		listFacts.put(key, value);
	}

	@SuppressWarnings("rawtypes")
	public void putMap(String key, Map map) {
		mapFacts.put(key, map);
	}

	public Object get(String key) {
		return plainFacts.get(key);
	}

	public boolean getBoolean(String key) {
		return (boolean) plainFacts.get(key);
	}

	public int getInt(String key) {
		if (plainFacts.get(key) instanceof String)
			return Integer.valueOf(plainFacts.get(key).toString());
		return (int) plainFacts.get(key);
	}

	public Double getNumeric(String key) {
		if (plainFacts.get(key) instanceof String)
			return Double.valueOf(plainFacts.get(key).toString());
		return (double) plainFacts.get(key);
	}

	public String getString(String key) {
		return plainFacts.get(key).toString();
	}

	public Date getDate(String key) {
		return (Date) plainFacts.get(key);
	}

	@SuppressWarnings("rawtypes")
	public List getList(String key) {
		return listFacts.get(key);
	}

	public Object getListItemAt(String key, int index) {
		if (listFacts.get(key).size() < index)
			return null;
		return listFacts.get(key).get(index);
	}

	public Object get(EphemeralFactType type, String key) {
		if (type == EphemeralFactType.list)
			return listFacts.get(key).get(listFacts.size());

		if (type == EphemeralFactType.stack)
			return stackFacts.get(key).pop();

		if (type == EphemeralFactType.plain)
			return plainFacts.get(key);

		return null;
	}

	@SuppressWarnings("rawtypes")
	public Map getMap(String key) {
		return mapFacts.get(key);
	}

	public Object getMapItem(String key, String name) {
		return mapFacts.get(key).get(name);
	}

	public Integer getMapItemInt(String key, String name) {
		return (Integer) mapFacts.get(key).get(name);
	}

	public Object peekStack(String key) {
		return stackFacts.get(key).peek();
	}

	public Object getListIndex(String key, int index) {
		return listFacts.get(key).get(index);
	}

	public void remove(String... keys) {
		for (String key : keys) {
			listFacts.remove(key);
			stackFacts.remove(key);
			plainFacts.remove(key);
			mapFacts.remove(key);
		}
	}

	@Override
	public String toString() {
		return "EphemeralFact [plainFacts=" + plainFacts + ", stackFacts="
				+ stackFacts + ", listFacts=" + listFacts + ", mapFacts="
				+ mapFacts + "]";
	}

}
