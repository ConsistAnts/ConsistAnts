package de.hu_berlin.cchecker.core.learning.blockwise;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A map that combines new values for a given key with 
 * existing values, if they exist.
 *
 * @param <K> The type of keys
 * @param <V> The type of values
 */
public abstract class CombineMap<K, V> {
	private HashMap<K, V> internalMap = new HashMap<>();
	
	/**
	 * Combine operation to use in case of the collision of the values.
	 */
	public abstract V combine(V valueBefore, V newValue);
	
	/**
	 * Associates the specified value with the specified key in this map.
	 * 
	 * Combines existing values with the new value if applicable.
	 * 
	 * @see Map#put(Object, Object)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(K key, V value) {
		V existingValue = internalMap.get(key);
		V newValue = value;
		if (null != existingValue) {
			newValue = combine(existingValue, newValue);
		}
		internalMap.put(key, newValue);
		return null != existingValue;
	}
	
	/**
	 * Returns a {@link Set} view of the mappings contained in this map.
	 * 
	 * @see Map#entrySet()
	 */
	public Set<Entry<K, V>> entrySet() {
		return internalMap.entrySet();
	}
	
	/**
	 * Returns a {@link Collection} view of the values contained in this map.
	 * 
	 * @see Map#values()
	 */
	public Collection<V> values() {
		return internalMap.values();
	}
	
	/**
	 * 
	 * Returns a {@link Set} view of the keys contained in this map.
	 * 
	 * @see Map#keySet()
	 */
	public Set<K> keySet() {
		return internalMap.keySet();
	}
	
	/**
	 * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
	 * 
	 * @see Map#get(Object)
	 */
	public V get(K key) {
		return this.internalMap.get(key);
	}
	
	/**
	 * Returns the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key.
	 * 
	 * @see Map#getOrDefault(Object, Object)
	 */
	public V getOrDefault(K key, V defaultValue) {
		return this.internalMap.getOrDefault(key, defaultValue);
	}
	
	@Override
	public String toString() {
		return internalMap.toString();
	}
}
