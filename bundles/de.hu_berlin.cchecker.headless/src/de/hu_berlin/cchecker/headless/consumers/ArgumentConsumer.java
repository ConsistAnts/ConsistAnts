package de.hu_berlin.cchecker.headless.consumers;

import java.util.List;
import java.util.Map;

import de.hu_berlin.cchecker.headless.Argument;

/**
 * An {@link ArgumentConsumer} consumes the whole or part of 
 * a given list of command line arguments.
 * 
 * @author lucabeurer-kellner
 *
 */
public interface ArgumentConsumer {
	/**
	 * Process the given arguments and returns a modified version
	 * of the arguments map that doesn't contain the consumed arguments anymore.
	 */
	public Map<String, String> consume(Map<String, String> arguments);
	
	/**
	 * Returns a list of missing arguments after the most-recent invocation of {@link #consume(Map)}.
	 * 
	 * Returns an empty list, if all arguments were specified.
	 */
	public List<Argument> getMissingArguments();
}
