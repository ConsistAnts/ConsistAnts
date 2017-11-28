package de.hu_berlin.cchecker.core.checking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.hu_berlin.cchecker.core.models.traces.TraceDataProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataSetProvider;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * This {@link TraceFootprintMatrixGenerator} can be used to generate the
 * footprint matrices of a {@link TraceDataset}.
 */
public class TraceFootprintMatrixGenerator {

	private Map<Integer, Integer> matrixIndices;
	private int alphabetSize;

	private TraceDataProvider dataProvider = new TraceDataSetProvider();

	/**
	 * Initialises a new trace fooprint matrix generator for the given matrix
	 * indices.
	 */
	public TraceFootprintMatrixGenerator(Map<Integer, Integer> matrixIndices) {
		super();
		this.matrixIndices = matrixIndices;
		this.alphabetSize = matrixIndices.size();
	}

	/**
	 * Creates the trace data set footprint-matrices.
	 */
	public List<FPMatrix> generate(TraceDataset traces) {
		this.dataProvider.setInput(traces);
		
		List<String[]> stringtraces = getStringFromTraceDataset(traces);

		// generate events
		List<FootprintEvent> eventlist = generateEvents(dataProvider.getLabelMap());

		// Create Prefixsets<---------------
		Iterator<List<String[]>> prefixSetIterator = getPrefixSetIterator(stringtraces);

		// create matrices per prefixset <---------------
		List<FPMatrix> matrixListTraces = new LinkedList<>();

		while (prefixSetIterator.hasNext()) {
			List<String[]> prefixSet = prefixSetIterator.next();
			int prefixSetLength = prefixSet.get(0).length;
			FPMatrix tempMatrix = createTraceMatrix(prefixSet, eventlist, alphabetSize, prefixSetLength);
			matrixListTraces.add(tempMatrix);
		}
		return matrixListTraces;
	}

	/**
	 * Creates a single trace matrix for a given length.
	 * 
	 * @param traceDataset
	 *            The trace data set to work with. It is assumed that this dataset
	 *            only contains traces of the given length.
	 * @param events
	 *            The events to consider
	 * @param alphabet
	 *            The alphabet of the trace data set
	 * @param length
	 *            The length to create the matrix for.
	 */
	public FPMatrix createTraceMatrix(List<String[]> traceDataset, List<FootprintEvent> events, final int alphabetSize,
			int length) {
		FPMatrix matrix = new FPMatrix(length, traceDataset.size(), alphabetSize);
		double occurrences = 0;
		String temp = "";
		String eventname;
		FootprintEvent currevent;
		int id1;
		int id2;
		for (int i = 0; i < events.size(); i++) {
			currevent = events.get(i);
			eventname = currevent.getName();
			id1 = currevent.getFirstId();
			id2 = currevent.getSecondId();
			for (int k = 0; k < traceDataset.size(); k++) {
				for (int h = 0; h < traceDataset.get(k).length - 1; h++) {
					temp += traceDataset.get(k)[h] + traceDataset.get(k)[h + 1];
					if (temp.compareTo(eventname) == 0) {
						occurrences++;
					}
					temp = "";
				}
			}
			int row = matrixIndices.get(id1);
			int column = matrixIndices.get(id2);
			matrix.setElement(row, column, occurrences);

			occurrences = 0;
		}
		return matrix;
	}

	/**
	 * Returns an iterator to iterate over all prefix sets of the given trace data set.
	 * 
	 * @see {@link PrefixSetIterator}
	 */
	static Iterator<List<String[]>> getPrefixSetIterator(List<String[]> traceDataset) {
		if (traceDataset == null) {
			return null;
		}
		return new PrefixSetIterator(traceDataset);
	}

	/**
	 * Generates a list of unique events from a given alphabet.
	 * 
	 * An event is a concatenation of two characters of an alphabet.
	 * 
	 * @param map
	 *            list of elements of the alphabet
	 * @return list of events
	 */
	static List<FootprintEvent> generateEvents(Map<Integer, String> map) {
		int size = map.size();
		List<FootprintEvent> eventlist = new LinkedList<>();

		for (Integer firstId : map.keySet()) {
			for (Integer secondId : map.keySet()) {
				// TODO Make sure this is unambiguous (for now
				// start + toll is the same event as startt + oll
				final String first = map.get(firstId);
				final String second = map.get(secondId);
				String strEvent = first + second;

				FootprintEvent temp = new FootprintEvent(strEvent, firstId, secondId);
				eventlist.add(temp);
			}
		}

		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size; k++) {

			}
		}
		return eventlist;
	}

	/**
	 * Builds a string-based list of traces for the given trace data set.
	 */
	List<String[]> getStringFromTraceDataset(TraceDataset traces) {
		final Map<Integer, String> mapping = traces.getTransitionIdToLabel();

		// conversion of trace data set into algorithm format
		List<String[]> sometraces = new ArrayList<>();

		for (Integer[] trace : dataProvider) {
			String[] stringTrace = Arrays.asList(trace).stream().map(n -> mapping.get(n)).toArray(String[]::new);
			sometraces.add(stringTrace);
		}
		return sometraces;
	}

	/**
	 * Sets the trace data provider to use for reading trace data.
	 */
	public void setDataProvider(TraceDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	/**
	 * A custom iterator that iterates over all prefix sets of the given trace data
	 * set.
	 * 
	 * The iterator will automatically skip prefix sets for which there are no
	 * traces of that exact length.
	 *
	 */
	private static class PrefixSetIterator implements Iterator<List<String[]>> {

		private int prefixLength = 2;
		private boolean iteratorHasNext = true;

		private List<String[]> traceDataset;

		/**
		 * Initialises a new prefix set iterator for the given trace data set.
		 */
		public PrefixSetIterator(List<String[]> traceDataset) {
			this.traceDataset = traceDataset;
			this.iteratorHasNext = traceDataset.stream().anyMatch(trace -> trace.length >= prefixLength);
		}

		private int nextPrefixLength() {
			Integer nextLength = traceDataset.stream().filter(trace -> trace.length >= prefixLength + 1)
					.map(trace -> trace.length).sorted().findFirst().orElse(null);
			if (nextLength != null) {
				return prefixLength + 1;
			} else {
				iteratorHasNext = false;
				return 0;
			}
		}

		@Override
		public boolean hasNext() {
			return iteratorHasNext;
		}

		@Override
		public List<String[]> next() {
			List<String[]> result = traceDataset.stream().filter(trace -> trace.length >= prefixLength)
					.map(trace -> Arrays.copyOf(trace, prefixLength)).collect(Collectors.toList());

			prefixLength = nextPrefixLength();
			return result;
		}
	}
}
