package de.hu_berlin.cchecker.core.transformations.minimization;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import de.hu_berlin.cchecker.core.transformations.minimization.CombineSet;

/**
 * This class tests {@link CombineSet}.
 */
public class CombineSetTest {
	/**
	 * Tests the simple addition of new element to a {@link CombineSet}.
	 */
	@Test
	public void testSimpleElements() {
		TestCombineSet set = new TestCombineSet();

		// add initial distinct set of elements
		set.add(TestElement.create(1));
		set.add(TestElement.create(2));
		set.add(TestElement.create(3));

		assertEquals("No merging with distinct elements",
				"[{id=1, count=1, different=0}, {id=2, count=1, different=0}, "
						+ "{id=3, count=1, different=0}]",
				sortedToString(set));
	}

	/**
	 * Tests the simple combination of an element in a {@link CombineSet}.
	 */
	@Test
	public void testSimpleCombination() {
		TestCombineSet set = new TestCombineSet();

		// add initial distinct set of elements
		set.add(TestElement.create(1));
		set.add(TestElement.create(2));
		set.add(TestElement.create(3));

		// add a combinable element
		set.add(TestElement.create(1));

		assertEquals("Combinable element is merged",
				"[{id=1, count=2, different=0}, {id=2, count=1, different=0}, "
						+ "{id=3, count=1, different=0}]",
				sortedToString(set));
	}

	/**
	 * Tests that a {@link CombineSet} double-checks elements with the same hash
	 * value using {@link CombineSet#equals(Object)} before actually combining them. 
	 */
	@Test
	public void testSameHashButNotEqualElement() {
		TestCombineSet set = new TestCombineSet();

		// add initial distinct set of elements
		set.add(TestElement.create(1));
		set.add(TestElement.create(2));
		set.add(TestElement.create(3));

		// add a combinable element
		set.add(TestElement.create(1));

		// add an element that has the same hash value but is not equal
		TestElement differentElement = TestElement.create(2);
		differentElement.different = 1;

		set.add(differentElement);

		assertEquals("Unequal element is not combined",
				"[{id=1, count=2, different=0}, {id=2, count=1, different=0}, "
						+ "{id=2, count=1, different=1}, " + "{id=3, count=1, different=0}]",
				sortedToString(set));
	}
	
	/**
	 * Tests that a {@link CombineSet} double-checks elements with the same hash
	 * value using {@link CombineSet#equals(Object)} before actually combining them. 
	 */
	@Test
	public void testTriggerDoubleHashingTwice() {
		TestCombineSet set = new TestCombineSet();

		// add initial distinct set of elements
		set.add(TestElement.create(2));

		// add an element that has the same hash value but is not equal
		TestElement differentElement = TestElement.create(2);
		differentElement.different = 1;

		set.add(differentElement);
		
		// add another element that has the same hash value but is not equal with any existing element
		TestElement anotherElement = TestElement.create(2);
		anotherElement.different = 2;

		set.add(anotherElement);

		assertEquals("Unequal element is not combined",
				"[{id=2, count=1, different=0}, "
						+ "{id=2, count=1, different=1}, " + "{id=2, count=1, different=2}]",
				sortedToString(set));
	}

	private static <E extends Comparable<? super E>> String sortedToString(CombineSet<E> set) {
		List<E> list = new ArrayList<>(set.set());
		Collections.sort(list);
		return list.toString();
	}

	/**
	 * A test element has an id and a count field.
	 * 
	 * Equality is should be defined only on the id field.
	 * 
	 * Count keeps track of how many elements have been merged into this one.
	 *
	 */
	private static class TestElement implements Comparable<TestElement> {
		final int id;
		final int count;

		// element that is not part of hashCode but
		// considered by equal in TestCombineSet
		int different = 0;

		public TestElement(int id, int count) {
			super();
			this.id = id;
			this.count = count;
		}

		static TestElement create(int id) {
			return new TestElement(id, 1);
		}

		@Override
		public String toString() {
			return String.format("{id=%d, count=%d, different=%d}", this.id, this.count, this.different);
		}

		@Override
		public int compareTo(TestElement o) {
			// compare first by #id and then by #different
			int result = this.id - o.id;
			if (0 == result) {
				result = this.different - o.different;
			}
			return result;
		}
	}

	/**
	 * A simple test implementation of {@link CombineSet}.
	 *
	 */
	private class TestCombineSet extends CombineSet<TestElement> {

		@Override
		protected TestElement combine(TestElement valueBefore, TestElement newValue) {
			return new TestElement(valueBefore.id, valueBefore.count + newValue.count);
		}

		@Override
		protected int hashFunction(TestElement element) {
			return element.id;
		}

		@Override
		protected boolean equal(TestElement element1, TestElement element2) {
			return element1.id == element2.id && element1.different == element2.different;
		}
	}
}
