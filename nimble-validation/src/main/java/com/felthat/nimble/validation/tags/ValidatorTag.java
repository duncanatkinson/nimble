package com.felthat.nimble.validation.tags;

import javax.servlet.jsp.tagext.TagSupport;

public abstract class ValidatorTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPath(String path){
		setValue("path", path);
	}
	
	@Override
	public int doStartTag() throws ValidatorTagException {
		String path = (String) getValue("path");
		if(path == null) throw new ValidatorTagException("Path not set");
		return EVAL_BODY_INCLUDE;
	}
	
	
}
