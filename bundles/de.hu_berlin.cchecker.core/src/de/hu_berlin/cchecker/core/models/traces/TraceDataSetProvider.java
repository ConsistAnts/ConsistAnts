package de.hu_berlin.cchecker.core.models.traces;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A {@link TraceDataProvider} implementation for {@link TraceDataset}s.
 */
public class TraceDataSetProvider implements TraceDataProvider {

	private TraceDataset dataset;
	
	@Override
	public Iterator<Integer[]> iterator() {
		return this.dataset.getTraces().stream()
			// Map trace to its transitions
			.map(Trace::getTransitions)
			// Map list of transitions to list of transition IDs
			.map(list -> {
				return list.stream()
						.map(OrderedTransition::getId)
						.toArray(Integer[]::new);
			}).iterator();
	}

	@Override
	public void setInput(TraceDataset dataset) {
		this.dataset = dataset;
	}

	@Override
	public Set<Integer> getAlphabet() {
		return this.dataset.getTransitionIdToLabel().keySet();
	}

	@Override
	public Map<Integer, String> getLabelMap() {
		return this.dataset.getTransitionIdToLabel();
	}

}
