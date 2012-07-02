package com.felthat.nimble.graph;

import java.util.List;

public class NimbleAccessHelper {

	/**
	 * //TODO deal or warn about rest of array if working with lists, arrays etc.
	 * @param object
	 * @return
	 */
	public static String getAsString(Object object) {
		String value;
		
		Class clazz = object.getClass();
			
		if(clazz == String.class){
			value = (String) object;
		}else if(clazz == String[].class){
			String [] arrayStrings = (String[])object;
			value = arrayStrings.length > 0 ? arrayStrings[0] : null;
		}else if(clazz == List.class){
			List arrayList = (List)object;
			Object firstObject = arrayList.size() > 0 ? arrayList.get(0) : null;
			if(firstObject instanceof String){
				value = (String) firstObject;
			}else{
				value = firstObject.toString();
			}
		}else{
			value = "NimbleAccessHelper could not find value";
		}
		
		return value;
	}

}
