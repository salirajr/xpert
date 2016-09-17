package com.xybase.ax.eai.waconsole.appliance.qmap.dao;

import java.util.Map;

public interface QMapDao {

	public boolean hasKey(String key);

	public boolean update(String key, String... parameters);

	public String retrieve(String concealKey, String key, String... parameters);
	
	public String count(String key, String... parameters);

	public void setConceal(Map<String, String> conceal);

}
