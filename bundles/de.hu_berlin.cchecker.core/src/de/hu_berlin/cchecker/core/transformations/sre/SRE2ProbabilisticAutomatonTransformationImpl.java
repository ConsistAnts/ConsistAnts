package de.hu_berlin.cchecker.core.transformations.sre;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.stopwatch.StopWatchable;
import de.hu_berlin.cchecker.core.stopwatch.Stopwatch;
import de.hu_berlin.cchecker.core.transformations.epsilonremoval.EpsilonRemovalTransformation;
import de.hu_berlin.cchecker.core.transformations.epsilonremoval.EpsilonRemovalTransformationImpl;
import de.hu_berlin.cchecker.core.transformations.minimization.JFlapPAMinimization;
import de.hu_berlin.cchecker.core.transformations.minimization.PAMinimization;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation.SREParseException;
import de.hu_berlin.cchecker.core.transformations.unreachableremoval.UnreachableStateRemovalTransformation;
import de.hu_berlin.cchecker.core.transformations.unreachableremoval.UnreachableStateRemovalTransformationImpl;

/**
 * Default implementation of {@link SRE2ProbabilisticAutomatonTransformation}.
 */
public class SRE2ProbabilisticAutomatonTransformationImpl implements SRE2ProbabilisticAutomatonTransformation, StopWatchable {

	private Stopwatch stopwatch = null;
	
	
	@Override
	public ProbabilisticAutomata transform(String sre) throws SREParseException {
		return transform(sre, true);
	}
	
	public ProbabilisticAutomata transform(String sre, boolean minimize) throws SREParseException {
		stopwatch = new Stopwatch();
		stopwatch.start("SRE-to-PDFA Transformation");
		
		// create transformations
		SRE2DTMCTransformation sre2DtmcTransformation = new SRE2DTMCTransformation();
		EpsilonRemovalTransformation epsilonRemovalTransformation = new EpsilonRemovalTransformationImpl();
		UnreachableStateRemovalTransformation unreachableStateRemovalTransformation = new UnreachableStateRemovalTransformationImpl();
		
		// apply sre2dtmc transformation
		ProbabilisticAutomata epsilonAutomaton = sre2DtmcTransformation.transform(sre);
		
		stopwatch.checkpoint("Transformed SRE to DTMC");
		
		// apply epsilon removal
		ProbabilisticAutomata automaton = epsilonRemovalTransformation.transform(epsilonAutomaton);
		
		stopwatch.checkpoint("Removed epsilon transitions from resulting model");
		
		if (minimize) {
			// apply minimization
			PAMinimization minimization = new JFlapPAMinimization();
			automaton = minimization.minimize(automaton);
			
			stopwatch.checkpoint("Minimized automaton");
		}
		
		
		
		// apply unreachable state removal
		ProbabilisticAutomata result = unreachableStateRemovalTransformation.transform(automaton);
		
		stopwatch.checkpoint("Removed unreachable states");
		stopwatch.finish();
		
		return result;
	}

	@Override
	public Stopwatch getStopwatch() {
		return this.stopwatch;
	}

}
