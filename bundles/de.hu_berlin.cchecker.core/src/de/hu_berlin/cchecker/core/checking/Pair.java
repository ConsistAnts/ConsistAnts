package de.hu_berlin.cchecker.core.checking;

/**
 * A pair holds two values of chosen type.
 * @author Daniel
 *
 * @param <U> Type of the first value
 * @param <V> Type of the second value
 */
public class Pair<U, V> {
	public final U first;
	public final V second;

	private Pair(U first, V second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Creates a new pair with the given values.
	 */
	public static <U,V> Pair<U,V> create(U first, V second) {
		return new Pair<>(first, second);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof Pair<?, ?>)) {
			return false;
		}
		Pair<?, ?> other = (Pair<?, ?>) obj;
		return this.first.equals(other.first) && this.second.equals(other.second);
	}
	
	@Override
	public int hashCode() {
		return (this.first.hashCode()) ^ (30011 * this.second.hashCode());
	}
}
