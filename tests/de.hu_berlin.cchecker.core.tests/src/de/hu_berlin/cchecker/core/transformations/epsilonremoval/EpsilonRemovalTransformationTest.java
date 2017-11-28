package de.hu_berlin.cchecker.core.transformations.epsilonremoval;

import static de.hu_berlin.cchecker.core.tests.ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;

/**
 * Tests for a {@link EpsilonRemovalTransformation}.
 * 
 * See {@link #createTranformation()} to use this test for a custom implementation of epsilon removal.
 */
@RunWith(JUnit4.class)
public class EpsilonRemovalTransformationTest {
	@Test
	public void testSimple1() {
		EpsilonRemovalTransformation t = createTranformation();

		ProbabilisticAutomata automata = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 0.1, "")
			.fromTo(2, 3, 0.3, "a")
			.fromTo(2, 2, 0.7, "b")
			.fromTo(3, 4, 1.0, "")
			.fromTo(1, 5, 0.9, "c")
			.fromTo(4, 10, 1.0, "d")
			.terminatesIn(4)
			.terminatesIn(5)
			.terminatesIn(10)
			.create();
		ProbabilisticAutomata transformed = t.transform(automata);

		assertEquals("Automaton looks as expected",
				"1[0.0]\n" + 
				"	-a[0.03]-> 3\n" + 
				"	-b[0.06999999999999999]-> 2\n" + 
				"	-c[0.9]-> 5\n" + 
				"3[1.0]\n" + 
				"	-d[1.0]-> 10\n" + 
				"2[0.0]\n" + 
				"	-a[0.3]-> 3\n" + 
				"	-b[0.7]-> 2\n" + 
				"5[1.0]\n" + 
				"10[1.0]\n",
				ProbabilisticAutomataUtils.toTextualRepresentation(transformed));
	
		assertEqualProbabilisticLanguage(automata, transformed);
	}

	@Test
	public void testDoubleEpsilon() {
		EpsilonRemovalTransformation t = createTranformation();

		ProbabilisticAutomata automata = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 1.0, "")
			.fromTo(2, 3, 1.0, "")
			.fromTo(3, 4, 1.0, "s")
			.terminatesIn(5)
			.create();
		ProbabilisticAutomata transformed = t.transform(automata);

		assertEquals("Automaton looks as expected",
				"1[0.0]\n" + 
				"	-s[1.0]-> 4\n" + 
				"4[0.0]\n",
				ProbabilisticAutomataUtils.toTextualRepresentation(transformed));
		
		assertEqualProbabilisticLanguage(automata, transformed);
	}
	
	@Test
	public void testSimple1Transformed() {
		EpsilonRemovalTransformation t = createTranformation();

		ProbabilisticAutomata automata = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 0.1, "")
			.fromTo(2, 3, 0.5, "a")
			.fromTo(2, 6, 0.5, "b")
			.fromTo(6, 6, 0.5, "b")
			.fromTo(6, 3, 0.5, "a")
			.fromTo(3, 4, 1.0, "")
			.fromTo(1, 5, 0.9, "c")
			.terminatesIn(4)
			.terminatesIn(5)
			.create();
		ProbabilisticAutomata transformed = t.transform(automata);

		assertEquals("Automaton looks as expected",
				"1[0.0]\n" + 
						"	-a[0.05]-> 3\n" + 
						"	-b[0.05]-> 6\n" + 
						"	-c[0.9]-> 5\n" + 
						"3[1.0]\n" + 
						"6[0.0]\n" + 
						"	-a[0.5]-> 3\n" + 
						"	-b[0.5]-> 6\n" + 
						"5[1.0]\n",
				ProbabilisticAutomataUtils.toTextualRepresentation(transformed));
		
		assertEqualProbabilisticLanguage(automata, transformed);
	}

	@Test
	public void testMinimalEpsilonAuto() {
		EpsilonRemovalTransformation t = createTranformation();

		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 1.0, "")
			.fromTo(2, 3, 1.0, "a")
			.fromTo(3, 4, 1.0, "")
			.terminatesInWithProbability(4, 1.0)
			.create();

		ProbabilisticAutomata transformed = t.transform(a);
		
		assertEquals("Simple automaton looks as expected", "1[0.0]\n" + 
				"	-a[1.0]-> 3\n" + 
				"3[1.0]\n",
				ProbabilisticAutomataUtils.toTextualRepresentation(transformed));
		assertEqualProbabilisticLanguage(a, transformed);
	}
	
	@Test
	public void testTwoEpsilonAuto() {
		EpsilonRemovalTransformation t = createTranformation();

		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 1.0, "")
			.fromTo(3, 2, 1.0, "")
			.fromTo(1, 3, 1.0, "z")
			.fromTo(2, 4, 1.0, "a")
			.fromTo(2, 5, 1.0, "b")
			.terminatesInWithProbability(4, 1.0)
			.terminatesInWithProbability(5, 1.0)
			.create();

		ProbabilisticAutomata transformed = t.transform(a);
		
		assertEquals("Automaton looks as expected", "1[0.0]\n" + 
				"	-z[1.0]-> 3\n" + 
				"	-a[1.0]-> 4\n" + 
				"	-b[1.0]-> 5\n" + 
				"3[0.0]\n" + 
				"	-a[1.0]-> 4\n" + 
				"	-b[1.0]-> 5\n" + 
				"4[1.0]\n" + 
				"5[1.0]\n",
				transformed.toString());
		assertEqualProbabilisticLanguage(a, transformed);
	}

	@Test
	public void testMoreThanMinimalEpsilonAuto() {
		EpsilonRemovalTransformation t = createTranformation();

		ProbabilisticAutomata a = ProbabilisticAutomataBuilder.newAutomaton()
			.startIn(1)
			.fromTo(1, 2, 1.0, "")
			.fromTo(2, 3, 1.0, "a")
			.fromTo(3, 4, 1.0, "")
			.fromTo(4, 5, 1.0, "d")
			.terminatesInWithProbability(4, 1.0)
			.terminatesInWithProbability(5, 1.0)
			.create();

		ProbabilisticAutomata transformed = t.transform(a);
		
		assertEquals("Simple automaton looks as expected",
				"1[0.0]\n" + "	-a[1.0]-> 3\n" + "3[1.0]\n" + "	-d[1.0]-> 5\n" + "5[1.0]\n",
				ProbabilisticAutomataUtils.toTextualRepresentation(transformed));
		
		assertEqualProbabilisticLanguage(a, transformed);
	}
	
	/**
	 * Tests epsilon-removal with the first results of the BLA-SRE-learning algorithm.
	 */
	@Test
	public void testTask1SREResult() {
		ProbabilisticAutomata automaton = ProbabilisticAutomataBuilder.newAutomaton()
				.startIn(0)
				.fromTo(0, 1, 1.0, "")
				.fromTo(1, 2, 1.0, "start")
				.fromTo(2, 3, 1.0, "")
				.fromTo(3, 5, 0.19047619047619047, "")
				.fromTo(3, 14, 0.8095238095238095, "")
				.fromTo(5, 6, 1.0, "")
				.fromTo(14, 15, 1.0, "")
				.fromTo(6, 7, 1.0, "won")
				.fromTo(15, 16, 1.0, "toll")
				.fromTo(7, 10, 1.0, "")
				.fromTo(16, 19, 1.0, "")
				.fromTo(10, 11, 1.0, "")
				.fromTo(19, 20, 1.0, "")
				.fromTo(11, 8, 0.5, "")
				.fromTo(11, 12, 0.5, "")
				.fromTo(20, 17, 0.470588235294, "")
				.fromTo(20, 21, 0.5294117647059999, "")
				.fromTo(8, 9, 1.0, "won")
				.fromTo(12, 13, 1.0, "")
				.fromTo(17, 18, 1.0, "toll")
				.fromTo(21, 22, 1.0, "")
				.fromTo(9, 11, 1.0, "")
				.fromTo(13, 4, 1.0, "")
				.fromTo(18, 20, 1.0, "")
				.fromTo(22, 4, 1.0, "")
				.fromTo(4, 23, 1.0, "")
				.fromTo(23, 25, 0.38095238095238093, "")
				.fromTo(23, 27, 0.09523809523809523, "")
				.fromTo(23, 40, 0.5238095238095238, "")
				.fromTo(25, 26, 1.0, "")
				.fromTo(27, 28, 1.0, "")
				.fromTo(40, 41, 1.0, "")
				.fromTo(26, 24, 1.0, "")
				.fromTo(28, 29, 1.0, "won")
				.fromTo(41, 42, 1.0, "lost")
				.fromTo(24, 49, 1.0, "")
				.fromTo(29, 30, 1.0, "")
				.fromTo(42, 45, 1.0, "")
				.terminatesInWithProbability(49, 1.0)
				.fromTo(30, 31, 1.0, "won")
				.fromTo(45, 46, 1.0, "")
				.fromTo(31, 32, 1.0, "")
				.fromTo(46, 43, 0.545454545455, "")
				.fromTo(46, 47, 0.45454545454499995, "")
				.fromTo(32, 33, 1.0, "won")
				.fromTo(43, 44, 1.0, "lost")
				.fromTo(47, 48, 1.0, "")
				.fromTo(33, 36, 1.0, "")
				.fromTo(44, 46, 1.0, "")
				.fromTo(48, 24, 1.0, "")
				.fromTo(36, 37, 1.0, "")
				.fromTo(37, 34, 0.5, "")
				.fromTo(37, 38, 0.5, "")
				.fromTo(34, 35, 1.0, "won")
				.fromTo(38, 39, 1.0, "")
				.fromTo(35, 37, 1.0, "")
				.fromTo(39, 24, 1.0, "")
				.create();
		
		ProbabilisticAutomata transformed = createTranformation().transform(automaton);
		
		assertEquals("Simple automaton looks as expected",
				"0[0.0]\n" + 
				"	-start[1.0]-> 2\n" + 
				"2[0.0]\n" + 
				"	-won[0.19047619047619047]-> 7\n" + 
				"	-toll[0.8095238095238095]-> 16\n" + 
				"7[0.19047619047619047]\n" + 
				"	-won[0.5]-> 9\n" + 
				"	-won[0.047619047619047616]-> 29\n" + 
				"	-lost[0.2619047619047619]-> 42\n" + 
				"16[0.20168067226895234]\n" + 
				"	-won[0.050420168067238084]-> 29\n" + 
				"	-toll[0.470588235294]-> 18\n" + 
				"	-lost[0.2773109243698095]-> 42\n" + 
				"9[0.19047619047619047]\n" + 
				"	-won[0.5]-> 9\n" + 
				"	-won[0.047619047619047616]-> 29\n" + 
				"	-lost[0.2619047619047619]-> 42\n" + 
				"29[0.0]\n" + 
				"	-won[1.0]-> 31\n" + 
				"42[0.45454545454499995]\n" + 
				"	-lost[0.545454545455]-> 44\n" + 
				"18[0.20168067226895234]\n" + 
				"	-won[0.050420168067238084]-> 29\n" + 
				"	-toll[0.470588235294]-> 18\n" + 
				"	-lost[0.2773109243698095]-> 42\n" + 
				"31[0.0]\n" + 
				"	-won[1.0]-> 33\n" + 
				"44[0.45454545454499995]\n" + 
				"	-lost[0.545454545455]-> 44\n" + 
				"33[0.5]\n" + 
				"	-won[0.5]-> 35\n" + 
				"35[0.5]\n" + 
				"	-won[0.5]-> 35\n",
				ProbabilisticAutomataUtils.toTextualRepresentation(transformed));
		
		assertEqualProbabilisticLanguage(automaton, transformed, 30);
	}

	protected EpsilonRemovalTransformation createTranformation() {
		return new EpsilonRemovalTransformationImpl();
	}
}
