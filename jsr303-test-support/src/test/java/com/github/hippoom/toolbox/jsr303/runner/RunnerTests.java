package com.github.hippoom.toolbox.jsr303.runner;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.junit.runner.RunWith;

import com.github.hippoom.toolbox.jsr303.Sample;

@RunWith(MixedRunner.class)
public class RunnerTests {
	@Scenario(violate = NotNull.class)
	public Set<ConstraintViolation<Sample>> failIfNull() throws Exception {
		return run(new Sample());
	}

	@Scenario(violate = Min.class)
	public Set<ConstraintViolation<Sample>> failIfTooGreat() throws Exception {
		return run(new Sample());
	}

	private Set<ConstraintViolation<Sample>> run(Sample sample)
			throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(sample);
	}
}
