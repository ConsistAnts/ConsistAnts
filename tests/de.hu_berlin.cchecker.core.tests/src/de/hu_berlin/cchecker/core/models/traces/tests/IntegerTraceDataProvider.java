package de.hu_berlin.cchecker.core.models.traces.tests;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hu_berlin.cchecker.core.models.traces.TraceDataProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataSetProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * A {@link TraceDataProvider} that ignores the {@link TraceDataSetProvider}
 * input and provides its own static int based trace data.
 */
public class IntegerTraceDataProvider implements TraceDataProvider {

	private List<Integer[]> data;
	private Set<Integer> alphabet;
	private Map<Integer, String> labelMap;

	/**
	 * Instantiates a new {@link IntegerTraceDataProvider} with the given trace data
	 * and label map.
	 * 
	 * Note that the label map must be complete in that the provider won't work
	 * properly, if the key set of the label map doesn't map the whole alphabet of
	 * the data set.
	 * 
	 * @param traces
	 * @param labelMap
	 */
	public IntegerTraceDataProvider(List<Integer[]> traces, Map<Integer, String> labelMap) {
		this.data = Collections.unmodifiableList(traces);
		this.alphabet = labelMap.keySet();
		this.labelMap = labelMap;
	}
	
	/**
	 * Instantiates a new {@link IntegerTraceDataProvider} with the given trace data
	 * and label map.
	 * 
	 * Automatically creates a label-mapping by mapping all transition IDs to their 
	 * character representation. (e.g. 12 -> '12')
	 */
	public IntegerTraceDataProvider(List<Integer[]> traces) {
		this(traces, createNumberLabelMap(traces));
	}
	
	@Override
	public Iterator<Integer[]> iterator() {
		return data.iterator();
	}

	@Override
	public void setInput(TraceDataset dataset) {
		// ignore the actual {@link TraceDataset} input
	}

	@Override
	public Set<Integer> getAlphabet() {
		return this.alphabet;
	}

	@Override
	public Map<Integer, String> getLabelMap() {
		return labelMap;
	}

	/**
	 * Creates a simple label-mapping that just maps each integer transition id to its
	 * character representation. (e.g. 1 -> '1')
	 */
	private static Map<Integer, String> createNumberLabelMap(List<Integer[]> sequences) {
		HashMap<Integer, String> labelMap = new HashMap<>();
		sequences.stream()
			.flatMap(trace -> Arrays.asList(trace).stream())
			.forEach(i -> labelMap.put(i, i.toString()));
		
		return labelMap;
	}
}
