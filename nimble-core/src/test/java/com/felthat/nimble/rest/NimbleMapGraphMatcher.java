package com.felthat.nimble.rest;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;

import com.felthat.nimble.graph.Graph;

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
			}else if(!(argument instanceof Graph)){
				matchingFailureString = argument.toString();
				return false;
			}else{
				Graph graph = (Graph) argument;
				matchingFailureString = graph.toString();
				return true;
			}
		}
		
		
		
		private boolean isNimbleMap(Map<String,Object> graphObject) {
			
			return false;
		}



		@Override
		public void describeTo(Description description) {
			description.appendText(matchingFailureString );
		}
	}