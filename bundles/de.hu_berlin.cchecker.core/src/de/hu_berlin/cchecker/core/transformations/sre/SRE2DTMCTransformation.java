package de.hu_berlin.cchecker.core.transformations.sre;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.uni_stuttgart.beehts.model.DTMC;
import de.uni_stuttgart.beehts.model.DTMC.Edge;
import de.uni_stuttgart.beehts.model.DTMC.Node;
import de.uni_stuttgart.beehts.model.SRE;
import de.uni_stuttgart.beehts.model.construction.SREBuilder;
import de.uni_stuttgart.beehts.transformation.Transformer;

/**
 * Transformation to transform a StochasticRegularExpression which is given as a {@link String}
 * to an equivalent {@link ProbabilisticAutomata}.
 *
 */
public class SRE2DTMCTransformation {
	/**
	 * Returns a {@link ProbabilisticAutomata} that is equivalent to the given
	 * stochastic regular expression.
	 * 
	 * @throws SREParseException if the given SRE is malformed
	 */
	public ProbabilisticAutomata transform(String stochasticRegularExpression) throws SREParseException {  
		DTMC equivalentDTMC = transformSreToDtmc(stochasticRegularExpression);
		return transformToProbabilisticAutomaton(equivalentDTMC);
	}

	/**
	 * Transforms the given {@link DTMC} to a {@link ProbabilisticAutomata}. 
	 */
	ProbabilisticAutomata transformToProbabilisticAutomaton(DTMC dtmc) {
		// Translate to our own {@link ProbabilisticAutomata} model
		ProbabilisticAutomataBuilder builder = ProbabilisticAutomataBuilder.newAutomaton();
		builder.startIn(dtmc.getInitialNode().index);
		
		
		
		// Translate all transitions
		for (Node node : dtmc.getNodes()) {
			for (Edge e : dtmc.getOutgoingEdges(node)) {
				builder.fromTo(e.from.index, e.to.index, e.getProbability(), e.character);
			}
		}
		
		// Translate all final nodes
		for (Node node : dtmc.getFinalNodes()) {
			builder.terminatesIn(node.index);
		}
		
		return builder.create();
	}

	/**
	 * Returns the equivalent DTMC for the given SRE.
	 * 
	 * @throws SREParseException if the given SRE is malformed
	 */
	DTMC transformSreToDtmc(String sre) throws SREParseException {
		SRE parsedSRE;
		
		try {
			parsedSRE = SREBuilder.parse(sre);
		} catch (Exception e) {
			throw new SREParseException(sre, e);
		}
		
		Transformer<SRE, DTMC> s2dTransformer = Transformer.getNewTransformer(parsedSRE);
		
		DTMC equivalentDTMC =  s2dTransformer.getTransformed();
		return equivalentDTMC;
	}
	
	/**
	 * An exception which is thrown if the parsing of SRE string fails.
	 */
	public final static class SREParseException extends Exception {
		private static final long serialVersionUID = 1L;

		private Exception internalException;
		private String sre;
		
		/**
		 * Creates a new parse exception with the given internal exception.
		 */
		public SREParseException(String sre, Exception internalException) {
			this.internalException = internalException;
			this.sre = sre;
		}

		/**
		 * Returns the internal parse exception that occurred.
		 */
		public Exception getInternalException() {
			return internalException;
		}
		
		@Override
		public String toString() {
			return "Failed to parse '" + this.sre + "': " + getInternalException().toString();
		}
	}
}
