package de.hu_berlin.cchecker.core.models.pdfa.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;
import de.hu_berlin.cchecker.core.tests.ProbabilisticAutomataEquivalenceUtils;

/**
 * Tests the automata builder to produce correct reusults.
 */
@RunWith(JUnit4.class)
public class ProbabilisticAutomataBuilderTest extends AbstractProbabilisticAutomataTest {

	public ProbabilisticAutomataBuilderTest() {
		super("res/");
	}

	@Test
	public void testBuilderEquivalentToFile() {	
		URI testURI = URI.createURI("cctest:/alergiaResult.pdfa");
		Resource testResource = getResourceSet().getResource(testURI, true);
		EList<EObject> contents = testResource.getContents();

		assertTrue("File has only one object in it.", contents.size() == 1);
		assertTrue("File has a ProbabilisticAutomata object in it.", contents.get(0) instanceof ProbabilisticAutomata);

		ProbabilisticAutomata a = (ProbabilisticAutomata) contents.get(0);
		
		ProbabilisticAutomata builderAutomata = ProbabilisticAutomataBuilder.newAutomaton()
			.fromTo(1, 2, 1.0, "start")
			.fromTo(2, 3, 0.8095, "toll")
			.fromTo(2, 3, 0.1905, "won")
			.fromTo(3, 5, 0.1, "lost")
			.fromTo(3, 6, 0.1143, "toll")
			.fromTo(3, 3, 0.7, "won")
			.fromTo(6, 10, 0.1429, "lost")
			.fromTo(6, 6, 0.6857, "toll")
			.fromTo(10, 10, 0.7778, "lost")
			.fromTo(5, 5, 0.8205, "toll")
			.terminatesInWithProbability(1, 0.0)
			.terminatesInWithProbability(2, 0.0)
			.terminatesInWithProbability(3, 0.0857)
			.terminatesInWithProbability(6, 0.1714)
			.terminatesInWithProbability(10, 0.2222)
			.terminatesInWithProbability(5, 0.1795)
			.startIn(1)
			.create();

		assertEquals("String representation is as expected",
				ProbabilisticAutomataUtils.toTextualRepresentation(builderAutomata),
				ProbabilisticAutomataUtils.toTextualRepresentation(a));
	}
	
	/**
	 * Tests the {@link ProbabilisticAutomataBuilder#copyOf(ProbabilisticAutomata)} method using
	 * the task 1 alergia output automaton.
	 */
	@Test
	public void testEquivalenceOfCopy() {
		ProbabilisticAutomata someAutomaton = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();
		ProbabilisticAutomata copy = ProbabilisticAutomataBuilder.copyOf(someAutomaton).create();
		
		// test structural equivalence
		assertEquals("String representation of copy matches original", someAutomaton.toString(), copy.toString());
		
		// test probabilistic language equivalence
		ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(someAutomaton, copy);
	}
	
	/**
	 * Tests the {@link ProbabilisticAutomataBuilder#copyOf(ProbabilisticAutomata)} method using
	 * the paper 2 alergia result.
	 */
	@Test
	public void testEquivalenceOfCopyPaper2Result() {
		ProbabilisticAutomata someAutomaton = ProbabilisticAutomataTestUtils.createPaper2AlergiaResult();
		ProbabilisticAutomata copy = ProbabilisticAutomataBuilder.copyOf(someAutomaton).create();
		
		// test structural equivalence
		assertEquals("String representation of copy matches original", someAutomaton.toString(), copy.toString());
		
		// test probabilistic language equivalence
		ProbabilisticAutomataEquivalenceUtils.assertEqualProbabilisticLanguage(someAutomaton, copy);
	}
	
	@Test
	public void testInvalidBuilderState() {
		assertException("Builder throws illegal state exception", IllegalStateException.class, () -> {
			ProbabilisticAutomataBuilder.newAutomaton().startIn(1)
				.fromTo(1, 2, 1.0, 1).create();
		});
	}
}
