package de.hu_berlin.cchecker.tools.traces.generation;

import java.util.Comparator;
import java.util.PriorityQueue;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDataGenerator.TraceGeneratorQueueElement;

/**
 * A provider that can be supplied to methods in {@link TraceDataGeneratorUtils}
 * to influence the order in which traces are generated (order of traversal of
 * the automaton).
 * 
 * The parsing of the automaton is done using a priority queue to decide which
 * branch to follow next. By providing a custom implementation of this provider,
 * one can control how the automaton is traversed and in which order the
 * generated traces are returned.
 */
public interface TraceGeneratorQueueProvider {
	/**
	 * Returns a new {@link TraceGeneratorQueueElement} for the given parameters.
	 * 
	 * @param current
	 *            The current state
	 * @param traversedTransition
	 *            The traversed transition. Might be <code>null</code> which means,
	 *            that no transition has been traversed.
	 * @param newPrefix
	 *            The prefix that was produced by traversing the given transition.
	 * @return A new {@link TraceGeneratorQueueElement}
	 */
	TraceGeneratorQueueElement getElement(TraceGeneratorQueueElement current,
			ProbabilisticTransition traversedTransition, Integer[] newPrefix);

	/**
	 * Returns the initial {@link TraceGeneratorQueueElement} for the traversal
	 * queue.
	 * 
	 * In most cases this method will infer an initial element from the initial
	 * state of the given automaton.
	 */
	TraceGeneratorQueueElement getInitialElement(ProbabilisticAutomata automaton);

	/**
	 * Creates a new {@link PriorityQueue}.
	 * 
	 * Note that in this method it is possible to specify a custom comparator for the 
	 * priority queue. In this way it is possible to control the traversal order.
	 */
	PriorityQueue<TraceGeneratorQueueElement> createPriorityQueue();

	/**
	 * A comparator that orders the queue elements in descending order with regard 
	 * to the length of the trace they represent.
	 */
	public static final Comparator<? super TraceGeneratorQueueElement> COMPARE_BY_TRACE_LENGTH = (e1,
			e2) -> e1.getPrefix().length - e2.getPrefix().length;

	/**
	 * A simple default implementation of {@link TraceGeneratorQueueElement} that
	 * creates plain {@link TraceGeneratorQueueElement} with the new state and
	 * prefix.
	 * 
	 * This provider make sure that the output of the trace generation is ordered by
	 * the length of the traces.
	 */
	public static class DefaultQueueElementProvider implements TraceGeneratorQueueProvider {
		@Override
		public TraceGeneratorQueueElement getElement(TraceGeneratorQueueElement current,
				ProbabilisticTransition traversedTransition, Integer[] newPrefix) {
			return new TraceGeneratorQueueElement(traversedTransition.getTarget(), newPrefix);
		}

		@Override
		public TraceGeneratorQueueElement getInitialElement(ProbabilisticAutomata automaton) {
			return new TraceGeneratorQueueElement(automaton.getStartState(), new Integer[] {});
		}

		@Override
		public PriorityQueue<TraceGeneratorQueueElement> createPriorityQueue() {
			return new PriorityQueue<>(COMPARE_BY_TRACE_LENGTH);
		}
	}
}