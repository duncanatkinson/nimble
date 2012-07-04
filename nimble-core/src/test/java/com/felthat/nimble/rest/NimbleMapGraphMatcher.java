package com.felthat.nimble.rest;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMap;
import com.felthat.nimble.graph.NimbleMapGraph;

public class NimbleMapGraphMatcher extends ArgumentMatcher<Graph>{

		public static Matcher<Graph> isGraphUsingNimbleMap() {
			return new NimbleMapGraphMatcher();
		}

		private String matchingFailureString = "";
		
		@Override
		public boolean matches(Object argument) {
			if(argument == null){
				matchingFailureString = "null";
				return false;
			}
			if(!(argument instanceof Graph)){
				matchingFailureString = argument.toString();
				return false;
			}
			Map graph = (Map) argument;
			
			boolean isNimbleMap = isNimbleMap(graph);
			
			if(!isNimbleMap){
				matchingFailureString = "Not Nimble Map";
			}
			return isNimbleMap;
		}
		
		
		
		private boolean isNimbleMap(Map<String,Object> graphObject) {
			
			return false;
		}



		@Override
		public void describeTo(Description description) {
			description.appendText(matchingFailureString );
		}
	}