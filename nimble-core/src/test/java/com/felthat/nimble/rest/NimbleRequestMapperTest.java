package com.felthat.nimble.rest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMapGraph;

public class NimbleRequestMapperTest {

	private NimbleRequestMapper nimbleRequestMapper;
	
	@Before
	public void setup(){
		this.nimbleRequestMapper = new NimbleRequestMapper();
	}
	
	//@Test
	public final void testReadHandCraftedJSON() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		File[] files = new File("src/test/resources/example-json").listFiles();
		for(File f : files){
			String json = FileUtils.readFileToString(f);
			String jsonRequest = String.format("{\"data\":%s}", json);
			NimbleRequest request = objectMapper.readValue(jsonRequest, NimbleRequest.class);
			Graph graph = nimbleRequestMapper.map(request);
			boolean graphIsGood = graphIsGood((NimbleMapGraph) graph);
			System.out.println(f.getCanonicalPath() + " is ok? " + graphIsGood);
			assertTrue(graphIsGood);
		}
	}
	
	
	
	// These next tests are just for testing the privte test method!
	
	@Test
	public void testGraphIsGoodMethod1(){
		Graph graph = new GraphFactory().create();
		graph.put("/","");
		assertTrue(graphIsGood((NimbleMapGraph) graph));
	}

	@Test
	public void testGraphIsGoodMethod2(){	
		Graph graph = new GraphFactory().create();
		graph.put("customer",new ArrayList<String>());
		assertTrue(graphIsGood((NimbleMapGraph) graph));
	}
		
	@Test
	public void testGraphIsBadMethod1(){	
		Graph graph = new GraphFactory().create();
		graph.put("customer",new Integer(1));
		assertFalse(graphIsGood((NimbleMapGraph) graph));	
	}
	
	
	/**
	 * linked hashmaps should not be in the model, they should be graphs themselves.
	 * 
	 */
	@Test public void testGraphIsBadMethod2(){	
		NimbleMapGraph graph = new NimbleMapGraph();
		LinkedHashMap customer = new LinkedHashMap();
		customer.put("name", "Brian");
		LinkedHashMap address = new LinkedHashMap();
		address.put("postcode", "SW11");
		customer.put("address", address);
		graph.put("customer",customer);
		assertFalse(graphIsGood((NimbleMapGraph) graph));	
	}
	
	/**
	 * linked hashmaps should not be in the model, they should be graphs themselves.
	 * 
	 */
	@Test public void testGraphIsBadMethod3(){	
		NimbleMapGraph graph = new NimbleMapGraph();
		LinkedHashMap customer = new LinkedHashMap();
		customer.put("name", "Brian");
		graph.getGraphObject().put("customer", customer);
		assertFalse(graphIsGood((NimbleMapGraph) graph));	
	}
	
	private boolean graphIsGood(NimbleMapGraph nimbleMapGraph){
		boolean ok = true;
		for(String key : nimbleMapGraph.getGraphObject().keySet()){
			Object obj = nimbleMapGraph.getGraphObject().get(key);
			if(!(obj instanceof Graph
					|| obj instanceof String
					||  obj instanceof List)){
				ok = false;
				System.out.println("non graph object found " + obj.getClass());
			}
			if(ok && obj instanceof Graph){
				Graph subGraph = (Graph)obj;
				if(!subGraph.isSingleValue()){
					ok = graphIsGood((NimbleMapGraph) obj);
				}else{
					ok = subGraph.getValue() instanceof String || subGraph.getValue() instanceof List;
					if(!ok){
						System.out.println("non graph object found " + obj.getClass());
					}
				}
			}
			if(!ok){
				break;
			}
		}
		return ok;
	}
	
	
	
	
}
