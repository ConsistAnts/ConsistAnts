package de.hu_berlin.cchecker.core.checking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.google.common.collect.HashBiMap;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReportBuilder;
import de.hu_berlin.cchecker.core.learning.blockwise.CombineMap;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.Trace;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;
import de.hu_berlin.cchecker.core.transformations.Transformation.ConditionNotFulfilledException;
import de.hu_berlin.cchecker.core.transformations.mapping.LabelMappingTransformation;

/**
 * A consistency checking algorithm based on footprint matrices.
 * 
 * The algorithm creates probabilistic footprint matrices for the trace data set
 * and the given automaton and measures consistency by comparing the results.
 * 
 * @author HorazVitae
 */
public class FootprintMatrixCheckingAlgorithm implements CheckingAlgorithm, StopWatchable {
	
	
	private Stopwatch stopwatch;
	
	private double traversalThreshold = AutomatonMatrixGenerator.DEFAULT_TRAVERSAL_THRESHOLD;
	
	@Override
	public ConsistencyReport performConsistencyCheck(ProbabilisticAutomata model, TraceDataset dataset,
			IProgressMonitor monitor) {
		SubMonitor checkingMonitor = SubMonitor.convert(monitor, 1);

		// Make sure model and trace dataset are using the same ID mapping
		LabelMappingTransformation labelMappingTransformation = new LabelMappingTransformation(
				HashBiMap.create(dataset.getTransitionIdToLabel()).inverse());

		ProbabilisticAutomata transformed;
		try {
			transformed = labelMappingTransformation.safeTransform(model);
		} catch (ConditionNotFulfilledException e) {
			// if the label mapping transformation fails, we can't perform the consistency
			// check.
			throw new IllegalArgumentException("Input trace data set and automaton do not have a compatible alphabet");
		}

		checkingMonitor.setTaskName("Checking consistency");
		ConsistencyReport report = doPerformCheck(dataset, transformed, checkingMonitor.split(1));
		checkingMonitor.done();

		return report;
	}

	/**
	 * Main function for calling all components of the algorithm.
	 * 
	 * Report progress via the given {@link IProgressMonitor}.
	 */
	private ConsistencyReport doPerformCheck(TraceDataset traces, ProbabilisticAutomata auto, IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 5);
		subMonitor.subTask("Preprocessing...");

		stopwatch = new Stopwatch();
		stopwatch.start("FP-based consistency checking algorithm");
		
		Map<Integer, String> labelMapping = traces.getTransitionIdToLabel();
		Map<Integer, Integer> matrixIndices = buildMatrixIndicesMap(labelMapping);

		subMonitor.split(1);
		stopwatch.checkpoint("Initialized working alphabet");
		subMonitor.subTask("Creating Trace Data Matrices...");
		
		TraceFootprintMatrixGenerator traceMatrixGenerator = new TraceFootprintMatrixGenerator(matrixIndices);

		// create FP matrices for traces
		List<FPMatrix> tracematrices = traceMatrixGenerator.generate(traces);
		
		stopwatch.checkpoint("Generated FP-matrices from traces");

		// generate corresponding lengths of matrices as list
		List<Integer> lengths = new ArrayList<Integer>();
		for (int i = 0; i < tracematrices.size(); i++) {
			lengths.add(tracematrices.get(i).getLength());
		}

		subMonitor.split(1);
		subMonitor.subTask("Creating Automata Matrices...");

		// create automaton matrix generator
		AutomatonMatrixGenerator automatonMatrixGenerator = new AutomatonMatrixGenerator(matrixIndices, lengths);
		automatonMatrixGenerator.setTraversalThreshold(this.traversalThreshold);
		// create FP matrices for automaton
		List<FPMatrix> automatrices = automatonMatrixGenerator.generate(auto);

		subMonitor.split(1);
		stopwatch.checkpoint("Generated FP-matrices from corresponding automata");
		
		subMonitor.subTask("Creating Conformance Matrices...");

		// create FP matrices for conformance
		List<ConfFPMatrix> confmatrices = createConformanceConfusion(tracematrices, automatrices, true);

		subMonitor.split(1);
		stopwatch.checkpoint("Evaluated FP-matrices to create conformance matrices");

		// create list of counter-examples
		subMonitor.subTask("Compiling list of counter examples...");

		subMonitor.split(1);
		stopwatch.checkpoint("Compiled list of counter examples");

		// Compile list of counter examples
		List<Pair<Trace, Integer>> counterExamples = compileCounterExamples(confmatrices, traces, matrixIndices);

		// Finally created a report based on the conformance matrices
		return createReport(confmatrices, labelMapping, counterExamples);
	}
	
	/**
	 * Compiles list of counter examples by examining the result conformance matrices
	 * and the input traces.
	 */
	private List<Pair<Trace, Integer>> compileCounterExamples(List<ConfFPMatrix> confmatrices, TraceDataset traces, Map<Integer, Integer> matrixIndices) {
		Map<Integer, ConfFPMatrix> conformanceMatricesByLength = confmatrices.stream().collect(Collectors.toMap(m -> m.getLength(), m -> m));
		List<Pair<Trace, Integer>> counterExamples = new ArrayList<>();
		
		for (Trace trace : traces.getTraces()) {
			List<Integer> transitions = trace.getTransitions().stream().map(t -> t.getId()).collect(Collectors.toList());
			
			for (int i=0; i<transitions.size() - 1; i++) {
				final int firstId = transitions.get(i);
				final int secondId = transitions.get(i + 1);
				
				// conformance matrix in which to look for a counter example marker
				final ConfFPMatrix lengthMatrix = conformanceMatricesByLength.get(i + 2);
				
				final Integer row = matrixIndices.get(firstId);
				final Integer column = matrixIndices.get(secondId);
				
				if(null == lengthMatrix) {
					throw new IllegalArgumentException("No conformance matrix for length " + (i+2));
				}
				
				if ( null == row || null == column) {
					throw new IllegalStateException("Failed to get matrix index for (" + row + ", " + column + ")");
				}
				
				if (lengthMatrix.getCounterExampleElement(row, column).equals("f")) {
					counterExamples.add(Pair.create(trace, i));
				}
			}
		}
		
		return counterExamples;
	}

	/**
	 * Builds a matrix index map (@see {@link #matrixIndices}) for the given
	 * label mapping and returns it.
	 */
	static Map<Integer, Integer> buildMatrixIndicesMap(Map<Integer, String> labelMapping) {
		Map<Integer, Integer> matrixIndices = new HashMap<>();
		int counter = 0;
		for (Entry<Integer, String> e : labelMapping.entrySet()) {
			matrixIndices.put(e.getKey(), counter++);
		}
		return matrixIndices;
	}

	/**
	 * Create conformance matrices based on the given trace data set matrices and
	 * automata matrices.
	 * 
	 * @param traceMatrices
	 *            The trace data set matrices.
	 * @param automataMatrices
	 *            The automata matrices.
	 * @param fixTracesToRelativeValues
	 *            Specifies whether the trace data set matrices should be
	 *            normalized.
	 * @return
	 */
	static List<ConfFPMatrix> createConformanceConfusion(List<FPMatrix> traceMatrices,
			List<FPMatrix> automataMatrices, boolean fixTracesToRelativeValues) {
		if (fixTracesToRelativeValues) {
			normalizeFootprintMatrix(traceMatrices);
		}

		// create conformance matrices
		List<ConfFPMatrix> confs = new ArrayList<>();
		for (int i = 0; i < traceMatrices.size(); i++) {
			final FPMatrix traceMatrix = traceMatrices.get(i);
			final FPMatrix automatonMatrix = automataMatrices.get(i);
			final int traceLength = traceMatrix.getLength();

			ConfFPMatrix temp = new ConfFPMatrix(traceLength, traceMatrix, automatonMatrix, traceMatrix.getDimension());
			confs.add(temp);
		}
		return confs;
	}

	/**
	 * Normalizes the given footprint matrix to relative values.
	 */
	static void normalizeFootprintMatrix(List<FPMatrix> traces) {
		// fix traces to relative values
		for (int h = 0; h < traces.size(); h++) {
			int dim = traces.get(h).getDimension();
			for (int i = 0; i < dim; i++) {
				for (int k = 0; k < dim; k++) {
					double value = traces.get(h).getElement(i, k);
					value = value / traces.get(h).getTraceCount();
					traces.get(h).setElement(i, k, value);
				}
			}
		}
	}

	/**
	 * Creates a textual report from the given conformance matrices.
	 * 
	 * @param labelMapping
	 * @param counterExamples
	 */
	static ConsistencyReport createReport(List<ConfFPMatrix> transcendence, Map<Integer, String> labelMapping,
			List<Pair<Trace, Integer>> counterExamples) {
		ConsistencyReportBuilder builder = ConsistencyReportBuilder.newReport();

		builder.setCreatedOn(new Date());
		builder.labelMapping(labelMapping);

		for (int h = 0; h < transcendence.size(); h++) {
			ConfFPMatrix conformanceMatrix = transcendence.get(h);

			final int traceLength = conformanceMatrix.getLength();
			final double[][] resultMatrix = conformanceMatrix.getResultMatrix();
			builder.addResult(traceLength, conformanceMatrix.getConformanceDegree(), resultMatrix);
		}

		CounterExampleCombiner counterExampleCombiner = new CounterExampleCombiner();

		for (Pair<Trace, Integer> e : counterExamples) {
			List<Integer> trace = e.first.getTransitions().stream().map(t -> t.getId()).collect(Collectors.toList());
			final int index = e.second;
			counterExampleCombiner.put(Pair.create(trace, index), Arrays.asList(e.first.getId()));
		}

		counterExampleCombiner.entrySet().forEach(c -> {
			final List<String> traceIds = c.getValue();
			final List<Integer> trace = c.getKey().first;
			final int index = c.getKey().second;
			builder.addCounterExample(traceIds, index, trace);
		});

		return builder.create();
	}

	/**
	 * CombineMap to aggregate counter example pairs (Pair<List<Integer>, Integer>
	 * representing (trace, counter-example index)) by their trace and index.
	 * 
	 * A combined value contains a list of all combined trace IDs.
	 *
	 */
	private static class CounterExampleCombiner extends CombineMap<Pair<List<Integer>, Integer>, List<String>> {
		@Override
		public List<String> combine(List<String> valueBefore, List<String> newValue) {
			List<String> result = new ArrayList<>();
			result.addAll(valueBefore);
			result.addAll(newValue);
			return result;
		}
	}
	
	/**
	 * Returns the stopwatch that was used for the most-recent
	 * invocation of {@link FootprintMatrixCheckingAlgorithm#performConsistencyCheck(ProbabilisticAutomata, TraceDataset, IProgressMonitor)}.
	 */
	@Override
	public Stopwatch getStopwatch() {
		return stopwatch;
	}

	/**
	 * Sets the traversal threshold for matrix generation.
	 * 
	 * @see AutomatonMatrixGenerator#setTraversalThreshold(double)
	 */
	public void setTraversalThreshold(double traversalThreshold) {
		this.traversalThreshold = traversalThreshold;
	}
}