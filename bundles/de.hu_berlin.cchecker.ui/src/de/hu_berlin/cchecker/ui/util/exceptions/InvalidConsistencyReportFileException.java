package de.hu_berlin.cchecker.ui.util.exceptions;

import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;

/**
 * An Exception that happens when a .report file that is supposed to contain a 
 * {@link ConsistencyReport} can unexpectedly not be parsed.
 *
 */
public class InvalidConsistencyReportFileException extends Exception {

	/**
	 * Generated by Eclipse.
	 */
	private static final long serialVersionUID = -2461069038558587757L;
	
	/**
	 * The file name of the file that could not be parsed to a ProbabilisticAutomata.
	 */
	private final String fileName;
	
	/**
	 * Returns a new instance of {@link InvalidConsistencyReportFileException} with the given message and fileName.
	 */
	public InvalidConsistencyReportFileException(String message, String fileName) {
		super(message);
		this.fileName = fileName;
	}
	
	/**
	 * Returns the file name of the file that caused this exception.
	 */
	public String getFileName() {
		return this.fileName;
	}
}