package com.github.hippoom.toolbox.jsr303.parameterized;

import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.given;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.givenBlank;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.givenEmpty;
import static com.github.hippoom.toolbox.jsr303.parameterized.Scenarios.when;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.runners.Parameterized.Parameters;

import com.github.hippoom.toolbox.jsr303.FieldGiven;
import com.github.hippoom.toolbox.jsr303.Group;
import com.github.hippoom.toolbox.jsr303.Sample;

public class SampleTests extends ParameterizedValidationTestTemplate<Sample> {

	private Sample target = new Sample();

	public SampleTests(Class<?> expected, Class<?>[] groups,
			FieldGiven[] fieldsGiven) {
		super(expected, groups, fieldsGiven);
	}

	@Parameters
	public static Collection<Object[]> data() {
		return new Scenarios()
				.itShouldPassGivenValid()
				.itShouldFailFor(NotNull.class, given("notNullField", null))
				.itShouldFailFor(NotEmpty.class, givenEmpty("notEmptyField"))
				.itShouldFailFor(NotBlank.class, givenBlank("notBlankField"))
				.itShouldFailFor(NotBlank.class, when(Group.class),
						given("sampleGroupNotBlankField", null))
				.itShouldFailFor(NotBlank.class, when(Group.class),
						givenBlank("notBlankField")).build();
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
