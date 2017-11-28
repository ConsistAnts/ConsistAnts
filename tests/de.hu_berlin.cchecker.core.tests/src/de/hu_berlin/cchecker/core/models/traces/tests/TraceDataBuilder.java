package de.hu_berlin.cchecker.core.models.traces.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import de.hu_berlin.cchecker.core.models.traces.TraceDataProvider;

/**
 * A fluent-API builder for creating trace data sets and
 * {@link TraceDataProvider}s by hand.
 * 
 * <code>
 * Example:
 * 
 * TraceDataProvider provider = TraceDataBuilder.newTraceData()
 *		.trace(1, 2, 4)
 *		.trace(1, 2, 3)
 *		.labelMapping(TraceDataBuilder.ASCII_MAPPING_FUNCTION)
 *		.provider();
 * 
 * </code>
 */
public class TraceDataBuilder {
	/**
	 * Creates a new integer trace data builder instance.
	 */
	public static TraceDataBuilder newTraceData() {
		return new TraceDataBuilder();
	}

	private static final int ASCII_MAPPING_START = 'A';
	/**
	 * A mapping function (see {@link #labelMapping(Function)}) that maps label ids
	 * to a ASCII character starting with 0 to 'A', 1 to 'B', etc.
	 */
	public static final Function<Integer, String> ASCII_MAPPING_FUNCTION = i -> String
		.valueOf((char) (ASCII_MAPPING_START + i));

	private List<Integer[]> traces = new ArrayList<>();
	private Map<Integer, String> mapping = null;
	private Function<Integer, String> mappingFunction = null;
	private Set<Integer> alphabet = new HashSet<>();

	private TraceDataBuilder() {
	}

	/**
	 * Adds a new trace to this integer data set.
	 */
	public TraceDataBuilder trace(Integer... transitionIds) {
		this.traces.add(transitionIds);
		alphabet.addAll(Arrays.asList(transitionIds));
		return this;
	}

	/**
	 * Sets the label mapping for this data set.
	 * 
	 * It is assumed that the given mapping is complete. If not, {@link #create()}
	 * might return null.
	 */
	public TraceDataBuilder labelMapping(Map<Integer, String> mapping) {
		this.mapping = mapping;
		return this;
	}

	public TraceDataBuilder labelMapping(Function<Integer, String> mappingFunction) {
		this.mappingFunction = mappingFunction;
		return this;
	}

	/**
	 * Returns raw representation of the trace data set of this builder.
	 */
	public List<Integer[]> create() {
		return traces;
	}

	/**
	 * Creates a {@link TraceDataProvider} that provides the constructed trace data
	 * set.
	 */
	public TraceDataProvider provider() {
		if (mapping != null) {
			return new IntegerTraceDataProvider(traces, mapping);
		} else if (mappingFunction != null) {
			return new IntegerTraceDataProvider(traces, Maps.asMap(alphabet, this.mappingFunction));
		}

		return new IntegerTraceDataProvider(traces);
	}
}
