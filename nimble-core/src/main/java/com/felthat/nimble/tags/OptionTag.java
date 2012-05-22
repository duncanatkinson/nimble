package com.felthat.nimble.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;


public class OptionTag extends FormInputTag {
	
	private static final long serialVersionUID = -3439455270987878464L;

	public OptionTag() {
		this.tagName = "option";
	}
	
	@Override
	public int doStartTag() throws JspException{
		String valueOfSelect = (String) pageContext.getAttribute(SelectTag.SELECT_TAG_VALUE,PageContext.PAGE_SCOPE);
		if(valueOfSelect == null){
			valueOfSelect = "";
		}
		if(valueOfSelect.equals(getValue("value"))){
			setValue("selected", "true");
		}
		super.doStartTag();
		return EVAL_BODY_INCLUDE;
	}
}
