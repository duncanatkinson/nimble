package com.felthat.nimble.rest;

import static com.felthat.nimble.rest.NimbleMapGraphMatcher.isGraphUsingNimbleMap;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerMapping;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMapGraph;

public class NimbleRestServiceTest {

	private NimbleRestService nimbleRestService;
	
	@Mock private HttpServletRequest requestMock;
	@Mock private HttpServletResponse responseMock;
	@Mock private HttpSession sessionMock;
	@Mock private Graph graphMock;
	
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		nimbleRestService = new NimbleRestService();
//		requestMock = new MockHttpServletRequest();
//		responseMock = new MockHttpServletResponse();
//		sessionMock = new MockHttpSession();
//		graphMock = Mockito.mock(Graph.class);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(NimbleRestService.NIMBLE_GRAPH)).thenReturn(graphMock);
	}
	
	@Test
	public final void testGet() {
		performGet("customer");
		verify(responseMock).setStatus(HttpStatus.NOT_FOUND.value());
	}

	private void performGet(String path) {
		when(requestMock.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).thenReturn("customer");
		when(requestMock.getRequestURL()).thenReturn(new StringBuffer("http://www.mytestwebsite.com/contextPath/nimble"));
		when(requestMock.getContextPath()).thenReturn("contextPath");
		nimbleRestService.get(requestMock,responseMock);
		verify(graphMock).get(eq(path));
	}

	@Test
	public final void testCreate() {
		NimbleRequest request = createCustomerRequest();
		createObject("customer",request);
		verify(graphMock).put(eq("customer"), argThat(isGraphUsingNimbleMap()));
	}

	@Test
	public final void testCreateAndGet() {
		NimbleRequest request = createCustomerRequest();
		createObject("customer",request);
		verify(graphMock).put(eq("customer"), argThat(isGraphUsingNimbleMap()));
		Graph customerGraph = new NimbleMapGraph();
		customerGraph.put("name","Duncan");
		when(graphMock.get("customer")).thenReturn(customerGraph);
		performGet("customer");
		verify(responseMock).setStatus(HttpStatus.OK.value());
	}
	
	private NimbleRequest createCustomerRequest() {
		NimbleRequest request = new NimbleRequest();
		request.setData(new LinkedHashMap<String,Object>());
		request.getData().put("name", "Duncan");
		return request;
	}

	private void createObject(String path,NimbleRequest request) {
		when(requestMock.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).thenReturn(path);
		nimbleRestService.create(request,requestMock);
	}
		
	@Test
	public final void testUpdate() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDelete() {
//		fail("Not yet implemented"); // TODO
	}

}
