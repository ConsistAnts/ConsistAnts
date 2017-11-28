package de.hu_berlin.cchecker.core.learning.blockwise;

/**
 * An {@link OccurrenceCounter} can be used to keep track of the number
 * of occurrences of a given key element.
 */
public class OccurrenceCounter<KEY> extends CombineMap<KEY, Integer>{
	/**
	 * Increases the number of occurences for the given key by 1.
	 */
	public void increase(KEY key) {
		this.put(key, 1);
	}

	@Override
	public Integer combine(Integer valueBefore, Integer newValue) {
		return valueBefore + newValue;
	}
}
