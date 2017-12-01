package de.hu_berlin.cchecker.core.checking;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.hu_berlin.cchecker.core.checking.FootprintMatrixCheckingAlgorithm;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReport;
import de.hu_berlin.cchecker.core.checking.report.ConsistencyReportUtils;
import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.core.models.pdfa.tests.ProbabilisticAutomataTestUtils;
import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.tests.TraceTestUtils;

@RunWith(JUnit4.class)
public class ConsistencyCheckingSystemTest extends AbstractFootprintMatrixTest {
	/**
	 * Tests the complete consistency checking stack using the task 1 sample data
	 * set.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTask1ConformanceMatricesWithTraces() throws IOException {
		FootprintMatrixCheckingAlgorithm algorithm = new FootprintMatrixCheckingAlgorithm();

		// Get task 1 alergia automata and rename labels to match trace data set
		// mappings
		ProbabilisticAutomata alergiaResult = ProbabilisticAutomataTestUtils.createTask1AlergiaResult();
		ProbabilisticAutomataTestUtils.renameLabels(alergiaResult, "s", "start", "t", "toll", "w", "won", "l", "lost");

		TraceDataset task1Traces = TraceTestUtils.getTask1TraceDataset();

		ConsistencyReport report = algorithm.performConsistencyCheck(alergiaResult, task1Traces,
				new NullProgressMonitor());

		FileInputStream fis = new FileInputStream("res/reports/task1.expectation.report");
		BufferedReader in = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line + "\n");
		}
		fis.close();
		String reportExpectation = sb.toString();
		
		assertEquals("Consistency report looks as expected", reportExpectation,	ConsistencyReportUtils.createTextualReport(report));

	}
}