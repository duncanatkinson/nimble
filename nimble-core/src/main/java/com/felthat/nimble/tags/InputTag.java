package com.felthat.nimble.tags;

import javax.servlet.jsp.JspException;



public class InputTag extends FormInputTag {

	private static final long serialVersionUID = 1L;

	public InputTag() {
		super();
		this.tagName = "input";
	}

	
	
	@Override
	public int doStartTag() throws JspException {
		addCssClass("nimble");
		String valueFromGraph = getValueFromGraph(pageContext);
		setValue(valueFromGraph== null ? "" : valueFromGraph);
		return super.doStartTag();
	}

}