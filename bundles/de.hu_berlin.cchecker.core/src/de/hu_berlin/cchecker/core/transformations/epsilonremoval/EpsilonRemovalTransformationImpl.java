package de.hu_berlin.cchecker.core.transformations.epsilonremoval;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.HashBiMap;

import de.hu_berlin.cchecker.core.models.pdfa.PdfaFactory;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;

/**
 * Default implementation of {@link EpsilonRemovalTransformation} using a simple
 * transition-traversal approach.
 */
public class EpsilonRemovalTransformationImpl implements EpsilonRemovalTransformation, StopWatchable {
	private ProbabilisticAutomata automaton;
	private Integer epsilonId;
	private Deque<ProbabilisticTransition> traverseQueue;
	private Set<ProbabilisticTransition> visited;
	private Stopwatch stopwatch;
	
	public ProbabilisticAutomata transform(ProbabilisticAutomata input) {
		stopwatch = new Stopwatch();
		stopwatch.start("Epsilon removal transformation");
		
		this.automaton = ProbabilisticAutomataBuilder.copyOf(input).create();
		stopwatch.checkpoint("Working copy of input automaton created");
		
		traverseQueue = new ArrayDeque<>();

		traverseQueue.addAll(automaton.getStartState().getOutgoingTransitions());

		Integer mappingEpsilonId = HashBiMap.create(automaton.getTransitionLabels().map()).inverse().get("");

		// If there is no epsilon in the mapping, return the input automaton
		if (mappingEpsilonId == null) {
			return automaton;
		}

		this.epsilonId = mappingEpsilonId;

		visited = new HashSet<>();

		while (!traverseQueue.isEmpty()) {
			ProbabilisticTransition transition = traverseQueue.pop();

			visited.add(transition);
			
			if (null == transition.getSource()) {
				continue;
			}

			if (transition.getTransitionId() == mappingEpsilonId) {
				handleEpsilonTransition(transition);
				// remove this from source
				transition.getSource().getOutgoingTransitions().remove(transition);
			}

			for (ProbabilisticTransition nextTransition : transition.getTarget().getOutgoingTransitions()) {
				if (!visited.contains(nextTransition)) {
					traverseQueue.add(nextTransition);
				}
			}
		}
		
		HashMap<Integer, String> oldMapping = new HashMap<>(automaton.getTransitionLabels().map());
		// clear label mappings
		automaton.getTransitionLabels().clear();
		for (Entry<Integer, String> e : oldMapping.entrySet()) {
			// add back all mappings but for epsilon
			if (e.getValue() != "") { 
				automaton.getTransitionLabels().put(e.getKey(), e.getValue());
			}
		}
		stopwatch.checkpoint("Epsilons removed");
		stopwatch.finish();

		return automaton;
	}

	private void handleEpsilonTransition(ProbabilisticTransition transition) {
		ProbabilisticState target = transition.getTarget();
		ProbabilisticState source = transition.getSource();
		
		if (null == source) {
			return;
		}
		
		// If target is a terminating state, source is now too
		source.setTerminatingProbability(
				source.getTerminatingProbability() + transition.getProbability() * target.getTerminatingProbability());
		source.setTerminating(source.getTerminatingProbability() > 0);

		// Transfer all outgoing transition from target to source
		target.getOutgoingTransitions().forEach(t -> {
			ProbabilisticTransition newTransition = createTransition(automaton, source, t.getTarget(), t.getTransitionId(),
				t.getProbability() * transition.getProbability());
			// if we just created another epsilon-transition, add it to the traverse queue
			if (t.getTransitionId() == epsilonId) {
				traverseQueue.add(newTransition);
			}
		});
	}

	/**
	 * Creates a new transition in a automata which transitions between two
	 * probabilistic states with a given probability and label.
	 * 
	 * This method will do the required work to make an entry for the given label in
	 * the {@link ProbabilisticAutomata#getTransitionLabels()} map. This will result
	 * in transitions with the same label having the same numeric transition id.
	 * 
	 * @param a
	 *            The automata to create a new transition in.
	 * @param from
	 *            The state the transitions is starting from
	 * @param to
	 *            The state the transition is targeting.
	 * @param label
	 *            The label of the transition
	 * @param probability
	 *            The probability of the transition
	 */
	public ProbabilisticTransition createTransition(ProbabilisticAutomata a, ProbabilisticState from,
			ProbabilisticState to, int labelId, double probability) {

		// Create actual model object and set values
		ProbabilisticTransition t = PdfaFactory.eINSTANCE.createProbabilisticTransition();
		from.getOutgoingTransitions().add(t);
		t.setSource(from);
		t.setTarget(to);
		t.setTransitionId(labelId);
		t.setProbability(probability);

		return t;
	}

	@Override
	public Stopwatch getStopwatch() {
		
		return stopwatch;
	}
}
