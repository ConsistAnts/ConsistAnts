package de.hu_berlin.cchecker.headless.consumers;

import java.io.File;

import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.TraceDatasetParser;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.CommandLineArgumentException;
import de.hu_berlin.cchecker.headless.CommandLineExecutionFailed;

/**
 * A {@link KeyValueArgumentConsumer} for {@link TraceDataset}s.
 */
public class TraceDataSetArgumentConsumer extends KeyValueArgumentConsumer<TraceDataset> {

	private String absolutePath = "";
	
	public TraceDataSetArgumentConsumer(Argument argument) {
		super(argument);
	}

	@Override
	protected TraceDataset extractValue(String argument) {
		if (null == argument) {
			throw new CommandLineArgumentException("No dataset file path specified");
		}
		
		File tracesFile = new File(argument);
		
		if (!tracesFile.exists()) {
			throw new CommandLineExecutionFailed("File does not exist " + argument);
		}
		
		this.setAbsolutePath(tracesFile.getAbsolutePath());
		
		TraceDataset traceDataset = new TraceDatasetParser().parseTraceDatasetFromFile(tracesFile);
		if (traceDataset == null) {
			throw new CommandLineExecutionFailed("Failed to read from " + argument);
		}
		return traceDataset;
	}

	/**
	 * Returns the absolute path of the automaton file.
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}

	private void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

}
