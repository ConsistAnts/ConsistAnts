package de.hu_berlin.cchecker.core.transformations.unreachableremoval;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;

/**
 * Transformation that removes unreachable states from a given {@link ProbabilisticAutomata}.
 */
public interface UnreachableStateRemovalTransformation extends StopWatchable {
	/**
	 * Perform the unreachable-state-transformation.
	 */
	ProbabilisticAutomata transform(ProbabilisticAutomata automata);
}
