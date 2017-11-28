package de.hu_berlin.cchecker.core.transformations.minimization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.tests.ProbabilisticAutomataEquivalenceUtils;
import de.hu_berlin.cchecker.core.transformations.minimization.JFlapPAMinimization;
import de.hu_berlin.cchecker.core.transformations.minimization.PAMinimization;

/**
 * This class tests the minimization algorithm of probabilistic automata
 * implemented in {@link JFlapPAMinimization}. 
 * 
 * This tests only tests the implementation without character mapping.
 * 
 * @author Daniel - rychlewd
 */
public class PAMinimizationTest {

	/**
	 * Creates a new {@link PAMinimization} instance to be used in this test class.
	 */
	protected PAMinimization createMinimization() {
		// do not use character mapping
		return new JFlapPAMinimization(false);
	}
	
	/**
	 * This test considers a simple automaton with 4 states and 4 transitions which
	 * cannot be minimized. Consequentially, the output should be the same
	 * probabilistic automaton.
	 */
	@Test
	public void shouldNotMerge() {
		ProbabilisticAutomata input = ProbabilisticAutomataBuilder.newAutomaton().fromTo(1, 2, 0.5, "a")
				.fromTo(1, 3, 0.5, "b").fromTo(3, 4, 0.5, "a").fromTo(2, 4, 0.5, "b").terminatesIn(4).startIn(1)
				.create();
		ProbabilisticAutomata output = createMinimization().minimize(input);
		assertEquals("Number of states in minimized automaton is correct", 4, output.getStates().size());
		for (ProbabilisticState s : output.getStates()) {
			int numberOfTransitionsForStateS = s.getOutgoingTransitions().size();
			if (numberOfTransitionsForStateS > 0) {
				assertEquals("Outgoing probability is as expected", 0.5,
						s.getOutgoingTransitions().get(0).getProbability(), 0.0);
				if (numberOfTransitionsForStateS == 2) {
					assertEquals("State 1 has 2 outgoing transitions", 0, s.getStateId()); // Jflap starts state IDs
																							// from 0, not 1
				}
			}
		}
	}
	
	/**
	 * This test is a simple example which shows that Jflap merges states 2 and 3
	 * even though the transitions 1->2 and 1->3 are differently labeled. As a
	 * result, we end up with two transitions from 1 to 2 and two states in total.
	 * Therefore, it is important to recognize that there might be more than one
	 * transition between two states.
	 */
	@Test
	public void shouldMergeWithMultipleTransitions() {
		ProbabilisticAutomata input = ProbabilisticAutomataBuilder.newAutomaton().fromTo(1, 2, 0.5, "a")
				.fromTo(1, 3, 0.5, "b").terminatesIn(2).terminatesIn(3).startIn(1).create();
		ProbabilisticAutomata output = createMinimization().minimize(input);
		assertEquals("Number of states in minimized automaton is correct", 2, output.getStates().size());
		for (ProbabilisticState s : output.getStates()) {
			if (s.getOutgoingTransitions().size() > 0) {
				assertEquals("There are two outgoing transitions from the initial state to the other state", 2,
						s.getOutgoingTransitions().size());
				assertEquals("Outgoing probability of first transition is as expected", 0.5,
						s.getOutgoingTransitions().get(0).getProbability(), 0.0);
				assertEquals("Outgoing probability of second transition is as expected", 0.5,
						s.getOutgoingTransitions().get(1).getProbability(), 0.0);
			}
		}
	}

	/**
	 * This test is a complex example taken from the slides of the course
	 * "Introduction to Theoretical Computer Science". The automaton should be
	 * minimized from 6 to 3 states.
	 */
	@Test
	public void etiTest() {
		ProbabilisticAutomata input = ProbabilisticAutomataBuilder.newAutomaton().fromTo(1, 2, 0.5, "a")
				.fromTo(2, 1, 0.5, "a").fromTo(1, 6, 0.5, "b").fromTo(6, 1, 0.5, "b").fromTo(2, 3, 0.5, "b")
				.fromTo(3, 2, 0.5, "b").fromTo(3, 4, 0.5, "a").fromTo(4, 3, 0.5, "a").fromTo(6, 5, 0.5, "a")
				.fromTo(5, 6, 0.5, "a").fromTo(4, 5, 0.5, "b").fromTo(5, 4, 0.5, "b").terminatesIn(3).terminatesIn(6)
				.startIn(1).create();
		ProbabilisticAutomata output = createMinimization().minimize(input);
		
		assertEquals("Number of states in minimized automaton is correct", 3, output.getStates().size());
		for (ProbabilisticState s : output.getStates()) {
			assertEquals("Number of transition for each state is correct", 2, s.getOutgoingTransitions().size());
		}
	}

	/**
	 * This test is a complex example taken from the slides of the course
	 * "Introduction to Theoretical Computer Science". The labels of the transitions
	 * consist of multiple characters. The automaton should be minimized from 6 to 3
	 * states.
	 */
	@Test
	public void etiTestMultiCharacterLabels() {
		ProbabilisticAutomata input = ProbabilisticAutomataBuilder.newAutomaton().fromTo(1, 2, 0.5, "aa")
				.fromTo(2, 1, 0.5, "aa").fromTo(1, 6, 0.5, "bc").fromTo(6, 1, 0.5, "b").fromTo(2, 3, 0.5, "bc")
				.fromTo(3, 2, 0.5, "b").fromTo(3, 4, 0.5, "asd").fromTo(4, 3, 0.5, "a").fromTo(6, 5, 0.5, "asd")
				.fromTo(5, 6, 0.5, "a").fromTo(4, 5, 0.5, "bbbbb").fromTo(5, 4, 0.5, "bbbbb").terminatesIn(3)
				.terminatesIn(6).startIn(1).create();
		ProbabilisticAutomata output = createMinimization().minimize(input);
		assertEquals("Number of states in minimized automaton is correct", 3, output.getStates().size());
		for (ProbabilisticState s : output.getStates()) {
			assertEquals("Number of transition for each state is correct", 2, s.getOutgoingTransitions().size());
		}
	}

	/**
	 * This test uses the same example as etiTest, but with differing probabilities
	 * for the transitions. Since different probabilities are part of a transition's
	 * label, it is an inconvenient scenario for minimization because nothing can be
	 * minimized. Without probabilities, this automaton could have been reduced from
	 * 6 to 3 states.
	 */
	@Test
	public void etiTestProbabilityDeviations() {
		// Use example from slides for Introduction to Theoretical Computer Science, but
		// with inconvenient probabilities - therefore, nothing should be minimized
		ProbabilisticAutomata input = ProbabilisticAutomataBuilder.newAutomaton().fromTo(1, 2, 0.4, "a")
				.fromTo(2, 1, 0.6, "a").fromTo(1, 6, 0.6, "b").fromTo(6, 1, 0.4, "b").fromTo(2, 3, 0.4, "b")
				.fromTo(3, 2, 0.4, "b").fromTo(3, 4, 0.6, "a").fromTo(4, 3, 0.4, "a").fromTo(6, 5, 0.6, "a")
				.fromTo(5, 6, 0.4, "a").fromTo(4, 5, 0.6, "b").fromTo(5, 4, 0.6, "b").terminatesIn(3).terminatesIn(6)
				.startIn(1).create();
		ProbabilisticAutomata output = createMinimization().minimize(input);
		assertEquals("Number of states in minimized automaton is correct", 6, output.getStates().size());
	}
	
	@Test
	public void testEpsilonRemovalResultAutomatonTask1() {
		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
				.startIn(314)
				.fromTo(314, 318, 1.0, "start")
				.fromTo(318, 312, 0.19047619047619047, "won")
				.fromTo(318, 321, 0.8095238095238095, "toll")
				.fromTo(312, 315, 0.5, "won")
				.fromTo(312, 334, 0.047619047619047616, "won")
				.fromTo(312, 341, 0.2619047619047619, "lost")
				.fromTo(321, 334, 0.050420168067238084, "won")
				.fromTo(321, 320, 0.470588235294, "toll")
				.fromTo(321, 341, 0.2773109243698095, "lost")
				.fromTo(315, 315, 0.5, "won")
				.fromTo(315, 334, 0.047619047619047616, "won")
				.fromTo(315, 341, 0.2619047619047619, "lost")
				.fromTo(334, 331, 1.0, "won")
				.terminatesInWithProbability(341, 0.45454545454499995)
				.fromTo(341, 342, 0.545454545455, "lost")
				.fromTo(320, 334, 0.050420168067238084, "won")
				.fromTo(320, 320, 0.470588235294, "toll")
				.fromTo(320, 341, 0.2773109243698095, "lost")
				.fromTo(331, 337, 1.0, "won")
				.terminatesInWithProbability(342, 0.45454545454499995)
				.fromTo(342, 342, 0.545454545455, "lost")
				.terminatesInWithProbability(337, 0.5)
				.fromTo(337, 330, 0.5, "won")
				.terminatesInWithProbability(330, 0.5)
				.fromTo(330, 330, 0.5, "won")
				.create();
		
		ProbabilisticAutomata output = createMinimization().minimize(a);
		
		ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(a, output);
	}

	/**
	 * This tests checks whether the special case of an automaton without states is
	 * considered in the implementation.
	 */
	@Test
	public void emptyAutomaton() {
		ProbabilisticAutomata input = ProbabilisticAutomataBuilder.newAutomaton().create();
		ProbabilisticAutomata output = createMinimization().minimize(input);
		assertEquals("Number of states in minimized automaton is correct", 0, output.getStates().size());
	}

	/**
	 * This tests checks how the algorithm deals with null as an input. It should
	 * return null.
	 */
	@Test
	public void nullAutomaton() {
		assertEquals("Null return value for null argument", null, createMinimization().minimize(null));
	}
}
