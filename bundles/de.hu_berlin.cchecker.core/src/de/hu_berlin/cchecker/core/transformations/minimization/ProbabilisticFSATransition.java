package de.hu_berlin.cchecker.core.transformations.minimization;

import edu.duke.cs.jflap.automata.State;
import edu.duke.cs.jflap.automata.fsa.FSATransition;

/**
 * {@link FSATransition} that also holds a probability value.
 */
class ProbabilisticFSATransition extends FSATransition {
	private static final long serialVersionUID = -3970049074344605588L;
	private double probability;
	private String actualLabel;

	public ProbabilisticFSATransition(State from, State to, String label, double probability) {
		super(from, to, label + probability);
		this.probability = probability;
		this.actualLabel = label;
	}

	public double getProbability() {
		return probability;
	}
	
	public String getActualLabel() {
		return actualLabel;
	}
	
}
