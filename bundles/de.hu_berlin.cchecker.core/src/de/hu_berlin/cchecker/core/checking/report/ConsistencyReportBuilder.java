package de.hu_berlin.cchecker.core.checking.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;

/**
 * A builder class to create instances of {@link ConsistencyReport}.
 */
public class ConsistencyReportBuilder {

	/** Template class for {@link CounterExample}s. */
	private static class CounterExampleTemplate {
		final List<Integer> trace;
		final List<String> traceIds;
		final int index;

		public CounterExampleTemplate(List<Integer> trace, List<String> traceIds, int index) {
			super();
			this.trace = trace;
			this.traceIds = traceIds;
			this.index = index;
		}
	}

	/** Template class for a list of {@link MatrixRow}s. */
	private static class FootprintMatrixTemplate {
		final double result;
		final double[][] matrix;
		final int traceLength;

		public FootprintMatrixTemplate(double result, double[][] matrix, int traceLength) {
			super();
			this.result = result;
			this.matrix = matrix;
			this.traceLength = traceLength;
		}
	}

	Map<Integer, String> labelMapping = new HashMap<>();
	private String modelChecksum;
	private String modelPath;
	private String traceDataSetPath;
	private String traceDataSetChecksum;
	private Date createdOn;
	private List<CounterExampleTemplate> counterExamples = new ArrayList<>();
	private List<FootprintMatrixTemplate> results = new ArrayList<>();

	/**
	 * Returns a new report builder instance.
	 */
	public static ConsistencyReportBuilder newReport() {
		return new ConsistencyReportBuilder();
	}

	/**
	 * Extends (overrides) label mappings of the report builder.
	 */
	public ConsistencyReportBuilder labelMapping(Map<Integer, String> labeMap) {
		this.labelMapping.putAll(labeMap);
		return this;
	}

	/**
	 * Adds or replaces the label mapping given by id -> label
	 */
	public ConsistencyReportBuilder labelMapping(int id, String label) {
		this.labelMapping.put(id, label);
		return this;
	}

	/**
	 * Sets the path of the trace data set.
	 */
	public ConsistencyReportBuilder setTraceDataSetPath(String traceDataSetPath) {
		this.traceDataSetPath = traceDataSetPath;
		return this;
	}

	/**
	 * Sets the checksum of the trace data set file.
	 */
	public ConsistencyReportBuilder setTraceDataSetChecksum(String traceDataSetChecksum) {
		this.traceDataSetChecksum = traceDataSetChecksum;
		return this;
	}

	/**
	 * Sets the path of the model file.
	 */
	public ConsistencyReportBuilder setModelPath(String modelPath) {
		this.modelPath = modelPath;
		return this;
	}

	/**
	 * Sets the checksum of the model file.
	 */
	public ConsistencyReportBuilder setModelChecksum(String modelChecksum) {
		this.modelChecksum = modelChecksum;
		return this;
	}

	/**
	 * Sets the Create-On-Date of the report.
	 */
	public ConsistencyReportBuilder setCreatedOn(Date d) {
		this.createdOn = d;
		return this;
	}

	/**
	 * Adds a counter example to the report.
	 * 
	 * @param traceIds
	 *            A list of trace IDs that are such a counter example.
	 * @param index
	 *            The index of the pair of transitions that represents a counter
	 *            example.
	 * @param trace
	 *            The number-encoded trace that is such a counter example. (Numbers
	 *            according to {@link ConsistencyReportBuilder#labelMapping})
	 * @return
	 */
	public ConsistencyReportBuilder addCounterExample(List<String> traceIds, int index, List<Integer> trace) {
		this.counterExamples.add(new CounterExampleTemplate(trace, traceIds, index));
		return this;
	}

	/**
	 * Adds/overrides the consistency result for the given length.
	 * 
	 * @param traceLength
	 *            The trace length of the consistency result.
	 * @param result
	 *            The result value (percent)
	 * @param matrix
	 *            The conformance matrix. (Dimensions according to {@link #labelMapping})
	 * @return
	 */
	public ConsistencyReportBuilder addResult(int traceLength, double result, double[][] matrix) {
		this.results.add(new FootprintMatrixTemplate(result, matrix, traceLength));
		return this;
	}

	/**
	 * Returns a new report instance as configured by this builder.
	 */
	public ConsistencyReport create() {
		ReportFactory factory = ReportFactory.eINSTANCE;
		ConsistencyReport report = factory.createConsistencyReport();

		// set general information
		report.setModelChecksum(this.modelChecksum);
		report.setModelPath(this.modelPath);
		report.setTraceDataSetChecksum(this.traceDataSetChecksum);
		report.setTraceDataSetPath(this.traceDataSetPath);
		report.setCreatedOn(this.createdOn);

		// transfer alphabet / label mapping
		Map<Integer, String> alphabet = report.getLabelMapping().map();
		for (Entry<Integer, String> mapping : this.labelMapping.entrySet()) {
			alphabet.put(mapping.getKey(), mapping.getValue());
		}

		EList<CounterExample> reportCounterExamples = report.getCounterExamples();

		// transfer counter examples
		for (CounterExampleTemplate template : this.counterExamples) {
			CounterExample example = factory.createCounterExample();
			example.getTraceIds().addAll(template.traceIds);
			example.setIndex(template.index);
			example.getTrace().addAll(template.trace);
			report.getCounterExamples().add(example);
			reportCounterExamples.add(example);
		}

		// transfer footprint matrices
		for (FootprintMatrixTemplate template : this.results) {
			ConsistencyLengthResult result = factory.createConsistencyLengthResult();
			result.setTraceLength(template.traceLength);
			result.setResult(template.result);
			report.getFootprintMatrices().add(result);

			for (double[] row : template.matrix) {
				result.getResultMatrix().add(createRow(row));
			}
		}

		// finally return the created report
		return report;
	}

	/**
	 * Creates a new {@link MatrixRow} with the given values.
	 */
	private static MatrixRow createRow(double... values) {
		MatrixRow row = ReportFactory.eINSTANCE.createMatrixRow();
		EList<Double> elements = row.getElements();
		for (double v : values) {
			elements.add(v);
		}
		return row;
	}
}
