package com.felthat.nimble;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMapGraph;

public class NimbleGraphTest {

	
	@Test
	public void testGraphObject(){
		Graph graph = new NimbleMapGraph();
		graph.put("name","Duncan");
		graph.put("nimble/products/breakdown/vehicle/type","Van");
		graph.put("nimble/products/breakdown/brc","true");
		graph.put("nimble/products/breakdown/vehicle/reg","UG10UYU");
		
		Graph vehicleGraph = graph.get("nimble/products/breakdown/vehicle");
		System.out.println(graph.getField("nimble/products"));
		assertEquals("Van",vehicleGraph.getField("type"));
		assertEquals("UG10UYU",vehicleGraph.getField("reg"));
		
		Graph breakdownGraph = graph.get("nimble/products/breakdown");
		assertEquals("true",breakdownGraph.getField("brc"));
		Graph sameVehicle = breakdownGraph.get("vehicle");
		assertEquals(((NimbleMapGraph)vehicleGraph).getGraphObject(),((NimbleMapGraph)sameVehicle).getGraphObject());
	}
	
	@Test
	public void testSlashWrongGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("person\\name","Duncan");
		assertEquals("Duncan", graph.getField("person/name"));
	}
	
	@Test
	public void singleFieldSet(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.getField("person/name"));
		
		Graph graphUpdate = new NimbleMapGraph();
		graphUpdate.put("name","Jeph");
		
		graph.put("person/name", graphUpdate);
		assertEquals("Jeph", graph.getField("person/name"));
	}
	
	@Test
	public void singleFieldSetFromEmpty(){
		Graph graph = new NimbleMapGraph();
		
		Graph graphUpdate = new NimbleMapGraph();
		graphUpdate.put("name","Jeph");
		
		graph.put("person/name", graphUpdate);
		assertEquals("Jeph", graph.getField("person/name"));
	}
	
	@Test
	public void testNestedValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.getField("person/name"));
		assertEquals("Duncan", graph.get("person").getField("name"));
		assertEquals("Duncan", graph.get("person").getField("name"));
	}
	
	@Test
	public void testLongNestedValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("something/somethingelse/s/__/???/name","Duncan");
		assertEquals("Duncan", graph.getField("something/somethingelse/s/__/???/name"));
	}
	
	@Test
	public void testIgnoreMultipleSlashesInPath(){
		Graph graph = new NimbleMapGraph();
		graph.put("person////name","Duncan");
		assertEquals("Duncan", graph.get("person").getField("name"));
	}
	
	@Test
	public void testOverwriteMultipleSlashesInPath(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.get("person").getField("name"));
		
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.get("person").getField("name"));
	}
	
	@Test
	public void testOverwriteValueWithSubtree(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/address","Fanum House, RG21 4EA");
		graph.put("person/address/postcode","RG21 4EA");//this will overwrite the old address object
		assertEquals("RG21 4EA", (graph.get("person/address")).getField("postcode"));
	}
	
	@Test
	public void testDeleteValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		graph.put("person/address/postcode","RG21 4EA");
		graph.delete("person/address/postcode");
		assertNull(graph.getField("person/address/postcode"));
		assertEquals("Duncan",graph.getField("person/name"));
	}
	
	@Test
	public void testNestedDeleteValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		graph.put("person/address/postcode","RG21 4EA");
		graph.delete("person/address/postcode");
		assertNull(graph.getField("person/address"));
		assertEquals("Duncan",graph.getField("person/name"));
	}
	
	@Test
	public void testSaveGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		Graph address = makeAddress("100","RG30 2AA","Reading","Berkshire");
		graph.put("person/address", address);
		assertEquals("Duncan", graph.getField("person/firstname"));
		assertEquals("100", graph.getField("person/address/housenumber"));
		assertEquals("RG30 2AA", graph.getField("person/address/postcode"));
		assertEquals("Reading", graph.getField("person/address/town"));
		assertEquals("Berkshire", graph.getField("person/address/county"));
	}
	
	
	
	@Test
	public void testReplaceGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("firstname","Duncan");
		graph.put("secondName","Atkinson");
		
		Graph graph2 = new NimbleMapGraph();
		graph2.put("firstname", "Peter");
		graph.put("/",graph2);
		
		assertEquals("Peter", graph.getField("firstname"));
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
		
		assertEquals("Duncan", graph.getField("person/firstname"));
		assertEquals("nimble", graph.getField("person/address/housenumber"));
		assertEquals("RG21 4EA", graph.getField("person/address/postcode"));
		assertEquals("Basingstoke", graph.getField("person/address/town"));
		assertEquals("Hampshire", graph.getField("person/address/county"));
		assertEquals("Cheddar", graph.getField("person/cheese"));
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
		assertEquals("nimble", graph.getField("person/address/housenumber"));
		assertNull(graph.getField("person/address/postcode"));
		assertNull(graph.getField("person/address/town"));
		assertNull(graph.getField("person/address/county"));
		assertEquals("Cheddar", graph.getField("person/cheese"));
	}
	
	
	@Test
	public void testGetRoot(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		graph.put("person/surname","Atkinson");
		Graph firstAddress = makeAddress("100","RG30 2AA","Reading","Berkshire");
		graph.put("person/address", firstAddress);
		graph = graph.get("/");
		assertEquals("Duncan", graph.getField("person/firstname"));
		assertEquals("100", graph.getField("person/address/housenumber"));
		assertEquals("RG30 2AA", graph.getField("person/address/postcode"));
		assertEquals("Reading", graph.getField("person/address/town"));
		assertEquals("Berkshire", graph.getField("person/address/county"));
	}
	
	@Test
	public void testUpdateMergeRoot(){
		Graph graph = new NimbleMapGraph();
		graph.put("firstname","Duncan");
		graph.put("surname","Atkinson");
		Graph firstAddress = makeAddress("100","RG30 2AA","Reading","Berkshire");
		
		graph.merge("/", firstAddress);
		
		assertEquals("Duncan", graph.getField("firstname"));
		assertEquals("Atkinson", graph.getField("surname"));
		assertEquals("100", graph.getField("housenumber"));
		assertEquals("RG30 2AA", graph.getField("postcode"));
		assertEquals("Reading", graph.getField("town"));
		assertEquals("Berkshire", graph.getField("county"));
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

