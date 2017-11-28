package de.hu_berlin.cchecker.core.learning.blockwise;

/**
 * A {@link BlockLetter} represents a repetitions of a given character (see
 * {@link #character}) for a given number of times (see {@link #repetitions}).
 * 
 * <code> Example: 
 * won won won toll toll lost
 * 
 * 	becomes the three {@link BlockLetter}s:
 * 
 * [won^3] [toll^2] [lost]
 *
 </code>
 */
public class BlockLetter {
	public static final BlockLetter EPSILON_BLOCK_LETTER = new BlockLetter(SimpleSREBuilder.EPSILON_ID, 1);

	final int repetitions;
	final int character;

	public BlockLetter(int character, int repetitions) {
		this.repetitions = repetitions;
		this.character = character;
	}

	@Override
	public String toString() {
		return "[" + this.character + "^" + this.repetitions + "]";
	}
}