package com.github.hippoom.toolbox.jsr303.runner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.runners.model.FrameworkMethod;

public class FrameworkFactoryTest extends FrameworkMethod {

	public FrameworkFactoryTest(Method method) {
		super(method);
	}

	@Override
	public Object invokeExplosively(Object target, Object... params)
			throws Throwable {

		Scenario scenario = getMethod().getAnnotation(Scenario.class);

		Class<?> constraint = scenario.violate();

		Set<ConstraintViolation<?>> cvs = (Set<ConstraintViolation<?>>) super
				.invokeExplosively(target, params);

		if (constraint != null) {
			boolean flag = false;
			for (ConstraintViolation<?> cv : cvs) {
				if (constraint.equals(cv.getConstraintDescriptor()
						.getAnnotation())) {
					flag = true;
					break;
				} else {
					continue;
				}
			}
			assertTrue("Expect @" + constraint.getSimpleName()
					+ " violated, got " + cvs, flag);
		}

		return null;
	}
}