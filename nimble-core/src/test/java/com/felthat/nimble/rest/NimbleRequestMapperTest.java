package com.felthat.nimble.rest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class NimbleRequestMapperTest {

	private NimbleRequestMapper nimbleRequestMapper;
	
	@Before
	public void setup(){
		this.nimbleRequestMapper = new NimbleRequestMapper();
	}
	
	@Test
	public final void testReadHandCraftedJSON() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		File[] files = new File("src/test/resources/example-json").listFiles();
		for(File f : files){
			System.out.println(f.getCanonicalPath());
			String json = FileUtils.readFileToString(f);
			String jsonRequest = String.format("{\"data\":%s}", json);
			NimbleRequest request = objectMapper.readValue(jsonRequest, NimbleRequest.class);
			Graph graph = nimbleRequestMapper.map(request);
		}
	}
	
	@Test
	public void testGraphIsGoodMethod1(){
		Graph graph = new GraphFactory().create();
		graph.put("/","");
		assertTrue(graphIsGood(graph));
	}

	@Test
	public void testGraphIsGoodMethod2(){	
		Graph graph = new GraphFactory().create();
		graph.put("customer",new ArrayList<String>());
		assertTrue(graphIsGood(graph));
		}
		
	@Test
	public void testGraphIsGoodMethod3(){	
		Graph graph = new GraphFactory().create();
		graph.put("customer",new Integer(1));
		assertFalse(graphIsGood(graph));
		
	}
	
	private boolean graphIsGood(Graph graph){
		boolean ok = true;
//		if(graph.get("/") != null) ok = false;
		for(String key : graph.getKeys()){
			Object obj = graph.get(key);
			if(!(obj instanceof Graph
					|| obj instanceof String
					||  obj instanceof List)){
				ok = false;
			}
			if(ok && obj instanceof Graph){
				ok = graphIsGood((Graph) obj);
			}
			if(!ok)break;
		}
		return ok;
	}
	
	
	
	
}
