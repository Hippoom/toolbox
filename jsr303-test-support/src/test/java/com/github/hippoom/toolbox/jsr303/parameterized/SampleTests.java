package com.github.hippoom.toolbox.jsr303.parameterized;

import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.given;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.givenBlank;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.givenEmpty;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.runners.Parameterized.Parameters;

import com.github.hippoom.toolbox.jsr303.FieldGiven;
import com.github.hippoom.toolbox.jsr303.parameterized.ParameterizedValidationTestTemplate;
import com.github.hippoom.toolbox.jsr303.parameterized.Scenarios;

public class SampleTests extends ParameterizedValidationTestTemplate<Sample> {

	private Sample target = new Sample();

	public SampleTests(Class<?> expected, FieldGiven[] fieldsGiven) {
		super(expected, fieldsGiven);
	}

	@Parameters
	public static Collection<Object[]> data() {
		return new Scenarios().itShouldPassGivenVald()
				.itShouldFailFor(NotNull.class, given("notNullField", null))
				.itShouldFailFor(NotEmpty.class, givenEmpty("notEmptyField"))
				.itShouldFailFor(NotBlank.class, givenBlank("notBlankField"))
				.build();
	}

	@Override
	public Sample toBeValidated() {
		return target;
	}

	@Override
	public void populateWithValidValues() {
		target.setNotNullField("");
		target.setNotEmptyField(" ");
		target.setNotBlankField("hello");
	}

}