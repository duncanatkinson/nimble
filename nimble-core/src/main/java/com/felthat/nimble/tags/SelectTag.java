package com.felthat.nimble.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;


public class SelectTag extends FormInputTag {

	private static final long serialVersionUID = 1L;
	
	public static final String SELECT_TAG_VALUE = "SELECT_TAG_VALUE";

	public SelectTag() {
		this.tagName = "select";
		addCssClass("nimble");
	}

	@Override
	public int doStartTag() throws JspException {
		String value = NimbleAccessHelper.getAsString(getValueFromGraph(pageContext));
		pageContext.setAttribute(SELECT_TAG_VALUE, value, PageContext.PAGE_SCOPE);
		super.doStartTag();
		return EVAL_BODY_INCLUDE;//We have a body!
	}
	
	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(SELECT_TAG_VALUE);
		return super.doEndTag();
	}

}
