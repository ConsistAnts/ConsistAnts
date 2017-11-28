package de.hu_berlin.cchecker.core.models.traces.tests;

import java.io.File;

import de.hu_berlin.cchecker.core.models.traces.SimpleTraceDatasetParser;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.JsonTraceDatasetParser;

/**
 * This class can be used to easily retrieve the TraceDataset from task 1.
 * 
 * @author Linus
 *
 */
public class TraceTestUtils {

	/**
	 * Returns the parsed {@link TraceDataset} from task 1.
	 */
	public static TraceDataset getTask1TraceDataset() {
		File task1File = new File("res/tracedataset_task1.trc");
		return new JsonTraceDatasetParser().parseTraceDatasetFromFile(task1File);
	}
	
	/**
	 * Returns a custom TraceDataSet for unit testing.
	 */
	public static TraceDataset getCustomTestingDataset() {
		File customTestingFile = new File("res/tracedataset_custom.trc");
		return new JsonTraceDatasetParser().parseTraceDatasetFromFile(customTestingFile);
	}
	
	/**
	 * Returns a TraceDataSet out of a trace file in simple format.
	 */
	public static TraceDataset getSimpleTraceDataset() {
		File simpleTestingFile = new File("res/tracedataset_simple.trc");
		return new SimpleTraceDatasetParser().parseTraceDatasetFromFile(simpleTestingFile);
	}
	
	/**
	 * Returns a (simple) TraceDataset that is read from the given path.
	 */
	public static TraceDataset getSimpleTraceDataset(String path) {
		File simpleTestingFile = new File(path);
		return new SimpleTraceDatasetParser().parseTraceDatasetFromFile(simpleTestingFile);
	}
	
	/**
	 * Returns a (json) TraceDataset that is read from the given path.
	 */
	public static TraceDataset getJsonTraceDataset(String path) {
		File simpleTestingFile = new File(path);
		return new JsonTraceDatasetParser().parseTraceDatasetFromFile(simpleTestingFile);
	}
	
	private TraceTestUtils() {
		// Prevent instantiation of this class
	}
}
