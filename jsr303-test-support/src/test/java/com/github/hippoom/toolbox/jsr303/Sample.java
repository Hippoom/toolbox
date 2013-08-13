package com.github.hippoom.toolbox.jsr303;

import javax.validation.constraints.NotNull;

public class Sample {
	@NotNull
	private String notNullField;

	public String getNotNullField() {
		return notNullField;
	}

	public void setNotNullField(String notNullField) {
		this.notNullField = notNullField;
	}

}
