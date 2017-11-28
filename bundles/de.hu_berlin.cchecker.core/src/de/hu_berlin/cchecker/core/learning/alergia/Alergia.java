package de.hu_berlin.cchecker.core.learning.alergia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.xtext.xbase.lib.Pair;

import de.hu_berlin.cchecker.core.learning.LearningAlgorithm;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataSetProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;

/**
 * An implementation of {@link LearningAlgorithm} based on the Alergia Learning Algorithm.
 */
public class Alergia implements LearningAlgorithm, StopWatchable {

	public static final double DEFAULT_ALPHA = 0.8;
	
	private HashMap<Integer, ProbabilisticState> transitionTable;
	private double alpha = DEFAULT_ALPHA;
	private Set<Integer> alphabet;
	
	private Stopwatch stopwatch;
	
	// Trace data provider used to read traces from the input
	private TraceDataProvider traceDataProvider;
	// Automata converter used to create a {@link ProbabilisticAutomata} from the transition table
	private AutomataConverter automataConverter = new AutomataConverter();
	
	/**
	 * Creates a new {@link Alergia} algorithm instance using the given
	 * {@link TraceDataProvider} to extract trace data from the input {@link TraceDataset}.
	 */
	public Alergia(TraceDataProvider dataProvider) {
		this.traceDataProvider = dataProvider;
	}
	
	/**
	 * Creates a new {@link Alergia} algorithm instance using {@link TraceDataSetProvider}.
	 */
	public Alergia() {
		this(new TraceDataSetProvider());
	}

	private void alergia(ProbabilisticTrie trie) {

		this.transitionTable = new PrefixTreeToTransitionTableConverter(trie).getResult();

		int maxSize = this.transitionTable.size();
		int j = 0;
		int i = 0;
		for (j = 2; j <= maxSize; j++) {
			if (this.transitionTable.get(j) != null) {
				for (i = 1; i < j; i++) {
					if (this.transitionTable.get(i) != null
							&& this.compatible(this.transitionTable.get(i), this.transitionTable.get(j))) {
						this.merge(i, j);
						break;
					}
				}
			}
		}
	}

	private void merge(Integer stateNr1, Integer stateNr2) {

		ProbabilisticState state1 = this.transitionTable.get(stateNr1);
		ProbabilisticState state2 = this.transitionTable.get(stateNr2);
		
		// Do not merge with null
		if (null == state1 || null == state2) {
			return;
		}
		
		List<Integer> successorSymbols = new ArrayList<>(state2.successor.keySet());
		for (int i = 0; i < successorSymbols.size(); i++) {
			Integer currentSymbol = successorSymbols.get(i);
			
			// Successor of state 1 for the current symbol
			Integer state1Successor = state1.successor.get(currentSymbol);
			
			// If there is a successor for this symbol
			if (state1Successor != null) {
				// Successor of state 2 for the current symbol
				Integer state2Successor = state2.successor.get(currentSymbol);
				// and its not the same state
				if (!state1Successor.equals(state2Successor)) {
					// merge them
					this.merge(state1Successor, state2Successor);
					// 
					if (state1Successor.equals(stateNr2)) {
						i = 0;
						successorSymbols = new ArrayList<>(state2.successor.keySet());					
					}
				}
				
			}
			else if (state1Successor == null){
				state1.successor.put(currentSymbol, state2.successor.get(currentSymbol));
				state1.successorFrequencies.put(currentSymbol, state2.successorFrequencies.get(currentSymbol));
			}
			
			ProbabilisticState succState = this.transitionTable.get(state2.successor.get(currentSymbol));
			if (null != succState) {
				succState.predecessor.remove(stateNr2);
				succState.predecessor.put(currentSymbol, state1.number);
			}
		}
		this.transitionTable.remove(stateNr2);
		this.transitionTable.get(stateNr1).terminating += state2.terminating;
		this.transitionTable.get(stateNr1).arriving += state2.arriving;
		
		for (Map.Entry<Integer, Integer> pred : state2.predecessor.entrySet()) {
			ProbabilisticState predecessorState = this.transitionTable.get(pred.getValue());
			
			// If predecessor state exists
			if (null != predecessorState) {
				// Remove successor link to state 2
				predecessorState.successor.remove(pred.getKey());
				// Add new successor link to state 1 
				predecessorState.successor.put(pred.getKey(), stateNr1);
				// Add predecessor to predecessor list if it existed
				this.transitionTable.get(stateNr1).predecessor.put(pred.getKey(), pred.getValue());
			}
		}
		for (Map.Entry<Integer, Integer> succ : state2.successor.entrySet()) {
			Integer oldValue = 0;
			Integer addition = state2.successorFrequencies.get(succ.getKey());
			if (this.transitionTable.get(stateNr1).successor.get(succ.getKey()) != null) {
				oldValue = this.transitionTable.get(stateNr1).successorFrequencies.get(succ.getKey());
				this.transitionTable.get(stateNr1).successorFrequencies.put(succ.getKey(), oldValue + addition);
			} else {
				this.transitionTable.get(stateNr1).successor.put(succ.getKey(), succ.getValue());
				this.transitionTable.get(stateNr1).successorFrequencies.put(succ.getKey(), oldValue + addition);
			}
		}
	}
	
	private boolean compatible(ProbabilisticState state1, ProbabilisticState state2) {
		return this.compatible(state1, state2, new HashSet<>());
	}

	private boolean compatible(ProbabilisticState state1, ProbabilisticState state2, 
			HashSet<Pair<Integer, Integer>> recursionGuard) {
		if (state1 == null || state2 == null)
			return true;
		
		// Do not check pairs that are already being checked further up the stack
		Pair<Integer, Integer> guardPair = Pair.of(state1.number, state2.number);
		if (recursionGuard.contains(guardPair)) {
			return true;
		}
		recursionGuard.add(guardPair);
		
		if (this.different(state1.terminating, state2.terminating, state1.arriving, state2.arriving)) {
			return false;
		}

		for (Integer symbol : this.alphabet) {
			if (this.different(state1.successorFrequencies.get(symbol), state2.successorFrequencies.get(symbol),
					state1.arriving, state2.arriving)) {
				return false;
			}
			ProbabilisticState state1SuccessorForSymbol = this.transitionTable.get(state1.successor.get(symbol));
			ProbabilisticState state2SuccessorForSymbol = this.transitionTable.get(state2.successor.get(symbol));
			if (!this.compatible(state1SuccessorForSymbol, state2SuccessorForSymbol, recursionGuard)) {
				return false;
			}
		}
		return true;
	}

	private boolean different(Integer prob1, Integer prob2, double arr1, double arr2) {
		prob1 = prob1 == null ? 0 : prob1;
		prob2 = prob2 == null ? 0 : prob2;
		Double left = Math.abs((prob1 / arr1) - (prob2 / arr2));
		Double right = Math.sqrt(0.5 * Math.log(2.0 / this.getAlpha())) * ((1.0 / Math.sqrt(arr1)) + (1.0 / Math.sqrt(arr2)));
		//System.out.println(left + "|" + right);
		return left > right;
	}

	@Override
	public ProbabilisticAutomata learnModel(TraceDataset dataset) {
		stopwatch = new Stopwatch();
		stopwatch.start("Alergia learning model from traces");
		
		// Convert the trace data set to a {@link ProbabilisticTrie}
		ProbabilisticTrie trie = new ProbabilisticTrie();
		
		traceDataProvider.setInput(dataset);
		for (Integer[] trace : traceDataProvider) {
			trie.insert(trace);
		}
		stopwatch.checkpoint("Tracedataset converted to probabilistic trie");
		
		// Set the alphabet
		this.alphabet = traceDataProvider.getAlphabet();
		stopwatch.checkpoint("Alphabet set");
		
		// Run the algorithm on the {@link ProbabilisticTrie}
		this.alergia(trie);
		stopwatch.checkpoint("Alergia algorithm run on prepared and provided input");
		
		// Finally convert transition table to a {@link ProbabilisticAutomata}
		ProbabilisticAutomata a = automataConverter.getAutomataForTransitionTable(this.transitionTable, traceDataProvider.getLabelMap());
		stopwatch.checkpoint("Transition table converted to use format automaton");
		stopwatch.finish();
		
		return a;
	}

	/**
	 * Returns the alpha parameter value.
	 */
	public double getAlpha() {
		return alpha;
	}
	
	/**
	 * Set the alpha parameter for this instance.
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	@Override
	public Stopwatch getStopwatch() {
		return stopwatch;
	}

}
