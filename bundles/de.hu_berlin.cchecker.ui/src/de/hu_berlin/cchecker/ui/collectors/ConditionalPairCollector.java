package de.hu_berlin.cchecker.ui.collectors;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * A collector that collects exactly two values of the given generic type.
 * 
 * Before collecting a value into the output {@link Pair} the given condition
 * ({@link #uCondition} or {@link #vCondition}) must be true. Values of correct
 * type that do not satisfy the conditions are skipped.
 *
 * @param <U>
 *            The type of the first value to collect
 * @param <V>
 *            The type of the second value to collect
 */
public class ConditionalPairCollector<U, V> extends PairCollector<U, V> {

	private Predicate<U> uCondition;
	private Predicate<V> vCondition;

	/**
	 * Instantiates a new {@link ConditionalPairCollector} for the given types and
	 * conditions.
	 * 
	 * @param uClass
	 *            A class instance of the first type
	 * @param vClass
	 *            A class instance of the second type
	 * @param uCondition
	 *            A predicate that must be true for a value of the first type to be
	 *            collected
	 * @param vCondition
	 *            A predicate that must be true for a value of the second type to be
	 *            collected
	 */
	public ConditionalPairCollector(Class<U> uClass, Class<V> vClass, Predicate<U> uCondition,
			Predicate<V> vCondition) {
		super(uClass, vClass);
		this.uCondition = uCondition;
		this.vCondition = vCondition;
	}

	@Override
	public BiConsumer<Pair<U, V>, Object> accumulator() {
		// This consumer checks object type and the according condition
		// before accumulating a value into the output pair.
		return new BiConsumer<Pair<U, V>, Object>() {

			@SuppressWarnings("unchecked")
			@Override
			public void accept(Pair<U, V> t, Object o) {
				if (uClass.isInstance(o) && uCondition.test((U) o) && t.getFirst() == null) {
					t.setFirst((U) o);
				} else if (vClass.isInstance(o) && vCondition.test((V) o) && t.getSecond() == null) {
					t.setSecond((V) o);
				}

			}
		};
	}
}
