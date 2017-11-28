package de.hu_berlin.cchecker.core.transformations.minimization;

import org.eclipse.emf.common.util.EMap;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import edu.duke.cs.jflap.automata.Automaton;
import edu.duke.cs.jflap.automata.Transition;
import edu.duke.cs.jflap.automata.fsa.FiniteStateAutomaton;

/**
 * **Experimental** implementation of a {@link ProbabilisticAutomata} minisation 
 * that allows to specify a threshold.
 * 
 * Correctness of this implementation has not been proved yet. 
 * Also, for now we do not have any tests testing this implementation.
 *
 */
public class JflapPAMinimizationWithThreshold extends JFlapPAMinimization implements PAMinimization {

	private double probabilityThreshold;

	public JflapPAMinimizationWithThreshold(double probabilityThreshold) {
		super();
		this.probabilityThreshold = probabilityThreshold;
	}

	/**
	 * Method which applies Jflap's minimization algorithm to a
	 * <code>ProbabilisticAutomata</code> and returns the minimized
	 * <code>ProbabilisticAutomata</code>. Depending on the probabilityThreshold,
	 * transitions with the same label but differing probabilities may be mapped to
	 * the same character if the probability deviation lies within the threshold.
	 * That means that the difference between the given probability and the other
	 * one must not be greater than probabilityThreshold/2 for the same mapping to
	 * be used. Depending on the value of useCharacterMapping, character mapping for
	 * transitions may or may not be used.
	 */
	@Override
	public ProbabilisticAutomata doMinimize(ProbabilisticAutomata unminimized, final int finalStateId,
			Pair<Integer, String> unusedMapping) {
		
		// Convert ProbabilisticAutomata unminimized to the Automaton format which is
		// used by JFlap
		Automaton inputForJflap = traverseStates(unminimized);
		traverseTransitionsWithoutMapping(unminimized, inputForJflap, probabilityThreshold);

		// Use JFlap to minimize the PA
		FiniteStateAutomaton outputOfJflap = minimizePAWithJflap(inputForJflap);

		// Convert JFlap's result FiniteStateAutomaton back to a ProbabilisticAutomata
		return convertFSAtoPAWithoutMapping(unminimized, outputOfJflap, finalStateId, unusedMapping);
	}

	/**
	 * Traverses the transitions of the given unminimized
	 * <code>ProbabilisticAutomata</code> as part of its conversion process to an
	 * <code>Automaton</code>, the format used by Jflap. This method does not use
	 * character mapping.
	 * 
	 * @param unminimized
	 *            Input <code>ProbabilisticAutomata</code>, which needs to be
	 *            converted into Jflap's format
	 * @param inputForJflap
	 *            <code>Automaton</code> for Jflap's minimization algorithm
	 */
	private void traverseTransitionsWithoutMapping(ProbabilisticAutomata unminimized, Automaton inputForJflap,
			double probabilityThreshold) {
		EMap<Integer, String> idToLabelsMapping = unminimized.getTransitionLabels();
		for (ProbabilisticState s : unminimized.getStates()) {
			for (ProbabilisticTransition t : s.getOutgoingTransitions()) {
				/*
				 * Before you add a new transition with a totally new probability, make sure
				 * that no transition with the same label and within the given probability
				 * threshold already exists. If such a transition does exist, use the
				 * probability of this existing transition for the new transition even though
				 * the actual probability for the new transition would have been different
				 * without the probability threshold.
				 */
				int counter = 1; // because this is the 1st transition of inputForJflap.getTransitions() that
									// we're dealing with in the upcoming loop
				for (Transition v : inputForJflap.getTransitions()) {
					ProbabilisticFSATransition u = (ProbabilisticFSATransition) v; // cast to ProbabilisticFSATransition
																					// to access getLabel() method; no
																					// need to parse (as opposed to
																					// FSATransition)

					// Determine the actual character label and the probability without parsing
					String labelOfTransition = u.getActualLabel();
					double probabilityOfTransition = u.getProbability();

					/*
					 * Is the character of the transition label the same and does the probability
					 * lie within the range of +-probabilityThreshold/2 ? If so, use the probability
					 * of the existing transition instead of the probability you initially wanted to
					 * assign to the transition
					 */
					if (idToLabelsMapping.get((Object) t.getTransitionId()).equals(labelOfTransition)
							&& t.getProbability() >= probabilityOfTransition - probabilityThreshold / 2
							&& t.getProbability() <= probabilityOfTransition + probabilityThreshold / 2) {
						ProbabilisticFSATransition transitionForJflap = new ProbabilisticFSATransition(
								inputForJflap.getStateWithID(t.getSource().getStateId()),
								inputForJflap.getStateWithID(t.getTarget().getStateId()),
								idToLabelsMapping.get((Object) t.getTransitionId()), probabilityOfTransition);
						inputForJflap.addTransition(transitionForJflap);
						// Now that the transition has been added, we can continue with the next one we
						// want to add without looking at other transitions in inputForJflap for this
						// transition (because this transition has been now added)
						break;
					} else {
						if (counter < inputForJflap.getTransitions().length) {
							// We haven't dealt with every transition yet - there might still be a
							// transition with the same label and in the probability range that we haven't
							// considered yet but need to consider
							counter++;
						} else {
							// The counter says that we've dealt with every possible transition, but not a
							// single one of them was fitting. In this case, add the transition with the
							// probability you initially wanted to assign to it
							ProbabilisticFSATransition transitionForJflap = new ProbabilisticFSATransition(
									inputForJflap.getStateWithID(t.getSource().getStateId()),
									inputForJflap.getStateWithID(t.getTarget().getStateId()),
									idToLabelsMapping.get((Object) t.getTransitionId()), t.getProbability());
							inputForJflap.addTransition(transitionForJflap);
							// The innermost for-loop will end now - the next for-loop for the
							// ProbabilisticTransition t will reset the counter
						}
					}
				}
				if (inputForJflap.getTransitions().length == 0) {
					// If there are no transitions in inputForJflap yet (so that the for-loop has
					// been skipped), we can safely add our new transition.
					ProbabilisticFSATransition transitionForJflap = new ProbabilisticFSATransition(
							inputForJflap.getStateWithID(t.getSource().getStateId()),
							inputForJflap.getStateWithID(t.getTarget().getStateId()),
							idToLabelsMapping.get((Object) t.getTransitionId()), t.getProbability());
					inputForJflap.addTransition(transitionForJflap);
				}
			}
		}
	}
}
