package com.github.hippoom.toolbox.jsr303.runner;

import java.util.LinkedList;
import java.util.List;

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
			tests.add(new FrameworkFactoryTest(method.getMethod()));
		}
		return tests;
	}
}
