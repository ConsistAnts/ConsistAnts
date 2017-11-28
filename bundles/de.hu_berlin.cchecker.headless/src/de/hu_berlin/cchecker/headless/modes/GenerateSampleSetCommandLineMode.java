package de.hu_berlin.cchecker.headless.modes;

import java.util.List;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.headless.AbstractCommandLineMode;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.consumers.KeyValueArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ProbabilisticAutomatonArgumentConsumer;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator.GeneratorResult;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator.RoundingStrategy;

/**
 * Command line mode "generate-sample-set" to generate sample sets for a given automaton.
 * 
 * @author lucabeurer-kellner
 *
 */
public class GenerateSampleSetCommandLineMode extends AbstractCommandLineMode {
	
	private ProbabilisticAutomatonArgumentConsumer automatonConsumer = new ProbabilisticAutomatonArgumentConsumer(
			new Argument(false, "automaton", "The automaton model to generate a sample set for."));
	
	private KeyValueArgumentConsumer<Integer> numberOfTracesConsumer = KeyValueArgumentConsumer.extract(
			new Argument(true, "number-of-traces", "The number of traces to generate", "integer"),
				20, Integer::parseInt);
	
	public GenerateSampleSetCommandLineMode() {
		super("generate-sample-set", "Generate a possible sample set for a given automaton");
		
		registerConsumers(automatonConsumer, numberOfTracesConsumer);
	}

	@Override
	public void run(List<String> mode) {
		ProbabilisticAutomata automaton = automatonConsumer.getValue();
		int numberOfTraces = numberOfTracesConsumer.getValue();
		
		SampleSetGenerator generator = new SampleSetGenerator(automaton, numberOfTraces,RoundingStrategy.UP);
		GeneratorResult result = generator.generatePossibleSampleSet();
		
		/** Print traces in simple input format */
		out(result.getDataSet().toString());
		
		verboseLog("Number of Traces: " + result.getNumberOfTraces());
		verboseLog("Representation rate: " + result.getRepresentationRate() + " %");
	}
	
}
