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
	 * Merge new graph into this one, assume both graphs share the same root node.
	 * @param graph
	 */
	public void merge(Graph graph);
	
	/**
	 * Merge new graph into this one, root node to apply graph to is the one in the path
	 * @param graph
	 * @param path where the new graph should be applied
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