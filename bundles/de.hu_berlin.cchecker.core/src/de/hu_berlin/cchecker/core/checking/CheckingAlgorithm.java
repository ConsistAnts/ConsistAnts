package de.hu_berlin.cchecker.core.checking;

import org.eclipse.core.runtime.IProgressMonitor;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * Generic interface for consistency checking algorithm.
 */
public interface CheckingAlgorithm {
	/**
	 * Performs a consistency check on the given trace data set with the given
	 * model.
	 */
	ConsistencyReport performConsistencyCheck(ProbabilisticAutomata model, TraceDataset dataset,
			IProgressMonitor monitor);
}
