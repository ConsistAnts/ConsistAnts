package de.hu_berlin.cchecker.core.models.traces;

import java.io.File;

/**
 * This class is the most general parser for files containing a {@link TraceDataset}, using indications such as the file extension
 * to determine which specific parser to delegate to.
 * 
 * @author Linus
 *
 */
public class TraceDatasetParser {

	private static final String JSON_FILE_EXT = "jtrc";
	private static final String SIMPLE_FILE_EXT = "strc";
	private static final String AMBIGUOUS_FILE_EXT = "trc";
	
	/**
	 * Attempts to parse the given {@link File} to a {@link TraceDataset}, trying to determine 
	 * the format of the file as best as it can. May return null.
	 * 
	 */
	public TraceDataset parseTraceDatasetFromFile(File trcFile) {
		String fileExt = getFileExtension(trcFile);
		if (JSON_FILE_EXT.equals(fileExt)) {
			return new JsonTraceDatasetParser().parseTraceDatasetFromFile(trcFile);
		} else if (SIMPLE_FILE_EXT.equals(fileExt)) {
			return new SimpleTraceDatasetParser().parseTraceDatasetFromFile(trcFile);
		} else if (AMBIGUOUS_FILE_EXT.equals(fileExt)) {
			TraceDataset json = new JsonTraceDatasetParser().parseTraceDatasetFromFile(trcFile);
			if (json != null) {
				return json;
			}
			TraceDataset simple = new SimpleTraceDatasetParser().parseTraceDatasetFromFile(trcFile);
			if (simple != null) {
				return simple;
			}
		}
		return null;
	}
	
	/**
	 * Returns the extension of the given {@link File}, specifically the last dot (".")
	 * in that file's name as well as anything after it.
	 * <br>
	 * Example: "/foo/bar/foobar.c" -> ".c"
	 */
	private static String getFileExtension(File file) {
	    String fileName = file.getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
}
