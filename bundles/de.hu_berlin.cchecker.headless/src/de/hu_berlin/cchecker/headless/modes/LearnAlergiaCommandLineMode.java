package de.hu_berlin.cchecker.headless.modes;

import java.util.List;

import de.hu_berlin.cchecker.core.learning.alergia.Alergia;
import de.hu_berlin.cchecker.core.models.ModelSetup;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.headless.AbstractCommandLineMode;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.HeadlessIOUtils;
import de.hu_berlin.cchecker.headless.consumers.KeyValueArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ToggleArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.TraceDataSetArgumentConsumer;

/**
 * Command line mode "learn alergia" to learn PDFA models from a given trace data set file.
 * 
 * @author lucabeurer-kellner
 *
 */
public class LearnAlergiaCommandLineMode extends AbstractCommandLineMode {

	private TraceDataSetArgumentConsumer traceDatasetConsumer = new TraceDataSetArgumentConsumer(
			new Argument(false, "dataset", "The trace data set to learn a model from"));
	
	private KeyValueArgumentConsumer<Double> alphaConsumer = KeyValueArgumentConsumer.extract(
			new Argument(true, "alpha", "The alpha parameter for the alergia algorithm.", "double"),
				Alergia.DEFAULT_ALPHA, Double::parseDouble);
	
	private ToggleArgumentConsumer humanReadableConsumer = new ToggleArgumentConsumer(
			new Argument(true, "h", "Use a human-readable PDFA output format. (not usable for further processing)"));
	
	public LearnAlergiaCommandLineMode() {
		super("learn alergia", "Learns a PDFA model from a given input trace data set");
		
		registerConsumers(traceDatasetConsumer, alphaConsumer, humanReadableConsumer);
	}

	@Override
	public void run(List<String> mode) {
		ModelSetup.register();
		
		Alergia alergia = new Alergia();
		alergia.setAlpha(alphaConsumer.getValue());
		
		ProbabilisticAutomata model = alergia.learnModel(traceDatasetConsumer.getValue());
		
		performanceOut(alergia);
		
		out(HeadlessIOUtils.automatonToString(model, humanReadableConsumer.isPresent()));
	}

}
