package com.xybase.ax.eai.waconsole.appliance;

public class Context {

	/*
	 * 0: DELETE 1: CREATE 2: UPDATE
	 */
	private String path;
	private String name;
	private String content;

	private String sourcePath;
	private String sourceName;

	/**
	 * 
	 */
	public Context() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Override
	public String toString() {
		return "Context [path=" + path + ", name=" + name + ", content="
				+ content + ", sourcePath=" + sourcePath + ", sourceName="
				+ sourceName + "]";
	}

}
