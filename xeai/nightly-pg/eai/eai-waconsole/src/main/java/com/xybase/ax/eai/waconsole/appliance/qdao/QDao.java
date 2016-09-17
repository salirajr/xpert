package com.xybase.ax.eai.waconsole.appliance.qdao;


public interface QDao {

	public boolean hasKey(String key);

	public boolean update(String key, String... parameters);

	public String get(String key, String... parameters);

}
