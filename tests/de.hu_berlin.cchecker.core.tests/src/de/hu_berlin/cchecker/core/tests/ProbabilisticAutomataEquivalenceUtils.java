package de.hu_berlin.cchecker.core.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDataGeneratorUtils;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDataProbabilityComputer;

/**
 * Utility class to assert language equivalence between given
 * {@link ProbabilisticAutomata}.
 */
public class ProbabilisticAutomataEquivalenceUtils {

	public final static int DEFAULT_TRACE_LENGTH = 10;
	public final static int DEFAULT_PROBABILISTIC_PRECISION = 10;

	/**
	 * Asserts the two given {@link ProbabilisticAutomata} to be equivalent in the
	 * probabilistic language that they accept.
	 * 
	 * All traces of maximum length of
	 * {@link ProbabilisticAutomataEquivalenceUtils#DEFAULT_TRACE_LENGTH} are
	 * considered for the comparison.
	 * 
	 * For the test to pass the trace probabilities must be equal up to a precision
	 * of
	 * {@link ProbabilisticAutomataEquivalenceUtils#DEFAULT_PROBABILISTIC_PRECISION}
	 * digits.
	 */
	public static void assertEqualProbabilisticLanguage(ProbabilisticAutomata a1, ProbabilisticAutomata a2) {
		assertEqualProbabilisticLanguage(a1, a2, DEFAULT_TRACE_LENGTH);
		assertEqualProbabilisticLanguage(a2, a1, DEFAULT_TRACE_LENGTH);
	}

	/**
	 * Asserts the two given {@link ProbabilisticAutomata} to be equivalent in the
	 * probabilistic language that they accept.
	 * 
	 * For the test to pass the trace probabilities must be equal up to a precision
	 * of
	 * {@link ProbabilisticAutomataEquivalenceUtils#DEFAULT_PROBABILISTIC_PRECISION}
	 * digits.
	 *
	 * @param traceLength
	 *            All traces up to this length are considered for the comparison.
	 */
	public static void assertEqualProbabilisticLanguage(ProbabilisticAutomata a1, ProbabilisticAutomata a2,
			int traceLength) {
		List<String[]> traces = TraceDataGeneratorUtils.generateTraces(a1, traceLength);

		List<Double> probInA1 = TraceDataProbabilityComputer.getProbabilitiesOfTracesInAutomaton(a1, traces);
		List<Double> probInA2 = TraceDataProbabilityComputer.getProbabilitiesOfTracesInAutomaton(a2, traces);

		final int precision = 10;
		Function<Double, Double> toFixed = n -> Math.round(n * Math.pow(10, precision)) / Math.pow(10, precision);

		StringBuilder summaryA1 = new StringBuilder();
		for (int i = 0; i < traces.size(); i++) {
			String[] trace = traces.get(i);
			Double probability = probInA1.get(i);
			summaryA1.append("P(" + Arrays.toString(trace) + ") = " + toFixed.apply(probability) + "\n");
		}
		StringBuilder summaryA2 = new StringBuilder();
		for (int i = 0; i < traces.size(); i++) {
			String[] trace = traces.get(i);
			Double probability = probInA2.get(i);
			summaryA2.append("P(" + Arrays.toString(trace) + ") = " + toFixed.apply(probability) + "\n");
		}

		assertEquals("The Probabilistic Automata A1 and A2 match in the language they accept", summaryA1.toString(),
				summaryA2.toString());
	}
	
	/**
	 * Creates a new string trace array by appending the given suffix.
	 */
	static String[] appendSuffix(String[] trace, String suffix) {
		String[] newTrace = Arrays.copyOf(trace, trace.length + 1);
		newTrace[trace.length] = suffix;
		return newTrace;
	}
	
	/**
	 * Asserts that the probability of all traces accepted by a1 is the same as the probability 
	 * of each of those traces suffixed by suffix, in a2.
	 * 
	 * <code>
	 * Example:
	 * 	Let suffix be 'A'.
	 * 	 If a1 accepts trace "a b c d" then it must hold true that:
	 * 
	 * 	 P_a1(a b c d) = 0.4 <=> P_a2(a b c d A) = 0.4 
	 * </code>
	 * 
	 *  
	 * @param a1 The first automaton
	 * @param a2 The second (suffix-) automaton
	 * @param traceLength The maximum trace length
	 * @param suffix The suffix to append
	 */
	public static void assertEqualProbabilisticLanguageWithSuffix(ProbabilisticAutomata a1, ProbabilisticAutomata a2, int traceLength, String suffix) {
		List<String[]> traces = buildTraces(a1, traceLength);
		List<String[]> suffixedTraces = traces.stream().map(t -> appendSuffix(t, suffix)).collect(Collectors.toList());
		
		List<Double> probInA1 = getProbabilitiesOfTracesInAutomaton(a1, traces);
		List<Double> probInA2 = getProbabilitiesOfTracesInAutomaton(a2, suffixedTraces);

		final int precision = 10;
		Function<Double, Double> toFixed = n -> Math.round(n * Math.pow(10, precision)) / Math.pow(10, precision);

		StringBuilder summaryA1 = new StringBuilder();
		for (int i = 0; i < traces.size(); i++) {
			String[] trace = traces.get(i);
			Double probability = probInA1.get(i);
			summaryA1.append("P(" + Arrays.toString(trace) + " [" + suffix + "]" + ") = " + toFixed.apply(probability) + "\n");
		}
		StringBuilder summaryA2 = new StringBuilder();
		for (int i = 0; i < traces.size(); i++) {
			String[] trace = traces.get(i);
			Double probability = probInA2.get(i);
			summaryA2.append("P(" + Arrays.toString(trace) + " [" + suffix + "]" + ") = " + toFixed.apply(probability) + "\n");
		}

		assertEquals("The Probabilistic Automata A1 and A2 match in the suffixed language they accept", summaryA1.toString(),
				summaryA2.toString());
	}

	static String tracesToString(List<String[]> traces) {
		StringBuilder builder = new StringBuilder();
		for (String[] trace : traces) {
			builder.append(Arrays.toString(trace) + "\n");
		}
		return builder.toString();
	}

	static List<String[]> buildTraces(ProbabilisticAutomata automaton, int traceLengthLimit) {
		List<Integer[]> numericTraces = buildTraces(automaton.getStartState(), new Integer[] {}, traceLengthLimit);

		Map<Integer, String> mapping = automaton.getTransitionLabels()
			.map();

		return numericTraces.stream()
			.map(trace -> {
				List<String> stringTrace = new ArrayList<String>();
				for (int i = 0; i < trace.length; i++) {
					String stringLabel = mapping.get(Integer.valueOf(trace[i]));
					if (!stringLabel.isEmpty()) {
						stringTrace.add(stringLabel);
					}
				}
				return stringTrace.toArray(new String[stringTrace.size()]);
			})
			.collect(Collectors.toList());
	}

	static List<Integer[]> buildTraces(ProbabilisticState s, Integer[] prefix, int traceLengthLimit) {
		List<Integer[]> traces = new ArrayList<>();

		if (prefix.length < traceLengthLimit) {
			for (ProbabilisticTransition transition : s.getOutgoingTransitions()) {
				Integer[] newPrefix = Arrays.copyOf(prefix, prefix.length + 1);
				newPrefix[prefix.length] = transition.getTransitionId();

				traces.addAll(buildTraces(transition.getTarget(), newPrefix, traceLengthLimit));
			}
		}

		if (s.getTerminatingProbability() > 0) {
			traces.add(prefix);
		}

		return traces;
	}

	static List<Double> getProbabilitiesOfTracesInAutomaton(ProbabilisticAutomata a, List<String[]> traces) {
		List<Integer[]> numericTraces = new ArrayList<>();

		BiMap<String, Integer> labelToIdMap = HashBiMap.create(a.getTransitionLabels()
			.map())
			.inverse();

		traces.forEach(trace -> {
			Integer[] numericTrace = new Integer[trace.length];
			for (int i = 0; i < trace.length; i++) {
				String stringLabel = trace[i];
				Integer integerId = labelToIdMap.get(stringLabel);
				if (integerId != null) {
					numericTrace[i] = integerId;
				} else {
					throw new IndexOutOfBoundsException(
							"Failed to re-map string label " + stringLabel + " in automaton " + a);
				}
			}
			numericTraces.add(numericTrace);
		});

		return numericTraces.stream()
			.map(trace -> getProbabilityOfTraceInAutomaton(a, trace))
			.collect(Collectors.toList());
	}

	static int getEpsilonTransitionId(ProbabilisticAutomata a) {
		Integer epsId = HashBiMap.create(a.getTransitionLabels()
			.map())
			.inverse()
			.get("");
		return epsId != null ? epsId : -1;
	}

	static double getProbabilityOfTraceInAutomaton(ProbabilisticAutomata a, Integer[] trace) {
		return simulateTrace(a.getStartState(), trace, 0, getEpsilonTransitionId(a));
	}

	static double simulateTrace(ProbabilisticState state, Integer[] trace, int traceIndex, int epsilonId) {
		if (traceIndex >= trace.length) {
			// We're at the end of our trace, just make sure to follow all remaining
			// epsilon-transitions
			List<ProbabilisticTransition> possibleTransitions = state.getOutgoingTransitions()
				.stream()
				.filter(t -> t.getTransitionId() == epsilonId)
				.collect(Collectors.toList());

			double accumulatedProbability = 0.0;

			for (ProbabilisticTransition t : possibleTransitions) {
				boolean isEpsilonTransition = t.getTransitionId() == epsilonId;
				// only advance in trace if this is not an epsilon-transition
				accumulatedProbability += t.getProbability()
						* simulateTrace(t.getTarget(), trace, traceIndex + (isEpsilonTransition ? 0 : 1), epsilonId);
			}

			return accumulatedProbability + state.getTerminatingProbability();
		} else {
			int currentId = trace[traceIndex];
			List<ProbabilisticTransition> possibleTransitions = state.getOutgoingTransitions()
				.stream()
				.filter(t -> t.getTransitionId() == currentId || t.getTransitionId() == epsilonId)
				.collect(Collectors.toList());

			double accumulatedProbability = 0.0;

			for (ProbabilisticTransition t : possibleTransitions) {
				boolean isEpsilonTransition = t.getTransitionId() == epsilonId;
				// only advance in trace if this is not an epsilon-transition
				accumulatedProbability += t.getProbability()
						* simulateTrace(t.getTarget(), trace, traceIndex + (isEpsilonTransition ? 0 : 1), epsilonId);
			}

			return accumulatedProbability;
		}

	}

	private ProbabilisticAutomataEquivalenceUtils() {
		// non-instantiable utility class
	}
}
