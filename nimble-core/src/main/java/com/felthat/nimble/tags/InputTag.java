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
		List<String> valueFromGraphList = getValueFromGraph(pageContext);
		String valueFromGraph = "";
		if(valueFromGraphList != null && valueFromGraphList.size() > 0){
			 valueFromGraph = valueFromGraphList.get(0);//TODO deal or warn about rest of array
		}
		if(!"radio".equalsIgnoreCase(getType())){
			setValue(valueFromGraph);
		}else if(RADIO.equalsIgnoreCase(getType())){
			if(getValue() != null && getValue().equals(valueFromGraph)){
				setChecked("checked");
			}
		}
		return super.doStartTag();
	}

}
