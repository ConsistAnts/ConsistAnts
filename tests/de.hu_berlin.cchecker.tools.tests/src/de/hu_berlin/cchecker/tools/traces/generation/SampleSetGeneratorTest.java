package de.hu_berlin.cchecker.tools.traces.generation;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.checking.CheckingAlgorithm;
import de.hu_berlin.cchecker.core.checking.FootprintMatrixCheckingAlgorithm;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomataBuilder;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator.GeneratorResult;
import de.hu_berlin.cchecker.tools.traces.generation.SampleSetGenerator.RoundingStrategy;

@RunWith(JUnit4.class)
public class SampleSetGeneratorTest {
	@Ignore("This test was just used to experiment with parameters")
	@Test
	public void testSimpleSampleSetEXPERIMENTING() {
		ProbabilisticAutomata automaton = ProbabilisticAutomataBuilder.newAutomaton()
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
		
		SampleSetEstimator estimator = new SampleSetEstimator(automaton);
		int sampleSetSize = estimator.estimateSampleSizeForRepresentationRate(0.8);
		
		System.out.println(sampleSetSize);
		
		SampleSetGenerator generator = new SampleSetGenerator(automaton, sampleSetSize, RoundingStrategy.HALF_UP);
		GeneratorResult result = generator.generatePossibleSampleSet();
		System.out.println(result);
	
		CheckingAlgorithm a = new FootprintMatrixCheckingAlgorithm();
		ConsistencyReport report = a.performConsistencyCheck(automaton, result.getDataSet(), new NullProgressMonitor());
		
		System.out.println(report);
	}
	

	@Test
	public void testGenerationNoFinalState() {
		ProbabilisticAutomata automaton = ProbabilisticAutomataBuilder.newAutomaton()
				.fromTo(1, 2, 0.5, "a1")
				.fromTo(1, 3, 0.5, "b1")
				.fromTo(3, 4, 1.0, "b2")
				.fromTo(2, 5, 1.0, "a2")
				.startIn(1)
				.create();
		
		SampleSetGenerator generator = new SampleSetGenerator(automaton, 4, RoundingStrategy.DOWN);
		GeneratorResult result = generator.generatePossibleSampleSet();
		
		assertEquals("No trace was generated", 0, result.getNumberOfTraces());
		assertEquals("Representation rate is 0%", 0 , result.getRepresentationRate(), 0.0);
		assertEquals("The trace data set contains no traces", 0, result.getDataSet().getTraces().size());
	}
	
	@Test
	public void testGenerationSimpleFiniteCompleteAutomaton() {
		ProbabilisticAutomata automaton = ProbabilisticAutomataBuilder.newAutomaton()
				.fromTo(1, 2, 0.5, "a1")
				.fromTo(1, 3, 0.5, "b1")
				.fromTo(3, 4, 1.0, "b2")
				.fromTo(2, 5, 1.0, "a2")
				.terminatesIn(4)
				.terminatesIn(5)
				.startIn(1)
				.create();
		
		SampleSetGenerator generator = new SampleSetGenerator(automaton, 4, RoundingStrategy.UP);
		GeneratorResult result = generator.generatePossibleSampleSet();
		
		assertEquals("4 traces were generated", 4, result.getNumberOfTraces());
		assertEquals("Representation rate is 100%", 1.0 , result.getRepresentationRate(), 0.0);
		assertEquals("The trace data set contains 4 traces", 4, result.getDataSet().getTraces().size());
		
		assertEquals("The trace data set looks as expected", "a1 a2\n" + 
				"a1 a2\n" + 
				"b1 b2\n" + 
				"b1 b2", result.getDataSet().toString());
	}
	
	@Test
	public void testGenerationSimpleNonFiniteCompleteAutomaton() {
		ProbabilisticAutomata automaton = ProbabilisticAutomataBuilder.newAutomaton()
				.fromTo(1, 2, 0.5, "a1")
				.fromTo(1, 3, 0.5, "b1")
				.fromTo(3, 4, 1.0, "b2")
				.fromTo(2, 5, 1.0, "a2")
				.fromTo(5, 1, 0.5, "z")
				.terminatesIn(4)
				.terminatesInWithProbability(5, 0.5)
				.startIn(1)
				.create();
		
		SampleSetGenerator generator = new SampleSetGenerator(automaton, 20, RoundingStrategy.UP);
		GeneratorResult result = generator.generatePossibleSampleSet();
		
		assertEquals("20 traces were generated", 20, result.getNumberOfTraces());
		assertEquals("Representation rate is ~98%", 0.984375 , result.getRepresentationRate(), 0.0);
		assertEquals("The trace data set contains 20 traces", 20, result.getDataSet().getTraces().size());
		
		assertEquals("The trace data set looks as expected", "a1 a2\n" + 
				"a1 a2\n" + 
				"a1 a2\n" + 
				"a1 a2\n" + 
				"a1 a2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"b1 b2\n" + 
				"a1 a2 z a1 a2\n" + 
				"a1 a2 z b1 b2\n" + 
				"a1 a2 z b1 b2\n" + 
				"a1 a2 z a1 a2 z a1 a2\n" + 
				"a1 a2 z a1 a2 z b1 b2", result.getDataSet().toString());
	}
}
