package com.github.hippoom.toolbox.jsr303.parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.github.hippoom.toolbox.jsr303.FieldGiven;
import com.github.hippoom.toolbox.jsr303.Passed;

/**
 * 
 * <pre>
 *   This is a template for validation tests.
 *   
 *   You could take a look at this example:
 *   
 *   public class SampleTests extends ParameterizedValidationTestTemplate&lt;Sample&gt; {
 *  
 *  	    private Sample target = new Sample();
 *  
 *  	    public SampleTests(Class&lt;?&gt; expected, FieldGiven[] fieldsGiven) {
 *  		    super(expected, fieldsGiven);
 *  	    }
 *  
 *  	    &#064;Parameters
 *  	    public static Collection&lt;Object[]&gt; data() {
 *  	        return new Scenarios()
 *  	            .itShouldPassGivenVald()
 *  	            .itShouldFailFor(NotNull.class, given("notNullField", null))
 *  	            .itShouldFailFor(NotEmpty.class, givenEmpty("notEmptyField"))
 *  	            .itShouldFailFor(NotBlank.class, givenBlank("notBlankField"))
 *  	            .itShouldFailFor(NotBlank.class, when(Group.class), given("sampleGroupNotBlankField", null))
 *  	            .itShouldFailFor(NotBlank.class, when(Group.class), givenBlank("notBlankField"))
 *  	            .build();
 *  	    }
 *  
 *  	    &#064;Override
 *  	    public Sample toBeValidated() {
 *  		    return target;
 *  	    }
 *  
 *  	    &#064;Override
 *  	    public void populateWithValidValues() {
 *  		    target.setNotNullField("");
 *  		    target.setNotEmptyField(" ");
 *  		    target.setNotBlankField("hello");
 *  	    }
 *  
 *  }
 *  
 *  You could use {@link Scenarios} for DSL support.
 * 
 * </pre>
 * 
 * @author Hippoom
 * @param <T>
 *            Type of
 *            {@link ParameterizedValidationTestTemplate#toBeValidated()}
 * @see Scenarios
 * 
 */
@RunWith(Parameterized.class)
public abstract class ParameterizedValidationTestTemplate<T> {

	private Class<?> expected;
	private Class<?>[] groups;
	private FieldGiven[] fieldsGiven;

	/**
	 * Constructor for {@link Parameterized}
	 * 
	 * @param expected
	 *            Which jsr303 annotation you expect
	 * @param groups
	 *            Which groups should be validated, new Class<?>[] {
	 *            Default.class } if default
	 * @param fieldsGiven
	 *            filedGiven to pass/break the expected constraint
	 */
	public ParameterizedValidationTestTemplate(Class<?> expected,
			Class<?>[] groups, FieldGiven[] fieldsGiven) {
		this.fieldsGiven = fieldsGiven;
		this.expected = expected;
		this.groups = groups;
	}

	@Test
	public void validates() throws Exception {

		if (expectThatItShouldPass()) {
			// assume to be validated has been populdated with valid values
			Set<ConstraintViolation<T>> constraintViolations = validate();
			assertNone(constraintViolations);
		} else {
			update(toBeValidated());
			Set<ConstraintViolation<T>> constraintViolations = validate();
			assertThereAre(constraintViolations);
			final ConstraintViolation<T> constraintViolation = theOnlyOneOf(constraintViolations);
			assertExpected(constraintViolation);
		}
	}

	private Set<ConstraintViolation<T>> validate() {
		Validator validator = getValidator();
		return validator.validate(toBeValidated(), groups);
	}

	private boolean expectThatItShouldPass() {
		return Passed.class.equals(expected);
	}

	/**
	 * Subclass could override this to provide a custom {@link Validator}
	 */
	protected Validator getValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator;
	}

	/**
	 * update {@link ParameterizedValidationTestTemplate#toBeValidated()} with
	 * {@link ParameterizedValidationTestTemplate#fieldsGiven}
	 */
	private void update(Object toBeValidated) throws NoSuchFieldException,
			IllegalAccessException {
		ReflectionHelper.populate(toBeValidated, fieldsGiven);
	}

	private void assertNone(Set<ConstraintViolation<T>> constraintViolations) {
		assertTrue("Unexpected constraint violation found, "
				+ constraintViolations.toString(),
				constraintViolations.isEmpty());
	}

	private ConstraintViolation<T> theOnlyOneOf(
			Set<ConstraintViolation<T>> constraintViolations) {
		return constraintViolations.iterator().next();
	}

	private void assertExpected(final ConstraintViolation<T> constraintViolation) {
		assertEquals("Unexpected constraint violation found,", expected,
				constraintViolation.getConstraintDescriptor().getAnnotation()
						.annotationType());
	}

	private void assertThereAre(Set<ConstraintViolation<T>> constraintViolations)
			throws AssertionError {
		if (constraintViolations.size() == 0) {
			throw new java.lang.AssertionError(
					"Expect constraint violations, got none. ");
		}
	}

	/**
	 * <pre>
	 * The returning instance will be tested.
	 * 
	 * Subclass should make sure it is not a null value.
	 * 
	 * e.g.
	 *  
	 * public class SampleTests extends ParameterizedValidationTestTemplate&lt;Sample&gt; {
	 * 
	 *     private Sample target = new Sample();
	 * 
	 *     &#064;Override
	 *     public Sample toBeValidated() {
	 *         return target;
	 *     }
	 * }
	 * 
	 * </pre>
	 */
	public abstract T toBeValidated();

	/**
	 * <pre>
	 * 
	 * Subclass should override this to provide a valid instance.
	 * e.g.
	 *  
	 * public class SampleTests extends ParameterizedValidationTestTemplate&lt;Sample&gt; {
	 * 
	 *     private Sample target = new Sample();
	 * 
	 *     &#064;Override
	 *     public void populateWithValidValues() {
	 *         target.setNotNullField("");
	 *         target.setNotEmptyField(" ");
	 *         target.setNotBlankField("hello");
	 *     }
	 * }  
	 * 
	 * This could minimize constraints broken. 
	 * This is very important for assertion.
	 * 
	 * For example, we need to assert the {@link ConstraintViolation#getPropertyPath()} 
	 *     to determine whether it is an expected constraint violation:
	 *     
	 *     &#064;NotNull
	 *     private String aNotNullField;//This is we want to test
	 *     
	 *     &#064;NotNull
	 *     private String anotherNotNullField;//This will also break for &#064;NotNull if null
	 *     
	 * But I'm afraid that fieldName check does not suit well for class level constraint:
	 * 
	 * 
	 * 
	 * 
	 * </pre>
	 */
	@Before
	public abstract void populateWithValidValues();

}