package de.hu_berlin.cchecker.core.learning.blockwise;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Reduces a trace to a list of block-letters. That is all repetitions of a character/letter
 * is transformed to a block-letter.
 * 
 * <code>
 * Example:
 * 
 * won won won toll toll lost
 *  is reduced to
 * [won^3] [toll^2] [lost]
 * 
 * </code>
 *
 */
public class BlockLetterReducer implements Function<Integer[], BlockLetter[]> {
	
	@Override
	public BlockLetter[] apply(Integer[] t) {
		if (t.length == 0) {
			return new BlockLetter[] {};
		}
		List<BlockLetter> blockLetters = new ArrayList<>();
		// keep track of currently-repeated character...
		int currentCharacter = t[0];
		// ...and the number of repetition
		int currentCharacterCount = 1;
		
		// add a new block-letter whenever the currently repeated character changes
		for (int i=1; i<t.length; i++) {
			if (t[i] == currentCharacter) {
				currentCharacterCount += 1;
			} else {
				blockLetters.add(new BlockLetter(currentCharacter, currentCharacterCount));
				currentCharacter = t[i];
				currentCharacterCount = 1;
			}
		}
		// add last remaining currentCharacter as block-letter
		blockLetters.add(new BlockLetter(currentCharacter, currentCharacterCount));
		
		return blockLetters.toArray(new BlockLetter[blockLetters.size()]);
	}
}
