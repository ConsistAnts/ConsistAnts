package de.hu_berlin.cchecker.headless.modes;

import java.util.List;

import de.hu_berlin.cchecker.headless.CommandLineExecutionFailed;
import de.hu_berlin.cchecker.headless.consumers.ArgumentConsumer;

/**
 * A command line mode specifies an entry point on the command line interface.
 * 
 * Example: The CLI supports the modes "random-pdfa", "check", "learn", etc.
 */
public interface CommandLineMode {
	/**
	 * Returns true if this {@link CommandLineMode} can be activated for 
	 * the given mode specifier.
	 */
	public boolean isApplicableForMode(List<String> modes);
	
	/**
	 * Returns the argument consumer of this command line mode.
	 */
	public ArgumentConsumer getArgumentConsumer();
	
	/**
	 * Returns an ASCII formatted help paragraph for this mode.
	 */
	public default String getHelp() {
		return "";
	}
	
	/**
	 * Runs the command line mode.
	 * 
	 * Before this method was invoked the {@link #getArgumentConsumer()} was
	 * applied to the command line arguments.
	 * 
	 * This method is only invoked if the argument consumer returns an empty 
	 * list of missing arguments.
	 * 
	 * @throws CommandLineExecutionFailed if the execution fails.
	 */
	public void run(List<String> mode) throws CommandLineExecutionFailed;
}
