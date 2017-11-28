package de.hu_berlin.cchecker.core.models.traces;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to parse a .jtrc (or any json) file containing a trace data set json into a {@link TraceDataset}
 * 
 * @author Linus
 *
 * @see TraceDataset
 */
public class JsonTraceDatasetParser {

	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Attempts to parse the given file to a TraceDataset.
	 * Returns null if an IOException occurs during file reading or json binding.
	 * 
	 */
	public TraceDataset parseTraceDatasetFromFile(File trcFile) {
		try {
			return mapper.readValue(trcFile, TraceDataset.class);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Attempts to parse the given {@link InputStream} to a TraceDataset.
	 * Returns null if an IOException occurs during file reading or json binding.
	 * 
	 */
	public TraceDataset parseTraceDatasetFromInputStream(InputStream trcFileInputStream) {
		try {
			return mapper.readValue(trcFileInputStream, TraceDataset.class);
		} catch (IOException e) {
			return null;
		}
	}
}
