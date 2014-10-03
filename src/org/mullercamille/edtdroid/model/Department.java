package org.mullercamille.edtdroid.model;

public class Department {
	private String name;
	private String value;

	public Department(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}
}
