package com.xybase.ax.eai.archcomp.xyscrib.listener.pg.model;

import java.io.Serializable;

public class PostgresNotification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1652468432605790445L;
	private int processId;
	private String channelName;
	private String payloadType;
	private String payload;
	private String tableName;
	private String database;

	private String sourceSystem;

	public PostgresNotification(int processId, String channelName,
			String payload) {
		super();
		this.processId = processId;
		this.channelName = channelName;
		this.payload = payload;
	}

	public PostgresNotification(int processId, String channelName,
			String payload, String database) {
		super();
		this.processId = processId;
		this.channelName = channelName;
		this.payload = payload;
		this.database = database;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getPayloadType() {
		return payloadType;
	}

	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	@Override
	public String toString() {
		return "PostgresNotification [processId=" + processId
				+ ", channelName=" + channelName + ", payloadType="
				+ payloadType + ", payload=" + payload + ", tableName="
				+ tableName + ", database=" + database + ", sourceSystem="
				+ sourceSystem + "]";
	}

	public String asAcdmTemplate() {
		return "{ \"service\" : \"" + channelName + "_on_" + tableName
				+ "\", \"payload\" : " + payload + ", \"metadata\" : {}, }";
	}

	public String asJson() {
		return "{ \"service\" : \"" + channelName + "_on_" + tableName
				+ "\", \"payload\" : " + payload + ", \"metadata\" : {}, }";
	}

}
