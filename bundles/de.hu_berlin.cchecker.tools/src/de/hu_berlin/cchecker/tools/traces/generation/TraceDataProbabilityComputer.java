package de.hu_berlin.cchecker.tools.traces.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

/**
 * A {@link TraceDataProbabilityComputer} can be used to determine the probability
 * of a given trace with regard to a {@link ProbabilisticAutomata}.
 * 
 * That is the probability that the trace originates from a system that is modelled by the 
 * given {@link ProbabilisticAutomata}.
 * 
 * You can use {@link #getProbabilitiesOfTracesInAutomaton(ProbabilisticAutomata, List)} for easy access.
 *
 */
public class TraceDataProbabilityComputer {

	/**
	 * Returns a list of probabilities for the given list of traces.
	 * 
	 * The probability of a trace is given by the product of all the probabilities
	 * of the transitions it executes.
	 * 
	 * @param a
	 *            The automaton to simulate the traces in
	 * @param traces
	 *            The traces to simulate
	 * @return
	 */
	public static List<Double> getProbabilitiesOfTracesInAutomaton(ProbabilisticAutomata a, List<String[]> traces) {
		List<Integer[]> numericTraces = new ArrayList<>();
	
		BiMap<String, Integer> labelToIdMap = HashBiMap.create(a.getTransitionLabels().map()).inverse();
	
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
		
		TraceDataProbabilityComputer probabilityComputer = new TraceDataProbabilityComputer(a);
		return numericTraces.stream().map(probabilityComputer::getProbabilityOfTrace).collect(
				Collectors.toList());
	}

	/**
	 * Returns the probability of the given trace being accepted by the given automaton.
	 */
	public static double getProbabilityOfTraceInAutomaton(ProbabilisticAutomata automaton, Integer[] trace) {
		TraceDataProbabilityComputer probabilityComputer = new TraceDataProbabilityComputer(automaton);
		return probabilityComputer.getProbabilityOfTrace(trace);
	}

	// the automaton to work with
	private ProbabilisticAutomata automaton;
	// the transition ID that is mapped to epsilon
	private int epsilonId;

	/**
	 * Instantiates a new {@link TraceDataProbabilityComputer}.
	 */
	public TraceDataProbabilityComputer(ProbabilisticAutomata automaton) {
		this.automaton = automaton;
		this.epsilonId = ProbabilisticAutomataUtils.getEpsilonTransitionId(automaton);
	}
	
	/**
	 * Returns the probability of a given number-encoded trace in the given
	 * automaton.
	 */
	public double getProbabilityOfTrace(Integer[] trace) {
		return simulateTrace(automaton.getStartState(), trace, 0);
	}

	/**
	 * Simulates the sequence of transition executions that is given by a
	 * number-encoded traces starting from the given state.
	 * 
	 * @param state
	 *            The state to start executing in
	 * @param trace
	 *            The trace sequence to execute
	 * @param traceIndex
	 *            The current index in the trace sequence
	 * @param epsilonId
	 *            The transition id of an epsilon-transition
	 * 
	 * @return A dependent probability representing the probability that the trace
	 *         is executed given that we already are in the given state.
	 */
	public double simulateTrace(ProbabilisticState state, Integer[] trace, int traceIndex) {
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
						* simulateTrace(t.getTarget(), trace, traceIndex + (isEpsilonTransition ? 0 : 1));
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
						* simulateTrace(t.getTarget(), trace, traceIndex + (isEpsilonTransition ? 0 : 1));
			}
	
			return accumulatedProbability;
		}
	
	}

}
