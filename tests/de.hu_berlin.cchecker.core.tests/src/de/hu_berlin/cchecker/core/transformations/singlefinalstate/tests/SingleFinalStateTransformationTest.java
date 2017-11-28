package de.hu_berlin.cchecker.core.transformations.singlefinalstate.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.checking.Pair;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.tests.ProbabilisticAutomataEquivalenceUtils;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.FromSingleFinalStateTransformation;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.FromSingleFinalStateTransformationImpl;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.ToSingleFinalStateTransformation;
import de.hu_berlin.cchecker.core.transformations.singlefinalstate.ToSingleFinalStateTransformationImpl;

/**
 * This class contains tests for the {@link ToSingleFinalStateTransformation}
 * and the {@link FromSingleFinalStateTransformation}.
 *
 */
@RunWith(JUnit4.class)
public class SingleFinalStateTransformationTest {
	@Test
	public void testSimpleForthAndBack() {
		ProbabilisticAutomata automaton = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 0.5, "a")
			.fromTo(1, 3, 0.5, "c")
			.fromTo(2, 2, 0.5, "Z")
			.fromTo(3, 3, 0.5, "Z")
			.terminatesInWithProbability(2, 0.5)
			.terminatesInWithProbability(3, 0.5)
			.create();

		ToSingleFinalStateTransformation to = createToTransformation();
		FromSingleFinalStateTransformation from = createFromTransformation();

		Pair<Integer, String> unusedMapping = ToSingleFinalStateTransformation.getUnusedTerminalMapping(automaton);

		ProbabilisticAutomata singleFinalState = to.transform(automaton, unusedMapping);
		ProbabilisticAutomata original = from.transform(singleFinalState, unusedMapping);

		assertTrue("Single final state automaton makes use of unused suffix mapping",
				singleFinalState.getTransitionLabels().keySet().contains(unusedMapping.first));

		assertEquals("Single final state automaton has exactly one final state", 1,
				ProbabilisticAutomataVisitor.stateStream(singleFinalState)
					.filter(s -> s.getTerminatingProbability() > 0)
					.count());

		ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguageWithSuffix(automaton, singleFinalState,
				10, unusedMapping.second);

		assertEquals("Forth-and-back with simple automaton leads to output that is equivalent to original automaton",
				automaton.toString(), original.toString());
	}

	protected ToSingleFinalStateTransformation createToTransformation() {
		return new ToSingleFinalStateTransformationImpl();
	}

	protected FromSingleFinalStateTransformation createFromTransformation() {
		return new FromSingleFinalStateTransformationImpl();
	}
}
