package com.felthat.nimble.tags;

import java.util.List;

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
		List<String> valueFromGraph = getValueFromGraph(pageContext);
		
		if(!"radio".equalsIgnoreCase(getType())){
			setValue(valueFromGraph== null ? "" : valueFromGraph.get(0));//TODO deal or warn about rest of array
		}else if(RADIO.equalsIgnoreCase(getType())){
			if(getValue() != null && getValue().equals(valueFromGraph.get(0))){
				setChecked("checked");
			}
		}
		return super.doStartTag();
	}

}
