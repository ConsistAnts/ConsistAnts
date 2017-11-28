package de.hu_berlin.cchecker.core.transformations.sre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation.SREParseException;
import de.uni_stuttgart.beehts.model.DTMC;
import de.uni_stuttgart.beehts.model.DTMC.Edge;
import de.uni_stuttgart.beehts.model.DTMC.Node;

@RunWith(JUnit4.class)
public class SRE2DTMCTransformationTest {
	
	/**
	 * Tests that basic SRE parsing works as intended.
	 */
	@Test
	public void testBasicParsing() throws SREParseException {
		SRE2DTMCTransformation sre2dtmcTransformation = new SRE2DTMCTransformation();

		sre2dtmcTransformation.transform(
				"((start)) : ((won : (won*0.5))[4] + (toll : (toll*0.470588235294))[17]) : ((won : won : won : (won*0.5))[2] + (e)[8] + (lost : (lost*0.545454545455))[11])");
	}

	/**
	 * Tests that invalid SREs are detected and an according {@link SREParseException} is thrown. 
	 */
	@Test
	public void testInvalidSREParsing() {
		SRE2DTMCTransformation sre2dtmcTransformation = new SRE2DTMCTransformation();

		final String sre = "fa[1] d";

		try {
			sre2dtmcTransformation.transform(sre);
		} catch (Exception e) {
			assertEquals("Parsing fails with a SREParseException", SREParseException.class, e.getClass());
			return;
		}

		fail("Invalid SRE '" + sre + "' was parsed without any problems.");
	}
	
	/**
	 * Tests that the {@link DTMC} to {@link ProbabilisticAutomata} conversion works as intended. (no sematic change).
	 * 
	 */
	@Test
	public void testPAEquivalentToDTMC() throws SREParseException {
		SRE2DTMCTransformation sre2dtmcTransformation = new SRE2DTMCTransformation();

		// The SRE in use is a first output of our BLA algorithm applied to the task 1 data set.
		DTMC dtmc = sre2dtmcTransformation.transformSreToDtmc(
				"((start)) : ((won : (won*0.5))[4] + (toll : (toll*0.470588235294))[17]) : (((EPSILON*0.0))[8] + (won : won : won : (won*0.5))[2] + (lost : (lost*0.545454545455))[11])");
		
		ProbabilisticAutomata automaton = sre2dtmcTransformation.transformToProbabilisticAutomaton(dtmc);
		
		assertEquals("DTMC and PA are identical in their structure",
				toTextualRepresentation(dtmc, getLabelToIdMap(automaton)),
				ProbabilisticAutomataUtils.toTextualRepresentation(automaton));
	}
	
	/**
	 * Tests the that SRE that contain an epsilon are parsed and transformed correctly.
	 */
	@Test
	public void testEpsilonSRE() throws SREParseException {
		SRE2DTMCTransformation transformation = new SRE2DTMCTransformation();
		
		String epsilonSre = "((start)) : ((won : (won*0.5))[4] + (toll : (toll*0.470588235294))[17]) : (((EPSILON*0.0))[8] + (won : won : won : (won*0.5))[2] + (lost : (lost*0.545454545455))[11])";
		
		DTMC dtmc = transformation.transformSreToDtmc(epsilonSre);
		ProbabilisticAutomata automaton = transformation.transformToProbabilisticAutomaton(dtmc);
		
		assertEquals("DTMC and PA are identical in their structure",
				toTextualRepresentation(dtmc, getLabelToIdMap(automaton)),
				ProbabilisticAutomataUtils.toTextualRepresentation(automaton));
	}

	/**
	 * Returns the inverted transition-id-to-labels map of the given automaton.
	 */
	private static Map<String, Integer> getLabelToIdMap(ProbabilisticAutomata automaton) {
		// Invert transition-id-label-mappings
		Map<String, Integer> transitionLabelToIdMap = new HashMap<>();
		automaton.getTransitionLabels()
			.stream().forEach(e -> transitionLabelToIdMap.put(e.getValue(), e.getKey()));
		
		return transitionLabelToIdMap;
	}
	
	/**
	 * Returns a textual representation of the given DTMC.
	 * 
	 * Uses the given transitionLabelIdMap to sort transitions.
	 */
	private static String toTextualRepresentation(DTMC dtmc, Map<String, Integer> transitionLabelIdMap) {
		Deque<Node> stateStack = new ArrayDeque<>();
		stateStack.push(dtmc.getInitialNode());
		Set<Node> visitedStates = new HashSet<>();

		StringBuilder textualRepresentation = new StringBuilder();
		
		while (!stateStack.isEmpty()) {
			Node s = stateStack.removeLast();
			visitedStates.add(s);
			
			double terminatingProbability = dtmc.getFinalNodes().contains(s) ? 1.0 : 0.0;
			textualRepresentation.append(s.index + "[" + terminatingProbability + "]\n");

			// Create a copy of the transitions list
			List<Edge> transitions = new ArrayList<Edge>(dtmc.getOutgoingEdges(s));

			// Sort transitions by label
			transitions.sort(new Comparator<Edge>() {
				@Override
				public int compare(Edge o1, Edge o2) {
					Integer o1Id = transitionLabelIdMap.get(o1.character);
					Integer o2Id = transitionLabelIdMap.get(o2.character);

					// if no label mapping for the labels can be found, abort 
					if (o1Id == null || o2Id == null) {
						throw new IllegalArgumentException("Failed to compare " + o1.character + " and " + o2.character
								+ ": Couldn't find corresponding label id in given mapping.");
					}
					
					int compareResult = Integer.compare(o1Id, o2Id);
					
					// if no order is defined by the transitions IDs...
					if (compareResult == 0) {
						// ...also consider the target node IDs
						compareResult = Integer.compare(o1.to.index, o2.to.index);
					}
					
					return compareResult;
				}
			});

			for (Edge transition : transitions) {
				Node target = transition.to;
				String label = transition.character;
				textualRepresentation.append("\t-" + label + "[" + transition.getProbability() + "]-> " + target.index + "\n");

				// Only add to stack if unvisited and not planning to visit anyway
				if (!visitedStates.contains(target) && !stateStack.contains(target)) {
					stateStack.push(target);
				}
			}
		}
		return textualRepresentation.toString();
	}
}
