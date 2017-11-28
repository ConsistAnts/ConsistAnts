package de.hu_berlin.cchecker.tools.traces.generation;

import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.tools.traces.generation.TraceDataGenerator.TraceGeneratorQueueElement;

/**
 * A {@link SampleSetEstimator} can be used to determine the required sample-set
 * size that is required to reach a specified representation rate.
 */
public class SampleSetEstimator {
	private final ProbabilisticAutomata automaton;
	private final TraceDataProbabilityComputer probabilityComputer;
	private IProgressMonitor monitor = new NullProgressMonitor();

	/**
	 * The summed probability of all collected traces so far This value indicates
	 * how many distinct traces of all possible traces have been included in the
	 * sample set so far.
	 */
	private double summedProbability = 0.0;
	private double minimumIncludedProbability = 1.0;

	private double targetRate = 0.0;

	private int progressReferenceValue = 0;

	/**
	 * Creates a new {@link SampleSetEstimator} for the given automaton that
	 * generates a given maximum number of traces.
	 * 
	 * @param automaton
	 *            The automaton to generate traces for
	 */
	public SampleSetEstimator(ProbabilisticAutomata automaton) {
		this.automaton = automaton;
		this.probabilityComputer = new TraceDataProbabilityComputer(automaton);
	}

	/**
	 * Returns the number of traces that have to be included in a sample set to
	 * reach the given representation rate.
	 */
	public int estimateSampleSizeForRepresentationRate(double rate) {
		this.targetRate = rate;
		
		if (targetRate > 1.0) {
			throw new IllegalArgumentException("The target representation rate must be a value between 0.0 and 1.0");
		}
		
		this.progressReferenceValue = 0;
		this.monitor.setTaskName("Estimating sample set size...");

		// terminate trace generation if the probability change falls below our
		// threshold
		Function<TraceGeneratorQueueElement, Boolean> terminationCriteria = element -> summedProbability > rate
				|| monitor.isCanceled();

		Stream<Integer[]> allPossibleTraces = TraceDataGeneratorUtils.traceStream(automaton,
				new SampleSetGenerator.ProbabilityTraceDataQueueElementProvider(), terminationCriteria);

		allPossibleTraces.forEach(this::processTraceProbability);

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}

		return (int) (1.0 / minimumIncludedProbability);
	}

	/**
	 * Computes the probability of the given trace and determines whether this
	 * specific trace is to be included in the sample set.
	 * 
	 * @returns A list that contains the determined number of copies of the trace.
	 *          Might be empty, if the trace should not be included in the sample
	 *          set.
	 */
	private void processTraceProbability(Integer[] trace) {
		double p = probabilityComputer.getProbabilityOfTrace(trace);

		summedProbability += p;
		
		int currentReferenceValue = (int)((summedProbability / targetRate) * 100);
		if (currentReferenceValue >= progressReferenceValue + 1) {
			progressReferenceValue = currentReferenceValue;
			monitor.worked(1);
		}
		
		minimumIncludedProbability = Math.min(p, minimumIncludedProbability);
	}

	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = SubMonitor.convert(monitor, 100);
	}
}
