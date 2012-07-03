package com.felthat.nimble.rest;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMapGraph;

public class NimbleRestServiceTest {

	private NimbleRestService nimbleRestService;
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private HttpSession sessionMock;
	private Graph graphMock;
	
	@Before
	public void setup(){
		nimbleRestService = new NimbleRestService();
		requestMock = new MockHttpServletRequest();
		responseMock = new MockHttpServletResponse();
		sessionMock = new MockHttpSession();
		graphMock = Mockito.mock(Graph.class);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(NimbleRestService.NIMBLE_GRAPH)).thenReturn(graphMock);
	}
	
	@Test
	public final void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public final void testCreate() {
		NimbleRequest requestObject = new NimbleRequest();
		Map<String, Object> data = new LinkedHashMap<String,Object>();
		data.put("name", "duncan");
		requestObject.setData(data);
		nimbleRestService.create(requestObject);	
	}

	@Test
	public final void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDelete() {
		fail("Not yet implemented"); // TODO
	}

}
