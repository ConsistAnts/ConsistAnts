package de.hu_berlin.cchecker.core.learning.blockwise;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import de.hu_berlin.cchecker.core.learning.LearningAlgorithm.LearningAlgorithmException;

public class SimpleSREBuilder {
	/** label id that is used to represent the epsilon character */
	public final static int EPSILON_ID = -1;
	private DecimalFormat format;
	
	public SimpleSREBuilder(String decimalFormatPattern) {
		this.format = new DecimalFormat(decimalFormatPattern, new DecimalFormatSymbols(Locale.ENGLISH));
	}
	
	String altenativeComponent(String expression, int rate) {
		return String.format("(%s)[%d]", expression, rate);
	}

	String concat(String... expressions) {
		return String.join(" : ", expressions);
	}

	/**
	 * Returns a SRE that is the factor-th repetition of the given expression.
	 */
	String factorConcat(String expression, int factor) {
		String[] expressions = new String[factor];
		Arrays.fill(expressions, expression);
		return String.join(" : ", expressions);
	}
	
	String kleeneStar(String expression, double rate) {
		return String.format("(%s*%s)", expression, format.format(rate));
	}
	
	String paranthesis(String expression) {
		return String.format("(%s)", expression);
	}

	String atomic(int id, Map<Integer, String> labelMapping) {
		if (id == EPSILON_ID) {
			// for the lack of proper epsilon syntax support, use an equivalent expression here
			return "(EPSILON*0.0)";
		}
		String label = labelMapping.get(id);
		if (null == label) {
			throw new LearningAlgorithmException(String.format("Failed to build SRE: Couldn't find label mapping for ID %d", id));
		}
		return label;
	}

	String alternative(String... components) {
		return String.join(" + ", components);
	}

}
