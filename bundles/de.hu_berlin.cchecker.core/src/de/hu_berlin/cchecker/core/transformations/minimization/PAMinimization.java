package de.hu_berlin.cchecker.core.transformations.minimization;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;

/**
 * Interface for minimizing a <code>ProbabilisticAutomata</code>.
 * 
 * @author Daniel - rychlewd
 */
public interface PAMinimization {
	/**
	 * Minimizes the given <code>ProbabilisticAutomata</code>
	 * with a minimization algorithm and return the minimized
	 * <code>ProbabilisticAutomata</code>.
	 */
	ProbabilisticAutomata minimize(ProbabilisticAutomata unminimized);
}
