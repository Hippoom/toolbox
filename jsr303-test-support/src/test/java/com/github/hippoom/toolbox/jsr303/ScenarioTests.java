package com.github.hippoom.toolbox.jsr303;

import static com.github.hippoom.toolbox.jsr303.Scenario.and;
import static com.github.hippoom.toolbox.jsr303.Scenario.given;
import static com.github.hippoom.toolbox.jsr303.Scenario.itShouldFailFor;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.validation.constraints.NotNull;

import org.junit.Test;

public class ScenarioTests {

	@Test
	public void givenReturnsAnInstanceOfReflectedFieldWithFieldNameAndValue()
			throws Throwable {

		final String fieldName = "notNullField";
		final ReflectedField field = given(fieldName, null);

		assertThat(field.getFieldName(), equalTo(fieldName));
		assertThat(field.getValue(), nullValue());
	}

	@Test
	public void itShouldFailForReturnsAnObjectArrayAsParameters()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		final String fieldName = "field1";

		final ReflectedField[] actual = new ReflectedField[] { new ReflectedField(
				fieldName, null) };

		final Object[] parameters = itShouldFailFor(expectedConstraint,
				given(fieldName, null));
		assertThat(parameters[0], equalTo((Object) expectedConstraint));
		assertThat(parameters[1], equalTo((Object) actual));
	}

	@Test
	public void itShouldFailForReturnsAnObjectArrayAsParametersGivenMultipleReflectedFields()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		ReflectedField reflectedField1 = new ReflectedField("f1", null);
		ReflectedField reflectedField2 = new ReflectedField("f2", "v2");

		final ReflectedField[] actual = new ReflectedField[] { reflectedField1,
				reflectedField2 };

		final Object[] parameters = itShouldFailFor(
				expectedConstraint,
				given(reflectedField1.getFieldName(),
						reflectedField1.getValue()),
				and(reflectedField2.getFieldName(), reflectedField2.getValue()));
		assertThat(parameters[0], equalTo((Object) expectedConstraint));
		assertThat(parameters[1], equalTo((Object) actual));
	}
}
