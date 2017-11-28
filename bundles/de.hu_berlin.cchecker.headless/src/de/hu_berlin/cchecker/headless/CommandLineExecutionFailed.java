package de.hu_berlin.cchecker.headless;

/**
 * This exception can be thrown from any code that was invoked by
 * the {@link ConsistAntsCommandLineInterface}. 
 * 
 * The message will be shown to the user in a CLI-friendly format. 
 * 
 * @author lucabeurer-kellner
 *
 */
public class CommandLineExecutionFailed extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CommandLineExecutionFailed(String message, Throwable t) {
		super(message, t);
	}
	
	public CommandLineExecutionFailed(String message) {
		super(message);
	}
}
