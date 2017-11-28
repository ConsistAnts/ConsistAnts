package de.hu_berlin.cchecker.core.learning.blockwise;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;
import de.hu_berlin.cchecker.core.learning.LearningAlgorithm.LearningAlgorithmException;
import de.hu_berlin.cchecker.core.models.traces.TraceDataProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataSetProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * A learning algorithm based on a blockwise left-alignment method for learning
 * (stochastic) regular expressions from trace data sets.
 */
public class BlockwiseLeftAlignedLearningAlgorithm implements StopWatchable {
	private TraceDataProvider dataProvider;
	private SimpleSREBuilder sreBuilder;
	private Stopwatch stopwatch;

	/**
	 * Instantiates a new {@link BlockwiseLeftAlignedLearningAlgorithm} with the
	 * given trace data set provider.
	 */
	public BlockwiseLeftAlignedLearningAlgorithm(TraceDataProvider traceDataProvider) {
		this(traceDataProvider, "0.#####");
	}

	/**
	 * Instantiates a new {@link BlockwiseLeftAlignedLearningAlgorithm} with the
	 * given decimal format.
	 */
	public BlockwiseLeftAlignedLearningAlgorithm(String decimalFormatPattern) {
		this(new TraceDataSetProvider(), decimalFormatPattern);
	}

	/**
	 * Instantiates a new {@link BlockwiseLeftAlignedLearningAlgorithm} with the
	 * given trace data set provider and decimal format.
	 * 
	 * @param traceDataProvider
	 *            The provider that is used to retrieve data from learning inputs
	 * @param decimalFormatPattern
	 *            The decimal format that is used to format decimals in the learned
	 *            SREs. See {@link DecimalFormat#DecimalFormat(String)} for
	 *            documentation.
	 */
	public BlockwiseLeftAlignedLearningAlgorithm(TraceDataProvider traceDataProvider, String decimalFormatPattern) {
		dataProvider = traceDataProvider;
		sreBuilder = new SimpleSREBuilder(decimalFormatPattern);
	}

	/**
	 * Instantiates a new {@link BlockwiseLeftAlignedLearningAlgorithm} with default
	 * trace data set provider.
	 */
	public BlockwiseLeftAlignedLearningAlgorithm() {
		this(new TraceDataSetProvider());
	}

	public String learnModel(TraceDataset dataset) {
		dataProvider.setInput(dataset);

		stopwatch = new Stopwatch();
		stopwatch.start("Blockwise left-aligned SRE learning");
		// group traces into block-letters
		List<BlockLetter[]> groupedTraces = groupTraces(dataProvider);
		stopwatch.checkpoint("Traces grouped");
		
		// perform left-alignment
		List<Column> leftAlignedTable = leftAlignBlockLetters(groupedTraces);
		stopwatch.checkpoint("Block letters left-aligned");
		
		// build SRE
		String sre = buildSRE(leftAlignedTable, dataProvider.getLabelMap());
		stopwatch.checkpoint("SRE built");
		
		stopwatch.finish();
		
		return sre;
	}
	
	/**
	 * Returns the finished {@link Stopwatch} that was used in most
	 * recent invocation of {@link #learnModel(TraceDataset)}.
	 */
	public Stopwatch getStopwatch() {
		return stopwatch;
	}

	/**
	 * Builds the SRE for the given label-mapping and left-aligned column list.
	 */
	String buildSRE(List<Column> columns, Map<Integer, String> labelMapping) {
		List<String> columnExpressions = new ArrayList<String>(columns.size());

		for (Column c : columns) {
			columnExpressions.add(sreBuilder.paranthesis(buildSREForColumn(c, labelMapping)));
		}

		return sreBuilder.concat(columnExpressions.toArray(new String[columnExpressions.size()]));
	}

	/**
	 * Builds and returns the SRE for the given column using the given
	 * label-mapping.
	 */
	String buildSREForColumn(Column c, Map<Integer, String> labelMapping) {
		List<String> columnAlternatives = new ArrayList<String>(c.getDistinctCharactersInColumn().size());

		for (Integer character : c.getDistinctCharactersInColumn()) {
			Set<Integer> repetitions = c.getCharacterRepetitions(character);
			if (repetitions.isEmpty()) {
				throw new LearningAlgorithmException("Failed to build SRE, character reptitions set was empty");
			}
			// determine lower bound repetition and number of occurences of that repetition
			int lowerBound = Collections.min(repetitions);
			int lowerBoundOccurence = c.getOccurencesOfCharacterRepetition(character, lowerBound);
			// number of all block-letters for character in this column
			int characterOccurence = c.getCharacterOccurence(character);
			// probability of repetition being more than lower bound is given by
			double probabilityOfRepetition = 1 - ((double) lowerBoundOccurence / characterOccurence);

			String atomicCharacterExpression = sreBuilder.atomic(character, labelMapping);

			String characterExpression = sreBuilder.factorConcat(atomicCharacterExpression, lowerBound);

			// if there are repetitions different from lower-bound, add kleene-star
			if (probabilityOfRepetition > 0) {
				characterExpression = sreBuilder.concat(characterExpression,
						sreBuilder.kleeneStar(atomicCharacterExpression, probabilityOfRepetition));
			}

			String innerExpression = characterExpression;
			// add the according character alternative component to the column alternatives
			columnAlternatives.add(sreBuilder.altenativeComponent(innerExpression, characterOccurence));
		}

		return sreBuilder.alternative(columnAlternatives.toArray(new String[columnAlternatives.size()]));
	}

	/**
	 * Converts the traces given by the provider into a list of block-letter grouped
	 * traces.
	 */
	static List<BlockLetter[]> groupTraces(TraceDataProvider provider) {
		return provider.stream().map(new BlockLetterReducer()).collect(Collectors.toList());
	}

	/**
	 * Performs the left alignment of a given set of block-letter grouped traces and
	 * returns the resulting table as a list of {@link Column}s.
	 */
	static List<Column> leftAlignBlockLetters(List<BlockLetter[]> blockLetters) {
		if (blockLetters.isEmpty()) {
			return Collections.emptyList();
		}
		// determine maximum block-letter trace length
		Integer maximumLength = Collections
			.max(blockLetters.stream().map(letters -> letters.length).collect(Collectors.toList()));
		if (null == maximumLength) {
			throw new LearningAlgorithmException(
					"Failed to left-align block letters. Couldn't determine maximum length.");
		}

		int numberOfColumns = maximumLength;
		List<Column> columns = new ArrayList<>(numberOfColumns);

		// build left-aligned columns
		for (int c = 0; c < numberOfColumns; c++) {
			List<BlockLetter> columnBlockLetters = new ArrayList<>();
			// for each block-letter trace add the block letter of the current column
			for (BlockLetter[] blockLetter : blockLetters) {
				if (blockLetter.length <= c) {
					// if this trace has no characters in this column, add an epsilon
					columnBlockLetters.add(BlockLetter.EPSILON_BLOCK_LETTER);
				} else {
					// otherwise add the block letter of this column
					columnBlockLetters.add(blockLetter[c]);
				}
			}
			;
			// finally instantiate the new column
			columns.add(new Column(columnBlockLetters));
		}

		return columns;
	}

	/**
	 * A {@link Column} represents a column in the left-aligned trace data set of
	 * block letters.
	 */
	public static class Column {
		private final SortedSet<Integer> distinctCharactersInColumn;
		// maps letters to how often they occur in this column
		private final OccurrenceCounter<Integer> letterOccurences;
		// maps letters to how often which number of repetitions occurs for the letter
		private final Map<Integer, OccurrenceCounter<Integer>> letterRepetitionOccurences;

		public Column(List<BlockLetter> blockLettersInColumn) {
			// collect distinct characters in column
			distinctCharactersInColumn = new TreeSet<>(
					blockLettersInColumn.stream().map(l -> l.character).collect(Collectors.toList()));

			// initialize letter maps
			letterRepetitionOccurences = new HashMap<>();
			letterOccurences = new OccurrenceCounter<Integer>();

			blockLettersInColumn.stream().forEach(blockLetter -> {
				// increase letter occurence
				letterOccurences.increase(blockLetter.character);

				// increase repetition occurence for this letter
				// make sure there is an occurrence counter for this character
				letterRepetitionOccurences.putIfAbsent(blockLetter.character, new OccurrenceCounter<>());
				letterRepetitionOccurences.get(blockLetter.character).increase(blockLetter.repetitions);
			});
		}

		/**
		 * Returns a distinct set of characters that can be found in this column.
		 */
		public SortedSet<Integer> getDistinctCharactersInColumn() {
			return distinctCharactersInColumn;
		}

		/**
		 * Returns a set of numbers that represent all different repetitions that can be
		 * found for this character in this column.
		 */
		public Set<Integer> getCharacterRepetitions(int character) {
			if (!getDistinctCharactersInColumn().contains(character)) {
				return Collections.emptySet();
			}
			OccurrenceCounter<Integer> repetitionsOccurences = letterRepetitionOccurences.get(character);
			return repetitionsOccurences.keySet();
		}

		/**
		 * Returns the number of block letters in this column that correspond to the
		 * given letter and repetition.
		 * 
		 * @param character
		 *            The grouped character
		 * @param repetitions
		 *            The number of grouped characters (repetitions)
		 */
		public int getOccurencesOfCharacterRepetition(int character, int repetitions) {
			if (!getDistinctCharactersInColumn().contains(character)) {
				return 0;
			}
			OccurrenceCounter<Integer> repetitionsOccurences = letterRepetitionOccurences.get(character);
			return repetitionsOccurences.getOrDefault(repetitions, 0);
		}

		/**
		 * Returns the number of block-letters for this character in this column.
		 */
		public int getCharacterOccurence(int character) {
			Integer occurrences = this.letterOccurences.get(character);
			return null != occurrences ? occurrences : 0;
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("Column with characters " + this.distinctCharactersInColumn.toString() + "\n");
			for (Integer character : distinctCharactersInColumn) {
				result.append(String.format("\t Character %d:", character));
				Set<Integer> reps = getCharacterRepetitions(character);
				for (Integer repetitionCount : reps) {
					result.append("#(x" + repetitionCount + ")="
							+ getOccurencesOfCharacterRepetition(character, repetitionCount) + " ");
				}
				result.append("\n");
			}
			return result.toString();
		}
	}
}
