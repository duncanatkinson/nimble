package com.felthat.nimble.validation;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class ValidationFilterTest {

	
	private ValidationFilter filter;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private FilterChain chain;
	
	@Before
	public void setup(){
		filter = new ValidationFilter();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		chain = new MockFilterChain();
	}
	
	
	@Test
	public void testFilterEmptyRequest() throws IOException, ServletException {
		filter.doFilter(request, response, chain);
	}
	
	@Test
	public void testFilterEmptyRequest_emptyErrorsInSession() throws IOException, ServletException {
		filter.doFilter(request, response, chain);
		checkResultsSize(0);
	}


	@Test
	public void testFilterInit() throws IOException, ServletException {
		FilterConfig config = new MockFilterConfig();
		filter.init(config);
		ServletContext servletContext = config.getServletContext();
		List<Validator> validators = (List<Validator>) servletContext.getAttribute(Validator.VALIDATORS);
		assertNotNull(validators);
	}

	@Test
	public void testFilterRequestSingleField() throws IOException, ServletException {
		request.setAttribute("/customer/name","Brian");
		filter.doFilter(request, response, chain);
		List<ValidationResult> results = checkResultsSize(1);
		assertTrue(results.get(0).isSuccess());
	}
	
	@Test
	public void testFilterRequestSingleField_withValidator() throws IOException, ServletException {
		
		//Setup the validator
		ServletContext servletContext = request.getSession().getServletContext();
		List<Validator> validators = (List<Validator>) servletContext.getAttribute(Validator.VALIDATORS);
		Validator validator = new Validator() {
			
			@Override
			public ValidationResult validate() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		request.setAttribute("/customer/name","Brian");
		filter.doFilter(request, response, chain);
		List<ValidationResult> results = checkResultsSize(1);
		assertTrue(results.get(0).isSuccess());
	}
	
	private List<ValidationResult> checkResultsSize(int size) {
		@SuppressWarnings("unchecked")
		List<ValidationResult> result = (List<ValidationResult>) request.getAttribute(ValidationFilter.VALIDATION_RESULTS);
		assertEquals("Should be " + size, size,result.size());
		return result;
	}
	
	
}
