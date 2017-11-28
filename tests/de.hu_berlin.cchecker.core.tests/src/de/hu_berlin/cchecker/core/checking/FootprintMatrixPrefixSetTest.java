package de.hu_berlin.cchecker.core.checking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.checking.FootprintMatrixCheckingAlgorithm;

/**
 * Tests for the prefix set generation of the {@link FootprintMatrixCheckingAlgorithm}.
 */
@RunWith(JUnit4.class)
public class FootprintMatrixPrefixSetTest {
	/**
	 * Asserts the string representation of a given list of String arrays.
	 * 
	 * Example:
	 * <code>
	 * assertStringArrayList("Some message", "[[a,b], [a,b]]", someList);
	 * </code>
	 */
	private void assertStringArrayList(String message, String expected, List<String[]> actual) {
		List<String> actualRepresentation = new ArrayList<String>();
		for (String[] trace : actual) {
			actualRepresentation.add(Arrays.toString(trace));
		}
		Collections.sort(actualRepresentation);
		assertEquals(message,  expected, actualRepresentation.toString());
	}
	
	@Test
	public void testPrefixSetGeneration() {
		List<String[]> traceDataSet = Arrays.asList(
			new String[]{"a", "b", "c", "d", "e", "f"},
			new String[]{"a", "b", "c", "d"},
			new String[]{"a", "b", "1"},
			new String[]{"a", "b", "2"},
			new String[]{"a", "b"},
			new String[]{"a"}
		);
		
		Iterator<List<String[]>> generatePrefixSetsIterator = TraceFootprintMatrixGenerator.getPrefixSetIterator(traceDataSet);
		List<List<String[]>> generatedPrefixSets = new ArrayList<List<String[]>>();
		
		while (generatePrefixSetsIterator.hasNext()) {
			generatedPrefixSets.add(generatePrefixSetsIterator.next());
		}
		
		assertEquals("Correct number of prefix sets is generated", 5, generatedPrefixSets.size());
		
		assertEquals("First prefix set is for length 2", generatedPrefixSets.get(0).get(0).length, 2);
		assertEquals("Second prefix set is for length 3", generatedPrefixSets.get(1).get(0).length, 3);
		assertEquals("First prefix set is for length 4", generatedPrefixSets.get(2).get(0).length, 4);
		
		assertStringArrayList("First prefix set looks as expected", "[[a, b], [a, b], [a, b], [a, b], [a, b]]", generatedPrefixSets.get(0));
		assertStringArrayList("Second prefix set looks as expected", "[[a, b, 1], [a, b, 2], [a, b, c], [a, b, c]]", generatedPrefixSets.get(1));
		assertStringArrayList("Third prefix set looks as expected", "[[a, b, c, d], [a, b, c, d]]", generatedPrefixSets.get(2));
		assertStringArrayList("Fourth prefix set looks as expected", "[[a, b, c, d, e]]", generatedPrefixSets.get(3));
		assertStringArrayList("Fith prefix set looks as expected", "[[a, b, c, d, e, f]]", generatedPrefixSets.get(4));
	}
}
