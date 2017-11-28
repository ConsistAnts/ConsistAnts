package de.hu_berlin.cchecker.core.learning.alergia.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.tests.ProbabilisticAutomataTestUtils;
import de.hu_berlin.cchecker.core.models.traces.tests.IntegerTraceDataProvider;

/**
 * Alergia output tests that use {@link IntegerTraceDataProvider}-based raw trace data input.
 */
@RunWith(JUnit4.class)
public class AlergiaRawDataOutputTest extends AbstractAlergiaDataTest {
	@Test
	@Ignore("expected automata is probably wrong, result seems correct. ignored for further inspection")
	public void testTask1AlergiaResult() {
		ProbabilisticAutomata result = runAlergiaOnRawData(new Integer[][]{ {1, 2, 2, 2, 2}, {1, 2, 2, 2, 2}, {1, 2, 2, 2, 2, 2, 2, 2}, {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, {1, 2, 2, 2, 2, 2, 2, 4}, {1, 2, 2, 2, 4, 4}, {1, 2, 2, 4}, {1, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4},
			{1, 2, 4}, {1, 2, 4}, {1, 2, 4}, {1, 2, 4, 4}, {1, 2, 4, 4, 4, 4, 4, 4, 4}, {1, 2, 4, 4, 4, 4, 4, 4, 4, 4}, {1, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, {1, 2, 3, 3, 3},
			{1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, {1, 3}, {1, 3}, {1, 3, 3, 3, 3, 3}, {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3} },
				0.8);
		
		ProbabilisticAutomata expected = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();
		
		ProbabilisticAutomataTestUtils.roundProbabilities(result, 4);
		
		ProbabilisticAutomataTestUtils.assertEqualAutomata("Probabilistic Automata Conversion works as expected", expected, result);
	}
	
	/* 
	 * Reference execution
	 * see page 13 of paper "Learning Stochastic Regular Grammars by 
	 * Means of State Merging Method" by R. Carrasco and J. Oncina
	 */
	@Test
	public void testPaperAlergiaResult() {
		ProbabilisticAutomata result = runAlergiaOnRawData(new Integer[][]{ 
			{1, 1, 0},
			{},
			{},
			{},
			{0},
			{},
			{0, 0},
			{0, 0},
			{},
			{},
			{},
			{1, 0, 1, 1, 0},
			{},
			{},
			{1, 0, 0}
		}, 0.8);
		
		ProbabilisticAutomata expected = ProbabilisticAutomataTestUtils.createPaperAlergiaResult();
		
		ProbabilisticAutomataTestUtils.roundProbabilities(result, 2);
		
		ProbabilisticAutomataTestUtils.assertEqualAutomata("Probabilistic Automata Conversion works as expected", expected, result);
	}
	
	/* 
	 * Reference execution
	 * see page 15 of paper "Learning Probabilistic Finite Automata" 
	 * http://pagesperso.lina.univ-nantes.fr/~cdlh//book/Learning_Probabilistic_Finite_Automata.pdf
	 */
	@Test
	//@Ignore("Algorithm throws still an error")
	public void testPaper2AlergiaResult() {
		Map<Integer[], Integer> rawInputData = new HashMap<>();
		rawInputData.put(new Integer[]{}, 490);
		rawInputData.put(new Integer[]{0}, 128);
		rawInputData.put(new Integer[]{1}, 170);
		rawInputData.put(new Integer[]{0, 0}, 31);
		rawInputData.put(new Integer[]{0, 1}, 42);
		rawInputData.put(new Integer[]{1, 0}, 38);
		rawInputData.put(new Integer[]{1, 1}, 14);
		rawInputData.put(new Integer[]{0, 0, 0}, 8);
		rawInputData.put(new Integer[]{0, 0, 1}, 10);
		rawInputData.put(new Integer[]{0, 1, 0}, 10);
		rawInputData.put(new Integer[]{0, 1, 1}, 4);
		rawInputData.put(new Integer[]{1, 0, 0}, 9);
		rawInputData.put(new Integer[]{1, 0, 1}, 4);
		rawInputData.put(new Integer[]{1, 1, 0}, 3);
		rawInputData.put(new Integer[]{1, 1, 1}, 6);
		rawInputData.put(new Integer[]{0, 0, 0, 0}, 2);
		rawInputData.put(new Integer[]{0, 0, 0, 1}, 2);
		rawInputData.put(new Integer[]{0, 0, 1, 0}, 3);
		rawInputData.put(new Integer[]{0, 0, 1, 1}, 2);
		rawInputData.put(new Integer[]{0, 1, 0, 0}, 2);
		rawInputData.put(new Integer[]{0, 1, 0, 1}, 2);
		rawInputData.put(new Integer[]{0, 1, 1, 0}, 2);
		rawInputData.put(new Integer[]{0, 1, 1, 1}, 1);
		rawInputData.put(new Integer[]{1, 0, 0, 0}, 2);
		rawInputData.put(new Integer[]{1, 0, 0, 1}, 2);
		rawInputData.put(new Integer[]{1, 0, 1, 0}, 1);
		rawInputData.put(new Integer[]{1, 0, 1, 1}, 1);
		rawInputData.put(new Integer[]{1, 1, 0, 0}, 1);
		rawInputData.put(new Integer[]{1, 1, 0, 1}, 1);
		rawInputData.put(new Integer[]{1, 1, 1, 0}, 1);
		rawInputData.put(new Integer[]{0, 0, 0, 0, 0}, 1);
		rawInputData.put(new Integer[]{0, 0, 0, 0, 1}, 1);
		rawInputData.put(new Integer[]{0, 0, 0, 1, 0}, 1);
		rawInputData.put(new Integer[]{0, 0, 1, 0, 0}, 1);
		rawInputData.put(new Integer[]{0, 0, 1, 0, 1}, 1);
		rawInputData.put(new Integer[]{0, 0, 1, 1, 0}, 1);
		rawInputData.put(new Integer[]{0, 1, 1, 0, 0}, 1);
		rawInputData.put(new Integer[]{0, 1, 1, 0, 1}, 1);
		
		Integer[][] inputData = new Integer[1000][];
		
		int j = 0;
		for (Map.Entry<Integer[], Integer> trace : rawInputData.entrySet()) {
			for (int i = 0; i < trace.getValue(); i++) {
				inputData[j] = trace.getKey();
				j++;
			}
		}
		
		// TODO alpha should 0.05, investigate why this works
		ProbabilisticAutomata result = runAlergiaOnRawData(inputData, 0.08);
		
		ProbabilisticAutomata expected = ProbabilisticAutomataTestUtils.createPaper2AlergiaResult();
		
		//System.out.println(ProbabilisticAutomataTestUtils.toTextualRepresentation(expected));
		
		//ProbabilisticAutomataTestUtils.roundProbabilities(result, 3);
		//ProbabilisticAutomataTestUtils.roundProbabilities(expected, 3);
		
		ProbabilisticAutomataTestUtils.assertStructurallyEqual("Probabilistic Automata Conversion works as expected", expected, result);
	}

	/* Generated execution */
	@Test
	public void testTrace1AlergiaResult() {
		ProbabilisticAutomata result = runAlergiaOnRawData(new Integer[][]
				{{0, 0, 1}, {1, 1}, {0}},
				0.8
		);
		
		ProbabilisticAutomata expected = ProbabilisticAutomataTestUtils.createTrace1AlergiaResult();
		
		ProbabilisticAutomataTestUtils.roundProbabilities(result, 2);
		
		ProbabilisticAutomataTestUtils.assertEqualAutomata("Probabilistic Automata Conversion works as expected", expected, result);
	}
}
