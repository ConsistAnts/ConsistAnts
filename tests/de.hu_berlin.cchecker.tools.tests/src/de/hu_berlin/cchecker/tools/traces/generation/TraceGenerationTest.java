package de.hu_berlin.cchecker.tools.traces.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;

/**
 * Tests for the generation of traces that are accepted by automata as
 * well as the computation of their probability.
 */
@RunWith(JUnit4.class)
public class TraceGenerationTest {
	/**
	 * Tests the trace data set generation with a simple automaton.
	 */
	@Test
	public void testSimpleAutomatonSimulation() {
		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
			.fromTo(1, 2, 1.0, "a")
			.fromTo(2, 1, 1.0, "b")
			.terminatesIn(1)
			.terminatesIn(2)
			.startIn(1)
			.create();
		
		final int maxTraceLength = 4;
		final List<String[]> traces = TraceDataGeneratorUtils.generateTraces(a, maxTraceLength);
		
		for (String[] trace : traces) {
			assertTrue("Trace length is less or equal max trace length", trace.length <= maxTraceLength);
		}
		
		assertEquals("Traces of length 10 are complete", "[], [a], [a, b], [a, b, a], [a, b, a, b]", listToString(traces));
	}
	
	/**
	 * Tests the trace data set generation with a simple epsilon-automaton.
	 */
	@Test
	public void testSimpleEpsilonAutomatonSimulation() {
		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
			.fromTo(1, 2, 1.0, "a")
			.fromTo(2, 1, 0.5, "b")
			.fromTo(2, 3, 0.5, "")
			.fromTo(3, 4, 1.0, "c")
			.terminatesIn(4)
			.terminatesIn(2)
			.startIn(1)
			.create();
		
		final int maxTraceLength = 4;
		final List<String[]> traces = TraceDataGeneratorUtils.generateTraces(a, maxTraceLength);
		
		for (String[] trace : traces) {
			assertTrue("Trace length is less or equal max trace length", trace.length <= maxTraceLength);
		}
		
		assertEquals("Traces of length 5 are complete", "[a], [a, c], [a, b, a], [a, b, a, c]", listToString(traces));
	}
	
	/**
	 * Tests the computation of the probability that a trace is accepted by an automaton.
	 */
	@Test
	public void testTraceProbabilityComputation() {
		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
			.fromTo(1, 2, 1.0, 1)
			.fromTo(2, 3, 0.729, 2)
			.fromTo(3, 4, 1.0, 3)
			.fromTo(4, 5, 1 - 0.123, 4)
			.fromTo(2, 10, 1.0 - 0.729, 5)
			.startIn(1)
			.terminatesInWithProbability(4, 0.123)
			.automaticLabelMapping()
			.create();
		
		double p = TraceDataProbabilityComputer.getProbabilityOfTraceInAutomaton(a, 
				new Integer[]{1, 2, 3});
		
		assertEquals("Probability equals product of transitions + termination probability", 
				1.0 * 0.729 * 1.0 * 0.123, p, 0.0);
	}
	
	private static <E> String listToString(List<E[]> list) {
		return list.stream().map(e -> Arrays.toString(e)).collect(Collectors.joining(", "));
	}
}
