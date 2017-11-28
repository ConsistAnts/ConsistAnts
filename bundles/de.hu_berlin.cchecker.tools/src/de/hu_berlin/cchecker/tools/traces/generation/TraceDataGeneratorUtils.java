package de.hu_berlin.cchecker.tools.traces.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDataGenerator.TraceDataIterator;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDataGenerator.TraceGeneratorQueueElement;

/**
 * The {@link TraceDataGeneratorUtils} can be used to get streams of all the traces that
 * are accepted by a given {@link ProbabilisticAutomata}.
 * 
 * Note that these streams might not be finite. Thus, it might be of interest to limit the output
 * to traces of a certain length or by a different criteria.
 */
public class TraceDataGeneratorUtils {
	/**
	 * Generates all (string-) traces of the given maximum length (traceLengthLimit)
	 * that are accepted by the given automaton.
	 */
	public static List<String[]> generateTraces(ProbabilisticAutomata automaton, int traceLengthLimit) {
		Stream<Integer[]> rawTraceStream = traceStream(automaton, traceLengthLimit);
	
		// convert number-encoded raw trace stream to list of string-encoded traces
		Map<Integer, String> mapping = automaton.getTransitionLabels().map();
	
		return rawTraceStream.map(trace -> {
			List<String> stringTrace = new ArrayList<>();
			// add mapped string label for each ID in the trace
			for (int i = 0; i < trace.length; i++) {
				String stringLabel = mapping.get(Integer.valueOf(trace[i]));
				if (!stringLabel.isEmpty()) {
					stringTrace.add(stringLabel);
				}
			}
			// finally return array of Strings instead of ints
			return stringTrace.toArray(new String[stringTrace.size()]);
		}).collect(Collectors.toList());
	}

	/**
	 * Generates all raw traces (number-encoded) of the given maximum length
	 * (traceLengthLimit) that are accepted by the given automaton.
	 */
	public static List<Integer[]> generateRawTraces(ProbabilisticAutomata automaton, int traceLengthLimit) {
		return traceStream(automaton, traceLengthLimit).collect(Collectors.toList());
	}

	/**
	 * Returns a stream of number-encoded traces that can be build using the given
	 * automaton.
	 * 
	 * Depending on the automaton this stream might be infinite.
	 * 
	 * @param automaton
	 *            The automaton to generate traces for
	 */
	public static Stream<Integer[]> traceStream(ProbabilisticAutomata automaton) {
		TraceDataIterator iterator = TraceDataGenerator.TraceDataIteratorBuilder.defaults(automaton).create();
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false).filter(Objects::nonNull);
	}

	/**
	 * Returns a stream of number-encoded traces that can be build using the given
	 * automaton.
	 * 
	 * Depending on the automaton this stream might be infinite.
	 * 
	 * @param automaton
	 *            The automaton to generate traces for
	 */
	public static Stream<Integer[]> traceStream(ProbabilisticAutomata automaton,
			TraceGeneratorQueueProvider queueProvider, Function<TraceGeneratorQueueElement, Boolean> terminationCriteria) {
		TraceDataIterator iterator = TraceDataGenerator.TraceDataIteratorBuilder.defaults(automaton)
			.elementProvider(queueProvider)
			.terminationCriteria(terminationCriteria)
			.create();
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false).filter(Objects::nonNull);
	}

	/**
	 * Returns a stream of number-encoded traces that can be build using the given
	 * automaton.
	 * 
	 * @param automaton
	 *            The automaton to generate traces for
	 * @param lengthLimit
	 *            The maximum length to generate traces for
	 */
	public static Stream<Integer[]> traceStream(ProbabilisticAutomata automaton, int lengthLimit) {
		TraceDataGenerator.TraceDataIterator iterator = TraceDataGenerator.TraceDataIteratorBuilder.defaults(automaton).lengthLimit(lengthLimit).create();
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false).filter(Objects::nonNull);
	}

	private TraceDataGeneratorUtils() {
		// non-instantiable utils-class
	}

}
