package de.hu_berlin.cchecker.core.models.pdfa;

import com.google.common.collect.HashBiMap;

/**
 * Utility class for {@link ProbabilisticAutomata} models.
 */
public class ProbabilisticAutomataUtils {

	/**
	 * Returns a textual representation of the given automata.
	 * 
	 * The returned String is constructed by doing a breadth-first traversal
	 * of the automata.
	 * 
	 * States are represented by an unindented line representing state number and 
	 * termination-probability. Every state is followed by several indented lines that
	 * list all the outgoing transitions with labels and transition probabilities.
	 * 
	 * Example:
	 * 
	 * <code>
	 * 1[0.0]
	 *	-s[1.0]-> 2
	 * 2[0.0]
	 * 	-t[0.8095]-> 3
	 * 	-w[0.1905]-> 3
	 * (...) 
	 * </code>
	 */
	public static String toTextualRepresentation(ProbabilisticAutomata automata) {
		
		StringBuilder textualRepresentation = new StringBuilder();
		
		ProbabilisticAutomataVisitor.visit(automata, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				textualRepresentation.append(state.getStateId() + "[" + state.getTerminatingProbability() + "]\n");
			}
			@Override
			public void visit(ProbabilisticTransition transition) {
				ProbabilisticState target = transition.getTarget();
				String label = automata.getTransitionLabels().get(new Integer(transition.getTransitionId()));
				textualRepresentation.append("\t-" + label + "[" + transition.getProbability() + "]-> " + target.getStateId() + "\n");
			}
		});
		
		return textualRepresentation.toString();
	}


	/**
	 * Returns the transition ID which is mapped to epsilon.
	 * 
	 * Returns {@code null} if the automaton has no mapping for epsilon.
	 */
	public static int getEpsilonTransitionId(ProbabilisticAutomata a) {
		Integer epsId = HashBiMap.create(a.getTransitionLabels().map()).inverse().get("");
		return epsId != null ? epsId : -1;
	}

}
