package de.hu_berlin.cchecker.core.transformations.minimization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.tests.ProbabilisticAutomataEquivalenceUtils;
import de.hu_berlin.cchecker.core.transformations.minimization.JFlapPAMinimization;

/**
 * This class tests {@link ProbabilisticAutomata} minimization wrt 
 * the mergin of transitions if they originate from the same state.
 */
public class PAMinimizationTransitionMergingTest {
	@Test
	public void testPAMinimizationWithTransitionMerging() {
		ProbabilisticAutomata automaton = ProbabilisticAutomataBuilder.newAutomaton()
			.fromTo(1, 2, 0.5, "a")
			.fromTo(2, 3, 1.0, "b")
			.fromTo(1, 4, 0.5, "a")
			.fromTo(4, 5, 1.0, "b")
			.startIn(1)
			.terminatesIn(3)
			.terminatesIn(5)
			.create();
		
		JFlapPAMinimization minimization = createMinization();
		ProbabilisticAutomata minPDFA = minimization.minimize(automaton);
		
		ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(automaton, minPDFA);
		
		assertEquals("Minimized PDFA combines equivalent transitions", "0[0.0]\n" + 
				"	-a[1.0]-> 1\n" + 
				"1[0.0]\n" + 
				"	-b[1.0]-> 2\n" + 
				"2[1.0]\n", 
				minPDFA.toString());
	}

	private JFlapPAMinimization createMinization() {
		return new JFlapPAMinimization(false);
	}
}
