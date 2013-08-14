package com.github.hippoom.toolbox.jsr303.parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.hippoom.toolbox.jsr303.FieldGiven;
import com.github.hippoom.toolbox.jsr303.Passed;

/**
 * 
 * This is a builder for data() in Parameterized tests.
 * 
 * @author Hippoom
 * 
 */
public class Scenarios {

	private Collection<Object[]> scenarios = new ArrayList<Object[]>();

	/**
	 * add test that checks if it'll pass given valid values populated
	 */
	public Scenarios itShouldPassGivenVald() {
		return itShould(pass(), givenValid());
	}

	/**
	 * add test that checks if it'll fail
	 */
	public Scenarios itShouldFailFor(Class<?> expectedConstraint,
			FieldGiven... fields) {
		return itShould(failFor(expectedConstraint), fields);
	}

	/**
	 * add multiple tests that checks if they'll fail
	 */
	public Scenarios itShouldFailFor(Class<?> expectedConstraint,
			List<FieldGiven> fields) {
		for (FieldGiven field : fields) {
			itShouldFailFor(expectedConstraint, field);
		}
		return this;
	}

	private Scenarios itShould(Class<?> expectedConstraint,
			FieldGiven... fields) {
		this.scenarios.add(scenario(expectedConstraint, fields));
		return this;
	}

	public Collection<Object[]> build() {
		return scenarios;
	}

	/**
	 * returns a {@link FieldGiven}, this one should break the constraint.
	 */
	public static FieldGiven given(String fieldName, Object value) {
		return new FieldGiven(fieldName, value);
	}

	/**
	 * <pre>
	 * returns a {@link FieldGiven}, this one should break the constraint.
	 * </pre>
	 * 
	 * @see Scenarios#given(String, Object)
	 */
	public static FieldGiven and(String fieldName, Object value) {
		return given(fieldName, value);
	}

	/**
	 * <pre>
	 * returns three {@link FieldGiven}s, they should break the constraint with null/emtpy/blank.
	 * </pre>
	 * 
	 */
	public static List<FieldGiven> givenBlank(String fieldName) {
		List<FieldGiven> fields = new ArrayList<FieldGiven>();
		fields.addAll(givenEmpty(fieldName));
		fields.add(given(fieldName, " "));
		return fields;
	}

	/**
	 * <pre>
	 * returns two {@link FieldGiven}s, they should break the constraint with null/emtpy.
	 * </pre>
	 * 
	 */
	public static List<FieldGiven> givenEmpty(String fieldName) {
		List<FieldGiven> fields = new ArrayList<FieldGiven>();

		fields.add(given(fieldName, null));
		fields.add(given(fieldName, ""));

		return fields;
	}

	private static FieldGiven[] givenValid() {
		return new FieldGiven[] {};
	}

	private static Object[] scenario(Class<?> expectedConstraint,
			FieldGiven... fields) {
		Object[] parameters = new Object[2];

		parameters[0] = expectedConstraint;
		parameters[1] = fields;

		return parameters;
	}

	private static Class<?> failFor(Class<?> expectedConstraint) {
		return expectedConstraint;
	}

	private static Class<Passed> pass() {
		return Passed.class;
	}

}