package com.felthat.nimble.tags;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class NimbleAccessHelperTest {

	
	
	
	@Test
	public void testGetAsString_String() {
		assertEquals("someString", NimbleAccessHelper.getAsString("someString"));
	}

	@Test
	public void testGetAsString_ArrayOfStringSingleValue() {
		assertEquals("someString", NimbleAccessHelper.getAsString(new String[]{"someString"}));
	}

	@Test
	public void testGetAsString_ArrayOfStringMultipleValues() {
		assertEquals("someString", NimbleAccessHelper.getAsString(new String[]{"someString","someOtherString"}));
	}
	
	@Test
	public void testGetAsString_ListOfNull() {
		List listOfString = new ArrayList<String>();
		Assert.assertEquals("",NimbleAccessHelper.getAsString(listOfString));
	}
	
	@Test
	public void testGetAsString_ListOfString() {
		List listOfString = new ArrayList<String>();
		listOfString.add("someString");
		assertEquals("someString", NimbleAccessHelper.getAsString(listOfString));
	}
}
