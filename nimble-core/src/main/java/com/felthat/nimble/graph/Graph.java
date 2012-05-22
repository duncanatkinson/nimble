package com.felthat.nimble.graph;


public interface Graph {
	
	/**
	 * Store string at path do not effect other fields
	 * @param path
	 * @param value
	 */
	public void put(String path, String value);

	/**
	 * Overwrite a graph with a new one (
	 * @param graph
	 */
	public void put(String path, Graph replacement);

	/**
	 * merge new graph into this one.
	 * @param graph
	 */
	public void merge(String path, Graph graph);
	
	/**
	 * get a sub graph object from the tree
	 * @param path
	 * @return
	 */
	public Graph get(String path);
	
	
	/**
	 * This method is to be used to get a single String field from the graph
	 * if used on a path containing a subgraph then the results are not guaranteed (depends on implementation)
	 */
	public String getField(String path);

	/**
	 * Deletes from path, any child nodes now empty will also be removed
	 * @param path
	 */
	public void delete(String path);

}