package com.felthat.nimble.tags;

import javax.servlet.jsp.PageContext;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.rest.NimbleRestService;

public abstract class FormInputTag extends HtmlTag {

	private static final long serialVersionUID = 1L;
	
	public static final String RADIO = "radio";
	public static final String CHECKBOX = "checkbox";
	public static final String HIDDEN = "hidden";

	protected String tagName;

	public FormInputTag() {
		super();
	}

	protected String getValueFromGraph(PageContext pageContext){
		Graph graph = (Graph) pageContext.getSession().getAttribute(NimbleRestService.NIMBLE_GRAPH);
		String value ="";
		if(graph!= null){
			value = graph.getField(getName());
		}
		return value;
	}
	
	public String getAlt() {
		return (String)getValue("alt");
	}

	public void setAlt(String alt) {
		put("alt", alt);
	}

	public String getChecked() {
		return (String)getValue("checked");
	}

	public void setChecked(String checked) {
			put("checked", checked);
	}

	public void setSelected(String value){
		put("selected",value);
	}
	
	public String getDisabled() {
		return (String)getValue("disabled");
	}

	
	public void setDisabled(String disabled) {
		put("disabled", "disabled");
	}

	public String getMaxlength() {
		return (String)getValue("maxlength");
	}

	public void setMaxlength(String maxlength) {
		put("maxlength", maxlength);
	}

	public String getName() {
		return (String)getValue("name");
	}

	public void setName(String name) {
		put("name", name);
	}

	public String getReadonly() {
		return (String)getValue("readonly");
	}

	public void setReadonly(String readonly) {
		put("readonly", readonly);
	}

	public String getSize() {
		return (String)getValue("size");
	}

	public void setSize(String size) {
		put("size", size);
	}

	public String getSrc() {
		return (String)getValue("src");
	}

	public void setSrc(String src) {
		put("src", src);
	}

	public String getType() {
		return (String)getValue("type");
	}

	public String getSelected() {
		return (String)getValue("selected");
	}
	
	public void setType(String type) {
		put("type", type);
	}

	public String getValue() {
		return (String)getValue("value");
	}

	public void setValue(String value) {
		put("value", value);
	}

	public String getTagName() {
		return tagName;
	}

	public String getPlaceholder() {
		return (String)getValue("placeholder");
	}

	public void setPlaceholder(String placeholder) {
		put("placeholder", placeholder);
	}

	public String getMin() {
		return (String)getValue("min");
	}

	public void setMin(String min) {
		put("min", min);
	}

	public String getMax() {
		return (String)getValue("max");
	}

	public void setMax(String max) {
		put("max", max);
	}

	boolean isCheckBox() {
		return getType().equals(FormInputTag.CHECKBOX);
	}

	boolean isRadio() {
		return getType().equals(FormInputTag.RADIO);
	}

	boolean isDisabled() {
		return getDisabled() != null;
	}

}
