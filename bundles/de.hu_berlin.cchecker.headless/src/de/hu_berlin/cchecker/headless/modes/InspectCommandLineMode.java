package de.hu_berlin.cchecker.headless.modes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.headless.AbstractCommandLineMode;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.consumers.ProbabilisticAutomatonArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ToggleArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.TraceDataSetArgumentConsumer;

/**
 * Command line mode "inspect" to print basic metadata on .pdfa or .trc files.
 * 
 * @author lucabeurer-kellner
 *
 */
public class InspectCommandLineMode extends AbstractCommandLineMode {

	private TraceDataSetArgumentConsumer traceDatasetConsumer = new TraceDataSetArgumentConsumer(
			new Argument(true, "dataset", "The trace data set to inspect."));

	private ProbabilisticAutomatonArgumentConsumer automatonConsumer = new ProbabilisticAutomatonArgumentConsumer(
			new Argument(true, "automaton", "The automaton model to inspect."));
	
	private ToggleArgumentConsumer csvConsumer = new ToggleArgumentConsumer(
			new Argument(true, "csv", "Use a csv output format for the information."));
	
	public InspectCommandLineMode() {
		super("inspect", "Prints general information on the supplied automaton or dataset.");
		
		this.registerConsumers(traceDatasetConsumer, automatonConsumer, csvConsumer);
	}

	@Override
	public void run(List<String> mode) {
		boolean hasAutomaton = automatonConsumer.hasValue();
		boolean hasDataset = traceDatasetConsumer.hasValue();
		
		if (hasAutomaton) {
			runAutomaton(automatonConsumer.getValue());
		} else if (hasDataset) {
			runDataset(traceDatasetConsumer.getValue());
		} else {
			out("No file to inspect was specified.");
		}
		
	}
	
	public void runAutomaton(ProbabilisticAutomata automaton) {
		double averageNumberOfTransitions = ProbabilisticAutomataVisitor
			.stateStream(automaton)
			.mapToInt(s -> s.getOutgoingTransitions().size())
			.average().orElse(0.0);
		
		int numeberOfTransitions = ProbabilisticAutomataVisitor
				.stateStream(automaton)
				.mapToInt(s -> s.getOutgoingTransitions().size())
				.sum();
		
		int alphabetSize = automaton.getTransitionLabels().size();
		
		outInfo(Arrays.asList(
				Pair.create("Number of States", automaton.getStates().size()),
				Pair.create("Number of Transitions", numeberOfTransitions),
				Pair.create("Average Number of Transitions", averageNumberOfTransitions),
				Pair.create("Alphabet Size", alphabetSize)));
	}
	
	public void runDataset(TraceDataset dataset) {
		int numberOfTraces = dataset.getTraces().size();
		double averageLength = dataset.getTraces().stream().mapToInt(t -> t.getTransitions().size()).average().orElse(0.0);
		int alphabetSize = dataset.getTransitionIdToLabel().size();
		
		outInfo(Arrays.asList(
				Pair.create("Number of Traces", numberOfTraces),
				Pair.create("Average Trace Length", averageLength),
				Pair.create("Alphabet Size", alphabetSize)));
	}
	
	/**
	 * Prints the given list of properties either as
	 * CSV or human-readable list. (as specified by {@link #csvConsumer})
	 */
	public void outInfo(List<Pair<String,?>> list) {
		if (csvConsumer.isPresent()) {
			String header = list.stream().map(p -> p.first).collect(Collectors.joining(";"));
			String data = list.stream().map(p -> p.second.toString()).collect(Collectors.joining(";"));
			
			out(header + "\n" + data);
		} else {
			out(list.stream().map(p -> p.first + ": " + p.second).collect(Collectors.joining("\n")));
		}
	}

}
