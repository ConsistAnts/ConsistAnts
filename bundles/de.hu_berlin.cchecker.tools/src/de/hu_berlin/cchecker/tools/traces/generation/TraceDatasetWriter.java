package de.hu_berlin.cchecker.tools.traces.generation;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hu_berlin.cchecker.core.models.traces.TraceDataset;

/**
 * This class can be used to write Trace Data Set files (.trc)
 *
 * @see TraceDataset
 */
public class TraceDatasetWriter {
	public static void writeTraceDatasetToFile(TraceDataset dataSet, OutputStream outputStream) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writer().writeValue(outputStream, dataSet);
	}
	
	private TraceDatasetWriter() {
		// non-instantiable utiltity class
	}
}
