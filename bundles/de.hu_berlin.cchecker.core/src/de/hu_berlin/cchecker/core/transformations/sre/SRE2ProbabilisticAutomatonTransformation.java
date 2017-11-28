package de.hu_berlin.cchecker.core.transformations.sre;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation.SREParseException;

/**
 * A transformation to transform string-encoded SREs to a
 * consistency-checking-ready {@link ProbabilisticAutomata}s.
 * 
 * That is a a {@link ProbabilisticAutomata} that does not contain any epsilon
 * transitions or unreachable states.
 *
 */
public interface SRE2ProbabilisticAutomatonTransformation {
	/**
	 * Performs the transformation.
	 */
	ProbabilisticAutomata transform(final String sre) throws SREParseException;
}
