package de.hu_berlin.cchecker.core.checking;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.checking.FootprintMatrixCheckingAlgorithm;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;

/**
 * Test suite that tests the {@link FootprintMatrixCheckingAlgorithm} for 
 * discovered bugs and that they are fixed in the current version. 
 */
@RunWith(JUnit4.class)
public class ConsistencyCheckingBugsTest extends AbstractFootprintMatrixTest {
	/**
	 * Tests that the algorithm can handle an island (one state only, no transitions) automaton as input.
	 */
	@Test
	public void testIslandAutomaton() {
		ProbabilisticAutomata islandAutomaton = ProbabilisticAutomataBuilder.newAutomaton().startIn(1).create();
		TraceDataset task1TraceDataset = TraceTestUtils.getTask1TraceDataset();
		
		try {
			FootprintMatrixCheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();
			algorithm.performConsistencyCheck(islandAutomaton, task1TraceDataset, new NullProgressMonitor());
		} catch (RuntimeException e) {
			fail("Algorithm throws exception " + e.toString() + " for island automaton as input");
		}
	}
}
