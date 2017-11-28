package de.hu_berlin.cchecker.core.transformations.singlefinalstate;

import java.util.HashMap;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;

/**
 * Default implementation of {@link FromSingleFinalStateTransformation}.
 */
public class FromSingleFinalStateTransformationImpl implements FromSingleFinalStateTransformation {

	/**
	 * Checks whether the preconditions for this transformation are met by the given
	 * input automaton.
	 * 
	 * @returns <code>true</code> if the preconditions are met. <code>false</code>
	 *          otherwise.
	 */
	public boolean checkPreConditions(ProbabilisticAutomata automaton) {
		boolean foundFinalState = false;

		for (ProbabilisticState state : automaton.getStates()) {
			if (state.isTerminating() || state.getTerminatingProbability() > 0) {
				if (foundFinalState) {
					// found a second final state
					return false;
				} else {
					foundFinalState = true;
				}
			}
		}

		return foundFinalState;
	}

	@Override
	public ProbabilisticAutomata transform(ProbabilisticAutomata automaton, Pair<Integer, String> suffixMapping) {
		final String suffixLabel = automaton.getTransitionLabels().map().get(suffixMapping.first);

		// check that suffix label was actually used by automaton
		if (null == suffixLabel) {
			throw new IllegalArgumentException("The given suffix mapping is not used in the given automaton.");
		}

		// store suffix label id
		final int suffixLabelId = suffixMapping.first;

		// create a new builder
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();

		// transfer all transition
		ProbabilisticAutomataVisitor.transitionStream(automaton).forEach(t -> {
			// if transition to single (special) final state
			if (t.getTransitionId() == suffixLabelId) {
				// set termination probability instead
				final int finalStateId = t.getSource().getStateId();
				final double terminationProbability = t.getProbability();
				builder.terminatesInWithProbability(finalStateId, terminationProbability);
			} else {
				// otherwise simply copy transition to builder
				builder.fromTo(t.getSource().getStateId(), t.getTarget().getStateId(), t.getProbability(),
						t.getTransitionId());
			}
		});

		// set initial state
		builder.startIn(automaton.getStartState().getStateId());

		// transfer label mapping
		HashMap<Integer, String> labelMapping = new HashMap<>(automaton.getTransitionLabels().map());
		labelMapping.remove(suffixLabelId);
		builder.labelMapping(labelMapping);

		// create transformed automaton
		return builder.create();
	}

}
