package de.hu_berlin.cchecker.ui.tests;

import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForJobs;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class RunAsConsistencyCheckSwtBotTest extends AbstractUITest {
	private static final String PDFA_FILENAME = "tracedataset_task1.pdfa";
	private static final String PDFA_DUPLICATE_FILENAME = "tracedataset_task1_duplicate.pdfa";
	private static final String TRACEDATASET_FILENAME = "tracedataset_task1.trc";
	private static final String SRE_FILENAME = "test.sre";
	private static final String TEST_PROJECT_NAME = "Task1BaseProject";
	
	// The project explorer item for the test project
	private SWTBotTreeItem projectItem;

	@BeforeClass
	public static void beforeClass() throws IOException {
		AbstractUITest.beforeClass();
		
		importProject(TEST_PROJECT_NAME);
	}

	@After
	public void after() {
		bot.closeAllEditors();
	}
	
	@Before
	public void before() {
		waitForJobs(null, "All jobs");
		focusPackageExplorer();
		
		bot.waitUntil(treeHasItem(bot, TEST_PROJECT_NAME));

		projectItem = bot.tree()
			.getTreeItem(TEST_PROJECT_NAME);
		
		waitForTreeItems(projectItem);
	}
	
	/**
	 * Tests behaviour when performing a consistency check with a .pdfa and .trc file.
	 */
	@Test
	public void testRunConsistencyCheckWithPDFA() {
		projectItem.select(PDFA_FILENAME, TRACEDATASET_FILENAME);
		
		bot.tree().contextMenu("Run As").menu("1 Run Consistency Checker").click();

		bot.waitUntil(holdsTrue("Report editor opened", b -> {
			String title = b.activeEditor().getTitle();
			return title.startsWith("tracedataset_task1") && title.endsWith(".report");
		}));
	}
	
	/**
	 * Tests that no consistency check can be performed if the
	 * selection doesn't allow for it.
	 */
	@Test
	public void testInvalidConsistencyCheckSelection() {
		projectItem.select(SRE_FILENAME, PDFA_FILENAME);
		
		// Assert that there is no option "Run Consistency Checker [with SRE]"
		List<String> menuItems = bot.tree().contextMenu("Run As").menuItems();
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker with SRE"));
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker"));
		
		projectItem.select(SRE_FILENAME);
		
		// Assert that there is no option "Run Consistency Checker [with SRE]"
		menuItems = bot.tree().contextMenu("Run As").menuItems();
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker with SRE"));
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker"));
		
		projectItem.select(TRACEDATASET_FILENAME);
		
		// Assert that there is no option "Run Consistency Checker [with SRE]"
		menuItems = bot.tree().contextMenu("Run As").menuItems();
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker with SRE"));
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker"));
		
		projectItem.select(PDFA_FILENAME);
		
		// Assert that there is no option "Run Consistency Checker [with SRE]"
		menuItems = bot.tree().contextMenu("Run As").menuItems();
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker with SRE"));
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker"));
		
		projectItem.select(PDFA_FILENAME, PDFA_DUPLICATE_FILENAME);
		
		// Assert that there is no option "Run Consistency Checker [with SRE]"
		menuItems = bot.tree().contextMenu("Run As").menuItems();
		assertFalse("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker with SRE"));
		
		// There is the Run CC entry, but...
		assertTrue("Invalid selection contains consistency checker entry", menuItems.contains("1 Run Consistency Checker"));
		
		// ... on execution
		bot.tree().contextMenu("Run As").menu("1 Run Consistency Checker").click();
		
		// an error dialog pops up
		bot.waitUntil(shellIsActive("Invalid Selection for Consistency Checking"));
		bot.button("OK").click();
	}
	
	/**
	 * Tests performing a consistency check with an .sre and .trc file.
	 */
	@Test
	public void testRunConsistencyCheckWithSRE() {
		projectItem.select(SRE_FILENAME, TRACEDATASET_FILENAME);
		
		bot.tree().contextMenu("Run As").menu("1 Run Consistency Checker with SRE").click();
		
		bot.waitUntil(shellIsActive("Save intermediate automaton"));
		
		bot.button("No").click();

		bot.waitUntil(holdsTrue("Report editor opened", b -> {
			String title = b.activeEditor().getTitle();
			return title.startsWith("test") && title.endsWith(".report");
		}));
	}
}
