package de.hu_berlin.cchecker.headless;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;
import de.hu_berlin.cchecker.headless.consumers.ArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.KeyValueArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.MultiArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ToggleArgumentConsumer;
import de.hu_berlin.cchecker.headless.modes.CommandLineMode;

/**
 * An abstract base class for simple {@link CommandLineMode}s.
 */
public abstract class AbstractCommandLineMode implements CommandLineMode {
	private final String description;
	private List<ArgumentConsumer> consumers = new ArrayList<>();
	
	/** Singleton verbose trigger argument consumer that enables verbose logging on a global level */
	static final ToggleArgumentConsumer verboseModeConsumer = new ToggleArgumentConsumer(
			new Argument(true, "v", "Enables verbose output (e.g. additional progress information)"));
	
	/** Singleton performance mode trigger. When set all output-related non-measured operation can be skipped */ 
	static final ToggleArgumentConsumer performanceModeConsumer = new ToggleArgumentConsumer(new Argument(true, "perf",
			"Performs all operations as specified but only outputs performance information"));
	
	// registry point for general abstract consumers
	private final List<ArgumentConsumer> abstractConsumers = Arrays.asList(verboseModeConsumer, performanceModeConsumer);
	
	private List<String> modeSpecifier;
	
	public AbstractCommandLineMode(String mode, String description) {
		this.modeSpecifier = Arrays.asList(mode.split("\\ "));
		this.description = description;
		
		this.consumers.addAll(abstractConsumers);
	}

	@Override
	public boolean isApplicableForMode(List<String> modes) {
		if (modes.size() != this.modeSpecifier.size()) {
			return false;
		}
		
		// check each specifier segment for equality
		for (int i=0; i<modes.size(); i++) {
			if (!modes.get(i).equals(modeSpecifier.get(i))) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public final ArgumentConsumer getArgumentConsumer() {
		return new MultiArgumentConsumer(consumers);
	}
	
	/**
	 * Verbose logs the given line.
	 * 
	 * Does nothing if "-v" wasn't specified.
	 */
	public void verboseLog(String line) {
		if (ConsistAntsCommandLineInterface.getCurrent().isVerbose() && !ConsistAntsCommandLineInterface.getCurrent().isPerformance()) {
			System.out.println("[VERBOSE] " + line);
		}
	}
	
	/**
	 * Prints the given line to the console except
	 * when the output is suppressed.
	 */
	public void out(String line) {
		if (!ConsistAntsCommandLineInterface.getCurrent().isPerformance()) {
			System.out.println(line);
		}
	}
	
	/**
	 * Prints performance information of the given {@link StopWatchable} if 
	 * the '-perf' argument was specified.
	 */
	public void performanceOut(StopWatchable watchable) {
		if (ConsistAntsCommandLineInterface.getCurrent().isPerformance()) {
			Stopwatch stopwatch = watchable.getStopwatch();
			if (null == stopwatch) {
				System.err.println("Failed to get stopwatch for performance output");
				return;
			}
			
			Map<String, Double> evaluationTable = stopwatch.getEvaluationTable();
			
			
			String tableHeader = evaluationTable.entrySet().stream()
					.sorted(Comparator.comparing(Entry<String, Double>::getValue))
					.map(Entry<String, Double>::getKey)
					.collect(Collectors.joining(";"));
			
			String tableContent = evaluationTable.entrySet().stream()
				.sorted(Comparator.comparing(Entry<String, Double>::getValue))
				.map(e -> Double.toString(e.getValue()))
				.collect(Collectors.joining(";"));
			
			System.out.println(tableHeader + "\n" + tableContent);
		}
	}
	
	/**
	 * Registers the given {@link ArgumentConsumer} with this mode.
	 */
	public void registerConsumers(ArgumentConsumer... consumer) {
		this.consumers.addAll(Arrays.asList(consumer));
		
		// make sure abstract consumers are at the bottom of the consumer list
		Collections.sort(this.consumers, Comparator.comparing(abstractConsumers::contains));
	}
	
	@Override
	public String getHelp() {
		return HelpBuilder.newHelp(this.getModeName(), this.getDescription())
				.arguments(this.consumers.stream()
						.filter(c -> c instanceof KeyValueArgumentConsumer)
						.map(c -> ((KeyValueArgumentConsumer<?>)c).getArgument())
						.collect(Collectors.toList()))
				.toString();
	}
	
	/**
	 * Returns the name of this command line mode. (space separated mode specifier)
	 */
	public String getModeName() {
		return this.modeSpecifier.stream().collect(Collectors.joining(" "));
	}

	/**
	 * Returns a user-faced textual description of this command line mode.
	 */
	public String getDescription() {
		return description;
	}
}
