package de.hu_berlin.cchecker.core.checking;

import static org.junit.Assert.assertEquals;

import java.util.List;

import de.hu_berlin.cchecker.core.checking.ConfFPMatrix;
import de.hu_berlin.cchecker.core.checking.FPMatrix;

public abstract class AbstractFootprintMatrixTest {

	/**
	 * Helper function to assert footprint matrices to have an expected
	 * string representation.
	 */
	protected void assertMatrix(String message, String expected, FPMatrix actual) {
		assertEquals(message, expected, actual.toString());
	}
	
	/**
	 * Helper function to assert a list of footprint matrices to have an expected 
	 * string representation,
	 */
	protected void assertMatrices(String message, String expected, List<FPMatrix> actual) {
		assertEquals(message,  expected, actual.toString());
	}
	
	/**
	 * Helper function to assert a list of conformance footprint matrices to have an expected 
	 * string representation,
	 */
	protected void assertConformanceMatrices(String message, String expected, List<ConfFPMatrix> actual) {
		assertEquals(message,  expected, actual.toString());
	}

}