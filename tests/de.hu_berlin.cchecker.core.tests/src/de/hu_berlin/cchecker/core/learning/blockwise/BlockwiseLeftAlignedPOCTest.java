package de.hu_berlin.cchecker.core.learning.blockwise;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.JsonTraceDatasetParser;
import junit.framework.AssertionFailedError;

/**
 * This test should assure that our proof-of-concept (POC) implementation 
 * yields the same results as the production implementation.
 * 
 * See the{@code res/blockwise} folder for testdata and descriptions.
 */
@RunWith(JUnit4.class)
public class BlockwiseLeftAlignedPOCTest {
	@Test
	public void testDataset1() {
		TraceDataset traces = getTraceDataset("test-dataset-1.trc");
		BlockwiseLeftAlignedLearningAlgorithm algorithm = createLearningAlgorithm();

		String learnedSRE = algorithm.learnModel(traces);
		String expectedSRE = "((a : (a*0.25))[4] + (b)[1] + (c : (c*0.333333333333))[3] + (d : (d*0.2))[5] + (e : (e*0.5))[2]) : ((b)[4] + (c)[5] + (d)[2] + (e : (e*0.75))[4]) : ((a : (a*0.166666666667))[6] + (c : (c*0.333333333333))[3] + (d)[3] + (e : (e*0.333333333333))[3]) : (((EPSILON*0.0))[1] + (a)[2] + (b)[2] + (c)[4] + (d : (d*0.25))[4] + (e)[2]) : (((EPSILON*0.0))[3] + (a : (a*0.333333333333))[3] + (b : (b*0.4))[5] + (d : (d*0.5))[2] + (e : (e*0.5))[2]) : (((EPSILON*0.0))[7] + (a)[2] + (c)[2] + (d)[1] + (e)[3]) : (((EPSILON*0.0))[11] + (b)[1] + (c)[1] + (d)[1] + (e)[1]) : (((EPSILON*0.0))[12] + (d)[1] + (e)[2])";
		
		assertEquals("proof-of-concept implementation has same results as production implementation", expectedSRE, learnedSRE);
	}
	
	@Test
	public void testDataset2() {
		TraceDataset traces = getTraceDataset("test-dataset-2.trc");
		BlockwiseLeftAlignedLearningAlgorithm algorithm = createLearningAlgorithm();

		String learnedSRE = algorithm.learnModel(traces);
		String expectedSRE = "((a : (a*0.5))[4] + (c : (c*0.5))[2] + (d : (d*0.4))[5] + (e)[4]) : ((a : (a*0.2))[5] + (b)[3] + (c : (c*0.666666666667))[3] + (d)[2] + (e)[2]) : (((EPSILON*0.0))[1] + (a)[3] + (b)[1] + (c)[2] + (d)[3] + (e : (e*0.4))[5]) : (((EPSILON*0.0))[2] + (a)[2] + (b : (b*0.333333333333))[3] + (c : (c*0.333333333333))[3] + (d)[2] + (e : (e*0.333333333333))[3]) : (((EPSILON*0.0))[5] + (a : a)[1] + (b)[2] + (c)[1] + (d : (d*0.2))[5] + (e)[1]) : (((EPSILON*0.0))[8] + (a)[3] + (b)[1] + (c)[1] + (d : d)[1] + (e)[1]) : (((EPSILON*0.0))[10] + (a)[1] + (c : c)[1] + (d)[3]) : (((EPSILON*0.0))[14] + (b)[1])";
		
		assertEquals("proof-of-concept implementation has same results as production implementation", expectedSRE, learnedSRE);
	}
	
	@Test
	public void testDataset3() {
		TraceDataset traces = getTraceDataset("test-dataset-3.trc");
		BlockwiseLeftAlignedLearningAlgorithm algorithm = createLearningAlgorithm();

		String learnedSRE = algorithm.learnModel(traces);
		String expectedSRE = "((a : (a*0.333333333333))[3] + (b : b)[2] + (c : (c*0.333333333333))[3] + (d)[2] + (e)[5]) : ((a : a : a)[2] + (b : (b*0.333333333333))[6] + (c : c : (c*0.5))[2] + (d)[2] + (e)[3]) : (((EPSILON*0.0))[2] + (a : (a*0.333333333333))[3] + (b)[3] + (c)[3] + (d : (d*0.666666666667))[3] + (e)[1]) : (((EPSILON*0.0))[4] + (a)[4] + (b)[1] + (c)[1] + (d)[1] + (e)[4]) : (((EPSILON*0.0))[5] + (a)[2] + (b : (b*0.333333333333))[3] + (c)[1] + (d)[2] + (e)[2]) : (((EPSILON*0.0))[8] + (a : (a*0.5))[2] + (b)[2] + (c)[1] + (d : (d*0.5))[2]) : (((EPSILON*0.0))[10] + (a)[1] + (b)[1] + (c : c)[1] + (d)[1] + (e)[1]) : (((EPSILON*0.0))[13] + (a : a)[1] + (b)[1])";
		
		assertEquals("proof-of-concept implementation has same results as production implementation", expectedSRE, learnedSRE);
	}
	
	@Test
	public void testDataset4() {
		TraceDataset traces = getTraceDataset("test-dataset-4.trc");
		BlockwiseLeftAlignedLearningAlgorithm algorithm = createLearningAlgorithm();

		String learnedSRE = algorithm.learnModel(traces);
		String expectedSRE = "((a : (a*0.5))[2] + (b : (b*0.666666666667))[3] + (c : (c*0.333333333333))[3] + (d : (d*0.5))[4] + (e)[3]) : (((EPSILON*0.0))[1] + (a)[1] + (b : (b*0.25))[4] + (c)[2] + (d : (d*0.333333333333))[3] + (e : (e*0.25))[4]) : (((EPSILON*0.0))[1] + (a)[5] + (b)[5] + (d : (d*0.5))[4]) : (((EPSILON*0.0))[2] + (a)[3] + (b)[3] + (c)[2] + (d : (d*0.5))[2] + (e)[3]) : (((EPSILON*0.0))[3] + (a)[1] + (b)[1] + (c : (c*0.25))[4] + (d)[5] + (e)[1]) : (((EPSILON*0.0))[7] + (a)[1] + (b)[3] + (d)[2] + (e)[2]) : (((EPSILON*0.0))[9] + (a)[3] + (b)[1] + (d : (d*0.5))[2]) : (((EPSILON*0.0))[12] + (c)[3])";
		
		assertEquals("proof-of-concept implementation has same results as production implementation", expectedSRE, learnedSRE);
	}
	
	/**
	 * Creates an instance of the learning algorithm to test.
	 */
	protected BlockwiseLeftAlignedLearningAlgorithm createLearningAlgorithm() {
		// use custom decimal format to match proof-of-concept implementation
		return new BlockwiseLeftAlignedLearningAlgorithm("0.############");
	}
	
	/**
	 * Reads and loads the {@link TraceDataset} file that is saved at the 
	 * given location.
	 * 
	 * The filename is relative to the "res/blockwise" folder of the test bundle.
	 */
	private static TraceDataset getTraceDataset(String filename) {
		File file = new File("res/blockwise/" + filename);
		TraceDataset d = new JsonTraceDatasetParser().parseTraceDatasetFromFile(file);
		if (null == d) {
			throw new AssertionFailedError("Failed to load trace data set file " + filename);
		}
		return d;
	}
}
