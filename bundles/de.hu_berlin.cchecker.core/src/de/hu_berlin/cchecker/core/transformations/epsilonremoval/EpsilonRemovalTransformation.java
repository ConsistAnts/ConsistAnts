package de.hu_berlin.cchecker.core.transformations.epsilonremoval;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;

/**
 * Transformations to transform a {@link ProbabilisticAutomata} with epsilon-transitions
 * to an equivalent {@link ProbabilisticAutomata} without epsilon-transitions.
 */
public interface EpsilonRemovalTransformation {
	/**
	 * Performs the transformation.
	 */
	public ProbabilisticAutomata transform(ProbabilisticAutomata auto);
}
