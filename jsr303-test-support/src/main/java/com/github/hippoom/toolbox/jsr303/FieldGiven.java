package com.github.hippoom.toolbox.jsr303;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class FieldGiven {

	@Getter
	private String fieldName;
	@Getter
	private Object value;

	public FieldGiven(String fieldName, Object value) {
		this.fieldName = fieldName;
		this.value = value;
	}

}
