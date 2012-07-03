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
		Object valueFromGraph = getValueFromGraph(pageContext);
		String stringValueFromGraph = NimbleAccessHelper.getAsString(valueFromGraph);
		
		if(TEXT.equalsIgnoreCase(getType())){
			setValue(stringValueFromGraph);
		}else if(CHECKBOX.equalsIgnoreCase(getType()) || RADIO.equalsIgnoreCase(getType())){
			if(getValue() != null && getValue().equals(stringValueFromGraph)){
				setChecked("checked");
			}
		}
		return super.doStartTag();
	}

}
