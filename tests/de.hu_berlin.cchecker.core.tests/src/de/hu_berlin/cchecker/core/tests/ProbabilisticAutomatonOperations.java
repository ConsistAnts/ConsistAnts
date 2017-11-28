package de.hu_berlin.cchecker.core.tests;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.transformations.epsilonremoval.EpsilonRemovalTransformation;
import de.hu_berlin.cchecker.core.transformations.epsilonremoval.EpsilonRemovalTransformationImpl;

/**
 * Utility class to perform operations with multiple automata as input
 * that yield a new automata.
 */
public class ProbabilisticAutomatonOperations {
	/**
	 * Returns a new {@link ProbabilisticAutomata} that is the concatentation
	 * of the two input automata. 
	 */
	public static ProbabilisticAutomata concat(ProbabilisticAutomata a1, ProbabilisticAutomata a2) {
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();

		// Offset to use for state IDs of the second automaton,
		// to prevent ID collisions.
		int stateIdOffset = getMaximumStateId(a1) + 1;

		Map<Integer, String> a1LabelMapping = a1.getTransitionLabels().map();
		Map<Integer, String> a2LabelMapping = a2.getTransitionLabels().map();

		// Set start state in result to start state in A1
		builder.startIn(a1.getStartState().getStateId());

		// Add all transitions of A1 to result
		ProbabilisticAutomataVisitor.transitionStream(a1).forEach(t -> builder.fromTo(t.getSource().getStateId(),
				t.getTarget().getStateId(), t.getProbability(), a1LabelMapping.get(t.getTransitionId())));

		// Add all transitions of A2 to result but offset state IDs to prevent
		// collisions
		ProbabilisticAutomataVisitor.transitionStream(a2)
				.forEach(t -> builder.fromTo(t.getSource().getStateId() + stateIdOffset,
						t.getTarget().getStateId() + stateIdOffset, t.getProbability(),
						a2LabelMapping.get(t.getTransitionId())));

		final int initialStateInA2OffsetId = a2.getStartState().getStateId() + stateIdOffset;

		// Create epsilon-transitions from A1 termination states to A2 initial state
		ProbabilisticAutomataVisitor.stateStream(a1).filter(s -> s.getTerminatingProbability() > 0.0).forEach(s -> {
			builder.fromTo(s.getStateId(), initialStateInA2OffsetId, s.getTerminatingProbability(), /* epsilon */"");
		});

		// Re-use termination probabilities of A2
		ProbabilisticAutomataVisitor.stateStream(a2).forEach(s -> builder
				.terminatesInWithProbability(s.getStateId() + stateIdOffset, s.getTerminatingProbability()));

		// Remove epsilon transitions
		EpsilonRemovalTransformation epsilonRemoval = new EpsilonRemovalTransformationImpl();
		ProbabilisticAutomata transformed = epsilonRemoval.transform(builder.create());

		return transformed;
	}

	/**
	 * Returns an (epsilon-free) automaton that allows either one of the branches
	 * with the given distribution.
	 * 
	 * @param distribution
	 *            The probability of branch1 compared to branch2
	 */
	public static ProbabilisticAutomata createAlternativeAutomaton(ProbabilisticAutomata branch1,
			ProbabilisticAutomata branch2, double distribution) {
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();

		// Offset to use for state IDs of the second automaton,
		// to prevent ID collisions.
		final int newInitialStateId = getMaximumStateId(branch1) + 1;
		final int stateIdOffset = newInitialStateId + 1;
		 

		Map<Integer, String> a1LabelMapping = branch1.getTransitionLabels().map();
		Map<Integer, String> a2LabelMapping = branch2.getTransitionLabels().map();

		// Set start state in result to start state in A1
		builder.startIn(newInitialStateId);
		
		// add branch (epsilon-) transitions
		builder.fromTo(newInitialStateId, branch1.getStartState().getStateId(), distribution, "");
		builder.fromTo(newInitialStateId, branch2.getStartState().getStateId() + stateIdOffset, 1.0 - distribution, "");

		// Add all transitions of A1 to result
		ProbabilisticAutomataVisitor.transitionStream(branch1).forEach(t -> builder.fromTo(t.getSource().getStateId(),
				t.getTarget().getStateId(), t.getProbability(), a1LabelMapping.get(t.getTransitionId())));

		// Add all transitions of A2 to result but offset state IDs to prevent
		// collisions
		ProbabilisticAutomataVisitor.transitionStream(branch2)
				.forEach(t -> builder.fromTo(t.getSource().getStateId() + stateIdOffset,
						t.getTarget().getStateId() + stateIdOffset, t.getProbability(),
						a2LabelMapping.get(t.getTransitionId())));

		// Re-use termination probabilities of A1
				ProbabilisticAutomataVisitor.stateStream(branch1).forEach(s -> builder
						.terminatesInWithProbability(s.getStateId(), s.getTerminatingProbability()));
		// Re-use termination probabilities of A2
		ProbabilisticAutomataVisitor.stateStream(branch2).forEach(s -> builder
				.terminatesInWithProbability(s.getStateId() + stateIdOffset, s.getTerminatingProbability()));

		// Remove epsilon transitions
		EpsilonRemovalTransformation epsilonRemoval = new EpsilonRemovalTransformationImpl();
		ProbabilisticAutomata transformed = epsilonRemoval.transform(builder.create());

		return transformed;
	}

	/**
	 * Returns the maximum state ID used in the given automaton.
	 */
	private static int getMaximumStateId(ProbabilisticAutomata a) {
		Optional<Integer> maxStateIdInA = a.getStates().stream().map(s -> s.getStateId())
				.max(Comparator.comparing(s -> s));
		if (!maxStateIdInA.isPresent()) {
			throw new IllegalArgumentException("Failed to determine maximum state ID in automaton 1");
		}
		return maxStateIdInA.get();
	}

	private ProbabilisticAutomatonOperations() {
		// non-instantiable utility class
	}
}
