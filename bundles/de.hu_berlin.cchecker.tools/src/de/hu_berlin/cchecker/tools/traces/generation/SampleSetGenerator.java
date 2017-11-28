package de.hu_berlin.cchecker.tools.traces.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import de.hu_berlin.cchecker.core.models.traces.OrderedTransition;
import de.hu_berlin.cchecker.core.models.traces.Trace;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDataGenerator.TraceGeneratorQueueElement;

/**
 * A {@link SampleSetGenerator} can be used to generate a sample-set of traces
 * that are consistent with a given {@link ProbabilisticAutomata}.
 * 
 * The sample-set size can be specified via a parameter.
 * 
 * If the maximum number of traces is not reached by the sample set output, then
 * the automaton might not accept enough different traces. Another reason might
 * be that the automaton does accept enough different traces, however, if adding
 * any of the remaining traces, the probabilities of the different traces aren't
 * represented accurately anymore.
 * 
 * See {@link GeneratorResult} for more such information on the result.
 */
public class SampleSetGenerator {
	private final ProbabilisticAutomata automaton;
	private final int maximumNumberOfTraces;
	private final TraceDataProbabilityComputer probabilityComputer;
	
	private IProgressMonitor monitor = new NullProgressMonitor();

	// counter to make sure each generated trace has a unique ID
	private int traceIdCounter = 1;

	// statistics to detect stagnation in probability change
	// so that the generation process can be terminated early.

	/**
	 * The summed probability of all collected traces so far This value indicates
	 * how many distinct traces of all possible traces have been included in the
	 * sample set so far.
	 */
	private double summedProbability = 0.0;

	/** The number of traces in the sample set so far. */
	private int summedNumberOfTraces = 0;

	/**
	 * The average change in probability for each trace that is considered to be
	 * included in the sample set.
	 */
	private double averageSummedProbabilityChange = 0.0;

	/**
	 * The probability change threshold. If the average probability change falls
	 * below this threshold, the generation is terminated.
	 */
	private double probabilityChangeThreshold = 2E-10;

	/**
	 * The generator keeps adding traces to the sample set until the given
	 * representation value or the maximum number of traces is reached.
	 */
	private double representationThreshold = 0.1;

	/**
	 * RoundingStrategy for relative probability to absolute number conversion.
	 */
	private RoundingStrategy roundingStrategy;

	/**
	 * Strategy to use to convert from relative probability of a trace to absolute
	 * number of traces contained in a sample set.
	 * 
	 * The strategy only influences traces for which the absolute values are less than 1.0.
	 */
	public enum RoundingStrategy {
		/**
		 * Add at least one instance of every trace that is found. 
		 */
		UP, 
		/**
		 * Don't add any traces that have an absolute value that is less than 1.
		 */
		DOWN, 
		/**
		 * Add at least one instance of every trace that has an absolute value of more than 0.5.
		 */
		HALF_UP
	}

	/**
	 * Creates a new {@link SampleSetGenerator} for the given automaton that
	 * generates a given maximum number of traces.
	 * 
	 * @param automaton
	 *            The automaton to generate traces for
	 * @param maximumNumberOfTraces
	 *            The maximum number of traces to generate.
	 * @param roundingStrategy
	 *            The {@link RoundingStrategy} to use for conversion between
	 *            relative probability of a trace and absolute number of this trace
	 *            that are contained in the sample set.
	 */
	public SampleSetGenerator(ProbabilisticAutomata automaton, int maximumNumberOfTraces,
			RoundingStrategy roundingStrategy) {
		this.automaton = automaton;
		this.maximumNumberOfTraces = maximumNumberOfTraces;
		this.probabilityComputer = new TraceDataProbabilityComputer(automaton);
		this.roundingStrategy = roundingStrategy;
	}

	/**
	 * Represents the result of a {@link SampleSetGenerator}.
	 */
	public static final class GeneratorResult {
		private final TraceDataset dataSet;
		private final int numberOfTraces;
		/**
		 * Specifies what part of all possible traces is included in this result.
		 */
		private final double representationRate;

		private GeneratorResult(TraceDataset dataSet, int numberOfTraces, double representationRate) {
			super();
			this.dataSet = dataSet;
			this.numberOfTraces = numberOfTraces;
			this.representationRate = representationRate;
		}

		@Override
		public String toString() {
			return "Result contains " + getNumberOfTraces() + " traces that represent " + getRepresentationRate()
					+ " of all possible inputs/traces.";
		}

		public TraceDataset getDataSet() {
			return dataSet;
		}

		public int getNumberOfTraces() {
			return numberOfTraces;
		}

		public double getRepresentationRate() {
			return representationRate;
		}
	}

	/**
	 * Returns a possible and random sample set for the given automaton.
	 */
	public GeneratorResult generatePossibleSampleSet() {
		monitor.setTaskName("Generating sample set... (" + maximumNumberOfTraces + " traces)");
		// terminate trace generation if the probability change falls below our
		// threshold
		Function<TraceGeneratorQueueElement, Boolean> terminationCriteria = element -> ((averageSummedProbabilityChange != 0
				&& averageSummedProbabilityChange < getProbabilityChangeThreshold()
				&& summedProbability > this.getRepresentationThreshold()) ||
				monitor.isCanceled());

		Stream<Integer[]> allPossibleTraces = TraceDataGeneratorUtils.traceStream(automaton,
				new ProbabilityTraceDataQueueElementProvider(), terminationCriteria);

		List<Integer[]> rawTraces = allPossibleTraces.map(this::multiplyByProbability)
			.flatMap(List<Integer[]>::stream)
			.limit(maximumNumberOfTraces)
			.collect(Collectors.toList());
		
		// early exit if generation was cancelled
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		
		List<Trace> traces = rawTraces.stream().map(this::convertToTrace)
				.collect(Collectors.toList());

		TraceDataset dataSet = new TraceDataset(new HashMap<>(automaton.getTransitionLabels().map()), traces);
		return new GeneratorResult(dataSet, summedNumberOfTraces, summedProbability);
	}

	static class ProbabilityTraceDataQueueElementProvider implements TraceGeneratorQueueProvider {

		@Override
		public TraceGeneratorQueueElement getElement(TraceGeneratorQueueElement current,
				ProbabilisticTransition traversedTransition, Integer[] newPrefix) {
			ProbabilityTraceDataQueueElement currentProbabilisticState = (ProbabilityTraceDataQueueElement) current;

			return new ProbabilityTraceDataQueueElement(traversedTransition.getTarget(), newPrefix,
					currentProbabilisticState.getProbability() * traversedTransition.getProbability());
		}

		@Override
		public TraceGeneratorQueueElement getInitialElement(ProbabilisticAutomata automaton) {
			return new ProbabilityTraceDataQueueElement(automaton.getStartState(), new Integer[] {}, 1.0);
		}

		@Override
		public PriorityQueue<TraceGeneratorQueueElement> createPriorityQueue() {
			return new PriorityQueue<>(new ProbabilityTraceDataQueueElementComparator());
		}
	}

	/**
	 * A custom comparator for {@link ProbabilityTraceDataQueueElement}s that keeps
	 * them in descending order according to the probability of the traces they
	 * represent.
	 */
	private static class ProbabilityTraceDataQueueElementComparator implements Comparator<TraceGeneratorQueueElement> {
		@Override
		public int compare(TraceGeneratorQueueElement o1, TraceGeneratorQueueElement o2) {
			ProbabilityTraceDataQueueElement e1 = (ProbabilityTraceDataQueueElement) o1;
			ProbabilityTraceDataQueueElement e2 = (ProbabilityTraceDataQueueElement) o2;

			double diff = (e2.getProbability() - e1.getProbability());
			if (diff > 0) {
				return 1;
			} else if (diff < 0) {
				return -1;
			} else {
				return 0;
			}
		}

	}

	/**
	 * A custom {@link TraceGeneratorQueueElement} that also keeps track of the
	 * probability of the current branch.
	 */
	private static class ProbabilityTraceDataQueueElement extends TraceGeneratorQueueElement {
		private final double probability;

		public ProbabilityTraceDataQueueElement(ProbabilisticState state, Integer[] prefix, double probability) {
			super(state, prefix);
			this.probability = probability;
		}

		public double getProbability() {
			return probability;
		}

	}

	/**
	 * Converts a raw-data trace to a {@link Trace} model instance.
	 */
	private Trace convertToTrace(Integer[] trace) {
		List<OrderedTransition> transitions = new ArrayList<>(trace.length);
		for (int i = 0; i < trace.length; i++) {
			transitions.add(new OrderedTransition(trace[i], i));
		}
		return new Trace(Integer.toString(traceIdCounter++), transitions);
	}

	/**
	 * Computes the probability of the given trace and determines whether this
	 * specific trace is to be included in the sample set.
	 * 
	 * @returns A list that contains the determined number of copies of the trace.
	 *          Might be empty, if the trace should not be included in the sample
	 *          set.
	 */
	private List<Integer[]> multiplyByProbability(Integer[] trace) {
		
		// Skip empty traces, there is no such thing
		if (0 == trace.length) {
			return Collections.emptyList();
		}
		
		double p = probabilityComputer.getProbabilityOfTrace(trace);

		int numberOfTraces = getNumberOfTracesForProbability(p);

		if (numberOfTraces > 0) {
			summedProbability += p;
			summedNumberOfTraces += numberOfTraces;
		}
		averageSummedProbabilityChange = (averageSummedProbabilityChange * (summedNumberOfTraces - 1) + p)
				/ summedNumberOfTraces;
		
		monitor.worked(numberOfTraces);
		
		return Collections.nCopies(numberOfTraces, trace);
	}

	/**
	 * Converts the given probability to the number of traces this probability would 
	 * yield in a sample set.
	 */
	private int getNumberOfTracesForProbability(double p) {
		// determine the absolute number of copies of this trace that should be included
		double absoluteNumberOfTraces = p * maximumNumberOfTraces;
		
		switch (this.roundingStrategy) {
		case DOWN:
			return (int) absoluteNumberOfTraces;
		case UP:
			return Math.max(1, (int) absoluteNumberOfTraces);
		case HALF_UP:
			if (absoluteNumberOfTraces >= 1) {
				return (int) absoluteNumberOfTraces;
			} else {
				return (int) absoluteNumberOfTraces + (absoluteNumberOfTraces > 0.5 ? 1 : 0);
			}
		default:
			return 0; // should never happen
		}
		
	}

	public double getProbabilityChangeThreshold() {
		return probabilityChangeThreshold;
	}

	public void setProbabilityChangeThreshold(double probabilityChangeThreshold) {
		this.probabilityChangeThreshold = probabilityChangeThreshold;
	}

	public double getRepresentationThreshold() {
		return representationThreshold;
	}

	public void setRepresentationThreshold(double representationThreshold) {
		this.representationThreshold = representationThreshold;
	}

	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = SubMonitor.convert(monitor, maximumNumberOfTraces);
	}
}
