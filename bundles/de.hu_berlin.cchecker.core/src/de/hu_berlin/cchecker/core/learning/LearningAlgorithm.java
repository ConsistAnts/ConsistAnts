package de.hu_berlin.cchecker.core.learning;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * Generic interface for learning algorithms.
 */
public interface LearningAlgorithm {
	/**
	 * Learns a model from the given {@link TraceDataset}
	 */
	ProbabilisticAutomata learnModel(TraceDataset dataset) throws LearningAlgorithmException;

	/**
	 * An exception that indicates a problem during model learning.
	 */
	public static class LearningAlgorithmException extends RuntimeException {
		private static final long serialVersionUID = 6602256448037483875L;
		
		public LearningAlgorithmException() {
			super();
		}
		
		public LearningAlgorithmException(String message) {
			super(message);
		}
	}
}
