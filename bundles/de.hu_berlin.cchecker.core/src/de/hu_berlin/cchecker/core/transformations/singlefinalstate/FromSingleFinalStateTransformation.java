package de.hu_berlin.cchecker.core.transformations.singlefinalstate;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;

/**
 * Transforms an output of {@link ToSingleFinalStateTransformation} back into
 * its original form.
 *
 * The output automaton will have initial states for all state that have
 * transitions to the given single final state.
 *
 */
public interface FromSingleFinalStateTransformation {
	/**
	 * Performs the transformation.
	 * 
	 * @param automaton
	 *            The automaton to transform from.
	 * @param suffixMapping
	 *            The suffix mapping that was used when transforming using
	 *            {@link ToSingleFinalStateTransformation}.
	 */
	ProbabilisticAutomata transform(ProbabilisticAutomata automaton, Pair<Integer, String> suffixMapping);
}
