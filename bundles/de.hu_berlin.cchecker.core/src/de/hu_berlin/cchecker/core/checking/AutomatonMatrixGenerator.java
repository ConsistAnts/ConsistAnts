package de.hu_berlin.cchecker.core.checking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

/**
 * An {@link AutomatonMatrixGenerator} can be used to create footprint matrices
 * for a given automaton.
 */
public class AutomatonMatrixGenerator {

	public static final double DEFAULT_TRAVERSAL_THRESHOLD = 0.0;
	
	private Map<Integer, Integer> matrixIndices;
	private List<Integer> lengths;

	private double traversalThreshold = DEFAULT_TRAVERSAL_THRESHOLD;

	/**
	 * Instantiates a new {@link AutomatonMatrixGenerator} that uses the given
	 * matrix indices for the matrix generation.
	 */
	public AutomatonMatrixGenerator(Map<Integer, Integer> matrixIndices, List<Integer> lengths) {
		super();
		this.matrixIndices = matrixIndices;
		this.lengths = lengths;
	}

	/**
	 * Creates the automata footprint matrices.
	 * 
	 * @param automaton
	 *            The automata to work with.
	 * @param lengths
	 *            The length for which matrices should be created.
	 * @param labelMapping
	 *            The alphabet to work with.
	 */
	public List<FPMatrix> generate(ProbabilisticAutomata automaton) {
		Map<Integer, ProbabilisticState> stateMap = automaton.getStates().stream()
				.collect(Collectors.toMap(s -> s.getStateId(), s -> s));

		// initialize automaton matrices
		List<FPMatrix> modelset = new ArrayList<FPMatrix>();
		for (int i = 0; i < lengths.size(); i++) {
			modelset.add(new FPMatrix(lengths.get(i), 0, matrixIndices.size()));
		}

		final ArrayDeque<ParseQElement> parsequeue = new ArrayDeque<>();

		int currlength = 0;
		int longest = lengths.get(lengths.size() - 1);
		
		ParseQElement currelement = new ParseQElement(automaton.getStartState().getStateId(), 0, 1.0, -1, -1);
		parsequeue.add(currelement);

		while (currlength <= longest) {
			currelement = parsequeue.peek();
			int tosetpasttrans = -1;
			final int currentStateId = currelement.getStateId();
			List<ProbabilisticTransition> nextTranslist = stateMap.get(currentStateId).getOutgoingTransitions();
			if (currelement.getFirstTransitionId() != -1) {
				if (currelement.getSecondTransitionId() != -1) { // if at least two transitions
					final Integer row = matrixIndices.get(currelement.getFirstTransitionId());
					final Integer column = matrixIndices.get(currelement.getSecondTransitionId());

					for (int h = 0; h < modelset.size(); h++) { // add current pvalue to eventvalue in each
																// matrix
																// whose length corresponds with current depth
						if (modelset.get(h).getLength() >= currlength) {
							modelset.get(h).increaseElementBy(column, row, currelement.getProbabilityValue());
						}
					}
				}
				tosetpasttrans = currelement.getFirstTransitionId(); // set up savepoint of transition in Q element that
																		// led to
																		// the node the trans comes from
			}
			if (currelement.getDepth() <= longest) {
				for (int k = 0; k < nextTranslist.size(); k++) {
					ProbabilisticTransition transition = nextTranslist.get(k);
					double p = currelement.getProbabilityValue() * transition.getProbability();
					
					if (p < getTraversalThreshold()) {
						continue;
					}
					
					ParseQElement fill = new ParseQElement(transition.getTarget().getStateId(), currelement.getDepth() + 1,
							p, transition.getTransitionId(),
							tosetpasttrans);
					parsequeue.add(fill);
				}
			}
			
			if (!parsequeue.isEmpty()) { // kill if whole model already parsed
				parsequeue.remove();
				if (parsequeue.size() >= 1 && parsequeue.peek().getDepth() > currlength) { // if next state is of higher
																							// depth heighten
					// currenttransitionlength (depth)
					currlength++;
				}
				// If the parse queue is empty, break here, there isn't anything left to do
				if (parsequeue.isEmpty()) {
					break;
				}
			} else {
				break;
			}

		}
		return modelset;
	}

	/**
	 * Returns the traversal threshold.
	 * 
	 * @see #setTraversalThreshold(double)
	 */
	public double getTraversalThreshold() {
		return traversalThreshold;
	}

	/**
	 * Sets the traversal threshold for this matrix generator.
	 * 
	 * Branches that have a probability less than the threshold
	 * are dismissed automatically.
	 * 
	 * A higher threshold can increase matrix generation time dramatically.
	 * However, in some cases a high threshold might affect the consistency
	 * result accuracy.
	 * 
	 * @param traversalThreshold
	 */
	public void setTraversalThreshold(double traversalThreshold) {
		this.traversalThreshold = traversalThreshold;
	}
}
