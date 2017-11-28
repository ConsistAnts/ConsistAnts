package de.hu_berlin.cchecker.core.learning.alergia.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hu_berlin.cchecker.core.learning.alergia.Alergia;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.IntegerTraceDataProvider;

/**
 * An abstract test class for tests regarding Alergia model output.
 */
public abstract class AbstractAlergiaDataTest {
	
	/**
	 * See {@link #runAlergiaOnRawData(List)}.
	 */
	protected ProbabilisticAutomata runAlergiaOnRawData(Integer[][] sequences, double alpha){
		return runAlergiaOnRawData(Arrays.asList(sequences), alpha);
	}
	
	private Map<Integer, String> createNumberLabelMap(List<Integer[]> sequences) {
		HashMap<Integer, String> labelMap = new HashMap<>();
		sequences.stream()
			.flatMap(trace -> Arrays.asList(trace).stream())
			.forEach(i -> labelMap.put(i, i.toString()));
		
		return labelMap;
	}
	
	/**
	 * Runs Alergia just as {@link #runAlergiaOnRawData(Integer[][], Map)} but infers
	 * labelMap from all occurring integers.
	 */
	protected ProbabilisticAutomata runAlergiaOnRawData(List<Integer[]> sequences, double alpha){
		return runAlergiaOnRawData(sequences, createNumberLabelMap(sequences), alpha);
	}
	
	/**
	 * Runs Alergia on the given raw trace data with the given label map.
	 */
	protected ProbabilisticAutomata runAlergiaOnRawData(List<Integer[]> sequences, Map<Integer, String> labelMap, double alpha) {
		Alergia alergia = new Alergia(new IntegerTraceDataProvider(sequences, labelMap));
		alergia.setAlpha(alpha);
		
		return alergia.learnModel(TraceDataset.EMPTY_DATASET);
	}
}