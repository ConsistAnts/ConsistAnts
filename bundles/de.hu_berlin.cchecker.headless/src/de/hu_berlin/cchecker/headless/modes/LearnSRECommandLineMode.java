package de.hu_berlin.cchecker.headless.modes;

import java.util.List;

import de.hu_berlin.cchecker.core.learning.blockwise.BlockwiseLeftAlignedLearningAlgorithm;
import de.hu_berlin.cchecker.headless.AbstractCommandLineMode;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.consumers.TraceDataSetArgumentConsumer;

/**
 * Command line mode "learn bla" to learn a SRE from a given trace data set file. 
 * 
 * @author lucabeurer-kellner
 *
 */
public class LearnSRECommandLineMode extends AbstractCommandLineMode {

	private TraceDataSetArgumentConsumer traceDatasetConsumer = new TraceDataSetArgumentConsumer(
			new Argument(false, "dataset", "The trace data set to learn a model from"));
	
	public LearnSRECommandLineMode() {
		super("learn bla", "Learns a SRE from a given input trace data set using the Blockwise Left-Aligned algorithm");
		
		registerConsumers(traceDatasetConsumer);
	}

	@Override
	public void run(List<String> mode) {
		BlockwiseLeftAlignedLearningAlgorithm algorithm = new BlockwiseLeftAlignedLearningAlgorithm();
		
		String model = algorithm.learnModel(traceDatasetConsumer.getValue());
		
		out(model);
		performanceOut(algorithm);
	}

}
