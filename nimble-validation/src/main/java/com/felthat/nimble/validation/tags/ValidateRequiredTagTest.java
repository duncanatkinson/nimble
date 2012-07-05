package com.felthat.nimble.validation.tags;

import static org.junit.Assert.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

public class ValidateRequiredTagTest {

	@Test(expected=ValidatorTagException.class)
	public final void doStartTag_noPath() throws JspException {
		ValidateRequiredTag tag = new ValidateRequiredTag();
		tag.doStartTag();
	}

	
	@Test
	public final void doStartTag_simple() throws JspException {
		ValidateRequiredTag tag = new ValidateRequiredTag();
		tag.setValue("path", "customer/name");
		int pageEval = tag.doStartTag();
		assertEquals(TagSupport.EVAL_BODY_INCLUDE, pageEval);
	}
}
