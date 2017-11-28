package de.hu_berlin.cchecker.headless.modes;

import java.util.List;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation.SREParseException;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2ProbabilisticAutomatonTransformationImpl;
import de.hu_berlin.cchecker.headless.AbstractCommandLineMode;
import de.hu_berlin.cchecker.headless.Argument;
import de.hu_berlin.cchecker.headless.CommandLineExecutionFailed;
import de.hu_berlin.cchecker.headless.HeadlessIOUtils;
import de.hu_berlin.cchecker.headless.consumers.FileStringArgumentConsumer;
import de.hu_berlin.cchecker.headless.consumers.ToggleArgumentConsumer;

/**
 * A "sre-to-pdfa" command line mode.
 * 
 * @author lucabeurer-kellner
 *
 */
public class TransformSRECommandLineMode extends AbstractCommandLineMode {

	private FileStringArgumentConsumer sreFileConsumer = new FileStringArgumentConsumer(
			new Argument(false, "sreFile", "The location of the SRE file (.sre)"));

	private ToggleArgumentConsumer noMinimizeConsumer = new ToggleArgumentConsumer(
			new Argument(true, "no-minimization", "Do not minize as part of the transformation."));
	
	private ToggleArgumentConsumer humanReadableConsumer = new ToggleArgumentConsumer(
			new Argument(true, "h", "Use a human-readable PDFA output format.  (not usable for further processing)"));

	public TransformSRECommandLineMode() {
		super("sre-to-pdfa", "Transforms a SRE-file to a PDFA model.");
		
		registerConsumers(sreFileConsumer, noMinimizeConsumer, humanReadableConsumer);
	}

	@Override
	public void run(List<String> mode) {
		String sre = sreFileConsumer.getValue();
		
		SRE2ProbabilisticAutomatonTransformationImpl transformation = new SRE2ProbabilisticAutomatonTransformationImpl();
		try {
			ProbabilisticAutomata automaton = transformation.transform(sre, !noMinimizeConsumer.isPresent());
			String pdfaModel = HeadlessIOUtils.automatonToString(automaton, humanReadableConsumer.isPresent());
			
			out(pdfaModel);
			performanceOut(transformation);
		} catch (SREParseException e) {
			throw new CommandLineExecutionFailed("Failed to parse the given sre: " + sre, e);
		}
	}

}
