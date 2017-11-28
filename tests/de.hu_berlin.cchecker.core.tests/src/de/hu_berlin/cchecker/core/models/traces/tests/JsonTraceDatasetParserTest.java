package de.hu_berlin.cchecker.core.models.traces.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.traces.OrderedTransition;
import de.hu_berlin.cchecker.core.models.traces.Trace;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.JsonTraceDatasetParser;

/**
 * 
 * @author Linus
 *
 */
public class JsonTraceDatasetParserTest {

	
	@Test
	public void shouldTestThatTraceDatasetIsParsedCorrectly() {
		File testFileValid = new File("res/tracedataset_valid.json");

		Map<Integer, String> transitionIdToLabel = new HashMap<>();
		transitionIdToLabel.put(1, "A_TO_B");
		transitionIdToLabel.put(2, "B_TO_A");
		transitionIdToLabel.put(3, "B_TO_C");
		transitionIdToLabel.put(4, "C_TO_B");
		
		List<OrderedTransition> trace1transitions = new ArrayList<>();
		trace1transitions.add(new OrderedTransition(1, 1));
		trace1transitions.add(new OrderedTransition(3, 2));
		trace1transitions.add(new OrderedTransition(4, 3));
		trace1transitions.add(new OrderedTransition(2, 4));
		List<Trace> traces = new ArrayList<>();
		traces.add(new Trace("1", trace1transitions));
		
		TraceDataset dataset = new JsonTraceDatasetParser().parseTraceDatasetFromFile(testFileValid);
		Map<Integer, String> parsedTransitionIdToLabel = dataset.getTransitionIdToLabel();
		List<Trace> parsedTraces = dataset.getTraces();
		assertEquals("The parsed transitions should be equal to the expected ones!", transitionIdToLabel, parsedTransitionIdToLabel);
		assertEquals("The parsed traces should be equal to the expected ones!", traces, parsedTraces);
	}
	
	@Test
	public void shouldThrowNoSuchFileExceptionIfFileDoesNotExist() {
		File testFileValid = new File("thisfiledoesnotexist.doesnotexist");
		TraceDataset dataset = new JsonTraceDatasetParser().parseTraceDatasetFromFile(testFileValid);
		assertEquals("The parsed dataset should be null if the file does not exist!", null, dataset);
	}
	
	@Test
	public void shouldTestThatNullIsReturnedIfFileIsNotValid() {
		File testFileInValid = new File("res/tracedataset_invalid.json");
		assertTrue("The invalid test file should exist!", testFileInValid.exists());
		TraceDataset dataset = new JsonTraceDatasetParser().parseTraceDatasetFromFile(testFileInValid);
		assertEquals("The parsed dataset should be null if the file is invalid!", null, dataset);
	}
	
	@Test
	public void shouldTestThatTask1DatasetCanBeParsed() {
		assertNotNull("Task 1 trace data set is parsed correctly", TraceTestUtils.getTask1TraceDataset());
	}
}

