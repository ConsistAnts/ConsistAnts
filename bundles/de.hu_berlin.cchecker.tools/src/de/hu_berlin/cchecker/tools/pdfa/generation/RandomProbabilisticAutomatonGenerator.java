package de.hu_berlin.cchecker.tools.pdfa.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;
import de.hu_berlin.cchecker.core.transformations.unreachableremoval.UnreachableStateRemovalTransformationImpl;

/**
 * This class can be used to generate random {@link ProbabilisticAutomata}.
 */
public class RandomProbabilisticAutomatonGenerator implements StopWatchable {

	/**
	 * Default value for the self-cycle probability.
	 * 
	 * @see {@link #createRandom(Range, Range, Range, double)}
	 */
	public static final double DEFAULT_SELF_CYCLE_PROBABILITY = 0.1;

	/**
	 * Default range for the number of states of a random
	 * {@link ProbabilisticAutomata}
	 */
	public static final Range DEFAULT_RANGE_OF_STATES = Range.fromTo(6, 8);

	/**
	 * Default range for the number of outgoing transitions for state in a random
	 * {@link ProbabilisticAutomata}
	 */
	public static final Range DEFAULT_RANGE_OF_OUTGOING_TRANSITIONS_PER_STATE = Range.fromTo(2, 4);

	/**
	 * Default range for the number of distinct labels a random
	 * {@link ProbabilisticAutomata} uses.
	 */
	public static final Range DEFAULT_RANGE_OF_DISTINCT_LABELS = Range.fromTo(3, 8);

	/**
	 * A {@link Range} is a non-empty, inclusive integer-interval with two bounds.
	 */
	public static class Range {
		/**
		 * Returns a new inclusive range from lowerBound to upperBound.
		 * 
		 * @return
		 */
		public static Range fromTo(int lowerBound, int upperBound) {
			if (lowerBound >= upperBound) {
				return new Range(lowerBound, lowerBound);
			}

			return new Range(lowerBound, upperBound);
		}

		public final int lowerBound;
		public final int upperBound;

		private Range(int lowerBound, int upperBound) {
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
		}

		/**
		 * Returns the size of this range. Given by upperBound - lowerBound.
		 */
		public int getSize() {
			return upperBound - lowerBound;
		}

		/**
		 * Returns a random value in this range.
		 */
		public int getRandom(Random r) {
			return this.lowerBound + r.nextInt(getSize() + 1);
		}

		@Override
		public String toString() {
			return this.lowerBound + "..." + this.upperBound;
		}
	}

	public static class ElementBag<E> {
		/**
		 * Instantiates a new element bag with the given elements.
		 * 
		 * @return
		 */
		public static <E> ElementBag<E> of(List<E> elements) {
			return new ElementBag<>(elements);
		}

		private List<E> elements;
		private Range elementRange;

		private ElementBag(List<E> elements) {
			this.elements = elements;
			this.elementRange = Range.fromTo(0, elements.size() - 1);
		}

		/**
		 * Returns a random element from this {@link ElementBag}.
		 * 
		 * @return
		 */
		E getRandom(Random r) {
			return this.elements.get(this.elementRange.getRandom(r));
		}
	}

	private Random random;
	private UnreachableStateRemovalTransformationImpl unreachableTransformation;
	private Stopwatch stopwatch = null;

	/**
	 * Instantiates a new {@link RandomProbabilisticAutomatonGenerator} which
	 * automatically chooses a random seed for each new instance.
	 */
	public RandomProbabilisticAutomatonGenerator() {
		this.random = new Random();
		init();
	}

	/**
	 * Instantiates a new {@link RandomProbabilisticAutomatonGenerator} with the
	 * given seed for randomness.
	 * 
	 * This will yield predictable and reproducible results.
	 */
	public RandomProbabilisticAutomatonGenerator(long seed) {
		this.random = new Random(seed);
		init();
	}

	/**
	 * Initialises this {@link RandomProbabilisticAutomatonGenerator}.
	 */
	private void init() {
		unreachableTransformation = new UnreachableStateRemovalTransformationImpl();
	}

	/**
	 * Creates a new random {@link ProbabilisticAutomata} using the default
	 * randomness ranges.
	 * 
	 * See {@link RandomProbabilisticAutomatonGenerator#DEFAULT_RANGE_OF_STATES},
	 * {@link RandomProbabilisticAutomatonGenerator#DEFAULT_RANGE_OF_OUTGOING_TRANSITIONS_PER_STATE},
	 * {@link RandomProbabilisticAutomatonGenerator#DEFAULT_RANGE_OF_DISTINCT_LABELS}.
	 */
	public ProbabilisticAutomata createRandom() {
		return createRandom(DEFAULT_RANGE_OF_STATES, DEFAULT_RANGE_OF_OUTGOING_TRANSITIONS_PER_STATE,
				DEFAULT_RANGE_OF_DISTINCT_LABELS);
	}

	public ProbabilisticAutomata createRandom(Range numberOfStatesRange, Range numberOfTransitionsRange,
			Range distinctLabelsRange) {
		return createRandom(numberOfStatesRange, numberOfTransitionsRange, distinctLabelsRange,
				DEFAULT_SELF_CYCLE_PROBABILITY);
	}

	/**
	 * Creates a new random {@link ProbabilisticAutomata} with the given parameter
	 * ranges.
	 * 
	 * @param numberOfStatesRange
	 *            The range of number of states to randomly choose from
	 * @param numberOfTransitionsRange
	 *            The range of outgoing transitions to randomly choose from per
	 *            state
	 * @param distinctLabelsRange
	 *            The range of distinct labels (alphabet size) to randomly choose
	 *            from
	 * @param selfCycleProbability
	 *            The probability that a state can have a self-cycle. Note that a
	 *            value of 1.0 doesn't imply a self-cycle for each state.
	 * @return
	 */
	public ProbabilisticAutomata createRandom(Range numberOfStatesRange, Range numberOfTransitionsRange,
			Range distinctLabelsRange, double selfCycleProbability) {

		this.stopwatch = new Stopwatch();
		stopwatch.start("Generate random probabilistic automaton");
		
		if (numberOfStatesRange.lowerBound < 2) {
			throw new IllegalArgumentException("Lower bound for number of states must be >= 2");
		}

		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();

		// generate an alphabet for our random number of distinct characters
		List<String> alphabet = generateAlphabet(distinctLabelsRange.getRandom(random));

		// always start in state 1
		builder.startIn(1);

		final int numberOfStates = numberOfStatesRange.getRandom(random);

		// generate list of all state IDs.
		// this list is shuffled later on
		List<Integer> stateIds = IntStream.range(1, numberOfStates + 1).boxed()
				.collect(Collectors.toCollection(ArrayList::new));

		// This reserved state ID indicates termination in a state.
		// When a random transition to this state is generated, instead of
		// actually creating a transition, the current state should be marked as
		// terminating.
		// The probability of the virtual transition marks the termination probability.
		final int terminatingStateID = -1;
		stateIds.add(terminatingStateID);
		
		stopwatch.checkpoint("Parsed input parameters");

		// creates states
		for (int fromStateId = 1; fromStateId <= numberOfStates; fromStateId++) {
			// number of possible states to transition to
			final List<Integer> availableStateIds = new ArrayList<>(stateIds);

			// for the start state...
			if (fromStateId == 1) {
				// ...we must prevent that there is a transition to the reserved trap state
				// since the start state must have a termination probability of 0.0
				availableStateIds.remove((Integer) terminatingStateID);
			}

			// determine if a self-cycle is possible for this state
			final boolean canHaveSelfCycle = random.nextDouble() > selfCycleProbability && fromStateId != 1;

			if (!canHaveSelfCycle) {
				// delete itself from available state IDs
				availableStateIds.remove((Integer) fromStateId);
			}

			int numberOfTransitions = numberOfTransitionsRange.getRandom(random);

			// there can never be more transitions than states
			numberOfTransitions = Math.min(availableStateIds.size(), numberOfTransitions);

			// there can also never be more transitions than distinct labels
			numberOfTransitions = Math.min(alphabet.size(), numberOfTransitions);

			// generate random distribution for outgoing transition probabilities
			final double[] transitionProbabilities = generateRandomDistribution(numberOfTransitions);

			// Randomise list of states and alphabet
			Collections.shuffle(availableStateIds, random);
			Collections.shuffle(alphabet, random);

			// create transitions
			for (int transitionNumber = 0; transitionNumber < numberOfTransitions; transitionNumber++) {
				// get random state to transition to
				int stateId = availableStateIds.get(transitionNumber); // random since stateIds were shuffled

				// get probability from distribution
				double transitionProbability = transitionProbabilities[transitionNumber];
				// get random label
				String label = alphabet.get(transitionNumber); // random since alphabet was shuffled

				// if the reserved state ID was chosen
				if (terminatingStateID == stateId) {
					// mark state as terminating with given probability
					builder.terminatesInWithProbability(fromStateId, transitionProbability);
				} else { // otherwise, add a regular state transition
					builder.fromTo(fromStateId, stateId, transitionProbability, label);
				}
			}

			// if there were no outgoing transitions...
			if (0 == numberOfTransitions) {
				// all the probability must go to termination
				builder.terminatesIn(fromStateId);
			}
		}
		
		stopwatch.checkpoint("Generated automaton");

		ProbabilisticAutomata automaton = builder.create();
		ProbabilisticAutomata result = unreachableTransformation.transform(automaton);
		
		stopwatch.checkpoint("Remove unreachable states");
		stopwatch.finish();
		
		return result;
	}

	/**
	 * Generates an alphabet of the given size.
	 * 
	 * Note that the alphabet size is limited by the number of available ASCII
	 * characters starting with 'A'.
	 */
	private static List<String> generateAlphabet(int size) {
		List<String> alphabet = new ArrayList<>();
		for (int i = 'A'; i < 'A' + size; i++) {
			alphabet.add(String.valueOf((char) i));
		}
		return alphabet;
	}

	/**
	 * Generates a random probabilistic distribution for the number of given
	 * choices.
	 */
	private double[] generateRandomDistribution(int choices) {
		// if there's nothing to choose from, there is no distribution
		if (choices <= 0) {
			return new double[] {};
		}

		double sum = 0;

		double[] distribution = new double[choices];
		double[] absoluteValues = new double[choices];
		for (int i = 0; i < choices; i++) {
			absoluteValues[i] = random.nextDouble();
			sum += absoluteValues[i];
		}

		if (0.0 == sum) { // in case all absolute values are 0
			return generateRandomDistribution(choices);
		}

		for (int i = 0; i < choices; i++) {
			distribution[i] = absoluteValues[i] / sum;
		}

		return distribution;
	}

	@Override
	public Stopwatch getStopwatch() {
		return this.stopwatch;
	}
}
