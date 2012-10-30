package com.felthat.nimble.validation;

public interface Validator {

	String VALIDATORS = "VALIDATORS_APPLICATIONSCOPE_KEY";

	public ValidationResult validate();
	
}
