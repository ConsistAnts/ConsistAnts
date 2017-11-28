package de.hu_berlin.cchecker.ui.util.exceptions;

/**
 * This exception indicates a problem with processing an automaton.
 * 
 * This may happen when the transformation or construction of an automaton fails.
 */
public class ProcessingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ProcessingException(String message) {
		super(message);
	}
}
