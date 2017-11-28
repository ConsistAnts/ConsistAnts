package de.hu_berlin.cchecker.core.models.traces;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A {@link TraceDataProvider} allows an algorithm to iterate over all traces of
 * a data set without knowing about its concrete implementation.
 * 
 * Implementations may or may not provide their own label mappings. 
 */
public interface TraceDataProvider extends Iterable<Integer[]> {
	/**
	 * Sets the input for this {@link TraceDataProvider}.
	 * 
	 * This method has to be invoked before {@link #iterator()} is invoked.
	 */
	void setInput(TraceDataset dataset);

	/**
	 * Returns the alphabet of the trace data set that is being provided.
	 */
	Set<Integer> getAlphabet();
	
	/**
	 * Returns the label map of this provider. The map can be used to convert back 
	 * from transition ids to human-readable transition labels.
	 */
	Map<Integer, String> getLabelMap();
	
	/**
	 * Returns a {@link Stream} of traces. 
	 * 
	 * Each trace is represented as an {@link Integer} array according to {@link #getLabelMap()}. 
	 */
	default Stream<Integer[]> stream() {
		return StreamSupport.stream(this.spliterator(), false);
	}
}
