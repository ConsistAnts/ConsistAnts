package de.hu_berlin.cchecker.ui.collectors;

/**
 * Abstract data type that holds two values of generic type.
 *
 * @param <U> The type of the first value
 * @param <V> The type of the second value
 */
public class Pair<U, V> {
	private U first;
	private V second;
	
	/**
	 * Instantiates a new {@link Pair} with the given values
	 */
	public Pair(U a, V b) {
		this.setFirst(a);
		this.setSecond(b);
	}
	
	/**
	 * Instantiates a new {@link Pair} with {@code null} for both fields.
	 */
	public Pair() {
		
	}

	public U getFirst() {
		return first;
	}

	public void setFirst(U a) {
		this.first = a;
	}

	public V getSecond() {
		return second;
	}

	public void setSecond(V b) {
		this.second = b;
	}
}
