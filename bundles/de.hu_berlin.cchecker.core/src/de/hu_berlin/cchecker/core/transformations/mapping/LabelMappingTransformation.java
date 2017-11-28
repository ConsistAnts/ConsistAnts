package de.hu_berlin.cchecker.core.transformations.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import de.hu_berlin.cchecker.core.transformations.ProbabilisticAutomatonTransformation;

/**
 * A transformation that maps all label-IDs of a given automaton to a specified
 * target label mapping.
 * 
 * Precondition for this transformation is, that the target mapping must re-map
 * all string labels that appear in the transformed input
 * {@link ProbabilisticAutomata}.
 *
 */
public class LabelMappingTransformation implements ProbabilisticAutomatonTransformation {

	// the target mapping of this transformation
	private Map<String, Integer> targetMapping;

	/**
	 * Instantiates a new {@link LabelMappingTransformation}.
	 * 
	 * @param targetMapping
	 *            The mapping to transform all given automata to.
	 */
	public LabelMappingTransformation(Map<String, Integer> targetMapping) {
		this.targetMapping = targetMapping;
	}

	/**
	 * A visitor for {@link ProbabilisticAutomata} that re-maps all the transition
	 * IDs of the visited automaton according to the
	 * {@link LabelMappingTransformation#targetMapping}.
	 * 
	 * Also keeps track of which mappings are actually used, so that the number of
	 * required label mappings can be reduced to minimum.
	 */
	private final class MappingAwareTransitionReMapVisitor implements ProbabilisticAutomataVisitor {
		private final Map<Integer, String> originalMapping;
		private final HashMap<Integer, String> usedMappings = new HashMap<>();

		private MappingAwareTransitionReMapVisitor(Map<Integer, String> originalMapping) {
			this.originalMapping = originalMapping;
		}

		@Override
		public void visit(ProbabilisticTransition transition) {
			String label = originalMapping.get(Integer.valueOf(transition.getTransitionId()));
			Integer targetTransitionId = targetMapping.get(label);

			if (null == targetTransitionId) { // this might happen if the precondition wasn't fulfilled
				throw new IllegalArgumentException();
			}

			// update transition with new transition ID
			transition.setTransitionId(targetTransitionId);
			// make sure this transition id is included in the mapping
			usedMappings.put(targetTransitionId, label);
		}

		/**
		 * Returns the subset of {@link LabelMappingTransformation#targetMapping} that
		 * was used during all visits of transitions.
		 */
		public HashMap<Integer, String> getUsedMappings() {
			return usedMappings;
		}
	}

	@Override
	public boolean checkPrecondition(ProbabilisticAutomata from) {
		// target mapping contains re-mappings for all labels of the given automaton
		return targetMapping.keySet().containsAll(from.getTransitionLabels().map().values());
	}

	@Override
	public ProbabilisticAutomata transform(ProbabilisticAutomata from) {
		// do not transform if the mapping is already contained by the target mapping
		if (checkPostCondition(from)) {
			return from;
		}

		ProbabilisticAutomata copy = ProbabilisticAutomataBuilder.copyOf(from).create();

		// create the according re-map visitor for this automaton
		MappingAwareTransitionReMapVisitor reMapVisitor = new MappingAwareTransitionReMapVisitor(
				copy.getTransitionLabels().map());

		// try to perform the re-mapping
		try {
			ProbabilisticAutomataVisitor.visit(copy, reMapVisitor);
		} catch (IllegalArgumentException e) {
			return null;
		}

		// replace label mappings
		copy.getTransitionLabels().clear();
		copy.getTransitionLabels().map().putAll(reMapVisitor.getUsedMappings());

		return copy;
	}

	@Override
	public boolean checkPostCondition(ProbabilisticAutomata to) {
		// check that all the mappings were changed to the target mapping
		for (Entry<Integer, String> mapping : to.getTransitionLabels().entrySet()) {
			if (targetMapping.get(mapping.getValue()) != mapping.getKey()) {
				return false;
			}
		}
		return true;
	}
}
