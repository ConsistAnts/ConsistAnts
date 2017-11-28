package de.hu_berlin.cchecker.core.models.traces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hu_berlin.cchecker.core.learning.blockwise.OccurrenceCounter;

/**
 * This class models a set of traces of software behaviour with a mapping of
 * transition ids to a readable label as well as a list of traces.
 * 
 * @author Linus
 *
 */
public class TraceDataset {

	/**
	 * An immutable empty {@link TraceDataset}.
	 */
	public static final TraceDataset EMPTY_DATASET = new TraceDataset(Collections.emptyMap(), Collections.emptyList());

	/**
	 * Maps the id of a transition to a human-readable label. Those ids must be
	 * defining the same transitions as in the model.
	 */
	private Map<Integer, String> transitionIdToLabel;

	/**
	 * A list of traces, ordered lists of transitions.
	 */
	private List<Trace> traces;

	/**
	 * Returns a new instance of TraceDataset, initializing empty collections.
	 */
	public TraceDataset() {
		super();
		this.transitionIdToLabel = new HashMap<>();
		this.traces = new ArrayList<>();
	}

	/**
	 * Returns a new instance of TraceDataset with the given transition label map
	 * and traces.
	 */
	public TraceDataset(Map<Integer, String> transitionIdToLabel, List<Trace> traces) {
		super();
		this.transitionIdToLabel = transitionIdToLabel;
		this.traces = traces;
	}

	/**
	 * Returns an {@link OccurrenceCounter} for each distinct trace in this dataset.
	 */
	@JsonIgnore /* as the result is fully dependent on #getTraces */
	public OccurrenceCounter<List<OrderedTransition>> getTracesAggregated() {
		OccurrenceCounter<List<OrderedTransition>> aggregated = new OccurrenceCounter<>();
		this.getTraces().stream().forEach(trace -> aggregated.increase(trace.getTransitions()));
		return aggregated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((traces == null) ? 0 : traces.hashCode());
		result = prime * result + ((transitionIdToLabel == null) ? 0 : transitionIdToLabel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraceDataset other = (TraceDataset) obj;
		if (traces == null) {
			if (other.traces != null)
				return false;
		} else if (!traces.equals(other.traces))
			return false;
		if (transitionIdToLabel == null) {
			if (other.transitionIdToLabel != null)
				return false;
		} else if (!transitionIdToLabel.equals(other.transitionIdToLabel))
			return false;
		return true;
	}

	public List<Trace> getTraces() {
		return traces;
	}

	public void setTraces(List<Trace> traces) {
		this.traces = traces;
	}

	public Map<Integer, String> getTransitionIdToLabel() {
		return transitionIdToLabel;
	}

	public void setTransitionIdToLabel(Map<Integer, String> transitionIdToLabel) {
		this.transitionIdToLabel = transitionIdToLabel;
	}

	@Override
	public String toString() {
		return this.getTraces().stream()
				.map(trace -> trace.getTransitions().stream().map(OrderedTransition::getId)
						.map(getTransitionIdToLabel()::get).collect(Collectors.joining(" ")))
				.collect(Collectors.joining("\n"));
	}
}
