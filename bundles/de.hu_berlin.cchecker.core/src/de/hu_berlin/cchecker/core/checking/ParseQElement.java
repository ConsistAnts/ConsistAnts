package de.hu_berlin.cchecker.core.checking;

/**
 * A parse queue element that holds additional information to keep track of depth and
 * the transition history.
 * 
 * @author HorazVitae
 */
public class ParseQElement {

	/**
	 * current state we are at
	 */
	private final int state;

	/**
	 * current depth in prefixtree of automaton the state is at
	 */
	private final int depth;

	/**
	 * total probability value for reaching this specific state from the starting state via all previous steps in breadth first search
	 */
	private final double currentProbabilityValue;

	private final int recentTransitionId;

	private final int pastTransitionId;

	/**
	 * Instantiates a new {@link ParseQElement} with current state, current depth, probabilistic value that led to the state, the recent and the past transition
	 */
	public ParseQElement(int state, int depth, double pvalue, int recentTransitionId, int pastTransitionId) {
		this.state = state;
		this.depth = depth;
		this.currentProbabilityValue = pvalue;
		this.recentTransitionId = recentTransitionId;
		this.pastTransitionId = pastTransitionId;
	}

	/**
	 * Returns the state of this queue element.
	 */
	public int getStateId() {
		return this.state;
	}

	/**
	 * Returns the depth of this queue element.
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * Returns total probability value leading to the wrapped state.
	 */
	public double getProbabilityValue() {
		return this.currentProbabilityValue;
	}

	/**
	 * Returns id of the first transition in the history of this path.
	 */
	public int getFirstTransitionId() {
		return this.recentTransitionId;
	}
	
	/**
	 * Returns id of second transition in the history of this path.
	 */
	public int getSecondTransitionId() {
		return this.pastTransitionId;
	}
}
