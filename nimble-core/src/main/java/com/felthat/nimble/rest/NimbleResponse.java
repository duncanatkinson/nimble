package com.felthat.nimble.rest;

import java.io.Serializable;
import java.util.Map;

import com.felthat.nimble.graph.Graph;
import com.felthat.nimble.graph.NimbleMapGraph;

public class NimbleResponse implements Serializable {
	
	private static final long serialVersionUID = 6320709312448457036L;
	
	private Map<String,Object> data;

	public NimbleResponse(Map<String, Object> graphObject,String url) {
		this.data = graphObject;
		addUrls(graphObject, url);
	}
	
	public NimbleResponse() {
	}

	public NimbleResponse(NimbleMapGraph graph, String baseURL) {
		this(graph.getGraphObject(),baseURL);
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public static void addUrls(Map<String, Object> data, String url) {
		data.put("url", url);
		for(String key : data.keySet()){
			if(data.get(key) instanceof Map){
				addUrls((Map<String, Object>) data.get(key),url + "/" + key);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NimbleResponse other = (NimbleResponse) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("NimbleResponse [data=%s]", data);
	}
	
	
	
}
