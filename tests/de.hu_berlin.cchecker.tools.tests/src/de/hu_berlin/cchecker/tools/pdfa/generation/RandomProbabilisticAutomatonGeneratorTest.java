package de.hu_berlin.cchecker.tools.pdfa.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticTransition;
import de.hu_berlin.cchecker.tools.pdfa.generation.RandomProbabilisticAutomatonGenerator.Range;

/**
 * Tests for {@link RandomProbabilisticAutomatonGenerator}.
 */
@RunWith(JUnit4.class)
public class RandomProbabilisticAutomatonGeneratorTest {
	public static final long TEST_SEED = 23468919;

	/**
	 * Generates 100 random {@link ProbabilisticAutomata}s and check if they fulfil
	 * minimal constraints that must be true for valid
	 * {@link ProbabilisticAutomata}s.
	 * 
	 * Make sure that all assert messages invoked inside this method body, include
	 * the seed value, so that failing tests can be reproduced by switching to a
	 * fixed seed.
	 */
	@Test
	public void testCheckConstraintsOnRandomPA() {
		for (int i = 0; i < 100; i++) {
			// choose random seed and store it (for reproducible test failures)
			final long seed = (long) (Math.random() * TEST_SEED);
			final String messagePrefix = "[seed=" + seed + "] ";

			RandomProbabilisticAutomatonGenerator generator = createGenerator(seed);
			
			assertValidProbabilisticAutomaton(generator.createRandom(), messagePrefix);
		}
	}

	/**
	 * Tests the output {@link ProbabilisticAutomata} of a createRandom call with a
	 * fixed seed.
	 */
	@Test
	public void testRandomProbabilisticAutomaton() {
		RandomProbabilisticAutomatonGenerator generator = createGenerator(TEST_SEED + 14);
		ProbabilisticAutomata automaton = generator.createRandom();

		assertEquals("Random automaton with fixed seed looks as expected", "1[0.0]\n" + 
				"	-C[0.31867073033861976]-> 2\n" + 
				"	-A[0.21269740823492989]-> 3\n" + 
				"	-E[0.46863186142645036]-> 4\n" + 
				"2[0.0]\n" + 
				"	-C[0.9512512971697341]-> 1\n" + 
				"	-D[0.04874870283026586]-> 4\n" + 
				"3[0.5927298023432853]\n" + 
				"	-B[0.4072701976567147]-> 5\n" + 
				"4[0.0]\n" + 
				"	-C[0.22639196357113145]-> 5\n" + 
				"	-A[0.36203451262625796]-> 1\n" + 
				"	-E[0.40672874241032553]-> 4\n" + 
				"	-D[0.00484478139228495]-> 2\n" + 
				"5[0.0]\n" + 
				"	-A[0.5853336007638668]-> 6\n" + 
				"	-D[0.4146663992361333]-> 4\n" + 
				"6[0.28865647170071146]\n" + 
				"	-C[0.2551497593377969]-> 1\n" + 
				"	-E[0.2650415993025109]-> 6\n" + 
				"	-D[0.19115216965898066]-> 2\n",
				automaton.toString());
	}

	/**
	 * Tests the output {@link ProbabilisticAutomata} of a createRandom call with a
	 * fixed seed and large range parameters.
	 */
	@Test
	public void testLargeParameterRanges() {
		final long seed = new Random().nextLong();
		RandomProbabilisticAutomatonGenerator generator = createGenerator(seed);
		// create a random automaton with many states, many transitions and many distinct labels
		ProbabilisticAutomata automaton = generator.createRandom(Range.fromTo(100, 120), Range.fromTo(10, 15),
				Range.fromTo(10, 15));
		
		assertValidProbabilisticAutomaton(automaton, "[seed=" + seed + "] ");
	}

	/**
	 * Asserts that the given automaton adheres to general constraints regarding
	 * probabilistic automata.
	 * 
	 * @param automaton
	 *            The automaton to validate
	 * @param messagePrefix
	 *            The message prefix to use for assert statements. (May include seed
	 *            data)
	 */
	private void assertValidProbabilisticAutomaton(ProbabilisticAutomata automaton, String messagePrefix) {
		assertNotNull(messagePrefix + "Automaton has start state", automaton.getStartState());

		ProbabilisticAutomataVisitor.visit(automaton, new ProbabilisticAutomataVisitor() {
			@Override
			public void visit(ProbabilisticState state) {
				assertValidProbabilisticState(state, messagePrefix);
			}

			@Override
			public void visit(ProbabilisticTransition transition) {
				assertValidProbabilisticTransition(transition, messagePrefix);
			}
		});
	}

	/**
	 * Asserts that the given {@link ProbabilisticTransition} adheres to general
	 * constraints regarding such transitions.
	 */
	private void assertValidProbabilisticTransition(ProbabilisticTransition transition, String messagePrefix) {
		assertNotNull(messagePrefix + "Transition source is not null", transition.getSource());
		assertNotNull(messagePrefix + "Transition target is not null", transition.getTarget());
	}

	/**
	 * Asserts the given {@link ProbabilisticState} adheres to general constraints
	 * regarding probabilistic states.
	 */
	private void assertValidProbabilisticState(ProbabilisticState state, String messagePrefix) {
		assertEquals(messagePrefix + "Termination probability is in sync with isTerminating property",
				state.getTerminatingProbability() > 0, state.isTerminating());
		assertEquals(messagePrefix + "No outgoing transitions implies 1.0 termination probabilitiy",
				state.getOutgoingTransitions().isEmpty(), state.getTerminatingProbability() == 1.0);

		double sumOutgoingProbabilities = 0.0;
		for (ProbabilisticTransition transition : state.getOutgoingTransitions()) {
			sumOutgoingProbabilities += transition.getProbability();
		}

		assertEquals(messagePrefix + "Outgoing transition probabilities + termination probabilities sums up to 1.0",
				1.0, sumOutgoingProbabilities + state.getTerminatingProbability(), 0.00001);
	}

	/**
	 * Creates a new {@link RandomProbabilisticAutomatonGenerator} with the given
	 * seed.
	 */
	protected RandomProbabilisticAutomatonGenerator createGenerator(long seed) {
		return new RandomProbabilisticAutomatonGenerator(seed);
	}

	/**
	 * Creates a new {@link RandomProbabilisticAutomatonGenerator} and chooses the
	 * seed randomly.
	 */
	protected RandomProbabilisticAutomatonGenerator createGenerator() {
		return new RandomProbabilisticAutomatonGenerator();
	}
}
