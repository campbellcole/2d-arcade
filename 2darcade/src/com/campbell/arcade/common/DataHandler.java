package com.campbell.arcade.common;

import java.util.HashMap;
import java.util.Set;

public class DataHandler {
	
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	public void set(String key, Object value) {
		map.put(key, value);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public Set<String> keys() {
		return map.keySet();
	}
	
}
