package de.hu_berlin.cchecker.core.learning.alergia.stability.tests;

import static de.hu_berlin.cchecker.core.models.pdfa.tests.ProbabilisticAutomataTestUtils.assertEqualAutomata;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import de.hu_berlin.cchecker.core.learning.alergia.Alergia;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.tests.AbstractProbabilisticAutomataTest;
import de.hu_berlin.cchecker.core.models.traces.JsonTraceDatasetParser;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import junit.framework.AssertionFailedError;

/**
 * Reference tests for the Alergia learning algorithm, so that we can notice changes in the outputs.
 */
public class AlergiaStabilityTest extends AbstractProbabilisticAutomataTest {

	/* Initialises abstract test class parameters */
	public AlergiaStabilityTest() {
		// Initialise root directory for PDFA model resources
		super("res/alergia/output");
	}
	
	@Test
	public void testSmallDataset1() {
		String filebasename = "small-dataset-1";
		
		TraceDataset traceData = traceData(filebasename + ".trc");
		ProbabilisticAutomata learnedModel = runAlergia(traceData);
		
		ProbabilisticAutomata expectation = loadAutomataFromTestResources(filebasename + ".pdfa");
		assertEqualAutomata("The learned model for " + filebasename + " was as expected",
				expectation, learnedModel);
	}
	
	@Test
	public void testSmallDataset2() {
		String filebasename = "small-dataset-2";
		
		TraceDataset traceData = traceData(filebasename + ".trc");
		ProbabilisticAutomata learnedModel = runAlergia(traceData);
		
		ProbabilisticAutomata expectation = loadAutomataFromTestResources(filebasename + ".pdfa");
		assertEqualAutomata("The learned model for " + filebasename + " was as expected",
				expectation, learnedModel);
	}
	
	@Test
	public void testMediumDataset1() {
		String filebasename = "medium-dataset-1";
		
		TraceDataset traceData = traceData(filebasename + ".trc");
		ProbabilisticAutomata learnedModel = runAlergia(traceData);
		
		ProbabilisticAutomata expectation = loadAutomataFromTestResources(filebasename + ".pdfa");
		assertEqualAutomata("The learned model for " + filebasename + " was as expected",
				expectation, learnedModel);
	}
	
	@Test
	public void testMediumDataset2() {
		String filebasename = "medium-dataset-2";
		
		TraceDataset traceData = traceData(filebasename + ".trc");
		ProbabilisticAutomata learnedModel = runAlergia(traceData);
		
		ProbabilisticAutomata expectation = loadAutomataFromTestResources(filebasename + ".pdfa");
		assertEqualAutomata("The learned model for " + filebasename + " was as expected",
				expectation, learnedModel);
	}
	
	@Test
	public void testLargeDataset1() {
		String filebasename = "large-dataset-1";
		
		TraceDataset traceData = traceData(filebasename + ".trc");
		ProbabilisticAutomata learnedModel = runAlergia(traceData);
		
		ProbabilisticAutomata expectation = loadAutomataFromTestResources(filebasename + ".pdfa");
		assertEqualAutomata("The learned model for " + filebasename + " was as expected",
				expectation, learnedModel);
	}
	
	@Test
	public void testLargeDataset2() {
		String filebasename = "large-dataset-2";
		
		TraceDataset traceData = traceData(filebasename + ".trc");
		ProbabilisticAutomata learnedModel = runAlergia(traceData);
		
		ProbabilisticAutomata expectation = loadAutomataFromTestResources(filebasename + ".pdfa");
		assertEqualAutomata("The learned model for " + filebasename + " was as expected",
				expectation, learnedModel);
	}
	
	/**
	 * Uncomment this method if you need to generate new PDFA expectation files
	 * in alergia/output. You have to change the filename variable as well.
	 */
	//@Test
	//@Ignore
	public static void main(String[] args) throws IOException {
		String filename = "large-dataset-2";
		
		TraceDataset testDataset = traceData(filename + ".trc");
		ProbabilisticAutomata learnedModel = runAlergia(testDataset);
		
		// Create test instance to execute setup
		AlergiaStabilityTest stabilityTestInstance = new AlergiaStabilityTest();
		stabilityTestInstance.setUp();
		
		Resource outputResource = stabilityTestInstance.getResourceSet().createResource(URI.createURI("cctest:/" + filename + ".pdfa"));
		outputResource.getContents().add(learnedModel);
		outputResource.save(null);
		
		stabilityTestInstance.tearDown();
	}
	
	private static TraceDataset traceData(String filename) {
		File file = new File("res/alergia/input/" + filename);
		TraceDataset d = new JsonTraceDatasetParser().parseTraceDatasetFromFile(file);
		if (null == d) {
			throw new AssertionFailedError("Failed to load trace data set file " + filename);
		}
		return d;
	}
	
	private static ProbabilisticAutomata runAlergia(TraceDataset dataset) {
		Alergia alergia = new Alergia();
		return alergia.learnModel(dataset);
	}
}
