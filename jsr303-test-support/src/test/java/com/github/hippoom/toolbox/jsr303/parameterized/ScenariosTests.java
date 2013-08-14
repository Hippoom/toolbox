package com.github.hippoom.toolbox.jsr303.parameterized;

import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.and;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.given;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.givenBlank;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.givenEmpty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.junit.Test;

import com.github.hippoom.toolbox.jsr303.FieldGiven;
import com.github.hippoom.toolbox.jsr303.Passed;
import com.github.hippoom.toolbox.jsr303.parameterized.Scenarios;

public class ScenariosTests {

	private Scenarios scenarios = new Scenarios();

	@Test
	public void givenReturnsAnInstanceOfFieldsGivenWithFieldNameAndValue()
			throws Throwable {

		final String fieldName = "notNullField";
		final FieldGiven field = given(fieldName, null);

		assertThat(field.getFieldName(), equalTo(fieldName));
		assertThat(field.getValue(), nullValue());
	}

	@Test
	public void itShouldFailForReturnsParametersToGenerateOneTestCase()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		final String fieldName = "field1";

		final FieldGiven[] fieldsGiven = new FieldGiven[] { new FieldGiven(
				fieldName, null) };

		Collection<Object[]> params = scenarios.itShouldFailFor(
				expectedConstraint, given(fieldName, null)).build();

		assertThat(params, hasItem(new Object[] { expectedConstraint,
				fieldsGiven }));
	}

	@Test
	public void itShouldFailForReturnsParametersToGenerateOneTestCaseGivenMultipleFieldsGiven()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		FieldGiven givenField1 = new FieldGiven("f1", null);
		FieldGiven givenField2 = new FieldGiven("f2", "v2");

		final FieldGiven[] fieldsGiven = new FieldGiven[] { givenField1,
				givenField2 };

		Collection<Object[]> params = scenarios.itShouldFailFor(
				expectedConstraint,
				given(givenField1.getFieldName(), givenField1.getValue()),
				and(givenField2.getFieldName(), givenField2.getValue()))
				.build();
		assertThat(params, hasItem(new Object[] { expectedConstraint,
				fieldsGiven }));
	}

	@Test
	public void itShouldFailForReturnsParameterGroupToGenerateNullEmptyAndBlankTestCaseGivenBlankField()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		String fieldName = "f1";
		FieldGiven[] nullField = new FieldGiven[] { new FieldGiven(fieldName,
				null) };
		FieldGiven[] emptyField = new FieldGiven[] { new FieldGiven(fieldName,
				"") };
		FieldGiven[] blankField = new FieldGiven[] { new FieldGiven(fieldName,
				" ") };

		Collection<Object[]> params = scenarios.itShouldFailFor(
				expectedConstraint, givenBlank(fieldName)).build();

		assertThat(params,
				hasItem(new Object[] { expectedConstraint, nullField }));

		assertThat(params, hasItem(new Object[] { expectedConstraint,
				emptyField }));

		assertThat(params, hasItem(new Object[] { expectedConstraint,
				blankField }));
	}

	@Test
	public void itShouldFailForReturnsParameterGroupToGenerateNullAndEmptyTestCaseGivenEmptyField()
			throws Throwable {

		final Class<NotNull> expectedConstraint = NotNull.class;

		String fieldName = "f1";
		FieldGiven[] nullField = new FieldGiven[] { new FieldGiven(fieldName,
				null) };
		FieldGiven[] emptyField = new FieldGiven[] { new FieldGiven(fieldName,
				"") };

		Collection<Object[]> params = scenarios.itShouldFailFor(
				expectedConstraint, givenEmpty(fieldName)).build();

		assertThat(params,
				hasItem(new Object[] { expectedConstraint, nullField }));

		assertThat(params, hasItem(new Object[] { expectedConstraint,
				emptyField }));
	}

	@Test
	public void itShouldPassReturnsParametersToGenerateOneTestCaseGivenMultipleFieldsGiven()
			throws Throwable {

		Collection<Object[]> params = scenarios.itShouldPassGivenVald().build();
		assertThat(params, hasItem(new Object[] { Passed.class,
				new FieldGiven[] {} }));
	}

}
