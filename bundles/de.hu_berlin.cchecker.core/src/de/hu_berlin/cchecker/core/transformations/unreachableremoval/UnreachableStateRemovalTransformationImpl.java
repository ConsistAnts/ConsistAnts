package de.hu_berlin.cchecker.core.transformations.unreachableremoval;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;

public class UnreachableStateRemovalTransformationImpl implements UnreachableStateRemovalTransformation {

	private Stopwatch stopwatch;
	
	/**
	 * Returns the transformed {@link ProbabilisticAutomata}. 
	 *
	 * Returns {@code null} if the given automaton has no start state.
	 */
	@Override
	public ProbabilisticAutomata transform(ProbabilisticAutomata automaton) {
		stopwatch = new Stopwatch();
		stopwatch.start("Removal of unreachable states");
		
		ProbabilisticAutomata transformed = ProbabilisticAutomataBuilder.copyOf(automaton).create();
		stopwatch.checkpoint("Working copy of to be transformed automaton created");
		
		if (null == transformed.getStartState()) {
			return null;
		}
		
		// collect set of reachable states
		Set<ProbabilisticState> visitedStates = new HashSet<>();
		ProbabilisticAutomataVisitor.visit(transformed, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				visitedStates.add(state);
			}
		});
		stopwatch.checkpoint("Set of reachble states collected");
		
		// remove unreachable states
		transformed.getStates().clear();
		transformed.getStates().addAll(visitedStates);
		stopwatch.checkpoint("Unreachable states removed");
		
		// normalise state space
		ProbabilisticAutomataVisitor.visit(transformed, new StateIdNormalizingVisitor());
		stopwatch.checkpoint("Statespace normalized");
		
		// sort model list of states
		List<ProbabilisticState> temporaryStateList = new ArrayList<>(transformed.getStates());
		temporaryStateList.sort((s1, s2) -> Integer.compare(s1.getStateId(), s2.getStateId()));
		stopwatch.checkpoint("Model list of states sorted");
		
		transformed.getStates().clear();
		transformed.getStates().addAll(temporaryStateList);
		stopwatch.checkpoint("Transformed automaton finalized");
		stopwatch.finish();
		
		return transformed;
	}

	/**
	 * State visitor that re-assigns state IDs by counting upwards.
	 */
	private final class StateIdNormalizingVisitor implements ProbabilisticAutomataVisitor {
		private int numberOfStates = 0;
	
		@Override
		public void visit(ProbabilisticState state) {
			state.setStateId(++numberOfStates);
		}
	}

	@Override
	public Stopwatch getStopwatch() {
		return stopwatch;
	}
}
