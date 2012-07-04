package com.felthat.nimble.rest;

import java.io.Serializable;
import java.util.List;

import com.felthat.nimble.graph.NimbleMap;

public class NimbleRequest implements Serializable {
	
	private static final long serialVersionUID = 6320709312448457036L;
	
	private NimbleMap<String,Object> data;
	private List<String> field;

	public List<String> getField() {
		return field;
	}

	public void setField(List<String> field) {
		this.field = field;
	}

	public NimbleMap<String, Object> getData() {
		return data;
	}

	public void setData(NimbleMap<String, Object> data) {
		this.data = data;
	}
}
