package com.felthat.nimble.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NimbleMapGraphTest {	

	
	@Test
	public void testGraphObject(){
		Graph graph = new NimbleMapGraph();
		graph.put("name","Duncan");
		graph.put("nimble/products/breakdown/vehicle/type","Van");
		graph.put("nimble/products/breakdown/brc","true");
		graph.put("nimble/products/breakdown/vehicle/reg","UG10UYU");
		
		Graph vehicleGraph = graph.get("nimble/products/breakdown/vehicle");
		System.out.println(graph.getValue("nimble/products"));
		assertEquals("Van",vehicleGraph.getValue("type"));
		assertEquals("UG10UYU",vehicleGraph.getValue("reg"));
		
		Graph breakdownGraph = graph.get("nimble/products/breakdown");
		assertEquals("true",breakdownGraph.getValue("brc"));
		Graph sameVehicle = breakdownGraph.get("vehicle");
		assertEquals(((NimbleMapGraph)vehicleGraph).getGraphObject(),((NimbleMapGraph)sameVehicle).getGraphObject());
	}

	@Test
	public void testPut_root(){
		Graph graph = new NimbleMapGraph();
		graph.put("","Duncan");
		assertEquals("Duncan",graph.getValue(""));
	}
	
	@Test
	public void testPut_rootSubObject(){
		Graph graph = new NimbleMapGraph();
		Graph subgraph = new NimbleMapGraph();
		subgraph.put("","Duncan");
		graph.put("",subgraph);
		assertEquals("Duncan",graph.getValue(""));
	}

	
	@Test
	public void testSlashWrongGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("person\\name","Duncan");
		assertEquals("Duncan", graph.getValue("person/name"));
	}
	
	@Test
	public void singleFieldSet(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.getValue("person/name"));
		
		Graph graphUpdate = new NimbleMapGraph();
		graphUpdate.put("","Jeph");
		
		graph.put("person/name", graphUpdate);
		assertEquals("Jeph", graph.getValue("person/name"));
	}
	
	@Test
	public void singleFieldSetMultipleValues(){
		Graph graph = new NimbleMapGraph();
		List<String> phoneNumbersList = new ArrayList<String>();
		phoneNumbersList.add("123456789");
		phoneNumbersList.add("987654321");
		graph.put("person/phoneNumbers",phoneNumbersList);
		graph.put("person/name","Brian");
		assertEquals("123456789", ((List)graph.getValue("person/phoneNumbers")).get(0));
		assertEquals("987654321", ((List)graph.getValue("person/phoneNumbers")).get(1));
		
		
		Graph graphUpdate = new NimbleMapGraph();
		graphUpdate.put("/person/phoneNumbers","741258963");
		
		graph.merge(graphUpdate);
		assertEquals("741258963", graph.getValue("person/phoneNumbers"));
		assertEquals("Brian", graph.getValue("person/name"));
	}
	
	@Test
	public void singleFieldSetFromEmpty(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name", "Jeph");
		assertEquals("Jeph", graph.getValue("person/name"));
	}
	
	@Test
	public void testNestedValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.getValue("person/name"));
		assertEquals("Duncan", graph.get("person").getValue("name"));
		assertEquals("Duncan", graph.get("person").getValue("name"));
	}
	
	@Test
	public void testLongNestedValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("something/somethingelse/s/__/???/name","Duncan");
		assertEquals("Duncan", graph.getValue("something/somethingelse/s/__/???/name"));
	}
	
	@Test
	public void testIgnoreMultipleSlashesInPath(){
		Graph graph = new NimbleMapGraph();
		graph.put("person////name","Duncan");
		assertEquals("Duncan", graph.get("person").getValue("name"));
	}
	
	@Test
	public void testOverwriteMultipleSlashesInPath(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.get("person").getValue("name"));
		
		graph.put("person/name","Duncan");
		assertEquals("Duncan", graph.get("person").getValue("name"));
	}
	
	@Test
	public void testOverwriteValueWithSubtree(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/address","Fanum House, RG21 4EA");
		graph.put("person/address/postcode","RG21 4EA");//this will overwrite the old address object
		assertEquals("RG21 4EA", (graph.get("person/address")).getValue("postcode"));
	}
	
	@Test
	public void testDeleteValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		graph.put("person/address/postcode","RG21 4EA");
		graph.delete("person/address/postcode");
		assertNull(graph.getValue("person/address/postcode"));
		assertEquals("Duncan",graph.getValue("person/name"));
	}
	
	@Test
	public void testNestedDeleteValue(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/name","Duncan");
		graph.put("person/address/postcode","RG21 4EA");
		graph.delete("person/address/postcode");
		assertNull(graph.getValue("person/address"));
		assertEquals("Duncan",graph.getValue("person/name"));
	}
	
	@Test
	public void testSaveGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		Graph address = makeAddress("100","RG30 2AA","Reading","Berkshire");
		graph.put("person/address", address);
		assertEquals("Duncan", graph.getValue("person/firstname"));
		assertEquals("100", graph.getValue("person/address/housenumber"));
		assertEquals("RG30 2AA", graph.getValue("person/address/postcode"));
		assertEquals("Reading", graph.getValue("person/address/town"));
		assertEquals("Berkshire", graph.getValue("person/address/county"));
	}
	
	
	
	@Test
	public void testReplaceGraph(){
		Graph graph = new NimbleMapGraph();
		graph.put("firstname","Duncan");
		graph.put("secondName","Atkinson");
		
		Graph graph2 = new NimbleMapGraph();
		graph2.put("firstname", "Peter");
		graph.put("/",graph2);
		
		assertEquals("Peter", graph.getValue("firstname"));
		assertNull(graph.getValue("secondName"));
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
		
		assertEquals("Duncan", graph.getValue("person/firstname"));
		assertEquals("nimble", graph.getValue("person/address/housenumber"));
		assertEquals("RG21 4EA", graph.getValue("person/address/postcode"));
		assertEquals("Basingstoke", graph.getValue("person/address/town"));
		assertEquals("Hampshire", graph.getValue("person/address/county"));
		assertEquals("Cheddar", graph.getValue("person/cheese"));
	}
	
	
	@Test
	public void testJustUpdateFromPath(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		graph.put("person/surname","Atkinson");
		Graph firstAddress = makeAddress("FANUM HOUSE", "RG21 4EA", "Basingstoke", "Hampshire");
		graph.put("person/address", firstAddress);
		
		
		Graph update = makeAddress("Noddys House", "NoddyLand", "NodVille", "Hampshire");
		graph.merge("/person/address", update);
		
		assertEquals("Duncan", graph.getValue("person/firstname"));
		assertEquals("Noddys House", graph.getValue("person/address/housenumber"));
		assertEquals("NoddyLand", graph.getValue("person/address/postcode"));
		assertEquals("NodVille", graph.getValue("person/address/town"));
		assertEquals("Hampshire", graph.getValue("person/address/county"));
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
		
		assertNull(graph.getValue("person/firstname"));
		assertEquals("nimble", graph.getValue("person/address/housenumber"));
		assertNull(graph.getValue("person/address/postcode"));
		assertNull(graph.getValue("person/address/town"));
		assertNull(graph.getValue("person/address/county"));
		assertEquals("Cheddar", graph.getValue("person/cheese"));
	}
	
	
	@Test
	public void testGetRoot(){
		Graph graph = new NimbleMapGraph();
		graph.put("person/firstname","Duncan");
		graph.put("person/surname","Atkinson");
		Graph firstAddress = makeAddress("100","RG30 2AA","Reading","Berkshire");
		graph.put("person/address", firstAddress);
//		graph = graph.get("/");//HMMMMMMM
		assertEquals("Duncan", graph.getValue("person/firstname"));
		assertEquals("100", graph.getValue("person/address/housenumber"));
		assertEquals("RG30 2AA", graph.getValue("person/address/postcode"));
		assertEquals("Reading", graph.getValue("person/address/town"));
		assertEquals("Berkshire", graph.getValue("person/address/county"));
	}
	
	@Test
	public void testUpdateMergeRoot(){
		Graph graph = new NimbleMapGraph();
		graph.put("firstname","Duncan");
		graph.put("surname","Atkinson");
		Graph firstAddress = makeAddress("100","RG30 2AA","Reading","Berkshire");
		
		graph.merge("/", firstAddress);
		
		assertEquals("Duncan", graph.getValue("firstname"));
		assertEquals("Atkinson", graph.getValue("surname"));
		assertEquals("100", graph.getValue("housenumber"));
		assertEquals("RG30 2AA", graph.getValue("postcode"));
		assertEquals("Reading", graph.getValue("town"));
		assertEquals("Berkshire", graph.getValue("county"));
	}
	
	
	@Test
	public void nimbleGraphValueStorage(){
		Graph graph = new NimbleMapGraph();
		graph.put("customer","Duncan");
		assertEquals("Duncan", graph.getValue("customer"));
		graph.put("Duncan"); //root object is now just the string duncan.
		assertEquals("Duncan", graph.getValue(""));
		assertEquals("Duncan", graph.getValue());
		assertNull("Customer should be removed", graph.getValue("customer"));
	}

	@Test
	public void testGets(){	
		Graph graph = new NimbleMapGraph();
		graph.put("/customer/name", "Duncan");
		graph.put("customer/age", 10);
		graph.put("customer/address", makeAddress("10", "SW1 3RJ", "London", "London"));
		graph.put("customer/products/motorInsurancePolicy/reference", "I67529847629701296A");
		assertEquals("Duncan", graph.get("customer/name").getValue());
		assertEquals("Duncan", graph.get("customer/name").getValue());
		assertEquals("SW1 3RJ", graph.get("customer/address/postcode").getValue());
		assertEquals("SW1 3RJ", graph.get("customer").get("address").getValue("postcode"));
	}
	
	@Test
	public void testGetNull(){	
		Graph graph = new NimbleMapGraph();
		graph.put("/customer/name", "Duncan");
		assertEquals("Duncan", graph.get("customer/name").getValue());
		assertNull(graph.get("customer").get("address"));
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

