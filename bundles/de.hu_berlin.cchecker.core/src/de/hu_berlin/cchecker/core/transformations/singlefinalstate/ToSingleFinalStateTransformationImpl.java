package de.hu_berlin.cchecker.core.transformations.singlefinalstate;

import java.util.HashMap;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;

/**
 * Default implementation of {@link ToSingleFinalStateTransformation}.
 * 
 * @author lucabeurer-kellner
 *
 */
public class ToSingleFinalStateTransformationImpl implements ToSingleFinalStateTransformation {

	@Override
	public ProbabilisticAutomata transform(ProbabilisticAutomata automaton, Pair<Integer, String> suffixMapping) {
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();

		final int suffixLabelId = suffixMapping.first;

		// create single final state
		final int finalStateId = -1;
		builder.terminatesInWithProbability(-1, 1.0);

		// set initial state
		builder.startIn(automaton.getStartState().getStateId());

		// add additional suffix-transitions for all final states
		ProbabilisticAutomataVisitor.stateStream(automaton).forEach(state -> {
			double terminationProbability = state.getTerminatingProbability();
			if (terminationProbability > 0) {
				builder.fromTo(state.getStateId(), finalStateId, terminationProbability, suffixLabelId);
			}
		});

		// transfer all the transitions
		ProbabilisticAutomataVisitor.transitionStream(automaton)
			.forEach(transition -> builder.fromTo(transition.getSource().getStateId(),
					transition.getTarget().getStateId(), transition.getProbability(), transition.getTransitionId()));

		HashMap<Integer, String> labelMapping = new HashMap<>();
		// transfer all original label mappings
		labelMapping.putAll(automaton.getTransitionLabels().map());
		// add suffix mapping
		labelMapping.put(suffixLabelId, suffixMapping.second);
		
		builder.labelMapping(labelMapping);
		
		return builder.create();
	}

}
