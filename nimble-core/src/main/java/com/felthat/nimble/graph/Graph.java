package com.felthat.nimble.graph;



public interface Graph {
	
	/**
	 * Store string at path do not effect other fields
	 * @param path
	 * @param value
	 */
	public void put(String path, Object value);

	
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
	 * get a value from graph, if referencing a graph object throws
	 * @param path
	 * @return
	 */
	public Object getValue(String path);
		
	/**
	 * Deletes from path, any child nodes now empty will also be removed
	 * @param path
	 */
	public void delete(String path);

}