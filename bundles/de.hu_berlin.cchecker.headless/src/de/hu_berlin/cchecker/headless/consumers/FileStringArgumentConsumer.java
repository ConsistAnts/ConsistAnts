package de.hu_berlin.cchecker.headless.consumers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.CommandLineArgumentException;
import de.hu_berlin.cchecker.headless.CommandLineExecutionFailed;

/**
 * An {@link ArgumentConsumer} that opens the specified file and reads it
 * as a string with the default encoding.
 * 
 * @author lucabeurer-kellner
 */
public class FileStringArgumentConsumer extends KeyValueArgumentConsumer<String> {

	public FileStringArgumentConsumer(Argument argument) {
		super(argument);
	}

	@Override
	protected String extractValue(String argument) {
		if (null == argument) {
			throw new CommandLineArgumentException("No path specified for argument " + this.getArgument().getKeyword());
		}
		
		File file = new File(argument);
		
		if (!file.exists()) {
			throw new CommandLineExecutionFailed("File does not exist: " + argument);
		}
		
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			throw new CommandLineExecutionFailed("Failed to open " + argument + " for reading", e);
		}
	}

}
