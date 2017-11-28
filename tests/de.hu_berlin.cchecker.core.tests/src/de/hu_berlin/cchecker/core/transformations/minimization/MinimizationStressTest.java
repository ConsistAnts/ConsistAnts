package de.hu_berlin.cchecker.core.transformations.minimization;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.tests.ProbabilisticAutomataEquivalenceUtils;
import de.hu_berlin.cchecker.core.tests.ProbabilisticAutomatonOperations;
import de.hu_berlin.cchecker.tools.pdfa.generation.RandomProbabilisticAutomatonGenerator;
import de.hu_berlin.cchecker.tools.pdfa.generation.RandomProbabilisticAutomatonGenerator.Range;

/**
 * This test is meant as a stress test wrt correctness for our {@link ProbabilisticAutomata} 
 * minimisation.
 *
 * The tests create random PDFA and run them through our miniser.
 * 
 * It checks for the equivalence of the resulting probabilistic languages of the
 * original random PDFA and the minisation result.
 *
 */
@RunWith(JUnit4.class)
public class MinimizationStressTest {
	private static final double SELF_CYCLE_PROBABILITY = 0.1;
	private static final Range SIZE_OF_ALPHABET = Range.fromTo(3, 4);
	private static final Range NUMBER_OF_TRANSITIONS_RANGE = Range.fromTo(2,  4);
	private static final Range NUMBER_OF_STATES_RANGE = Range.fromTo(4, 10);
	private static final int TEST_TRACE_LENGTH = 10;
	
	/**
	 * This method creates 100 different (large) PDFAs and minimises them.
	 * 
	 * After minimisation the result and the original random PDFA are checked for equivalence
	 * if they differ in number of states.
	 * 
	 * This tests is likely to not check a lot of minimised results, as random PDFAs
	 * almost always don't have a  
	 */
	@Ignore("Very long-running, not suitable for standard CI builds")
	@Test
	public void testRandomAutomata() {
		PAMinimization minimization = new JFlapPAMinimization();

		for (int i = 0; i < 100; i++) {
			long seed = new Random().nextLong();
			RandomProbabilisticAutomatonGenerator automataGenerator = new RandomProbabilisticAutomatonGenerator(seed);
			System.out.println("Seed is " + seed);
			
			ProbabilisticAutomata automata = automataGenerator.createRandom(NUMBER_OF_STATES_RANGE, NUMBER_OF_TRANSITIONS_RANGE, SIZE_OF_ALPHABET, SELF_CYCLE_PROBABILITY);
			ProbabilisticAutomata minimized = minimization.minimize(automata);
			System.out.println("Minimization done");

			System.out.println("Testing automaton #" + i);
			System.out.println(String.format("Automaton has %d states and and alphabet of size %d",
					automata.getStates().size(), automata.getTransitionLabels().map().keySet().size()));

			if (automata.getStates().size() != minimized.getStates().size()) {
				System.out.println("Minimization did transform, checking equivalence");
				ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(automata, minimized, TEST_TRACE_LENGTH);
				ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(minimized, automata, TEST_TRACE_LENGTH);
			} else {
				System.out.println("Minimization did not have any effect.");
			}

		}
	}
	
	/**
	 * This tests generates random automata and concatenates them in a 
	 * way that the automaton is highly redundant in its structures.
	 * 
	 * It is expected that minimisation recognises the redundancies and removes them. 
	 */
	@Ignore("Long running test, only meant to be used for check correctness of minisation. Not suitable for regular CI builds.")
	@Test
	public void testRandomBranchingAutomaton() {
		PAMinimization minimization = new JFlapPAMinimization();

		for (int i = 0; i < 100; i++) {
			long seed = new Random().nextLong();
			RandomProbabilisticAutomatonGenerator automataGenerator = new RandomProbabilisticAutomatonGenerator(seed);
			System.out.println("Seed is " + seed);
			
			ProbabilisticAutomata branchAutomaton = automataGenerator.createRandom(NUMBER_OF_STATES_RANGE, NUMBER_OF_TRANSITIONS_RANGE, SIZE_OF_ALPHABET, SELF_CYCLE_PROBABILITY);
			
			ProbabilisticAutomata automaton = null;
			for (int j=0; j<10; j++) {
				automaton = ProbabilisticAutomatonOperations.createAlternativeAutomaton(branchAutomaton, branchAutomaton, 0.5);
			}
			
			ProbabilisticAutomata minimized = minimization.minimize(automaton);
			System.out.println("Minimization done");

			System.out.println(automaton);
			
			System.out.println("Testing automaton #" + i);
			
			final int originalAutomatonNumberOfStates = automaton.getStates().size();
			final int minimizedNumberOfStates = minimized.getStates().size();
			
			System.out.println(String.format("Automaton has %d states and and alphabet of size %d",
					originalAutomatonNumberOfStates, automaton.getTransitionLabels().map().keySet().size()));

			if (originalAutomatonNumberOfStates != minimizedNumberOfStates) {
				System.out.println(String.format("Minimization did reduce number of states from %d to %d", originalAutomatonNumberOfStates, minimizedNumberOfStates));
				ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(automaton, minimized, TEST_TRACE_LENGTH);
				ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(minimized, automaton, TEST_TRACE_LENGTH);
			} else {
				System.out.println("Minimization did not have any effect.");
			}

		}
	}
}
