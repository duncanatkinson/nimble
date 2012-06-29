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
		
		if(!"radio".equalsIgnoreCase(getType())){
			setValue(valueFromGraph== null ? "" : valueFromGraph);
		}else if(RADIO.equalsIgnoreCase(getType())){
			if(getValue() != null && getValue().equals(valueFromGraph)){
				setSelected("selected");
			}
		}
		return super.doStartTag();
	}

}
