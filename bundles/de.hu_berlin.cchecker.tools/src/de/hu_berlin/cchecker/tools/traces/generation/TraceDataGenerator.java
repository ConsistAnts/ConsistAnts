package de.hu_berlin.cchecker.tools.traces.generation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.function.Function;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

/**
 * Internal trace data generator class. Use {@link TraceDataGeneratorUtils} to
 * actually generate trace data.
 */
class TraceDataGenerator {
	/**
	 * Builder class to allow for easy creation of {@link TraceDataIterator}s.
	 */
	static class TraceDataIteratorBuilder {
		final ProbabilisticAutomata automaton;
		int lengthLimit = 0;
		boolean hasLimit = false;
		TraceGeneratorQueueProvider queueProvider = new TraceGeneratorQueueProvider.DefaultQueueElementProvider();
		Function<TraceGeneratorQueueElement, Boolean> terminationCriteria = element -> false;

		/**
		 * Creates a new builder with default values for the given automaton.
		 * 
		 * <code> 
		 * That is:
		 *  
		 * lengthLimit = 0
		 * hasLimit = false
		 * elementProvider = DefaultQueueElementProvider
		 * terminationCriteria = e -> false
		 * </code>
		 */
		static TraceDataIteratorBuilder defaults(ProbabilisticAutomata automaton) {
			return new TraceDataIteratorBuilder(automaton);
		}

		private TraceDataIteratorBuilder(ProbabilisticAutomata automaton) {
			// see default field values for defaults
			this.automaton = automaton;
		}

		/**
		 * Sets a length limit for the iterator.
		 */
		public TraceDataIteratorBuilder lengthLimit(int limit) {
			this.lengthLimit = limit;
			this.hasLimit = true;
			return this;
		}

		/**
		 * Sets the element provider for the iterator.
		 */
		public TraceDataIteratorBuilder elementProvider(TraceGeneratorQueueProvider provider) {
			this.queueProvider = provider;
			return this;
		}
		
		/**
		 * Sets the termination criteria for the trace data iterator.
		 * 
		 * This can be used to set an early end-of-stream.
		 */
		public TraceDataIteratorBuilder terminationCriteria(Function<TraceGeneratorQueueElement, Boolean> terminationCriteria) {
			this.terminationCriteria = terminationCriteria;
			return this;
		}

		/**
		 * Create the configured instance of the iterator.
		 * 
		 * @return
		 */
		public TraceDataIterator create() {
			return new TraceDataIterator(automaton, lengthLimit, hasLimit, queueProvider, terminationCriteria);
		}
	}

	/**
	 * Trace-iterator for a given automaton.
	 * 
	 * Do not instantiate this class directly. Instead, use
	 * {@link TraceDataIteratorBuilder} for retrieving instances of a specifically
	 * configured iterator.
	 * 
	 * <p>
	 * Note that this class is only package-visible because it is **NOT SAFE** to
	 * use it as an iterator without any further do. Due to the nature of the
	 * automaton traversal the co-variant between {@link #hasNext()} and
	 * {@link #next()} cannot be assured, which can lead to NPEs during use.
	 * (Especially when using them in enhanced for-loops)
	 * 
	 * Please use static methods of the container class to access lazily-initialised
	 * generated trace data
	 * </p>
	 */
	static class TraceDataIterator implements Iterator<Integer[]> {

		private final PriorityQueue<TraceGeneratorQueueElement> queue;
		private final int lengthLimit;
		private final boolean hasLimit;
		private final int epsilonId;

		private final TraceGeneratorQueueProvider queueElementProvider;
		
		// A termination criteria that allows for an early termination based on the 
		// the current queue element.
		private final Function<TraceGeneratorQueueElement, Boolean> terminationCriteria;

		private TraceDataIterator(ProbabilisticAutomata automaton, int lengthLimit, boolean hasLimit,
				TraceGeneratorQueueProvider elementProvider, Function<TraceGeneratorQueueElement, Boolean> terminationCriteria) {
			if (null == automaton.getStartState()) {
				throw new IllegalArgumentException("The given automaton doesn't have a declared start state.");
			}

			this.terminationCriteria = terminationCriteria;
			this.queueElementProvider = elementProvider;
			this.queue = queueElementProvider.createPriorityQueue();

			// initialise builder queue with start state element and empty prefix
			TraceGeneratorQueueElement startQueueElement = queueElementProvider.getInitialElement(automaton);
			queue.add(startQueueElement);

			this.lengthLimit = lengthLimit;
			this.hasLimit = hasLimit;
			this.epsilonId = ProbabilisticAutomataUtils.getEpsilonTransitionId(automaton);
		}

		/**
		 * Generates the next trace for the underlying {@link ProbabilisticAutomata}.
		 * 
		 * Note that the running time of this method might vary depending on the current
		 * state of the iterator.
		 * 
		 * Returns {@code null} if the end of the iterator was reached.
		 * 
		 */
		public Integer[] next() {
			while (!queue.isEmpty()) {
				TraceDataGenerator.TraceGeneratorQueueElement current = queue.poll();
				
				// check for early termination
				if (terminationCriteria.apply(current)) {
					// remove all elements from the queue
					queue.clear();
					// end this iterator
					break;
				}
				
				// only follow transitions if maximum length isn't reached yet or there is no
				// limit
				if (!hasLimit || current.getPrefix().length < lengthLimit) {
					for (ProbabilisticTransition transition : current.getState().getOutgoingTransitions()) {
						createQueueElement(current, transition);
					}
				}

				// if we're in a terminating state, add the current trace to the result
				if (current.getState().getTerminatingProbability() > 0) {
					return current.getPrefix();
				}
			}
			// Signal end of iterator.
			// We have to return null instead of throwing a NoSuchElementException, to allow
			// the consumption of this iterator by Stream.
			return null;
		}

		/**
		 * Creates a new queue element for the given transition and current queue
		 * element.
		 */
		private void createQueueElement(TraceGeneratorQueueElement current, ProbabilisticTransition transition) {
			boolean isEpsilonTransition = transition.getTransitionId() == epsilonId;
			// do not count epsilon as a trace element
			int transitionLength = isEpsilonTransition ? 0 : 1;
			// create a new prefix array by adding the transition label ID
			Integer[] newPrefix = Arrays.copyOf(current.getPrefix(), current.getPrefix().length + transitionLength);

			if (!isEpsilonTransition) {
				newPrefix[current.getPrefix().length] = transition.getTransitionId();
			}

			// add a traversal element for all traces that have the new prefix
			TraceGeneratorQueueElement element = queueElementProvider.getElement(current, transition, newPrefix);
			queue.add(element);
		}

		@Override
		public boolean hasNext() {
			// Due to the nature of our traversal, this might not be accurate
			// since all remaining elements on the queue might be dead-ends that never
			// lead in a terminating state. Thus, it is always a possibility that {@link
			// #next} returns {@code null} without {@link #hasNext} being false.
			return !queue.isEmpty();
		}

	}

	/**
	 * An element on the traversal queue of a trace builder.
	 * 
	 * This elements represents a state of the automaton traversal.
	 */
	public static class TraceGeneratorQueueElement {
		private final ProbabilisticState state;
		private final Integer[] prefix;

		public TraceGeneratorQueueElement(ProbabilisticState s, Integer[] prefix) {
			super();
			this.state = s;
			this.prefix = prefix;
		}

		/**
		 * The state this traversal-branch is currently in.
		 */
		public ProbabilisticState getState() {
			return state;
		}

		/**
		 * The path of this traversal-branch.
		 */
		public Integer[] getPrefix() {
			return prefix;
		}
	}

	private TraceDataGenerator() {
		// non instantiable utility class
	}
}