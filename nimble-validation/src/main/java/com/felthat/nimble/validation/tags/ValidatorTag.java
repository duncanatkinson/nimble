package com.felthat.nimble.validation.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class ValidatorTag extends TagSupport {

	@Override
	public int doStartTag() throws ValidatorTagException {
		// TODO Auto-generated method stub
		return EVAL_BODY_INCLUDE;
	}
	
	
}
