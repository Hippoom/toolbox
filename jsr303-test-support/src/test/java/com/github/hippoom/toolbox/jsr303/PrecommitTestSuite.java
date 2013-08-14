package com.github.hippoom.toolbox.jsr303;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.hippoom.toolbox.jsr303.parameterized.ReflectionHelperTests;
import com.github.hippoom.toolbox.jsr303.parameterized.SampleTests;
import com.github.hippoom.toolbox.jsr303.parameterized.ScenariosTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ScenariosTests.class, ReflectionHelperTests.class,
		SampleTests.class })
public class PrecommitTestSuite {

}
