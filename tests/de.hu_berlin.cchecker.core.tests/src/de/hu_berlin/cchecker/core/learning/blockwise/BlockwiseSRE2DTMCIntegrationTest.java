package de.hu_berlin.cchecker.core.learning.blockwise;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation;
import de.hu_berlin.cchecker.core.transformations.sre.SRE2DTMCTransformation.SREParseException;

@RunWith(JUnit4.class)
public class BlockwiseSRE2DTMCIntegrationTest {
	@Ignore("This test fails for now, since minimization would be required for the two automata to match")
	@Test
	public void testLearnAndTransform() throws SREParseException {
		BlockwiseLeftAlignedLearningAlgorithm algorithm = new BlockwiseLeftAlignedLearningAlgorithm();
		TraceDataset task1TraceDataset = TraceTestUtils.getTask1TraceDataset();
		
		String learnedSRE = algorithm.learnModel(task1TraceDataset);
		
		SRE2DTMCTransformation transformation = new SRE2DTMCTransformation();
		ProbabilisticAutomata learnedAutomaton = transformation.transform(learnedSRE);
		
		ProbabilisticAutomata learnedAutomatonFromPOCResult = transformation.transform("((start)) : ((won : (won*0.5))[4] + (toll : (toll*0.470588235294))[17]) : (((EPSILON*0.0))[8] + (won : won : won : (won*0.5))[2] + (lost : (lost*0.545454545455))[11])");
		
		assertEquals("The transformed automaton are equivalent", 
				learnedAutomaton.toString(),
				learnedAutomatonFromPOCResult.toString());
		
	}
}
