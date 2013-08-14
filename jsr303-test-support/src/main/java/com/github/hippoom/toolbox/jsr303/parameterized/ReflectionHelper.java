package com.github.hippoom.toolbox.jsr303.parameterized;

import java.lang.reflect.Field;

import com.github.hippoom.toolbox.jsr303.FieldGiven;

public class ReflectionHelper {
	public static void populate(Object target, FieldGiven... fieldsGiven)
			throws NoSuchFieldException, IllegalAccessException {

		reportIfGivenNull(target);

		reportIfGivenNull(fieldsGiven);

		for (FieldGiven fieldGiven : fieldsGiven) {
			reportIfGivenNull(fieldGiven);
			Field field = target.getClass().getDeclaredField(
					fieldGiven.getFieldName());
			field.setAccessible(true);
			field.set(target, fieldGiven.getValue());
		}
	}

	private static void reportIfGivenNull(FieldGiven fieldGiven) {
		if (fieldGiven == null) {
			throw new NullPointerException("Field given must not be null.");
		}
	}

	private static void reportIfGivenNull(FieldGiven[] fieldsGiven) {
		if (fieldsGiven == null) {
			throw new NullPointerException("Fields given must not be null.");
		}
	}

	private static void reportIfGivenNull(Object target) {
		if (target == null) {
			throw new NullPointerException(
					"Object to be validated must not be null.");
		}
	}
}
