package com.github.hippoom.toolbox.jsr303;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ReflectedField {

	@Getter
	private String fieldName;
	@Getter
	private Object value;

	public ReflectedField(String fieldName, Object value) {
		this.fieldName = fieldName;
		this.value = value;
	}

}
