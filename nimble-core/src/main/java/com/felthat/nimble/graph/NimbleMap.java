package com.felthat.nimble.graph;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Marker class so we know we have a graph node
 * @author Duncan
 *
 * @param <K>
 * @param <V>
 */
public class NimbleMap<K, V> extends TreeMap<K, V> {

	public NimbleMap() {
	}

	public NimbleMap(Comparator<? super K> arg0) {
		super(arg0);
	}

	public NimbleMap(Map<? extends K, ? extends V> arg0) {
		super(arg0);
	}

	public NimbleMap(SortedMap<K, ? extends V> arg0) {
		super(arg0);
	}

}
