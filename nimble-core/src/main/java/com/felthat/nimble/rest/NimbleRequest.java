package com.felthat.nimble.rest;

import java.io.Serializable;
import java.util.Map;

public class NimbleRequest implements Serializable {
	
	private static final long serialVersionUID = 6320709312448457036L;
	
	private Map<String,Object> data;

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
