package de.hu_berlin.cchecker.core.models.pdfa.tests;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;

import com.google.common.base.Function;

import de.hu_berlin.cchecker.core.models.pdfa.PdfaFactory;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;

/**
 * Utility class for working with {@link ProbabilisticAutomata}s in test scenarios.
 */
public class ProbabilisticAutomataTestUtils {
	
	public static final String CCTEST_SCHEME = RedirectingURIConverter.CCTEST_SCHEME;
	
	/**
	 * Returns a {@link ProbabilisticAutomataBuilder} expression that can be used
	 * to create a new automaton. 
	 */
	public static String toBuilderExpression(ProbabilisticAutomata automata) {
		
		StringBuilder textualRepresentation = new StringBuilder();
		
		textualRepresentation.append("ProbabilisticAutomataBuilder.newAutomaton()\n");
		textualRepresentation.append("\t.startIn(" + automata.getStartState().getStateId() + ")\n");
		
		ProbabilisticAutomataVisitor.visit(automata, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				if (state.getTerminatingProbability() > 0) {
					textualRepresentation.append("\t.terminatesInWithProbability(" + 
							state.getStateId() + ", " + state.getTerminatingProbability() + ")\n");
				}
			}
			@Override
			public void visit(ProbabilisticTransition transition) {
				ProbabilisticState target = transition.getTarget();
				String label = automata.getTransitionLabels().get(new Integer(transition.getTransitionId()));
				textualRepresentation.append("\t.fromTo(" + transition.getSource().getStateId() + ", " + target.getStateId() + 
						", " + transition.getProbability() + ", \"" + label + "\")\n");
			}
		});
		
		textualRepresentation.append("\t.create();\n");
		
		return textualRepresentation.toString();
	}
	
	/**
	 * Returns a textual representation of the given automata.
	 * 
	 * Works like {@link ProbabilisticAutomataUtils#toTextualRepresentation(ProbabilisticAutomata)} but normalises the 
	 * state numbers. 
	 * 
	 */
	public static String toCanonicalTextualRepresentation(ProbabilisticAutomata automata) {
		
		StringBuilder textualRepresentation = new StringBuilder();
		final HashMap<Integer, Integer> stateNumberMap = new HashMap<>();
		
		ProbabilisticAutomataVisitor.visit(automata, new ProbabilisticAutomataVisitor() {
			private int normalizeStateNumber(int number) {
				if (stateNumberMap.containsKey(number)) {
					return stateNumberMap.get(number);
				} else {
					Integer maxStateNumber = stateNumberMap.isEmpty() ? 0 : Collections.max(stateNumberMap.values());
					int mappedNumber = maxStateNumber + 1;
					stateNumberMap.put(number, mappedNumber);
					return mappedNumber;
				}
			}
			
			@Override
			public void visit(ProbabilisticState state) {
				int stateNumber = normalizeStateNumber(state.getStateId());
				textualRepresentation.append(stateNumber + "[" + state.getTerminatingProbability() + "]\n");
			}
			@Override
			public void visit(ProbabilisticTransition transition) {
				ProbabilisticState target = transition.getTarget();
				String label = automata.getTransitionLabels().get(new Integer(transition.getTransitionId()));
				
				int normalizedTargetNumber = normalizeStateNumber(target.getStateId());
				
				textualRepresentation.append("\t-" + label + "[" + transition.getProbability() + "]-> " + normalizedTargetNumber + "\n");
			}
		});
		
		return textualRepresentation.toString();
	}
	
	/**
	 * Creates a {@link ProbabilisticState} with the given id.
	 */
	public static ProbabilisticState createState(int id) {
		ProbabilisticState state = PdfaFactory.eINSTANCE.createProbabilisticState();
		state.setStateId(id);
		return state;
	}
	
	/**
	 * Creates a new transition in a automata which transitions
	 * between two probabilistic states with a given probability and label.
	 * 
	 * This method will do the required work to make an entry for the given label
	 * in the {@link ProbabilisticAutomata#getTransitionLabels()} map. This will result in 
	 * transitions with the same label having the same numeric transition id.
	 * 
	 * @param a The automata to create a new transition in.
	 * @param from The state the transitions is starting from
	 * @param to The state the transition is targeting.
	 * @param label The label of the transition
	 * @param probability The probability of the transition
	 */
	public static ProbabilisticTransition createTransition(ProbabilisticAutomata a, 
			ProbabilisticState from, ProbabilisticState to,
			String label, double probability) {
		// Get id-to-transitions label
		EMap<Integer, String> transitionLabels = a.getTransitionLabels();
		
		// Invert the transitionLabels map
		Map<String, Integer> labelToIdMap = new HashMap<>();
		for (Entry<Integer, String> idToLabelEntry : transitionLabels.entrySet()) {
			labelToIdMap.put(idToLabelEntry.getValue(), idToLabelEntry.getKey());
		}
		
		// If there is no label-to-id mapping for label yet, add it to the
		// id-to-transitions map of our automata and to our inverted version labelToidMap
		if (!labelToIdMap.containsKey(label)) {
			Integer maximumTransitionId = transitionLabels.isEmpty() ? 
					0 : Collections.max(transitionLabels.keySet());
			transitionLabels.put(maximumTransitionId + 1, label);
			labelToIdMap.put(label, maximumTransitionId + 1);
		}
		
		// Finally get the corresponding transition id for our label
		int labelId = labelToIdMap.get(label);
		
		// Create actual model object and set values
		ProbabilisticTransition t = PdfaFactory.eINSTANCE.createProbabilisticTransition();
		t.setSource(from);
		t.setTarget(to);
		t.setTransitionId(labelId);
		t.setProbability(probability);
		
		
		return t;
	}
	
	public static void assertStructurallyEqual(String message, ProbabilisticAutomata expected, ProbabilisticAutomata actual) {
		assertEquals(message, toCanonicalTextualRepresentation(expected), toCanonicalTextualRepresentation(actual));
	}
	
	/**
	 * Asserts that the actual given {@link ProbabilisticAutomata} equals the expected {@link ProbabilisticAutomata}.
	 * 
	 * This includes the state numbers and transition labels.
	 */
	public static void assertEqualAutomata(String message, ProbabilisticAutomata expected, ProbabilisticAutomata actual) {
		assertEquals(message, ProbabilisticAutomataUtils.toTextualRepresentation(expected), ProbabilisticAutomataUtils.toTextualRepresentation(actual));
	}
	
	/**
	 * Rounds all probabilities in the given {@link ProbabilisticAutomata} to 
	 * a fixed number of digits after the decimal point.
	 */
	public static void roundProbabilities(ProbabilisticAutomata automata, int precision) {
		Function<Double, Double> toFixed = n -> Math.round(n * Math.pow(10, precision)) / Math.pow(10, precision);
		
		ProbabilisticAutomataVisitor.visit(automata, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				state.setTerminatingProbability(toFixed.apply(state.getTerminatingProbability()));
			}
			@Override
			public void visit(ProbabilisticTransition transition) {
				transition.setProbability(toFixed.apply(transition.getProbability()));
			}
		});
	}
	
	/**
	 * Renames the labels in the given automaton according to the list of fromTo strings.
	 * 
	 * The list of fromTo is evaluated as a listed map. (e.g. "s", "start", "w", "won" means
	 * "s" -> "start", "w" -> "won")
	 */
	public static void renameLabels(ProbabilisticAutomata automaton, String... fromTo) {
		HashMap<String, String> changes = new HashMap<>();
		for (int i=0; i<fromTo.length; i += 2) {
			changes.put(fromTo[i], fromTo[i + 1]);
		}
		HashMap<Integer, String> newMapping = new HashMap<>();
		for (Entry<Integer, String> mapping : automaton.getTransitionLabels().map().entrySet()) {
			if (changes.containsKey(mapping.getValue())) {
				newMapping.put(mapping.getKey(), changes.get(mapping.getValue()));
			} else {
				newMapping.put(mapping.getKey(), mapping.getValue());
			}
		}
		automaton.getTransitionLabels().clear();
		automaton.getTransitionLabels().map().putAll(newMapping);
		return;
	}
	
	/**
	 * Creates the PDFA which was the result of the Alergia algorithm in task 1.
	 */
	public static ProbabilisticAutomata createTask1AlergiaResult() {
		ProbabilisticAutomata automata = PdfaFactory.eINSTANCE.createProbabilisticAutomata();
		
		ProbabilisticState state1 = createState(1);
		ProbabilisticState state2 = createState(2);
		ProbabilisticState state3 = createState(3);
		ProbabilisticState state5 = createState(5);
		ProbabilisticState state6 = createState(6);
		ProbabilisticState state10 = createState(10);
		
		state1.getOutgoingTransitions().add(
			createTransition(automata, state1, state2, "s", 1.0));
		state1.setTerminatingProbability(0.0);
		
		state2.getOutgoingTransitions().add(
				createTransition(automata, state2, state3, "t", 0.8095));
		state2.getOutgoingTransitions().add(
				createTransition(automata, state2, state3, "w", 0.1905));
		state2.setTerminatingProbability(0.0);
		
		state3.getOutgoingTransitions().add(
				createTransition(automata, state3, state5, "l", 0.10));
		state3.getOutgoingTransitions().add(
				createTransition(automata, state3, state6, "t", 0.1143));
		state3.getOutgoingTransitions().add(
				createTransition(automata, state3, state3, "w", 0.7));
		state3.setTerminatingProbability(0.0857);
		
		state5.getOutgoingTransitions().add(
				createTransition(automata, state5, state5, "l", 0.8205));
		state5.setTerminatingProbability(0.1795);
		
		state6.getOutgoingTransitions().add(
				createTransition(automata, state6, state10, "l", 0.1429));
		state6.getOutgoingTransitions().add(
				createTransition(automata, state6, state6, "t", 0.6857));
		state6.setTerminatingProbability(0.1714);
		
		state10.getOutgoingTransitions().add(
				createTransition(automata, state10, state10, "l", 0.7778));
		state10.setTerminatingProbability(0.2222);
		
		automata.getStates().add(state1);
		automata.getStates().add(state2);
		automata.getStates().add(state3);
		automata.getStates().add(state5);
		automata.getStates().add(state6);
		automata.getStates().add(state10);
		
		automata.setStartState(state1);
		
		return automata;
	}
	
	/**
	 * Creates the PDFA which was the result of the Alergia algorithm in the given paper.
	 */
	public static ProbabilisticAutomata createPaperAlergiaResult() {
		ProbabilisticAutomata automata = PdfaFactory.eINSTANCE.createProbabilisticAutomata();
		
		ProbabilisticState state1 = createState(1);
		ProbabilisticState state3 = createState(3);
		
		state1.getOutgoingTransitions().add(
			createTransition(automata, state1, state1, "0", 0.24));
		state1.getOutgoingTransitions().add(
				createTransition(automata, state1, state3, "1", 0.16));
		state1.setTerminatingProbability(0.6);
		
		state3.getOutgoingTransitions().add(
				createTransition(automata, state3, state1, "0", 0.67));
		state3.getOutgoingTransitions().add(
				createTransition(automata, state3, state3, "1", 0.33));
		state3.setTerminatingProbability(0.0);
		
		automata.getStates().add(state1);
		automata.getStates().add(state3);
		
		automata.setStartState(state1);
		
		return automata;
	}
	
	/**
	 * Creates the PDFA which was the result of the Alergia algorithm another paper.
	 * 
	 * See page 15 of paper "Learning Probabilistic Finite Automata" 
	 * http://pagesperso.lina.univ-nantes.fr/~cdlh//book/Learning_Probabilistic_Finite_Automata.pdf
	 */
	public static ProbabilisticAutomata createPaper2AlergiaResult() {
//		ProbabilisticAutomata automata = PdfaFactory.eINSTANCE.createProbabilisticAutomata();
//		
//		ProbabilisticState state1 = createState(1);
//		ProbabilisticState state2 = createState(2);
//		
//		state1.getOutgoingTransitions().add(
//			createTransition(automata, state1, state1, "0", 0.354));
//		state1.getOutgoingTransitions().add(
//				createTransition(automata, state1, state2, "1", 0.351));
//		state1.setTerminatingProbability(0.295);
//		
//		state2.getOutgoingTransitions().add(
//				createTransition(automata, state2, state1, "1", 0.140));
//		state2.getOutgoingTransitions().add(
//				createTransition(automata, state2, state2, "0", 0.274));
//		state2.setTerminatingProbability(0.414);
//		
//		automata.getStates().add(state1);
//		automata.getStates().add(state2);
//		
//		automata.setStartState(state1);
//		
//		return automata;
		
		double state1All = 698 + 354 + 351;
		double state2All =  302 + 49 + 96;
		
		return ProbabilisticAutomataBuilder.newAutomaton()
			.fromTo(1, 1, 354.0 / state1All, "0")
			.fromTo(1, 2, 351.0 / state1All, "1")
			.terminatesInWithProbability(1, 698.0 / state1All)
			.fromTo(2, 1, 49.0 / state2All, "1")
			.fromTo(2, 2, 96.0 / state2All, "0")
			.terminatesInWithProbability(2, 302 / state2All)
			.startIn(1)
			.create();
	}
	
	/**
	 * Creates the PDFA which was the result of the Alergia algorithm in the given paper.
	 */
	public static ProbabilisticAutomata createTrace1AlergiaResult() {
		ProbabilisticAutomata automata = PdfaFactory.eINSTANCE.createProbabilisticAutomata();
		
		ProbabilisticState state1 = createState(1);
		
		state1.getOutgoingTransitions().add(
			createTransition(automata, state1, state1, "0", 0.33));
		state1.getOutgoingTransitions().add(
				createTransition(automata, state1, state1, "1", 0.44));
		state1.setTerminatingProbability(0.33);
		
		automata.getStates().add(state1);
		
		automata.setStartState(state1);
		
		return automata;
	}
	
	/**
	 * Configures the given {@link ResourceSet} to read test resources from the given directory.
	 * 
	 * For instance, if the specified resource location is 'res/' the URI 'test.trc' will be resolved to
	 * 'res/test.trc'. 
	 * 
	 * The expression <code>resourceSet.getResource(URI.createURI("test.trc"))</code> will return the resource
	 * that was read from 'res/test.trc'.
	 */
	public static void configureTestResourceLocation(ResourceSet resourceSet, String testResourceLocation) {
		resourceSet.setURIConverter(new RedirectingURIConverter(testResourceLocation));
	}

	/**
	 * A {@link URIConverter} which redirects all URIs into a specified
	 * directory. This normalizes all URLs to be relative URIs in the specified directory.
	 */
	private static final class RedirectingURIConverter extends ExtensibleURIConverterImpl {
		public static final String CCTEST_SCHEME = "cctest";
		private URI root;
		
		/**
		 * Creates a new {@link RedirectingURIConverter} 
		 * to redirect all URIs to the given project-relative location.
		 */
		public RedirectingURIConverter(String resourceDirectory) {
			super();
			root = URI.createURI(resourceDirectory);
		}
		@Override
		public URI normalize(URI uri) {
			if (!CCTEST_SCHEME.equals(uri.scheme())) {
				return super.normalize(uri);
			}
			return normalize(root.appendSegments(uri.segments()));
		}
		
	}
	
	private ProbabilisticAutomataTestUtils() {
		// Prevent instantiation of this class
	}
}
