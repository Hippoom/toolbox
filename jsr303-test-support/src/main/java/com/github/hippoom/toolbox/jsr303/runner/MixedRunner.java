package com.github.hippoom.toolbox.jsr303.runner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class MixedRunner extends BlockJUnit4ClassRunner {

	public MixedRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		final List<FrameworkMethod> tests = new LinkedList<FrameworkMethod>();
		tests.addAll(super.computeTestMethods());
		tests.addAll(computeFactoryTests());

		return tests;
	}

	private List<FrameworkMethod> computeFactoryTests() {
		final List<FrameworkMethod> tests = new LinkedList<FrameworkMethod>();

		// Final all methods in our test class marked with @Scenario.
		for (FrameworkMethod method : getTestClass().getAnnotatedMethods(
				Scenario.class)) {

			System.out.println(method.getMethod());

			// Make sure the TestFactory method is static
			if (!Modifier.isStatic(method.getMethod().getModifiers()))
				throw new IllegalArgumentException("TestFactory " + method
						+ " must be static.");

			try {
				// Execute the method (statically)
				Object instance = method.getMethod().invoke(
						getTestClass().getJavaClass());
				System.out.println(instance);
				tests.add(new FrameworkFactoryTest(getTestClass()
						.getAnnotatedMethods(Runner.class).get(0).getMethod(),
						instance, method.getName()));
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}
		return tests;
	}

	private static class FrameworkFactoryTest extends FrameworkMethod {
		private Object param;
		private String name;

		public FrameworkFactoryTest(Method method, Object param, String name) {
			super(method);
			this.param = param;
			this.name = name;
		}

		@Override
		public Object invokeExplosively(Object target, Object... params)
				throws Throwable {
			// Executes the test method on the supplied target (returned by the
			// TestFactory)
			// and not the instance generated by FrameworkMethod.
			Set<ConstraintViolation<?>> cvs = (Set<ConstraintViolation<?>>) getMethod()
					.invoke(getMethod().getClass(), param);

			System.out.println(cvs.size());

			return null;
		}

		@Override
		public String getName() {
			return String.format("%s=%s", name, getMethod().getName());
		}
	}
}
