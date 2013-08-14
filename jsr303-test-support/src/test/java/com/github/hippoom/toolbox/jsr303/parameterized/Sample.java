package com.github.hippoom.toolbox.jsr303.parameterized;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.hippoom.toolbox.jsr303.Group;

public class Sample {
	@Setter
	@Getter
	@NotNull
	private String notNullField;
	@Setter
	@Getter
	@NotEmpty
	private String notEmptyField;
	@Setter
	@Getter
	@NotBlank
	private String notBlankField;
	@Setter
	@Getter
	@NotBlank(groups = Group.class)
	private String sampleGroupNotBlankField;

}
