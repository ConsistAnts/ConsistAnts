package de.hu_berlin.cchecker.headless.consumers;

import java.io.File;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.CommandLineArgumentException;
import de.hu_berlin.cchecker.headless.CommandLineExecutionFailed;
import de.hu_berlin.cchecker.headless.HeadlessIOUtils;

/**
 * An {@link ArgumentConsumer} for {@link ProbabilisticAutomata} that automatically 
 * loads the model from the given file.
 *  
 * @author lucabeurer-kellner
 *
 */
public class ProbabilisticAutomatonArgumentConsumer extends KeyValueArgumentConsumer<ProbabilisticAutomata>{
	
	private String absolutePath = "";
	
	public ProbabilisticAutomatonArgumentConsumer(Argument argument) {
		super(argument);
	}
	
	@Override
	protected ProbabilisticAutomata extractValue(String argument) {
		if (null == argument) {
			throw new CommandLineArgumentException("No automaton file path specified");
		}
		
		File automatonFile = new File(argument);
		if (!automatonFile.exists()) {
			throw new CommandLineExecutionFailed("File does not exists: " + argument);
		}
		
		this.setAbsolutePath(automatonFile.getAbsolutePath());
		
		ProbabilisticAutomata automaton = HeadlessIOUtils.loadFromFile(automatonFile);
		
		if (null == automaton) {
			throw new CommandLineExecutionFailed("Failed to read automaton from path: " + argument);
		}
		
		return automaton;
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
