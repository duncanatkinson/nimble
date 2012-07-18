package com.felthat.nimble.rest;

import java.io.Serializable;
import java.util.List;
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
		NimbleRequest other = (NimbleRequest) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NimbleRequest [data=" + data + "]";
	}
		
	
}
