package com.smilepower.filebox.model;

public class BoxFile {

	private String id;
	private String fileName;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BoxFile(String id, String fileName) {
		super();
		this.id = id;
		this.fileName = fileName;
	}
}
