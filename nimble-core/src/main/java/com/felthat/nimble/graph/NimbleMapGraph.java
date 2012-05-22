package com.felthat.nimble.graph;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class NimbleMapGraph implements Graph {
	
	private static final String SINGLE_FIELD_VALUE = "value";
	private Map<String,Object> graphObject;
	
	public Map<String, Object> getGraphObject() {
		return graphObject;
	}

	public void setGraphObject(Map<String, Object> graphObject) {
		this.graphObject = graphObject;
	}

	public NimbleMapGraph() {
		this.graphObject = new TreeMap<String,Object>();
	}
	
	public NimbleMapGraph(Map<String,Object> graphObject) {
		this.graphObject = graphObject;
	}

	public synchronized void put(String key, String value) {
		StringTokenizer stringTokenizer = getTokenizer(key);
		save(stringTokenizer, this.graphObject, value);
	}
	
	@SuppressWarnings("unchecked")
	private void save(StringTokenizer tokenizer, Map<String,Object> graphMap, String value) {
		String key = tokenizer.nextToken();
		if(tokenizer.hasMoreTokens()){
			Map<String,Object> subGraph = null;
			
			Object object = graphMap.get(key);
			if(object instanceof Map){
				subGraph = (Map<String, Object>) object;
				//if it is not a map then it will be overwritten by a new one
			}
			if(subGraph == null){
				subGraph = new TreeMap<String,Object>();
				graphMap.put(key, subGraph);
			}
			save(tokenizer,subGraph, value);
		}else{
			graphMap.put(key, value);
		}
	}

	public synchronized void delete(String path) {
		delete(getTokenizer(path),graphObject,null,null);
	}

	@SuppressWarnings("unchecked")
	private void delete(StringTokenizer tokenizer,Map<String,Object> graph,Map<String,Object> parentGraph, String parentKey) {
		String key = tokenizer.nextToken();
		if(tokenizer.hasMoreTokens()){
			delete(tokenizer, (Map<String, Object>) graph.get(key),graph,key);
		}else{
			graph.remove(key);
		}
		if(graph.isEmpty() && parentGraph != null){
			parentGraph.remove(parentKey);
		}
	}

	public synchronized void put(String path, Graph graph) {
		StringTokenizer stringTokenizer = getTokenizer(path);
		if(stringTokenizer.hasMoreTokens()){
			save(stringTokenizer, this.graphObject,graph);
		}else{
			this.graphObject.clear();
			this.graphObject.putAll(((NimbleMapGraph) graph).getGraphObject());
		}
	}
	
	public synchronized void merge(String path, Graph graph) {
		StringTokenizer stringTokenizer = getTokenizer(path);
		if(stringTokenizer.hasMoreTokens()){
			save(stringTokenizer, this.graphObject,graph);
		}else{
			Map<String,Object> targetGraph = this.getGraphObject();
			Map<String,Object> mergeGraph = ((NimbleMapGraph)graph).getGraphObject();
			merge(targetGraph, mergeGraph);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void merge(Map<String, Object> targetGraph,Map<String, Object> mergeGraph) {
		for(String key: mergeGraph.keySet()){
			if(!targetGraph.containsKey(key) || targetGraph.get(key) instanceof String){
				targetGraph.put(key, mergeGraph.get(key));
			}else if(mergeGraph.get(key) instanceof Map<?,?>){
				merge((Map<String, Object>)targetGraph.get(key),(Map<String, Object>)mergeGraph.get(key));
			}else{
				targetGraph.put(key, mergeGraph.get(key));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void save(StringTokenizer tokenizer, Map<String, Object> graphToUpdate, Graph newGraph) {
		String key = tokenizer.nextToken();
		if(tokenizer.hasMoreTokens()){
			Map<String,Object> subGraph = null;
			
			Object object = graphToUpdate.get(key);
			if(object instanceof Map){
				subGraph = (Map<String, Object>) object;
				//if it is not a map then it will be overwritten by a new one
			}
			if(subGraph == null){
				subGraph = new TreeMap<String,Object>();
				graphToUpdate.put(key, subGraph);
			}
			save(tokenizer,subGraph,newGraph);
		}else{
			Map<String, Object> map = ((NimbleMapGraph) newGraph).getGraphObject();
			if(map.keySet().size() == 1 && map.get(key) instanceof String){
				//Special case, we want to allow updates to a single field
				//we assume this is desired when the value being set is a single item String.
				graphToUpdate.put(key, map.get(key));
			}else{
				graphToUpdate.put(key, map);				
			}
			
		}
	}

	public synchronized Graph get(String key) {
		if("".equals(key) || "/".equals(key) || key == null){
			return this;
		}
		return get(getTokenizer(key),this.graphObject);
	}

	private synchronized Graph get(StringTokenizer tokenizer, Map<String, Object> graph) {
		String key = tokenizer.nextToken();
		if(tokenizer.hasMoreTokens()){
			Object value = graph.get(key);
			if(value instanceof Map){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) value;
				return get(tokenizer,map);
			}else{
				return null;
			}
		}else{
			Object value = graph.get(key);
			if(value instanceof Map){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) value;
				return new NimbleMapGraph(map);
			}else{
				Map<String, Object> mapForSingleValue = new TreeMap<String,Object>();
				mapForSingleValue.put(SINGLE_FIELD_VALUE, graph.get(key));
				return new NimbleMapGraph(mapForSingleValue);
			}
		}
	}

	public synchronized String getField(String path) {
		NimbleMapGraph graph = (NimbleMapGraph) get(path);
		if(graph == null){
			return null;
		}
		if(graph.graphObject.size() == 1 && graph.graphObject.containsKey(SINGLE_FIELD_VALUE)){
			return (String) graph.getGraphObject().get(SINGLE_FIELD_VALUE);
		}else{
			return graph.toString();
		} 
	}

	public static StringTokenizer getTokenizer(String key) {
		String slashChangedKey = key.replace('\\','/');
		StringTokenizer stringTokenizer = new StringTokenizer(slashChangedKey,"/");
		return stringTokenizer;
	}
	
	@Override
	public String toString() {
		return NimbleMapGraph.class.getSimpleName() + ": " + this.graphObject.toString();
	}
}
