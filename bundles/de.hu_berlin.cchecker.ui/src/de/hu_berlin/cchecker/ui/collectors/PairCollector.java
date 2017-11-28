package de.hu_berlin.cchecker.ui.collectors;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A {@link Collector} that collects exactly two values of the given generic
 * types.
 * 
 * The first value of a type that is found is returned in the output {@link Pair}.
 * 
 * Further values of the same type are skipped.
 * 
 *
 * @param <U>
 *            The type of the first value to collect.
 * @param <V>
 *            The type of the second value to collect.
 */
public class PairCollector<U, V> implements Collector<Object, Pair<U, V>, Pair<U, V>> {

	Class<U> uClass;
	Class<V> vClass;

	/**
	 * Instantiates a new {@link PairCollector} for the given types for the first and second value.
	 */
	public PairCollector(Class<U> uClass, Class<V> vClass) {
		this.uClass = uClass;
		this.vClass = vClass;
	}

	@Override
	public Supplier<Pair<U, V>> supplier() {
		// creates a new empty Pair instance
		return new Supplier<Pair<U, V>>() {

			@Override
			public Pair<U, V> get() {
				return new Pair<U, V>(null, null);
			}
		};
	}

	@Override
	public BiConsumer<Pair<U, V>, Object> accumulator() {
		// accumulator that assigns a value to one of the Pair fields
		// iff the field hasn't been assigned so far and the type of the field 
		// matches.
		return new BiConsumer<Pair<U, V>, Object>() {

			@SuppressWarnings("unchecked")
			@Override
			public void accept(Pair<U, V> t, Object o) {
				if (uClass.isInstance(o) && t.getFirst() == null) {
					t.setFirst((U) o);
				} else if (vClass.isInstance(o) && t.getSecond() == null) {
					t.setSecond((V) o);
				}

			}
		};
	}

	@Override
	public BinaryOperator<Pair<U, V>> combiner() {
		// combines two Pair instances. The RHS always dominates the 
		// LHS in that its values always have precedence.
		return new BinaryOperator<Pair<U, V>>() {

			@Override
			public Pair<U, V> apply(Pair<U, V> t, Pair<U, V> u) {
				if (t.getFirst() == null) {
					t.setFirst(u.getFirst());
				}
				if (t.getSecond() == null) {
					t.setSecond(u.getSecond());
				}
				return t;
			}
		};
	}

	@Override
	public Function<Pair<U, V>, Pair<U, V>> finisher() {
		// no finializing needed, this is an identity function
		return new Function<Pair<U, V>, Pair<U, V>>() {

			@Override
			public Pair<U, V> apply(Pair<U, V> t) {
				return t;
			}
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		return new HashSet<>();
	}
}