package de.hu_berlin.cchecker.core.checking;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.CounterExample;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.tests.AbstractProbabilisticAutomataTest;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;

@RunWith(JUnit4.class)
public class ConsistencyCheckingCounterExampleTest extends AbstractProbabilisticAutomataTest {
	public ConsistencyCheckingCounterExampleTest() {
		super("./");
	}

	/**
	 * Tests the complete consistency checking stack using the task 1 sample data
	 * set.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTask1ConformanceMatricesWithTraces() throws IOException {
		FootprintMatrixCheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();

		// Get task 1 alergia automata and rename labels to match trace data set
		// mappings
		ProbabilisticAutomata automaton = loadAutomataFromTestResources("res/counterexamples/simple.pdfa");
		TraceDataset task1Traces = TraceTestUtils.getSimpleTraceDataset("res/counterexamples/simple_counter.strc");

		ConsistencyReport report = algorithm.performConsistencyCheck(automaton, task1Traces, new NullProgressMonitor());

		assertEquals("All counter-example pairs are found",
				"Counter Example with IDs [1] a b|d c, Counter Example with IDs [1] a b d|c",
				counterExamplesToString(report));
	}

	/**
	 * Tests that the (small-dataset) model learned with alergia does not lead to
	 * any counter examples.
	 */
	@Test
	public void testSmallDataSet() throws IOException {
		FootprintMatrixCheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();

		// Check alergia output for small-dataset-1 against small-dataset-1 input
		ProbabilisticAutomata automaton = loadAutomataFromTestResources("res/alergia/output/small-dataset-1.pdfa");
		TraceDataset task1Traces = TraceTestUtils.getJsonTraceDataset("res/alergia/input/small-dataset-1.trc");

		ConsistencyReport report = algorithm.performConsistencyCheck(automaton, task1Traces, new NullProgressMonitor());

		assertEquals("No counter-example pairs are found", "", counterExamplesToString(report));
	}

	/**
	 * Tests that a consistency check with a random automaton 1 with a synthesised sample set for random 
	 * automaton 2 yields counter-examples. This is a feasible assumption, as the sample set is unrelated
	 * to the automaton, thus has an inherently different structure. 
	 */
	@Test
	public void testRandomAutomaton1WithDataset2() throws IOException {
		FootprintMatrixCheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();

		// Check random automaton 1 with random automaton 2 sample set (this should yield counter examples, as
		// the data set is unrelated to the automaton)
		ProbabilisticAutomata automaton = loadAutomataFromTestResources("res/counterexamples/automaton1.pdfa");
		TraceDataset task1Traces = TraceTestUtils.getJsonTraceDataset("res/counterexamples/automaton2.gen.trc");

		ConsistencyReport report = algorithm.performConsistencyCheck(automaton, task1Traces, new NullProgressMonitor());

		assertEquals("All counter-example pairs are found",
				"Counter Example with IDs [19] A B|A,"
				+ " Counter Example with IDs [20] A|C D,"
				+ " Counter Example with IDs [16] B|C,"
				+ " Counter Example with IDs [17] B|F C,"
				+ " Counter Example with IDs [17] B F|C,"
				+ " Counter Example with IDs [15] C B|A,"
				+ " Counter Example with IDs [9] C|C A,"
				+ " Counter Example with IDs [9] C C|A,"
				+ " Counter Example with IDs [12] C|C E,"
				+ " Counter Example with IDs [7] D|A A,"
				+ " Counter Example with IDs [10] D|A E,"
				+ " Counter Example with IDs [4] D|B,"
				+ " Counter Example with IDs [3] D|C,"
				+ " Counter Example with IDs [11] D|C C,"
				+ " Counter Example with IDs [11] D C|C,"
				+ " Counter Example with IDs [5] E|A,"
				+ " Counter Example with IDs [13] F|C B,"
				+ " Counter Example with IDs [14] F|C E", counterExamplesToString(report));
	}
	
	/**
	 * Similar to {@link #testRandomAutomaton1WithDataset2()} but with automaton 2 with dataset 1
	 */
	@Ignore
	@Test
	public void testRandomAutomaton2WithDataset1() throws IOException {
		FootprintMatrixCheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();

		// Check random automaton 1 with random automaton 2 sample set (this should yield counter examples, as
		// the data set is unrelated to the automaton)
		ProbabilisticAutomata automaton = loadAutomataFromTestResources("res/counterexamples/automaton2.pdfa");
		TraceDataset automaton1Traces = TraceTestUtils.getJsonTraceDataset("res/counterexamples/automaton1.gen.trc");

		ConsistencyReport report = algorithm.performConsistencyCheck(automaton, automaton1Traces, new NullProgressMonitor());

		assertEquals("All counter-example pairs are found",
				"", counterExamplesToString(report));
	}
	
	/**
	 * Similar to {@link #testRandomAutomaton1WithDataset2()}, only that the sample set is larger. (automaton2.gen2.trc)
	 */
	@Test
	public void testRandomAutomaton1WithDataset2Large() throws IOException {
		FootprintMatrixCheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();

		// Check random automaton 1 with random automaton 2 sample set (this should yield counter examples, as
		// the data set is unrelated to the automaton)
		ProbabilisticAutomata automaton = loadAutomataFromTestResources("res/counterexamples/automaton1.pdfa");
		TraceDataset task1Traces = TraceTestUtils.getJsonTraceDataset("res/counterexamples/automaton2.gen_large.trc");

		ConsistencyReport report = algorithm.performConsistencyCheck(automaton, task1Traces, new NullProgressMonitor());

		String expectation = new String(Files.readAllBytes(new File("res/counterexamples/automaton1WithDataset2Large.expectation").toPath()));
		assertEquals("All counter-example pairs are found", expectation, counterExamplesToString(report));
	}

	/**
	 * Returns a string representation of all the counter examples in the given
	 * report.
	 */
	private String counterExamplesToString(ConsistencyReport report) {
		Map<Integer, String> mapping = report.getLabelMapping().map();
		List<String> summaries = new ArrayList<>();

		// sort list of examples by string trace and counter example index
		ArrayList<CounterExample> examples = new ArrayList<>(report.getCounterExamples());
		Collections.sort(examples, new Comparator<CounterExample>() {
			@Override
			public int compare(CounterExample o1, CounterExample o2) {
				return Comparator.<CounterExample, String>comparing(o -> o.getTrace().stream().map(n -> mapping.get(n)).collect(Collectors.joining(" ")))
						.thenComparing(o -> o.getIndex())
						.compare(o1, o2);
			}
		});
		
		for (CounterExample counterExample : examples) {
			final int index = counterExample.getIndex();
			// build head and tail of trace
			String head = counterExample.getTrace().subList(0, index + 1).stream().map(mapping::get)
					.collect(Collectors.joining(" "));
			String tail = counterExample.getTrace().subList(index + 1, counterExample.getTrace().size()).stream()
					.map(mapping::get).collect(Collectors.joining(" "));
			// put a separator '|' where the counter-example transition sequence happens
			summaries.add(
					"Counter Example with IDs " + counterExample.getTraceIds().toString() + " " + head + "|" + tail);
		}

		return summaries.stream().collect(Collectors.joining(", "));
	}
}