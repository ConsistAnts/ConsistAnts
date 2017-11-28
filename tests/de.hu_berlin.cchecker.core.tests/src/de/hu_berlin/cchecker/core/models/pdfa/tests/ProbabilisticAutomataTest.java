package de.hu_berlin.cchecker.core.models.pdfa.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.core.JsonParseException;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataUtils;

/**
 * Tests basic IO and parsing of {@link ProbabilisticAutomata} models.
 */
@RunWith(JUnit4.class)
public class ProbabilisticAutomataTest extends AbstractProbabilisticAutomataTest {
	
	// Sets abstract test class parameter
	public ProbabilisticAutomataTest() {
		super("res/");
	}

	/**
	 * Tests that model serialization and saving works as expected.
	 */
	@Test
	public void testSaveReportModel() throws IOException {
		URI testURI = URI.createURI("cctest:/testSaveReportModel." + PROBABILISTIC_AUTOMATA_FILE_EXTENSION);

		// Open resource
		Resource testResource = getResourceSet().createResource(testURI);

		// Clear possibly present content
		testResource.getContents().clear();

		// Create automata which models T1 data set according to alergia
		ProbabilisticAutomata automata = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();

		// Keep copy to keep original data separate 
		ProbabilisticAutomata copyAutomata = EcoreUtil.copy(automata);

		// Add automata to resource and save
		testResource.getContents().add(automata);
		testResource.save(null);

		// Close the resource
		testResource.unload();

		// Trigger reload
		testResource.load(null);

		EList<EObject> contents = testResource.getContents();
		assertEquals("Resource contains exactly one model", contents.size(), 1);

		EObject firstObject = contents.get(0);
		assertTrue("Resource model is of correct type", firstObject instanceof ProbabilisticAutomata);

		ProbabilisticAutomata modelInFile = (ProbabilisticAutomata) firstObject;

		assertEquals("Parsed model equals actual model", 
				ProbabilisticAutomataUtils.toTextualRepresentation(modelInFile),
				ProbabilisticAutomataUtils.toTextualRepresentation(copyAutomata));

		// Finally delete the resource
		testResource.delete(null);
	}

	/**
	 * Tests behavior for when the model file does not exist.
	 */
	@Test
	public void testModelFileNotFound() {
		URI nonExistentURI = URI.createURI("cctest:/doesNotExist.pdfa");
		Resource resource = null;
		try {
			resource = getResourceSet().getResource(nonExistentURI, true);
			assertNull("No resource for a non-existing model file", resource);
			fail("Non existing resource loaded fine.");
		} catch (WrappedException e) {
			Throwable cause = e.getCause();
			assertTrue("Resource retrieval aborts with FileNotFoundException", cause instanceof FileNotFoundException);
		}
	}

	/**
	 * Tests behavior if the model file is malformed.
	 */
	@Test
	public void testInvalidModelFile() {
		URI nonExistentURI = URI.createURI("cctest:/invalid.pdfa");
		Resource resource = null;
		try {
			resource = getResourceSet().getResource(nonExistentURI, true);
			assertNull("No resource for a malformed model file", resource);
			fail("Non existing resource loaded fine.");
		} catch (WrappedException e) {
			Throwable cause = e.getCause();
			assertEquals("Resource retrieval aborts with JsonParseException",
					cause.getClass().getName(),
					JsonParseException.class.getName());
		}
	}


	@Test
	public void testInvalidEClassURI() {
		URI nonExistentURI = URI.createURI("cctest:/invalidEClass.pdfa");
		Resource resource = null;
		try {
			resource = getResourceSet().getResource(nonExistentURI, true);
			assertNull("No resource for invalid EClass in model file", resource);
			fail("Non existing resource loaded fine.");
		} catch (WrappedException e) {
			Throwable cause = e.getCause();
			assertEquals("Resource retrieval aborts with FileNotFoundException",
					cause.getClass().getName(),
					FileNotFoundException.class.getName());
		}
	}

	@Test
	public void testReadExistingProbabilisticAutomataModel() {	
		URI testURI = URI.createURI("cctest:/alergiaResult.pdfa");
		Resource testResource = getResourceSet().getResource(testURI, true);
		EList<EObject> contents = testResource.getContents();

		assertTrue("File has only one object in it.", contents.size() == 1);
		assertTrue("File has a ProbabilisticAutomata object in it.", contents.get(0) instanceof ProbabilisticAutomata);

		ProbabilisticAutomata a = (ProbabilisticAutomata) contents.get(0);

		final String expectedStringRepresentation = 
				"1[0.0]\n" + 
				"	-start[1.0]-> 2\n" + 
				"2[0.0]\n" + 
				"	-toll[0.8095]-> 3\n" + 
				"	-won[0.1905]-> 3\n" + 
				"3[0.0857]\n" + 
				"	-toll[0.1143]-> 6\n" + 
				"	-won[0.7]-> 3\n" + 
				"	-lost[0.1]-> 5\n" + 
				"6[0.1714]\n" + 
				"	-toll[0.6857]-> 6\n" + 
				"	-lost[0.1429]-> 10\n" + 
				"5[0.1795]\n" + 
				"	-toll[0.8205]-> 5\n" + 
				"10[0.2222]\n" + 
				"	-lost[0.7778]-> 10\n";
		
		assertEquals("String representation is as expected", expectedStringRepresentation, ProbabilisticAutomataUtils.toTextualRepresentation(a));
	}
}
