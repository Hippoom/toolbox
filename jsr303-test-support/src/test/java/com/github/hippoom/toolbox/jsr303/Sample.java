package com.github.hippoom.toolbox.jsr303;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@ToString
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
