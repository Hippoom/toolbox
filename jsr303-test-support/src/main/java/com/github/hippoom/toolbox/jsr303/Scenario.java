package com.github.hippoom.toolbox.jsr303;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

	public static ReflectedField given(String fieldName, Object value) {
		return new ReflectedField(fieldName, value);
	}

	public static ReflectedField and(String fieldName, Object value) {
		return given(fieldName, value);
	}

	public static List<ReflectedField> givenBlank(String fieldName) {
		List<ReflectedField> fields = new ArrayList<ReflectedField>();

		fields.addAll(givenEmpty(fieldName));
		fields.add(given(fieldName, " "));
		return fields;
	}

	public static List<ReflectedField> givenEmpty(String fieldName) {
		List<ReflectedField> fields = new ArrayList<ReflectedField>();

		fields.add(given(fieldName, null));
		fields.add(given(fieldName, ""));

		return fields;
	}

	public static Object[] itShouldFailFor(Class<?> expectedConstraint,
			ReflectedField... fields) {
		Object[] parameters = new Object[2];

		parameters[0] = expectedConstraint;
		parameters[1] = fields;

		return parameters;
	}

	public static List<Object[]> itShouldFailFor(Class<?> expectedConstraint,
			List<ReflectedField> fields) {
		List<Object[]> parametersGroup = new ArrayList<Object[]>();
		for (ReflectedField field : fields) {
			parametersGroup.add(itShouldFailFor(expectedConstraint, field));
		}
		return parametersGroup;
	}

	public static Object[] itShouldPass(ReflectedField... fields) {
		return itShouldFailFor(Passed.class, fields);
	}

	public static Object[] itShouldPassGivenValidValues() {
		ReflectedField fields = null;// null is a special case when run tests
		return itShouldPass(fields);
	}

}
