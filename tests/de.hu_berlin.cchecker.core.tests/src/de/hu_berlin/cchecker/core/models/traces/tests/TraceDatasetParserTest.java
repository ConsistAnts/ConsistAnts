package de.hu_berlin.cchecker.core.models.traces.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import de.hu_berlin.cchecker.core.models.traces.TraceDataset;
import de.hu_berlin.cchecker.core.models.traces.TraceDatasetParser;

/**
 * 
 * @author Linus
 *
 */
public class TraceDatasetParserTest {

	@Test
	public void shouldTestThatJsonTracesCanBeParsed() {
		File testFileValid = new File("res/tracedataset_valid.jtrc");
		TraceDataset dataset = new TraceDatasetParser().parseTraceDatasetFromFile(testFileValid);
		assertNotNull(dataset);
	}
	
	@Test
	public void shouldTestThatSimpleTracesCanBeParsed() {
		File testFileValid = new File("res/tracedataset_simple.strc");
		TraceDataset dataset = new TraceDatasetParser().parseTraceDatasetFromFile(testFileValid);
		assertNotNull(dataset);
	}
	
	@Test
	public void shouldTestThatAmbiguosTracesCanBeParsed() {
		File testFileValid = new File("res/tracedataset_simple.trc");
		TraceDataset dataset = new TraceDatasetParser().parseTraceDatasetFromFile(testFileValid);
		assertNotNull(dataset);
		testFileValid = new File("res/tracedataset_custom.trc");
		dataset = new TraceDatasetParser().parseTraceDatasetFromFile(testFileValid);
		assertNotNull(dataset);
	}
}

