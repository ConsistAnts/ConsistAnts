package de.hu_berlin.cchecker.core.transformations.minimization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.hu_berlin.cchecker.core.learning.blockwise.CombineMap;

/**
 * A {@link CombineSet} behaves similarly to a {@link Set}.
 * 
 * The main difference is that when adding an element that has already been
 * added to the set (in terms of its hash code, @see {@link #hashFunction(Object)} and equality, @see {@link #equal(Object, Object)}),
 * the added element is combined with the existing one.
 * 
 * To combine elements, the abstract method {@link #combine(Object, Object)} is used.

 *
 * @param <E> The type of elements in this set.
 */
public abstract class CombineSet<E> {
	private CombineMap<Integer, E> internalCombineMap = new InternalCombineMap(); 
	
	/**
	 * Function to combine an existing value with a new value with 
	 * the same hash that is equal according to {@link #equal(Object, Object)}
	 * 
	 * @param valueBefore Existing value in the set
	 * @param newValue New added value
	 * @return
	 */
	protected abstract E combine(E valueBefore, E newValue);
	
	/**
	 * Function to compute hash values for elements.
	 * 
	 * Note: Most of the times, it will make sense for this hash value to not
	 * consider all of the fields of E types.
	 * 
	 * Also note that it must always hold true that:
	 * 
	 * 	for all e1, e2 in E: equal(e1, e2) => hashCode(e1) == hashCode(e2)
	 */
	protected abstract int hashFunction(E element);
	
	/**
	 * Returns <code>true</code> if the two elements are considered equal in the context of this {@link CombineSet}.
	 * 
	 * Returns <code>false</code> otherwise.
	 * 
	 * Note that it must always hold true that:
	 * 
	 * 	for all e1, e2 in E: equal(e1, e2) => hashCode(e1) == hashCode(e2)
	 * 
	 */
	protected abstract boolean equal(E element1, E element2);
	
	/**
	 * Adds the given element to this set.
	 */
	public void add(E newElement) {
		int hashValue = hashFunction(newElement);
		
		E existingElement = this.internalCombineMap.get(hashValue);
		
		// make sure we don't combine values that collide wrt hash value
		while (existingElement != null && !equal(existingElement, newElement)) {
			// double hash to get new address
			hashValue = (hashValue * 2) % 1300727; // prime
			existingElement = this.internalCombineMap.get(hashValue);
		}
		
		this.internalCombineMap.put(hashValue, newElement);
	}
	
	/**
	 * Adds all of the given element to this set.
	 */
	public void addAll(Collection<E> elements) {
		for (E element : elements) {
			this.add(element);
		}
	}
	
	/**
	 * Returns <code>true</code> if the set contains an element with 
	 * the same hash as the given element. Return <code>false</code> otherwise.
	 */
	public boolean contains(E element) {
		return this.internalCombineMap.values().contains(hashFunction(element));
	}
	
	/**
	 * Returns a set of all the values of this {@link CombineSet}.
	 */
	public Set<E> set() {
		return new HashSet<>(internalCombineMap.values());
	}

	/**
	 * The internally used {@link CombineMap} that uses the combine method 
	 * of the {@link CombineSet}.
	 */
	private class InternalCombineMap extends CombineMap<Integer, E>{
		@Override
		public E combine(E valueBefore, E newValue) {
			return CombineSet.this.combine(valueBefore, newValue);
		}
	}
	
	@Override
	public String toString() {
		return this.set().toString();
	}
}
