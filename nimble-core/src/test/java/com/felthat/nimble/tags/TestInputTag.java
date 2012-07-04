package com.felthat.nimble.tags;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.mock.web.MockPageContext;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.rest.NimbleRestService;

public class TestInputTag {

	private PageContext pageContext;
	private Graph nimbleGraph;
	private InputTag input;

	@Before
	public void setup(){
		input = new InputTag();
		pageContext = new MockPageContext();
		nimbleGraph = mock(Graph.class);
		pageContext.getSession().setAttribute(NimbleRestService.NIMBLE_GRAPH,nimbleGraph);
		input.setPageContext(pageContext);
	}
	
	@Test
	public void inputValueDefault(){
		assertNull(input.getValue());
	}
	
	
	@Test
	public void inputValueSet(){
		input.setValue("SomeValue");
		assertEquals("SomeValue", input.getValue());
	}
	
	@Test
	public void doStartTag_inputValuePickedUpFromModel() throws JspException{
		input.setName("myObject/mySubObject");
		input.setType("text");
		ArrayList<String> value = new ArrayList<String>();
		value.add("SomeValue");
		when(nimbleGraph.getValue("myObject/mySubObject")).thenReturn(value);
		input.doStartTag();
		assertEquals("SomeValue", input.getValue());
	}
	
	
	@Test
	public void radioSelectedNull(){
		assertNull(input.getChecked());
	}
	
	@Test
	public void radioSelected() throws JspException{
		input.setName("myObject/mySubObject");
		input.setType("radio");
		input.setValue("SomeValue");
		ArrayList<String> value = new ArrayList<String>();
		value.add("SomeValue");
		when(nimbleGraph.getValue("myObject/mySubObject")).thenReturn(value);
		input.doStartTag();
		assertEquals("checked",input.getChecked());
	}
	
	@Test
	public void doStartTag_emptyModelValueHardcoded() throws JspException{
		input.setName("myObject/mySubObject");
		input.setType("radio");
		input.setValue("SomeValue");
		input.doStartTag();
		assertEquals("SomeValue",input.getValue());
	}
	
	@Test
	public void doStartTag_TagValueOverriddenByModel() throws JspException{
		input.setName("myObject/mySubObject");
		input.setType("text");
		input.setValue("SomeValue");
		when(nimbleGraph.getValue("myObject/mySubObject")).thenReturn("ModelValue");
		input.doStartTag();
		assertEquals("ModelValue",input.getValue());
	}
	
	@Test
	public void radioNotSelected() throws JspException{
		input.setName("myObject/mySubObject");
		input.setType("radio");
		input.setValue("SomeValue");
		ArrayList<String> value = new ArrayList<String>();
		value.add("NotSomeValue");
		when(nimbleGraph.getValue("myObject/mySubObject")).thenReturn(value);
		input.doStartTag();
		assertEquals(null,input.getChecked());
	}
	
	@Test
	public void doStartTag_multipleCheckBoxes_Array() throws JspException{
		
		String[] value = new String[]{"a","b","d"};
		when(nimbleGraph.getValue("myObject/mySubObject")).thenReturn(value);
		
		InputTag inputTag = makeCheckbox("a");
		inputTag.doStartTag();
		assertEquals("checked",inputTag.getChecked());
		inputTag = makeCheckbox("b");
		inputTag.doStartTag();
		assertEquals("checked",inputTag.getChecked());
		inputTag = makeCheckbox("c");
		inputTag.doStartTag();
		assertEquals(null,inputTag.getChecked());
		inputTag = makeCheckbox("d");
		inputTag.doStartTag();
		assertEquals("checked",inputTag.getChecked());
	}
	
	@Test
	public void doStartTag_multipleCheckBoxes_List() throws JspException{
		
		ArrayList<String> value = new ArrayList<String>();
		value.add("a");
		value.add("b");
		value.add("d");
		when(nimbleGraph.getValue("myObject/mySubObject")).thenReturn(value);
		
		InputTag inputTag = makeCheckbox("a");
		inputTag.doStartTag();
		assertEquals("checked",inputTag.getChecked());
		inputTag = makeCheckbox("b");
		inputTag.doStartTag();
		assertEquals("checked",inputTag.getChecked());
		inputTag = makeCheckbox("c");
		inputTag.doStartTag();
		assertEquals(null,inputTag.getChecked());
		inputTag = makeCheckbox("d");
		inputTag.doStartTag();
		assertEquals("checked",inputTag.getChecked());
	}

	private InputTag makeCheckbox(String checkBoxValue) {
		InputTag inputTag = new InputTag();
		inputTag.setName("myObject/mySubObject");
		inputTag.setType("checkbox");
		inputTag.setValue(checkBoxValue);
		inputTag.setPageContext(pageContext);
		return inputTag;
	}
	
	@Test
	public void radioSelectedFromModel(){
		assertNull(input.getChecked());
	}
}
