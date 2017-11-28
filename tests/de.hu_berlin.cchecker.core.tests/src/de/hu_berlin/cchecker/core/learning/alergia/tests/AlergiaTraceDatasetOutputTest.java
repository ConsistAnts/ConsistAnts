package de.hu_berlin.cchecker.core.learning.alergia.tests;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.learning.alergia.Alergia;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.tests.ProbabilisticAutomataTestUtils;
import de.hu_berlin.cchecker.core.models.traces.TraceDataSetProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;

/**
 * Alergia output tests that use {@link TraceDataset} based input data.
 */
@RunWith(JUnit4.class)
public class AlergiaTraceDatasetOutputTest extends AbstractAlergiaDataTest {
	/**
	 * Tests that the results are the same when using the {@link TraceDataset} model 
	 * and when using raw data. 
	 */
	@Test
	public void testAlergiaTraceDatasetEqualsRawData() {
		TraceDataset task1TraceDataset = TraceTestUtils.getTask1TraceDataset();
		Alergia a = new Alergia(new TraceDataSetProvider());
		
		ProbabilisticAutomata learnedModel = a.learnModel(task1TraceDataset);
		
		ProbabilisticAutomata rawDataResult = runAlergiaOnRawData(new Integer[][]{ {1, 2, 2, 2, 2}, {1, 2, 2, 2, 2}, {1, 2, 2, 2, 2, 2, 2, 2}, {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, {1, 2, 2, 2, 2, 2, 2, 4}, {1, 2, 2, 2, 4, 4}, {1, 2, 2, 4}, {1, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4},
			{1, 2, 4}, {1, 2, 4}, {1, 2, 4}, {1, 2, 4, 4}, {1, 2, 4, 4, 4, 4, 4, 4, 4}, {1, 2, 4, 4, 4, 4, 4, 4, 4, 4}, {1, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, {1, 2, 3, 3, 3},
			{1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, {1, 3}, {1, 3}, {1, 3, 3, 3, 3, 3}, {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3} },
				0.8);
		
		ProbabilisticAutomataTestUtils.roundProbabilities(rawDataResult, 2);
		ProbabilisticAutomataTestUtils.roundProbabilities(learnedModel, 2);
		
		// Manually set the correct mapping for our raw data result
		rawDataResult.getTransitionLabels().clear();
		rawDataResult.getTransitionLabels().put(1, "start");
		rawDataResult.getTransitionLabels().put(2, "toll");
		rawDataResult.getTransitionLabels().put(3, "won");
		rawDataResult.getTransitionLabels().put(4, "lost");
		
		ProbabilisticAutomataTestUtils.assertEqualAutomata("TraceDataset based Alergia model is equivalent to raw data result",
				rawDataResult, learnedModel);
	}
	
	/**
	 * Tests that the Alergia output for the task 1 data set is equivalent 
	 * to our expectations.
	 */
	@Ignore
	@Test
	public void testAlergiaTraceDataset() {
		TraceDataset task1TraceDataset = TraceTestUtils.getTask1TraceDataset();
		Alergia a = new Alergia(new TraceDataSetProvider());
		
		ProbabilisticAutomata learnedModel = a.learnModel(task1TraceDataset);
		ProbabilisticAutomata expectedModel = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();
		
		ProbabilisticAutomataTestUtils.roundProbabilities(expectedModel, 2);
		ProbabilisticAutomataTestUtils.roundProbabilities(learnedModel, 2);
		
		ProbabilisticAutomataTestUtils.assertEqualAutomata("Learned model is equivalent with the expected model for task 1",
				expectedModel, learnedModel);
	}
}
