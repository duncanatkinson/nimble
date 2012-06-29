package com.felthat.nimble.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMapGraph;

public class NimbleMapGraphTest {

	
	@Test
	public void testGraphObject(){
		Graph graph = new NimbleMapGraph();
		graph.put("name","Duncan");
		graph.put("nimble/products/breakdown/vehicle/type","Van");
		graph.put("nimble/products/breakdown/brc","true");
		graph.put("nimble/products/breakdown/vehicle/reg","UG10UYU");
		
		Graph vehicleGraph = graph.get("nimble/products/breakdown/vehicle");
		System.out.println(graph.getField("nimble/products"));
		assertEquals("Van",vehicleGraph.getField("type").get(0));
		assertEquals("UG10UYU",vehicleGraph.getField("reg").get(0));
		
		Graph breakdownGraph = graph.get("nimble/products/breakdown");
		assertEquals("true",breakdownGraph.getField("brc").get(0));
		Graph sameVehicle = breakdownGraph.get("vehicle");
		assertEquals(((NimbleMapGraph)vehicleGraph).getGraphObject(),((NimbleMapGraph)sameVehicle).getGraphObject());
	}
	
	@Test
	public void testSlashWrongGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("person\\name","Duncan");
		assertEquals("Duncan", graph.getField("person/name").get(0));
	}
	
	@Test
	public void singleFieldSet(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.getField("person/name").get(0));
		
		Graph graphUpdate = new NimbleMapGraph();
		graphUpdate.put("value","Jeph");
		
		graph.put("person/name", graphUpdate);
		assertEquals("Jeph", graph.getField("person/name").get(0));
	}
	
	@Test
	public void singleFieldSetFromEmpty(){
		Graph graph = new NimbleMapGraph();
		
		Graph graphUpdate = new NimbleMapGraph();
		graphUpdate.put("value","Jeph");
		
		graph.put("person/name", graphUpdate);
		assertEquals("Jeph", graph.getField("person/name").get(0));
	}
	
	@Test
	public void testNestedValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.getField("person/name").get(0));
		assertEquals("Duncan", graph.get("person").getField("name").get(0));
		assertEquals("Duncan", graph.get("person").getField("name").get(0));
	}
	
	@Test
	public void testLongNestedValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("something/somethingelse/s/__/???/name","Duncan");
		assertEquals("Duncan", graph.getField("something/somethingelse/s/__/???/name").get(0));
	}
	
	@Test
	public void testIgnoreMultipleSlashesInPath(){
		Graph graph = new NimbleMapGraph();
		graph.put("person////name","Duncan");
		assertEquals("Duncan", graph.get("person").getField("name").get(0));
	}
	
	@Test
	public void testOverwriteMultipleSlashesInPath(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.get("person").getField("name").get(0));
		
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.get("person").getField("name").get(0));
	}
	
	@Test
	public void testOverwriteValueWithSubtree(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/address","Fanum House, RG21 4EA");
		graph.put("person/address/postcode","RG21 4EA");//this will overwrite the old address object
		assertEquals("RG21 4EA", (graph.get("person/address")).getField("postcode").get(0));
	}
	
	@Test
	public void testDeleteValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		graph.put("person/address/postcode","RG21 4EA");
		graph.delete("person/address/postcode");
		assertNull(graph.getField("person/address/postcode"));
		assertEquals("Duncan",graph.getField("person/name").get(0));
	}
	
	@Test
	public void testNestedDeleteValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		graph.put("person/address/postcode","RG21 4EA");
		graph.delete("person/address/postcode");
		assertNull(graph.getField("person/address"));
		assertEquals("Duncan",graph.getField("person/name").get(0));
	}
	
	@Test
	public void testSaveGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		Graph address = makeAddress("100","RG30 2AA","Reading","Berkshire");
		graph.put("person/address", address);
		assertEquals("Duncan", graph.getField("person/firstname").get(0));
		assertEquals("100", graph.getField("person/address/housenumber").get(0));
		assertEquals("RG30 2AA", graph.getField("person/address/postcode").get(0));
		assertEquals("Reading", graph.getField("person/address/town").get(0));
		assertEquals("Berkshire", graph.getField("person/address/county").get(0));
	}
	
	
	
	@Test
	public void testReplaceGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("firstname","Duncan");
		graph.put("secondName","Atkinson");
		
		Graph graph2 = new NimbleMapGraph();
		graph2.put("firstname", "Peter");
		graph.put("/",graph2);
		
		assertEquals("Peter", graph.getField("firstname").get(0));
		assertNull(graph.getField("secondName"));
	}
	
	
	
	@Test
	public void testJustUpdate(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		graph.put("person/surname","Atkinson");
		Graph firstAddress = makeAddress("FANUM HOUSE", "RG21 4EA", "Basingstoke", "Hampshire");
		graph.put("person/address", firstAddress);
		
		
		Graph update = new NimbleMapGraph();
		update.put("person/address/housenumber","nimble");
		update.put("person/cheese","Cheddar");
		
		
		graph.merge("/", update);
		
		assertEquals("Duncan", graph.getField("person/firstname").get(0));
		assertEquals("nimble", graph.getField("person/address/housenumber").get(0));
		assertEquals("RG21 4EA", graph.getField("person/address/postcode").get(0));
		assertEquals("Basingstoke", graph.getField("person/address/town").get(0));
		assertEquals("Hampshire", graph.getField("person/address/county").get(0));
		assertEquals("Cheddar", graph.getField("person/cheese").get(0));
	}
	
	@Test
	public void testJustReplaceRoot(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		graph.put("person/surname","Atkinson");
		Graph firstAddress = makeAddress("FANUM HOUSE", "RG21 4EA", "Basingstoke", "Hampshire");
		graph.put("person/address", firstAddress);
		
		
		Graph update = new NimbleMapGraph();
		update.put("person/address/housenumber","nimble");
		update.put("person/cheese","Cheddar");
		
		
		graph.put("/", update);
		
		assertNull(graph.getField("person/firstname"));
		assertEquals("nimble", graph.getField("person/address/housenumber").get(0));
		assertNull(graph.getField("person/address/postcode"));
		assertNull(graph.getField("person/address/town"));
		assertNull(graph.getField("person/address/county"));
		assertEquals("Cheddar", graph.getField("person/cheese").get(0));
	}
	
	
	@Test
	public void testGetRoot(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		graph.put("person/surname","Atkinson");
		Graph firstAddress = makeAddress("100","RG30 2AA","Reading","Berkshire");
		graph.put("person/address", firstAddress);
		graph = graph.get("/");
		assertEquals("Duncan", graph.getField("person/firstname").get(0));
		assertEquals("100", graph.getField("person/address/housenumber").get(0));
		assertEquals("RG30 2AA", graph.getField("person/address/postcode").get(0));
		assertEquals("Reading", graph.getField("person/address/town").get(0));
		assertEquals("Berkshire", graph.getField("person/address/county").get(0));
	}
	
	@Test
	public void testUpdateMergeRoot(){
		Graph graph = new NimbleMapGraph();
		graph.put("firstname","Duncan");
		graph.put("surname","Atkinson");
		Graph firstAddress = makeAddress("100","RG30 2AA","Reading","Berkshire");
		
		graph.merge("/", firstAddress);
		
		assertEquals("Duncan", graph.getField("firstname").get(0));
		assertEquals("Atkinson", graph.getField("surname").get(0));
		assertEquals("100", graph.getField("housenumber").get(0));
		assertEquals("RG30 2AA", graph.getField("postcode").get(0));
		assertEquals("Reading", graph.getField("town").get(0));
		assertEquals("Berkshire", graph.getField("county").get(0));
	}
	
	
	public void testGetField(){
		Graph graph = new NimbleMapGraph();
		ArrayList<String> stringList = new ArrayList<String>();
		stringList.add("String1");
		stringList.add("String2");
//		graph.put("/listOfStrings", stringList);
	}


	private Graph  makeAddress(String houseNumber,String postcode, String town, String county) {
		Graph graph = new NimbleMapGraph();
		graph.put("postcode",postcode);
		graph.put("housenumber",houseNumber);
		graph.put("town",town);
		graph.put("county",county);
		return graph;
	}
	
	
}

