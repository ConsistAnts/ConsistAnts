package de.hu_berlin.cchecker.core.models.traces.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.TraceDatasetParser;

public class TraceDatasetTest {

	@Test
	public void shouldTestThatAggregatedTracesAreCorrect() {
		File testFileValid = new File("res/tracedataset_simple.trc");
		TraceDataset dataset = new TraceDatasetParser().parseTraceDatasetFromFile(testFileValid);
		
		String expected = "{[1, 2, 2, 3]=1, [1, 2, 4, 4, 4]=1, [1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]=1, "
				+ "[1, 2, 3, 3, 3, 3, 3, 3, 3, 3]=1, [1, 2, 3, 3]=1, [1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]=1, "
				+ "[1, 2, 3, 3, 3, 3, 3, 3, 3]=1, [1, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3]=1, [1, 2, 3]=3, [1, 2, 2, 2, 3, 3]=1, [1, 4, 4, 4, 4, 4]=1, "
				+ "[1, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]=1, [1, 2, 2, 2, 2, 2, 2, 3]=1, "
				+ "[1, 2, 2, 2, 2]=2, [1, 4]=2}";
		assertEquals(expected, dataset.getTracesAggregated().toString());
	}
}
