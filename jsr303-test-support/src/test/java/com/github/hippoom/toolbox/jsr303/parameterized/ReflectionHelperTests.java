package com.github.hippoom.toolbox.jsr303.parameterized;

import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.and;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.hippoom.toolbox.jsr303.FieldGiven;
import com.github.hippoom.toolbox.jsr303.Sample;
import com.github.hippoom.toolbox.jsr303.parameterized.ReflectionHelper;

public class ReflectionHelperTests {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void updateValueIfFieldFound() throws Throwable {

		final Sample target = new Sample();

		ReflectionHelper.populate(target, given("notNullField", "value"));

		assertThat(target.getNotNullField(), equalTo("value"));
	}

	@Test
	public void updateValuesIfFieldsFound() throws Throwable {

		final Sample target = new Sample();

		ReflectionHelper.populate(target, given("notNullField", "value"),
				and("notBlankField", "anotherValue"));

		assertThat(target.getNotNullField(), equalTo("value"));
		assertThat(target.getNotBlankField(), equalTo("anotherValue"));
	}

	@Test(expected = NoSuchFieldException.class)
	public void throwExceptionIfNoSuchField() throws Throwable {

		final Sample target = new Sample();

		ReflectionHelper.populate(target, given("noSuchField", "value"));
	}

	@Test
	public void throwExceptionIfTargetIsNull() throws Throwable {
		expectedException.expect(NullPointerException.class);
		expectedException
				.expectMessage("Object to be validated must not be null.");

		final Sample target = null;

		ReflectionHelper.populate(target, given("notNullField", "value"));
	}

	@Test
	public void throwExceptionIfAllFieldGivenAreNull() throws Throwable {

		expectedException.expect(NullPointerException.class);
		expectedException.expectMessage("Fields given must not be null.");

		final Sample target = new Sample();

		FieldGiven[] fields = null;

		ReflectionHelper.populate(target, fields);
	}

	@Test
	public void throwExceptionIfAnyFieldGivenIsNull() throws Throwable {

		expectedException.expect(NullPointerException.class);
		expectedException.expectMessage("Field given must not be null.");

		final Sample target = new Sample();

		ReflectionHelper.populate(target, given("notNullField", "value"), null);
	}
}
