package com.smilepower.filebox.model;

public class User {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String nameSurname;

	public User() {
	}

	public User(String id, String nameSurname) {
		super();
		this.id = id;
		this.nameSurname = nameSurname;
	}

	public String getNameSurname() {
		return nameSurname;
	}

	public void setNameSurname(String nameSurname) {
		this.nameSurname = nameSurname;
	}

}
