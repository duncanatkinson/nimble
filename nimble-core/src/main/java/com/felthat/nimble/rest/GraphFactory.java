package com.felthat.nimble.rest;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMapGraph;

public class GraphFactory {

	public Graph create() {
		return new NimbleMapGraph();
	}

}
