package com.felthat.nimble.rest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class NimbleRequest implements Serializable {
	
	private static final long serialVersionUID = 6320709312448457036L;
	
	private Map<String,Object> data;
	private List<String> field;

	public List<String> getField() {
		return field;
	}

	public void setField(List<String> field) {
		this.field = field;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
