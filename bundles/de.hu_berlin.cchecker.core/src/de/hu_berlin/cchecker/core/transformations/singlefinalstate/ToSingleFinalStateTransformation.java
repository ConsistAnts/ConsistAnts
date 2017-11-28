package de.hu_berlin.cchecker.core.transformations.singlefinalstate;

import java.util.Random;

import com.google.common.collect.HashBiMap;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;

/**
 * Transforms a given {@link ProbabilisticAutomata} to a {@link ProbabilisticAutomata}
 * that accepts all words of the input automaton with a given suffix.
 *
 * Also, the output automaton only has a single final state with termination probability 1.0.
 *
 */
public interface ToSingleFinalStateTransformation {
	
	/**
	 * Transforms the given {@link ProbabilisticAutomata} into a prefix-automaton of the input automaton that has a single
	 * final state with termination probability 1.0.
	 */
	public ProbabilisticAutomata transform(ProbabilisticAutomata automaton, Pair<Integer, String> suffixMapping);
	
	/**
	 * Returns a pair of integer and string that represents a label mapping that hasn't been
	 * used yet. That is, the label id nor the label itself has been used in the given automaton.
	 */
	public static Pair<Integer, String> getUnusedTerminalMapping(ProbabilisticAutomata automaton) {
		HashBiMap<Integer, String> mappingBiMap = HashBiMap.create(automaton.getTransitionLabels().map());
		
		Random r = new Random();
		
		// determine an unused label id
		int labelId = r.nextInt();
		while (mappingBiMap.containsKey(labelId)) {
			labelId = r.nextInt();
		}
		
		// determine an unused string label
		String label = Integer.toString(labelId);
		
		// keep generating new labels until an unused one is found 
		for (int i=1; mappingBiMap.containsValue(label); i++) {
			// keep doubling the value of labelId mod 2147483647 (highest prime in INT_MAX)
			label = Integer.toString( (int)(labelId * Math.pow(2, i)) % 2147483647);
		}
		
		return Pair.create(labelId, label);
	}
}
