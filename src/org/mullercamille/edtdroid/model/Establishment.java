package org.mullercamille.edtdroid.model;

public class Establishment {
	private String name;
	private String value;

	public Establishment(String name, String value) {
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