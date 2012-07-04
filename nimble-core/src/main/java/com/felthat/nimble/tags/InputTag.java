package com.felthat.nimble.tags;

import java.util.Collection;

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
			if(valueFromGraph instanceof Collection) {
				@SuppressWarnings("rawtypes")
				boolean match = ((Collection)valueFromGraph).contains(getValue());
				if(match){
					setChecked("checked");
				}
			}else if(valueFromGraph instanceof String[]){
					String[] stringArray = (String[]) valueFromGraph;
					for(String s : stringArray){
						if(s.equals(getValue())){
							setChecked("checked");
							break;
						}
					}
			}else{
				if(getValue() != null && getValue().equals(stringValueFromGraph)){
					setChecked("checked");
				}
			}
		}
		return super.doStartTag();
	}

}
