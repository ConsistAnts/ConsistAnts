package de.hu_berlin.cchecker.core.checking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.traces.TraceDataProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceDataBuilder;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;

@RunWith(JUnit4.class)
public class ConsistencyCheckingTraceMatrixTest extends AbstractFootprintMatrixTest {
	
	/**
	 * Tests the footprint matrices generated for the task 1 sample data set.
	 */
	@Test
	public void testTask1WithTraces() {
		TraceDataset task1Traces = TraceTestUtils.getTask1TraceDataset();
		
		Map<Integer, String> mapping = task1Traces.getTransitionIdToLabel();
		Map<Integer, Integer> matrixIndices = FootprintMatrixCheckingAlgorithm.buildMatrixIndicesMap(mapping);
		
		TraceFootprintMatrixGenerator generator = new TraceFootprintMatrixGenerator(matrixIndices);
		
		// Create the trace matrices
		List<FPMatrix> traceMatrices = generator.generate(task1Traces);
		
		// Make sure the number of output matrices matches the expectations
		assertEquals("The number of trace matrices is as expected", 29, traceMatrices.size());
		
		StringBuilder actual = new StringBuilder();
		
		for (FPMatrix traceMatrix : traceMatrices) {
			actual.append(traceMatrix.toString());
		}
		
		assertEquals("Trace matrix generation yields expected results", "length=2\n" + 
				"numberOfTraces=21\n" + 
				"matrix=\n" + 
				"[0.0, 17.0, 4.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=3\n" + 
				"numberOfTraces=19\n" + 
				"matrix=\n" + 
				"[0.0, 17.0, 2.0, 0.0]\n" + 
				"[0.0, 8.0, 2.0, 7.0]\n" + 
				"[0.0, 0.0, 2.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=4\n" + 
				"numberOfTraces=16\n" + 
				"matrix=\n" + 
				"[0.0, 14.0, 2.0, 0.0]\n" + 
				"[0.0, 14.0, 2.0, 6.0]\n" + 
				"[0.0, 0.0, 6.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 4.0]\n" + 
				"length=5\n" + 
				"numberOfTraces=14\n" + 
				"matrix=\n" + 
				"[0.0, 12.0, 2.0, 0.0]\n" + 
				"[0.0, 18.0, 2.0, 5.0]\n" + 
				"[0.0, 0.0, 10.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 7.0]\n" + 
				"length=6\n" + 
				"numberOfTraces=11\n" + 
				"matrix=\n" + 
				"[0.0, 9.0, 2.0, 0.0]\n" + 
				"[0.0, 15.0, 1.0, 5.0]\n" + 
				"[0.0, 0.0, 11.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 12.0]\n" + 
				"length=7\n" + 
				"numberOfTraces=9\n" + 
				"matrix=\n" + 
				"[0.0, 8.0, 1.0, 0.0]\n" + 
				"[0.0, 16.0, 1.0, 4.0]\n" + 
				"[0.0, 0.0, 9.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 15.0]\n" + 
				"length=8\n" + 
				"numberOfTraces=9\n" + 
				"matrix=\n" + 
				"[0.0, 8.0, 1.0, 0.0]\n" + 
				"[0.0, 18.0, 1.0, 5.0]\n" + 
				"[0.0, 0.0, 11.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 19.0]\n" + 
				"length=9\n" + 
				"numberOfTraces=7\n" + 
				"matrix=\n" + 
				"[0.0, 6.0, 1.0, 0.0]\n" + 
				"[0.0, 8.0, 1.0, 4.0]\n" + 
				"[0.0, 0.0, 13.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 23.0]\n" + 
				"length=10\n" + 
				"numberOfTraces=6\n" + 
				"matrix=\n" + 
				"[0.0, 5.0, 1.0, 0.0]\n" + 
				"[0.0, 9.0, 1.0, 3.0]\n" + 
				"[0.0, 0.0, 15.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 20.0]\n" + 
				"length=11\n" + 
				"numberOfTraces=5\n" + 
				"matrix=\n" + 
				"[0.0, 4.0, 1.0, 0.0]\n" + 
				"[0.0, 10.0, 1.0, 2.0]\n" + 
				"[0.0, 0.0, 17.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 15.0]\n" + 
				"length=12\n" + 
				"numberOfTraces=4\n" + 
				"matrix=\n" + 
				"[0.0, 3.0, 1.0, 0.0]\n" + 
				"[0.0, 10.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 19.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 9.0]\n" + 
				"length=13\n" + 
				"numberOfTraces=4\n" + 
				"matrix=\n" + 
				"[0.0, 3.0, 1.0, 0.0]\n" + 
				"[0.0, 11.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 21.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 10.0]\n" + 
				"length=14\n" + 
				"numberOfTraces=3\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 23.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 11.0]\n" + 
				"length=15\n" + 
				"numberOfTraces=3\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 25.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 12.0]\n" + 
				"length=16\n" + 
				"numberOfTraces=3\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 27.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 13.0]\n" + 
				"length=17\n" + 
				"numberOfTraces=2\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 14.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 14.0]\n" + 
				"length=18\n" + 
				"numberOfTraces=2\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 15.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 15.0]\n" + 
				"length=19\n" + 
				"numberOfTraces=2\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 16.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 16.0]\n" + 
				"length=20\n" + 
				"numberOfTraces=2\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 17.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 17.0]\n" + 
				"length=21\n" + 
				"numberOfTraces=2\n" + 
				"matrix=\n" + 
				"[0.0, 2.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 1.0]\n" + 
				"[0.0, 0.0, 18.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 18.0]\n" + 
				"length=22\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 19.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=23\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 20.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=24\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 21.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=25\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 22.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=26\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 23.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=27\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 24.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=28\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 25.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=29\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 26.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n" + 
				"length=30\n" + 
				"numberOfTraces=1\n" + 
				"matrix=\n" + 
				"[0.0, 1.0, 0.0, 0.0]\n" + 
				"[0.0, 0.0, 1.0, 0.0]\n" + 
				"[0.0, 0.0, 27.0, 0.0]\n" + 
				"[0.0, 0.0, 0.0, 0.0]\n", actual.toString());
	}
	
	/**
	 * Tests generation of footprint matrices for traces with only one character of the alphabet for each trace.
	 * 
	 * No footprint matrix should be generated by the algorithm in this scenario.
	 */
	@Test
	public void testOneCharacterTraces() {
		ArrayList<String> alphabet = new ArrayList<>();
		alphabet.add("a"); // 0
		alphabet.add("b"); // 1
		alphabet.add("22"); // 2
		alphabet.add("qwertzuiopab"); // 3
		
		// synthesise mapping from alphabet
		Map<Integer, String> mapping = IntStream.range(0, alphabet.size()).boxed().collect(Collectors.toMap(i -> i, i -> alphabet.get(i)));
		
		// create one-character data set
		TraceDataProvider provider = TraceDataBuilder.newTraceData()
			.trace(0).trace(1).trace(2).trace(3)
			.labelMapping(mapping)
			.provider();
		
		Map<Integer, Integer> matrixIndices = FootprintMatrixCheckingAlgorithm.buildMatrixIndicesMap(mapping);
		TraceFootprintMatrixGenerator generator = new TraceFootprintMatrixGenerator(matrixIndices);
		
		generator.setDataProvider(provider);
		
		List<FPMatrix> traceMatrices = generator.generate(TraceDataset.EMPTY_DATASET);
		assertEquals("No trace matrices have been generated", traceMatrices.size(), 0);
	}
	/**
	 * Tests the footprint matrix generation algorithm with empty traces as an input.
	 * 
	 * No footprint matrix should be generated, so the list of them should be empty.
	 */
	@Test
	public void testEmptyTraceSet() {
		TraceDataset emptyTraces = new TraceDataset();
		Map<Integer, String> emptyMapping = new HashMap<>();
		emptyTraces.setTransitionIdToLabel(emptyMapping);
		
		Map<Integer, Integer> matrixIndices = FootprintMatrixCheckingAlgorithm.buildMatrixIndicesMap(emptyMapping);
		TraceFootprintMatrixGenerator generator = new TraceFootprintMatrixGenerator(matrixIndices);
		
		List<FPMatrix> traceMatrices = generator.generate(emptyTraces);
		
		assertEquals("No trace matrices are returned", 0, traceMatrices.size());
	}
	
	/*@Test
	public void testCustomAutomaton() {
		BestAlgorithm algorithm = new BestAlgorithm();
		// Create a simple automata with the builder
		ProbabilisticAutomata builderAutomata = ProbabilisticAutomataBuilder.newAutomata()
				.fromTo(1, 2, 0.3, "a")
				.fromTo(1, 3, 0.7, "b")
				.fromTo(2, 4, 0.4, "c")
				.fromTo(2, 5, 0.6, "d")
				.fromTo(3, 6, 0.2, "e")
				.fromTo(3, 7, 0.8, "f")
				.fromTo(4, 1, 1.0, "w")
				.fromTo(5, 1, 1.0, "x")
				.fromTo(6, 1, 1.0, "y")
				.fromTo(7, 1, 1.0, "z")
				.startIn(1)
				.create();
			
		TraceDataset task1Traces = TraceTestUtils.getTask1TraceDataset();
		ArrayList<String> alphabet = new ArrayList<>(task1Traces.getTransitionIdToLabel().values());
		List<String[]> testTraces = algorithm.getStringFromTraceDataset(task1Traces, alphabet);
		
		List<Event> events = BestAlgorithm.eventGen(alphabet);
		
		List<FPMatrix> tracematrices = algorithm.createFPMatricesTrace(testTraces, alphabet, events);
		
		List<Integer> lengths = new LinkedList<Integer>();
		for (int i = 0; i < tracematrices.size(); i++) {
			lengths.add(tracematrices.get(i).getkkkLength());
		}
		
		List<FPMatrix> automatrices = BestAlgorithm.createFPMatricesAuto(builderAutomata, lengths, alphabet, events);
		// Adapt to number of traces
		for (int h = 0; h < automatrices.size(); h++) {
			int dim = automatrices.get(h).getDimension();
			for (int i = 0; i < dim; i++) {
				for (int k = 0; k < dim; k++) {
					double value = automatrices.get(h).getMEle(i, k);
					value = value * tracematrices.get(h).getTraceCount();
					automatrices.get(h).setMEle(i, k, value);
				}
			}
		}
	System.out.println(automatrices);
	}*/
}