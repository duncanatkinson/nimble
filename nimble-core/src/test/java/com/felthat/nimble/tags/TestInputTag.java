package com.felthat.nimble.tags;

import static org.junit.Assert.*;

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
	public void inputValueFromModel() throws JspException{
		input.setName("myObject/mySubObject");
		when(nimbleGraph.getField("myObject/mySubObject")).thenReturn("SomeValue");
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
		when(nimbleGraph.getField("myObject/mySubObject")).thenReturn("SomeValue");
		input.doStartTag();
		assertEquals("selected",input.getChecked());
	}
	
	@Test
	public void radioNotSelected() throws JspException{
		input.setName("myObject/mySubObject");
		input.setType("radio");
		input.setValue("SomeValue");
		when(nimbleGraph.getField("myObject/mySubObject")).thenReturn("NotSomeValue");
		input.doStartTag();
		assertEquals(null,input.getChecked());
	}
	@Test
	public void radioSelectedFromModel(){
		assertNull(input.getChecked());
	}
}
