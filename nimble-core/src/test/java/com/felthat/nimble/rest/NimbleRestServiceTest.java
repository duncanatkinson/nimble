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
import org.springframework.web.servlet.HandlerMapping;

import com.felthat.nimble.graph.Graph;

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
//		fail("Not yet implemented");
	}

	@Test
	public final void testCreate() {
		NimbleRequest data = new NimbleRequest();
		Map<String, Object> map = new LinkedHashMap<String,Object>();
		map.put("name", "duncan");
		when(requestMock.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).thenReturn("name");
		data.setData(map);
		nimbleRestService.create(data,requestMock);
		verify(graphMock).put(eq("name"), argThat(isGraphUsingNimbleMap()));
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
