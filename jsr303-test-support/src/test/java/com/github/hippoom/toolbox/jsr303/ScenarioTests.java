package com.github.hippoom.toolbox.jsr303;

import static com.github.hippoom.toolbox.jsr303.Scenario.and;
import static com.github.hippoom.toolbox.jsr303.Scenario.given;
import static com.github.hippoom.toolbox.jsr303.Scenario.givenBlank;
import static com.github.hippoom.toolbox.jsr303.Scenario.givenEmpty;
import static com.github.hippoom.toolbox.jsr303.Scenario.itShouldFailFor;
import static com.github.hippoom.toolbox.jsr303.Scenario.itShouldPass;
import static com.github.hippoom.toolbox.jsr303.Scenario.itShouldPassGivenValidValues;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

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
	public void itShouldFailForReturnsParametersToGenerateOneTestCase()
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
	public void itShouldFailForReturnsParametersToGenerateOneTestCaseGivenMultipleReflectedFields()
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

	@Test
	public void itShouldFailForReturnsParameterGroupToGenerateNullEmptyAndBlankTestCaseGivenBlankField()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		String fieldName = "f1";
		ReflectedField[] nullField = new ReflectedField[] { new ReflectedField(
				fieldName, null) };
		ReflectedField[] emptyField = new ReflectedField[] { new ReflectedField(
				fieldName, "") };
		ReflectedField[] blankField = new ReflectedField[] { new ReflectedField(
				fieldName, " ") };

		final List<Object[]> parameters = itShouldFailFor(expectedConstraint,
				givenBlank(fieldName));

		assertThat(parameters.get(0)[0], equalTo((Object) expectedConstraint));
		assertThat(parameters.get(0)[1], equalTo((Object) nullField));

		assertThat(parameters.get(1)[0], equalTo((Object) expectedConstraint));
		assertThat(parameters.get(1)[1], equalTo((Object) emptyField));

		assertThat(parameters.get(2)[0], equalTo((Object) expectedConstraint));
		assertThat(parameters.get(2)[1], equalTo((Object) blankField));
	}

	@Test
	public void itShouldFailForReturnsParameterGroupToGenerateNullAndEmptyTestCaseGivenEmptyField()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		String fieldName = "f1";
		ReflectedField[] nullField = new ReflectedField[] { new ReflectedField(
				fieldName, null) };
		ReflectedField[] emptyField = new ReflectedField[] { new ReflectedField(
				fieldName, "") };

		final List<Object[]> parameters = itShouldFailFor(expectedConstraint,
				givenEmpty(fieldName));

		assertThat(parameters.get(0)[0], equalTo((Object) expectedConstraint));
		assertThat(parameters.get(0)[1], equalTo((Object) nullField));

		assertThat(parameters.get(1)[0], equalTo((Object) expectedConstraint));
		assertThat(parameters.get(1)[1], equalTo((Object) emptyField));
	}

	@Test
	public void itShouldPassReturnsParametersToGenerateOneTestCaseGivenMultipleReflectedFields()
			throws Throwable {

		ReflectedField reflectedField1 = new ReflectedField("f1", null);
		ReflectedField reflectedField2 = new ReflectedField("f2", "v2");

		final ReflectedField[] actual = new ReflectedField[] { reflectedField1,
				reflectedField2 };

		final Object[] parameters = itShouldPass(
				given(reflectedField1.getFieldName(),
						reflectedField1.getValue()),
				and(reflectedField2.getFieldName(), reflectedField2.getValue()));
		assertThat(parameters[0], equalTo((Object) Passed.class));
		assertThat(parameters[1], equalTo((Object) actual));
	}

	@Test
	public void itShouldPassGivenValidValuesReturnsParametersToGenerateOneTestCase()
			throws Throwable {

		final Object[] parameters = itShouldPassGivenValidValues();
		assertThat(parameters[0], equalTo((Object) Passed.class));
		assertThat(parameters[1], nullValue());
	}
}
