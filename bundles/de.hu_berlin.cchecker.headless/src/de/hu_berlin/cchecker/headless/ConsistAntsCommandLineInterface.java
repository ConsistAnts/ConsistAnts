package de.hu_berlin.cchecker.headless;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import de.hu_berlin.cchecker.headless.consumers.ArgumentConsumer;
import de.hu_berlin.cchecker.headless.modes.CheckCommandLineMode;
import de.hu_berlin.cchecker.headless.modes.CommandLineMode;
import de.hu_berlin.cchecker.headless.modes.GenerateSampleSetCommandLineMode;
import de.hu_berlin.cchecker.headless.modes.InspectCommandLineMode;
import de.hu_berlin.cchecker.headless.modes.LearnAlergiaCommandLineMode;
import de.hu_berlin.cchecker.headless.modes.LearnSRECommandLineMode;
import de.hu_berlin.cchecker.headless.modes.RandomPdfaCommandLineMode;
import de.hu_berlin.cchecker.headless.modes.TransformSRECommandLineMode;

/**
 * Main-class for the Command Line Interface of the Consistency Checker Framework.
 * 
 * Only use {@link #getCurrent()} to get an instance of this class.
 * 
 * @author lucabeurer-kellner 
 *
 */
public class ConsistAntsCommandLineInterface {

	/**
	 * Registry for all supported command line modes.
	 */
	private static final List<CommandLineMode> SUPPORTED_MODES = Arrays.asList(
			new LearnAlergiaCommandLineMode(), // learn alergia
			new LearnSRECommandLineMode(), // learn bla
			new CheckCommandLineMode(), // check
			new TransformSRECommandLineMode(), // sre-to-pdfa
			new InspectCommandLineMode(), // inspect
			new RandomPdfaCommandLineMode(), // random-pdfa
			new GenerateSampleSetCommandLineMode(), // generate-sample-set
			new HelpMode() // help
		);
	
	private static final String DASH = "-";
	private static ConsistAntsCommandLineInterface INSTANCE = null;
	
	/**
	 * Returns the currently active CLI instance.
	 * 
	 * Returns {@code null} if there is none.
	 */
	public static ConsistAntsCommandLineInterface getCurrent() {
		return INSTANCE;
	}
	
	/** Map of supplied arguments */
	private Map<String, String> argumentMap;
	
	/** Global toggles */
	private boolean verboseMode = false;
	private boolean performanceMode = false;

	/** User-specified {@link CommandLineMode} specifier */
	private List<String> modeSpecifiers;	
	
	/**
	 * Create a new CLI instance for the given list of command line arguments.
	 */
	private ConsistAntsCommandLineInterface(String[] args) {
		final List<String> argumentList = Arrays.asList(args);
		
		if (null != INSTANCE) {
			throw new IllegalStateException("There must only be one Cli instance at a time. Use Cli.getCurrent() to access it.");
		} else {
			// set singleton instance
			INSTANCE = this;
		}
		
		/** register toggle listeners*/
		AbstractCommandLineMode.verboseModeConsumer.addToggleListener(t -> this.verboseMode = true);
		AbstractCommandLineMode.performanceModeConsumer.addToggleListener(t -> this.performanceMode = true);
		
		argumentMap = new HashMap<>();
		modeSpecifiers = new ArrayList<>();
		
		parseArguments(argumentList);
	}
	
	/**
	 * Runs the CLI. 
	 * 
	 * Assumes {@link #argumentMap} and {@link #modeSpecifiers} to be fully populated.
	 */
	void run() {
		// if the cli is called without any arguments
		if (modeSpecifiers.isEmpty() && argumentMap.isEmpty()) {
			showHelp();
			return;
		}
		
		// if just the mode is missing, throw an error
		if (modeSpecifiers.isEmpty()) {
			throw new CommandLineArgumentException("No mode was specified");
		}
		Optional<CommandLineMode> optionalMode = SUPPORTED_MODES.stream().filter(m -> m.isApplicableForMode(modeSpecifiers)).findFirst();
		
		if (!optionalMode.isPresent()) {
			throw new CommandLineArgumentException("Unknown CLI mode: " + modeSpecifiers.toString());
		}
		
		CommandLineMode mode = optionalMode.get();
		
		// consume arguments
		ArgumentConsumer consumer = mode.getArgumentConsumer();
		Map<String, String> remainder = consumer.consume(argumentMap);
		
		// check if unconsumed arguments remain
		if (!remainder.isEmpty()) {
			System.out.println("Warning: The following arguments were not recognized: " + remainder.entrySet());
		}
		
		// check for missing arguments
		List<Argument> missingArguments = consumer.getMissingArguments();
		if (!missingArguments.isEmpty()) {
			String missingArgumentsDescription = missingArguments.stream().map(Argument::toString).collect(Collectors.joining("\n"));
			throw new CommandLineArgumentException("Missing arguments:\n " + missingArgumentsDescription);
		}

		try {
			// actually run mode
			mode.run(modeSpecifiers);
		} catch (CommandLineExecutionFailed e) {
			System.err.println("Error: " + e.getMessage());
			
			if (!isVerbose()) {
				System.err.println("You can try re-running the command with argument '-v' for a more detailed error message.");
			}
			
			if (this.isVerbose()) {
				// print stack-trace in verbose mode
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns true iff verbose logging is enabled for this CLI
	 */
	public boolean isVerbose() {
		// check if verbose argument was consumed
		return verboseMode;
	}
	
	/**
	 * Returns <code>true</code> iff this Cli is in performance mode.
	 */
	public boolean isPerformance() {
		// check if performance argument was consumed
		return performanceMode;
	}
	
	/**
	 * Parses the given raw list of arguments and populates {@link #argumentMap} and {@link #modeSpecifiers}.
	 */
	private void parseArguments(final List<String> argumentList) {
		boolean acceptModeArguments = true;
		final int argn = argumentList.size();
		
		for (int i=0; i<argn; i++) {
			final String segment = argumentList.get(i);
			
			// if dash argument
			if (segment.startsWith(DASH)) {
				// from now on, we don't accept mode arguments (dash-less) anymore
				acceptModeArguments = false;
				
				final String key = segment.substring(1);
				String value = "";
				
				// check if there is another segment following the current
				if (i + 1 < argn) {
					final String next = argumentList.get(i + 1);
					if (next.startsWith(DASH)) {
						// value-less argument, use empty string as value
						argumentMap.put(key, value);
						continue;
					} else {
						// next segment is value
						value = next;
						// skip regular processing of next segment
						i += 1;
						argumentMap.put(key, value);
						continue;
					}
				} else {
					// value-less argument, use empty string as value
					argumentMap.put(key, value);
					continue;
				}
			} else {
				if (!acceptModeArguments) {
					throw new CommandLineArgumentException("Invalid argument format. Please specify modes first, parameters second");
				}
				
				// add to modes
				modeSpecifiers.add(segment);
			}
		}
	}

	/**
	 * Shows a complete help description of all modes and commands.
	 */
	static void showHelp() {
		String header = "USAGE: consistants-cli <MODE> <ARGUMENTS>\n"
				+ "The command line tool support the following modes:\n\n";
		
		String modesHelp = SUPPORTED_MODES.stream().map(CommandLineMode::getHelp).collect(Collectors.joining("\n\n")); 
		
		System.out.println(header + modesHelp);
	}

	/**
	 * Main entry point of the CLI.
	 */
	public static void main(String[] args) {
		try {
			ConsistAntsCommandLineInterface cli = new ConsistAntsCommandLineInterface(args);		
			cli.run();
		} catch (CommandLineArgumentException e) {
			System.err.println("Invalid arguments: " + e.getMessage() + "\n");
			System.out.println("Run 'help' for instruction on how to use the command line tools");
		}
	}

	/**
	 * Help mode that just shows help using {@link #showHelp}
	 */
	private static class HelpMode extends AbstractCommandLineMode {
		public HelpMode() {
			super("help", "Shows this help text");
		}

		@Override
		public void run(List<String> mode) {
			showHelp();
		}
		
		@Override
		public String getHelp() {
			// do not show help for -v, -perf, etc.
			return HelpBuilder.newHelp(this.getModeName(), this.getDescription()).toString();
		}
	}
}
