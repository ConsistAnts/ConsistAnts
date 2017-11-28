package de.hu_berlin.cchecker.core.models.traces.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.traces.OrderedTransition;
import de.hu_berlin.cchecker.core.models.traces.SimpleTraceDatasetParser;
import de.hu_berlin.cchecker.core.models.traces.Trace;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

public class SimpleTraceDatasetParserTest {
	
	@Test
	public void shouldTestThatSimpleDatasetCanBeParsed() {
		assertNotNull("Simple trace data set is parsed correctly", TraceTestUtils.getSimpleTraceDataset());
	}

	@Test
	public void shouldTestThatSimpleTraceDatasetIsParsedCorrectly() {
		File simpleTestFile = new File("res/tracedataset_simple.trc");

		Map<Integer, String> transitionIdToLabel = new HashMap<>();
		transitionIdToLabel.put(1, "start");
		transitionIdToLabel.put(2, "toll");
		transitionIdToLabel.put(3, "lost");
		transitionIdToLabel.put(4, "won");
		
		List<OrderedTransition> trace1transitions = new ArrayList<>();
		trace1transitions.add(new OrderedTransition(1, 1));
		trace1transitions.add(new OrderedTransition(2, 2));
		trace1transitions.add(new OrderedTransition(2, 3));
		trace1transitions.add(new OrderedTransition(2, 4));
		trace1transitions.add(new OrderedTransition(2, 5));
		trace1transitions.add(new OrderedTransition(2, 6));
		trace1transitions.add(new OrderedTransition(2, 7));
		trace1transitions.add(new OrderedTransition(3, 8));
		Trace trace = new Trace("1", trace1transitions);
		
		TraceDataset dataset = new SimpleTraceDatasetParser().parseTraceDatasetFromFile(simpleTestFile);
		Map<Integer, String> parsedTransitionIdToLabel = dataset.getTransitionIdToLabel();
		List<Trace> parsedTraces = dataset.getTraces();
		assertEquals("The parsed transitions should be equal to the expected ones!", transitionIdToLabel, parsedTransitionIdToLabel);
		assertEquals("The parsed traces should be equal to the expected ones!", trace, parsedTraces.get(0));
	}

	@Test
	public void shouldTestThatSimpleTraceDatasetWithIdsIsParsedCorrectly() {
		File simpleTestFile = new File("res/tracedataset_simple_with_ids.trc");

		Map<Integer, String> transitionIdToLabel = new HashMap<>();
		transitionIdToLabel.put(1, "start");
		transitionIdToLabel.put(2, "toll");
		transitionIdToLabel.put(3, "lost");
		
		List<OrderedTransition> trace1transitions = new ArrayList<>();
		trace1transitions.add(new OrderedTransition(1, 1));
		trace1transitions.add(new OrderedTransition(2, 2));
		trace1transitions.add(new OrderedTransition(2, 3));
		trace1transitions.add(new OrderedTransition(2, 4));
		trace1transitions.add(new OrderedTransition(2, 5));
		Trace trace = new Trace("9:17:48", trace1transitions);
		
		TraceDataset dataset = new SimpleTraceDatasetParser().parseTraceDatasetFromFile(simpleTestFile);
		Map<Integer, String> parsedTransitionIdToLabel = dataset.getTransitionIdToLabel();
		List<Trace> parsedTraces = dataset.getTraces();
		assertEquals("The parsed transitions should be equal to the expected ones!", transitionIdToLabel, parsedTransitionIdToLabel);
		assertEquals("The parsed traces should be equal to the expected ones!", trace, parsedTraces.get(0));
	}

	@Test
	public void shouldTestThatSimpleTraceDatasetWithAlphabetMappingIsParsedCorrectly() {
		File simpleTestFile = new File("res/tracedataset_simple_with_alphabet_mapping.trc");

		Map<Integer, String> transitionIdToLabel = new HashMap<>();
		transitionIdToLabel.put(1, "start");
		transitionIdToLabel.put(2, "toll");
		transitionIdToLabel.put(3, "lost");
		
		List<OrderedTransition> trace1transitions = new ArrayList<>();
		trace1transitions.add(new OrderedTransition(1, 1));
		trace1transitions.add(new OrderedTransition(2, 2));
		trace1transitions.add(new OrderedTransition(2, 3));
		trace1transitions.add(new OrderedTransition(2, 4));
		trace1transitions.add(new OrderedTransition(2, 5));
		Trace trace = new Trace("1", trace1transitions);
		
		TraceDataset dataset = new SimpleTraceDatasetParser().parseTraceDatasetFromFile(simpleTestFile);
		Map<Integer, String> parsedTransitionIdToLabel = dataset.getTransitionIdToLabel();
		List<Trace> parsedTraces = dataset.getTraces();
		assertEquals("The parsed transitions should be equal to the expected ones!", transitionIdToLabel, parsedTransitionIdToLabel);
		assertEquals("The parsed traces should be equal to the expected ones!", trace, parsedTraces.get(0));
	}

	@Test
	public void shouldTestThatSimpleTraceDatasetWithIdsAndAlphabetMappingIsParsedCorrectly() {
		File simpleTestFile = new File("res/tracedataset_simple_with_ids_and_alphabet_mapping.trc");

		Map<Integer, String> transitionIdToLabel = new HashMap<>();
		transitionIdToLabel.put(1, "start");
		transitionIdToLabel.put(2, "toll");
		transitionIdToLabel.put(3, "lost");
		
		List<OrderedTransition> trace1transitions = new ArrayList<>();
		trace1transitions.add(new OrderedTransition(1, 1));
		trace1transitions.add(new OrderedTransition(2, 2));
		trace1transitions.add(new OrderedTransition(2, 3));
		trace1transitions.add(new OrderedTransition(2, 4));
		trace1transitions.add(new OrderedTransition(2, 5));
		Trace trace = new Trace("a", trace1transitions);
		
		TraceDataset dataset = new SimpleTraceDatasetParser().parseTraceDatasetFromFile(simpleTestFile);
		Map<Integer, String> parsedTransitionIdToLabel = dataset.getTransitionIdToLabel();
		List<Trace> parsedTraces = dataset.getTraces();
		assertEquals("The parsed transitions should be equal to the expected ones!", transitionIdToLabel, parsedTransitionIdToLabel);
		assertEquals("The parsed traces should be equal to the expected ones!", trace, parsedTraces.get(0));
	}
	
}
