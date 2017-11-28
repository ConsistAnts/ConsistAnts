package de.hu_berlin.cchecker.headless;

/**
 * This exception is thrown when the command line arguments are not valid and cannot be parsed.
 */
public class CommandLineArgumentException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CommandLineArgumentException(String message) {
		super(message);
	}
}