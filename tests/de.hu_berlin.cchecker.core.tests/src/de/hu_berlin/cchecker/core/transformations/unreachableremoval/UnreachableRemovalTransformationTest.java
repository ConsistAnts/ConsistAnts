package de.hu_berlin.cchecker.core.transformations.unreachableremoval;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataVisitor;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticState;

/**
 * Tests for the {@link UnreachableStateRemovalTransformation}.
 */
@RunWith(JUnit4.class)
public class UnreachableRemovalTransformationTest {
	
	/**
	 * Tests that unreachable states are removed properly.
	 */
	@Test
	public void testSimpleUnreachableStateTransformation() {
		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 4, 1.0, "a")
			.fromTo(1, 5, 1.0, "b")
			.fromTo(2, 5, 1.0, "hello")
			.fromTo(2, 1, 1.0, "world")
			.terminatesInWithProbability(4, 1.0)
			.terminatesInWithProbability(5, 1.0)
			.create();		
		
		UnreachableStateRemovalTransformation t = createTransformation();
		ProbabilisticAutomata transformed = t.transform(a);

		assertEquals("Previously there were unreachable states", 4, a.getStates().size());
		
		assertEquals("After the transformation the number of states was reduced", 3, transformed.getStates().size());
		assertEquals("Number of states matches number of reachable states", getNumberOfReachableStates(transformed), transformed.getStates().size());
		
		assertEquals("Automaton structure looks as expected and state space is normalized", "1[0.0]\n" + 
				"	-a[1.0]-> 2\n" + 
				"	-b[1.0]-> 3\n" + 
				"2[1.0]\n" + 
				"3[1.0]\n", transformed.toString());

	}

	public int getNumberOfReachableStates(ProbabilisticAutomata automaton) {
		StateCountingVisitor stateCounter = new StateCountingVisitor();
		ProbabilisticAutomataVisitor.visit(automaton, stateCounter);
		return stateCounter.getNumberOfStates();
	}

	protected UnreachableStateRemovalTransformation createTransformation() {
		return new UnreachableStateRemovalTransformationImpl();
	}

	/**
	 * State visitor that counts the number of states it's visiting.
	 *
	 */
	private final class StateCountingVisitor implements ProbabilisticAutomataVisitor {
		private int numberOfStates = 0;
	
		@Override
		public void visit(ProbabilisticState state) {
			numberOfStates++;
		}
		
		int getNumberOfStates() {
			return numberOfStates;
		}
	}
}
