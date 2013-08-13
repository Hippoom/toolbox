package com.github.hippoom.toolbox.jsr303;

public class Scenario {

	public static ReflectedField given(String fieldName, Object value) {
		return new ReflectedField(fieldName, value);
	}

	public static ReflectedField and(String fieldName, Object value) {
		return given(fieldName, value);
	}

	public static Object[] itShouldFailFor(Class<?> expectedConstraint,
			ReflectedField... fields) {
		Object[] parameters = new Object[2];

		parameters[0] = expectedConstraint;
		parameters[1] = fields;

		return parameters;
	}

}
