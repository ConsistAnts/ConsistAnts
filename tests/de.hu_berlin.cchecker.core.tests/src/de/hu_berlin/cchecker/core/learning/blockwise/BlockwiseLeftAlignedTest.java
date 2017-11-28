package de.hu_berlin.cchecker.core.learning.blockwise;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.learning.blockwise.BlockwiseLeftAlignedLearningAlgorithm.Column;
import de.hu_berlin.cchecker.core.models.traces.TraceDataProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceDataBuilder;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;

/**
 * Tests for SRE-learning via {@link BlockwiseLeftAlignedLearningAlgorithm}. 
 */
@RunWith(JUnit4.class)
public class BlockwiseLeftAlignedTest {
	
	/**
	 * Tests the block-wise grouping and column alignment of the {@link BlockwiseLeftAlignedLearningAlgorithm}.
	 */
	@Test
	public void testLeftAlignement() {
		// create a test data set. space indentation indicates the expected alignment and columns
		TraceDataProvider provider = TraceDataBuilder.newTraceData()
				.trace(1, 1,       2,       3, 4, 5, 5, 6)
				.trace(1,          2,       3, 4, 5,    6, 7, 8)
				.trace(3,          2,       5, 8, 5,    1, 7, 8)
				.trace(3, 3, 3, 3, 4, 4, 4, 42)
				.provider();
		List<BlockLetter[]> groupedTraces = BlockwiseLeftAlignedLearningAlgorithm.groupTraces(provider);
		List<Column> leftAlignedTable = BlockwiseLeftAlignedLearningAlgorithm.leftAlignBlockLetters(groupedTraces);
		
		assertEquals("Block-letter reduction works as expected", 
				"[[1^2], [2^1], [3^1], [4^1], [5^2], [6^1]]\n" + 
				"[[1^1], [2^1], [3^1], [4^1], [5^1], [6^1], [7^1], [8^1]]\n" + 
				"[[3^1], [2^1], [5^1], [8^1], [5^1], [1^1], [7^1], [8^1]]\n" +
				"[[3^4], [4^3], [42^1]]\n",
				blockLetterListToString(groupedTraces));
		
		assertEquals("Column statistics are accurate", 
				"[Column with characters [1, 3]\n" + 
				"	 Character 1:#(x1)=1 #(x2)=1 \n" + 
				"	 Character 3:#(x1)=1 #(x4)=1 \n" + 
				", Column with characters [2, 4]\n" + 
				"	 Character 2:#(x1)=3 \n" + 
				"	 Character 4:#(x3)=1 \n" + 
				", Column with characters [3, 5, 42]\n" + 
				"	 Character 3:#(x1)=2 \n" + 
				"	 Character 5:#(x1)=1 \n" + 
				"	 Character 42:#(x1)=1 \n" + 
				", Column with characters [-1, 4, 8]\n" + 
				"	 Character -1:#(x1)=1 \n" + 
				"	 Character 4:#(x1)=2 \n" + 
				"	 Character 8:#(x1)=1 \n" + 
				", Column with characters [-1, 5]\n" + 
				"	 Character -1:#(x1)=1 \n" + 
				"	 Character 5:#(x1)=2 #(x2)=1 \n" + 
				", Column with characters [-1, 1, 6]\n" + 
				"	 Character -1:#(x1)=1 \n" + 
				"	 Character 1:#(x1)=1 \n" + 
				"	 Character 6:#(x1)=2 \n" + 
				", Column with characters [-1, 7]\n" + 
				"	 Character -1:#(x1)=2 \n" + 
				"	 Character 7:#(x1)=2 \n" + 
				", Column with characters [-1, 8]\n" + 
				"	 Character -1:#(x1)=2 \n" + 
				"	 Character 8:#(x1)=2 \n" + 
				"]",
				leftAlignedTable.toString());
	}
	
	/**
	 * SRE output test for the sample set of task 1.
	 */
	@Test
	public void testTask1DataSet() {
		BlockwiseLeftAlignedLearningAlgorithm algorithm = new BlockwiseLeftAlignedLearningAlgorithm("0.############");
		TraceDataset task1TraceDataset = TraceTestUtils.getTask1TraceDataset();
		
		String learnedSRE = algorithm.learnModel(task1TraceDataset);
		assertEquals("The learned SRE looks as expected",  
				"((start)[21]) : ((toll : (toll*0.470588235294))[17] + (won : (won*0.5))[4]) : (((EPSILON*0.0))[8] + (won : won : won : (won*0.5))[2] + (lost : (lost*0.545454545455))[11])"
				, learnedSRE);
	}
	
	@Test
	public void testPrecisionParameter() {
		// create a test data set. space indentation indicates the expected alignment and columns
		TraceDataProvider provider = TraceDataBuilder.newTraceData()
				.trace(1, 2, 4)
				.trace(1, 2, 3)
				.labelMapping(TraceDataBuilder.ASCII_MAPPING_FUNCTION)
				.provider();
		// create the algorithm using the created provider
		BlockwiseLeftAlignedLearningAlgorithm algorithm = new BlockwiseLeftAlignedLearningAlgorithm(provider);
		// learn the model, input is null, since provider has a hard-coded input
		String model = algorithm.learnModel(null);
		assertEquals("Learned SRE looks as expected", "((B)[2]) : ((C)[2]) : ((D)[1] + (E)[1])", model);
	
	}
	
	/**
	 * Tests the learning with an empty trace data set as input.
	 */
	@Test
	public void testEmptyInput() {
		// create an empty trace data set
		TraceDataProvider provider = TraceDataBuilder.newTraceData().provider();
		// create the algorithm with the empty data set provider
		BlockwiseLeftAlignedLearningAlgorithm a = new BlockwiseLeftAlignedLearningAlgorithm(provider);
		String sre = a.learnModel(null);
		
		// this should yield in an empty SRE
		assertEquals("SRE is empty", "", sre);
	}
	
	/**
	 * Tests the methods of the algorithm data structure {@link Column}.
	 */
	@Test
	public void testColumnMethods() {
		// create a test data set. space indentation indicates the expected alignment and columns
		TraceDataProvider provider = TraceDataBuilder.newTraceData()
				.trace(1, 1,       2,       3, 4, 5, 5, 6)
				.trace(1,          2,       3, 4, 5,    6, 7, 8)
				.trace(3,          2,       5, 8, 5,    1, 7, 8)
				.trace(3, 3, 3, 3, 4, 4, 4, 42)
				.provider();
		List<BlockLetter[]> groupedTraces = BlockwiseLeftAlignedLearningAlgorithm.groupTraces(provider);
		List<Column> leftAlignedTable = BlockwiseLeftAlignedLearningAlgorithm.leftAlignBlockLetters(groupedTraces);
		
		Column firstColumn = leftAlignedTable.get(0);
		
		SortedSet<Integer> distinctCharacters = firstColumn.getDistinctCharactersInColumn();
		for (Integer character : distinctCharacters) {
			assertNotEquals("Occurences must not be 0", 0, firstColumn.getCharacterOccurence(character));
			
			Set<Integer> repetitions = firstColumn.getCharacterRepetitions(character);
			assertFalse("There must be at least one number of repetitions", repetitions.isEmpty());
			
			for (Integer r : repetitions) {
				assertNotEquals("Number of same repetition is not 0", 0, firstColumn.getOccurencesOfCharacterRepetition(character, r));
			}
		}
		
		// test with character that is not to be found in the first column
		assertFalse("Character 2 does not occur in the first column", distinctCharacters.contains(2));
		assertTrue("Character 2 repetitions-set is empty", firstColumn.getCharacterRepetitions(2).isEmpty());
		assertEquals("character 2 number of occurences is 0", 0, firstColumn.getCharacterOccurence(2));
		
		assertEquals("Character 2 repetitions for repetition 1 is 0", 0, firstColumn.getOccurencesOfCharacterRepetition(2, 1));
	}
	
	/**
	 * Computes a string representation of a given list of {@link BlockLetter}.
	 */
	private String blockLetterListToString(List<BlockLetter[]> list) {
		StringBuilder repr = new StringBuilder();
		for (BlockLetter[] blockLetter : list) {
			repr.append(Arrays.deepToString(blockLetter) + "\n");
		}
		return repr.toString();
	}
}
