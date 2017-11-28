package de.hu_berlin.cchecker.ui.util;

import org.eclipse.core.runtime.IPath;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * This class models a complete input for a consistency checking algorithm, containing
 * a {@link ProbabilisticAutomata} as well as a {@link TraceDataset}. In the future,
 * stochastic regular expressions may be supported as well, but for now it is just an automaton and a dataset.
 * 
 * @author Linus
 *
 */
public class ConsistencyCheckInput {

	/**
	 * The automaton for this input.
	 */
	private ProbabilisticAutomata automaton;
	
	/**
	 * The trace dataset for this input.
	 */
	private TraceDataset traceDataset;
	
	private IPath automataPath;
	private IPath traceDatasetPath;
	
	/**
	 * Returns a new instance of ConsistencyCheckInput with a null automaton and dataset.
	 */
	public ConsistencyCheckInput() {
		super();
	}

	/**
	 * Returns a new instance of ConsistencyCheckInput with the given automaton and traceDataset.
	 */
	public ConsistencyCheckInput(ProbabilisticAutomata automaton, TraceDataset traceDataset) {
		super();
		this.automaton = automaton;
		this.traceDataset = traceDataset;
	}

	/**
	 * Returns the automaton of this input. May be null.
	 */
	public ProbabilisticAutomata getAutomaton() {
		return automaton;
	}

	/**
	 * Sets this input's automaton to the given automaton.
	 */
	public void setAutomaton(ProbabilisticAutomata automaton) {
		this.automaton = automaton;
	}

	/**
	 * Returns the traceDataset of this input. May be null.
	 */
	public TraceDataset getTraceDataset() {
		return traceDataset;
	}

	/**
	 * Sets this input's traceDataset to the given traceDataset.
	 */
	public void setTraceDataset(TraceDataset traceDataset) {
		this.traceDataset = traceDataset;
	}

	public IPath getAutomataPath() {
		return automataPath;
	}

	public void setAutomataPath(IPath automataPath) {
		this.automataPath = automataPath;
	}

	public IPath getTraceDatasetPath() {
		return traceDatasetPath;
	}

	public void setTraceDatasetPath(IPath traceDatasetPath) {
		this.traceDatasetPath = traceDatasetPath;
	}
}
