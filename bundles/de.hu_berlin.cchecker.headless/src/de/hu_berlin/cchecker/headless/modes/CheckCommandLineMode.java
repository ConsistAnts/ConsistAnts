package de.hu_berlin.cchecker.headless.modes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.NullProgressMonitor;

import de.hu_berlin.cchecker.core.checking.AutomatonMatrixGenerator;
import de.hu_berlin.cchecker.core.checking.FootprintMatrixCheckingAlgorithm;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.headless.AbstractCommandLineMode;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.ConsistAntsCommandLineInterface;
import de.hu_berlin.cchecker.headless.CommandLineExecutionFailed;
import de.hu_berlin.cchecker.headless.HeadlessIOUtils;
import de.hu_berlin.cchecker.headless.consumers.KeyValueArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ProbabilisticAutomatonArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ToggleArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.TraceDataSetArgumentConsumer;

/**
 * Command line mode "check" to learn PDFA models from a given trace data set
 * file.
 * 
 * @author lucabeurer-kellner
 *
 */
public class CheckCommandLineMode extends AbstractCommandLineMode {

	private TraceDataSetArgumentConsumer traceDatasetConsumer = new TraceDataSetArgumentConsumer(
			new Argument(false, "dataset", "The trace data set to check against"));

	private ProbabilisticAutomatonArgumentConsumer automatonConsumer = new ProbabilisticAutomatonArgumentConsumer(
			new Argument(false, "automaton", "The automaton model to check"));

	private KeyValueArgumentConsumer<Double> traversalThresholdConsumer = KeyValueArgumentConsumer.extract(new Argument(
			true, "traversalThreshold",
			"The traversal threshold for automaton matrix generation. Branches with less probability are dismissed automatically. Default 0.",
			"double"), AutomatonMatrixGenerator.DEFAULT_TRAVERSAL_THRESHOLD, Double::parseDouble);

	private ToggleArgumentConsumer humanReadableConsumer = new ToggleArgumentConsumer(
			new Argument(true, "h", "Use a human-readable Consistency Report output format."));
	
	private ToggleArgumentConsumer csvConsumer = new ToggleArgumentConsumer(
			new Argument(true, "csv", "Use a csv output format that only prints the consistency results."));

	public CheckCommandLineMode() {
		super("check", "Checks consistency of a given dataset with an automaton model");

		registerConsumers(traceDatasetConsumer, 
				automatonConsumer, 
				traversalThresholdConsumer, 
				humanReadableConsumer,
				csvConsumer);
	}

	@Override
	public void run(List<String> mode) throws CommandLineExecutionFailed {
		FootprintMatrixCheckingAlgorithm checkingAlgorithm = new FootprintMatrixCheckingAlgorithm();

		TraceDataset dataset = traceDatasetConsumer.getValue();
		ProbabilisticAutomata automaton = automatonConsumer.getValue();

		// set traversal threshold from consumer value (default 0.0)
		checkingAlgorithm.setTraversalThreshold(traversalThresholdConsumer.getValue());

		ConsistencyReport report = checkingAlgorithm.performConsistencyCheck(automaton, dataset,
				new NullProgressMonitor());

		if (null == report) {
			throw new CommandLineExecutionFailed("Failed to perform consistency check");
		}

		performanceOut(checkingAlgorithm);

		// add file paths and checksums to report
		enhanceReport(report);
		
		if (csvConsumer.isPresent()) {
			outCsv(report);
		} else {
			out(HeadlessIOUtils.reportToString(report, humanReadableConsumer.isPresent()));
		}
	}
	
	private void outCsv(ConsistencyReport report) {
		String headerRow = report.getFootprintMatrices().stream()
				.map(m -> Integer.toString(m.getTraceLength()))
				.collect(Collectors.joining(";"));
		String resultsRow = report.getFootprintMatrices().stream()
			.map(m -> Double.toString(m.getResult()))
			.collect(Collectors.joining(";"));
		
		out(headerRow + "\n" + resultsRow);
	}

	/**
	 * Adds the input file paths and checksums to the report model.
	 * 
	 * Does nothing if the current CLI is in performance mode.
	 * 
	 * @param report
	 * @throws CommandLineExecutionFailed
	 *             if checksum computation fails
	 */
	private void enhanceReport(ConsistencyReport report) throws CommandLineExecutionFailed {
		if (ConsistAntsCommandLineInterface.getCurrent().isPerformance()) {
			// do not compute any checksum, if CLI is running in performance mode
			return;
		}

		String absoluteModelPath = automatonConsumer.getAbsolutePath();
		String absoluteDatasetPath = traceDatasetConsumer.getAbsolutePath();

		report.setModelPath(absoluteModelPath);
		report.setTraceDataSetPath(absoluteDatasetPath);

		try {
			String modelChecksum = HeadlessIOUtils.getChecksum(new File(absoluteModelPath));
			String datasetChecksum = HeadlessIOUtils.getChecksum(new File(absoluteDatasetPath));

			report.setModelChecksum(modelChecksum);
			report.setTraceDataSetChecksum(datasetChecksum);
		} catch (IOException e) {
			throw new CommandLineExecutionFailed("Failed to compute checksums for input files", e);
		}
	}

}
