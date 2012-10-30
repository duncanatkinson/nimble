package com.felthat.nimble.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ValidationFilter implements Filter {

	public static final String VALIDATION_RESULTS = "ERRORS_LIST_OBJECT_NAME";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ArrayList<ValidationResult> results = new ArrayList<ValidationResult>();
		
		@SuppressWarnings("unchecked")
		Enumeration<String> e = request.getAttributeNames();
		while(e.hasMoreElements()){
			String requestAttribute = e.nextElement();
			System.out.println(requestAttribute);
			ValidationResult validationResult = new ValidationResult();
			validationResult.setSuccess(true);
			results.add(validationResult);
		}
		request.setAttribute(VALIDATION_RESULTS, results);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		List<Validator> validators = new ArrayList<Validator>();
		config.getServletContext().setAttribute(Validator.VALIDATORS, validators);
	}

}
