package com.github.hippoom.toolbox.jsr303.runner;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FrameworkFactoryTestUnitTests {
	public static HashSet<ConstraintViolation<?>> NONE = new HashSet<ConstraintViolation<?>>();

	@Rule
	public ExpectedException thrown = ExpectedException.none()
			.handleAssertionErrors();

	private FrameworkFactoryTest target;

	@Test
	public void check() throws Throwable {
		target = new FrameworkFactoryTest(
				Methods.class.getDeclaredMethod("shouldViolateNotNullButNone"));

		thrown.expect(AssertionError.class);
		thrown.expectMessage("Expect @NotNull violated, got " + NONE);

		target.invokeExplosively(new Methods());
	}

	public static class Methods {
		@Scenario(violate = NotNull.class)
		public Set<ConstraintViolation<?>> shouldViolateNotNullButNone() {
			return NONE;
		}
	}
}
