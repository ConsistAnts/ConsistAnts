package de.hu_berlin.cchecker.ui.tests;

import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForJobs;

import java.io.IOException;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests that the user can transform SRE files to their equivalent Probabilistic
 * Automaton using the Run As > Transform SRE to Probabilistic Automaton action.
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class RunAsPALearningSwtBotTest extends AbstractUITest {
	private static final String TEST_PROJECT_NAME = "Task1BaseProject";
	private static final String TRACEDATASET_FILENAME = "tracedataset_task1.trc";

	@BeforeClass
	public static void beforeClass() throws IOException {
		AbstractUITest.beforeClass();

		importProject(TEST_PROJECT_NAME);
	}

	// The project explorer item for the test project
	private SWTBotTreeItem projectItem;

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
	 * Tests behaviour when learning a PA model from a selected .trc file in the project explorer.
	 */
	@Test
	public void testRunLearnModel() {
		projectItem.select(TRACEDATASET_FILENAME);

		bot.tree()
			.contextMenu("Run As")
			.menu("1 Learn Model From Traces")
			.click();

		bot.waitUntil(holdsTrue("PDFA opened in editor", b -> {
			String title = b.activeEditor()
				.getTitle();
			return title.equals("tracedataset_task1.pdfa");
		}));
	}
}
