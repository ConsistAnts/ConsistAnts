package de.hu_berlin.cchecker.core.learning.alergia;

import java.util.HashMap;
import java.util.Map;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;

/**
 * A converter to create a {@link ProbabilisticAutomata} from a given transition table. 
 */
public class AutomataConverter {
	/**
	 * Constructs a new {@link ProbabilisticAutomata} from the given transition table. 
	 */
	public ProbabilisticAutomata getAutomataForTransitionTable(HashMap<Integer, ProbabilisticState> transitionTable, Map<Integer, String> labelMap) {
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();
			
		// Set label mappings for this PA
		builder.labelMapping(labelMap);
		
		// Iterate over all the states
		for (Map.Entry<Integer, ProbabilisticState> trans : transitionTable.entrySet()) {
			ProbabilisticState state = trans.getValue();
			int fromStateId = trans.getKey();
			
			// Iterate over all the transitions
			for (Map.Entry<Integer, Integer> succ : state.successor.entrySet()) {
				// Add all transitions to the builder
				int symbol = succ.getKey();
				Integer toStateId = succ.getValue();
				Integer frequency = state.successorFrequencies.get(symbol);
				
				builder.fromTo(fromStateId, toStateId, ((double)frequency) / ((double) state.arriving), symbol);
			}
			
			// Add termination probability for current state
			builder.terminatesInWithProbability(trans.getKey(), ((double)state.terminating) / ((double) state.arriving));
		}
		
		// Always start in state 1
		builder.startIn(1);
		
		// Finally create the specified PA model
		return builder.create();
	}
}
