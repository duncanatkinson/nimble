package com.felthat.nimble.tags;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class HtmlTag extends TagSupport {

	private static final long serialVersionUID = -7282879166059584144L;
	
//	protected Map<String, String> attributes;

	public HtmlTag() {
		super();
	}

	protected Object get(String attributeName) {
		return getValue(attributeName);
	}

	protected void put(String attributeName, String value) {
		setValue(attributeName, value);
	}
	
	@Override
	public int doStartTag() throws JspException {
		writeStartTag();
		return super.doStartTag();
	}
	
	@Override
	public int doEndTag() throws JspException {
		writeEndTag();
		release();
		return super.doEndTag();
	}
	
	
	protected void writeEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print("</" + getTagName() + ">");
		} catch (IOException e) {
			throw new JspException(e);
		}  // We use print() rather than println() in order to avoid breaking JavaScript statements that refer to the output of AA custom HTML tags
	}

	protected void writeStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print("<" + getTagName() + " ");
			Enumeration<String> values = getValues();
			while(values.hasMoreElements()){
				String attributeName = values.nextElement();
				String attributeValue = (String) getValue(attributeName);
				if (attributeValue == null) {
					attributeValue = "";
				}
				attributeValue = attributeValue.replace("\"", "&quot;");
				out.print(attributeName + "=\"" + attributeValue + "\" ");
			}
			out.print(">"); // We use print() rather than println() in order to
							// avoid breaking JavaScript statements that refer
							// to the output of AA custom HTML tags
		} catch (IOException e) {
			throw new JspException(e);
		}
	}

	protected abstract String getTagName();

	protected void addErrorCssClass() {
		addCssClass("error");
	}
	
	protected void addCssClass(String clazz) {
		if(clazz == null){
			return;
		}
		String cssClass = (String) getValue("class");
		if(cssClass == null){
			cssClass = clazz;
		}else{
			if(!cssClass.contains(clazz)){
				cssClass = cssClass + " " + clazz;;				
			}
		}
		if(cssClass != null){
			put("class",cssClass);	
		}
	}

	public String getAccesskey() {
		return (String)getValue("accesskey");
	}

	public void setAccesskey(String accesskey) {
		put("accesskey", accesskey);
	}

	/**
	 * Lots of options instead of having class :(
	 */
	
	
	public String getClazz() {
		return (String)getValue("class");
	}

	public void setClazz(String clazz) {
		addCssClass(clazz);
	}
	
	public String getKlass() {
		return (String)getValue("class");
	}

	public void setKlass(String clazz) {
		addCssClass(clazz);
	}

	public String getCssStyle() {
		return (String)getValue("class");
	}

	public void setCssStyle(String clazz) {
		addCssClass(clazz);
	}

	public String getCssClass() {
		return (String)getValue("class");
	}

	public void setCssClass(String clazz) {
		addCssClass(clazz);
	}

	public String getDir() {
		return (String)getValue("dir");
	}

	public void setDir(String dir) {
		put("dir", dir);
	}

	public String getId() {
		return (String)getValue("id");
	}

	public void setId(String id) {
		put("id", id);
	}

	public String getLang() {
		return (String)getValue("lang");
	}

	public void setLang(String lang) {
		put("lang", lang);
	}

	public String getStyle() {
		return (String)getValue("style");
	}

	public void setStyle(String style) {
		put("style", style);
	}

	public String getTabIndex() {
		return (String)getValue("tabIndex");
	}

	public void setTabIndex(String tabIndex) {
		put("tabIndex", tabIndex);
	}

	public String getTitle() {
		return (String)getValue("title");
	}

	public void setTitle(String title) {
		put("title", title);
	}

	public String getOnblur() {
		return (String)getValue("onblur");
	}

	public void setOnblur(String onblur) {
		put("onblur", onblur);
	}

	public String getOnchange() {
		return (String)getValue("onchange");
	}

	public void setOnchange(String onchange) {
		put("onchange", onchange);
	}

	public String getOnclick() {
		return (String)getValue("onclick");
	}

	public void setOnclick(String onclick) {
		put("onclick", onclick);
	}

	public String getOndblclick() {
		return (String)getValue("ondblclick");
	}

	public void setOndblclick(String ondblclick) {
		put("ondblclick", ondblclick);
	}

	public String getOnfocus() {
		return (String)getValue("onfocus");
	}

	public void setOnfocus(String onfocus) {
		put("onfocus", onfocus);
	}

	public String getOnmousedown() {
		return (String)getValue("onmousedown");
	}

	public void setOnmousedown(String onmousedown) {
		put("onmousedown", onmousedown);
	}

	public String getOnmousemove() {
		return (String)getValue("onmousemove");
	}

	public void setOnmousemove(String onmousemove) {
		put("onmousemove", onmousemove);
	}

	public String getOnmouseout() {
		return (String)getValue("onmouseout");
	}

	public void setOnmouseout(String onmouseout) {
		put("onmouseout", onmouseout);
	}

	public String getOnmouseover() {
		return (String)getValue("onmouseover");
	}

	public void setOnmouseover(String onmouseover) {
		put("onmouseover", onmouseover);
	}

	public String getOnmouseup() {
		return (String)getValue("onmouseup");
	}

	public void setOnmouseup(String onmouseup) {
		put("onmouseup", onmouseup);
	}

	public String getOnkeydown() {
		return (String)getValue("onkeydown");
	}

	public void setOnkeydown(String onkeydown) {
		put("onkeydown", onkeydown);
	}

	public String getOnkeypress() {
		return (String)getValue("onkeypress");
	}

	public void setOnkeypress(String onkeypress) {
		put("onkeypress", onkeypress);
	}

	public String getOnkeyup() {
		return (String)getValue("onkeyup");
	}

	public void setOnkeyup(String onkeyup) {
		put("onkeyup", onkeyup);
	}

	public String getOnselect() {
		return (String)getValue("onselect");
	}

	public void setOnselect(String onselect) {
		put("onselect", onselect);
	}

	public String getAutocomplete(){
	    return (String)getValue("autocomplete");
	}
	
	public void setAutocomplete(String autoComplete){
	    put("autocomplete",autoComplete);
	}
	
}
