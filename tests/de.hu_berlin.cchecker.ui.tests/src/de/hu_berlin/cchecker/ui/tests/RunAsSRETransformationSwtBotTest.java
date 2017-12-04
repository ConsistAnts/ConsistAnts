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
public class RunAsSRETransformationSwtBotTest extends AbstractUITest {
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
	 * Tests behaviour when performing a consistency check with a .pdfa and .trc
	 * file.
	 */
	@Test
	public void testRunTransformSREToPA() {
		projectItem.select(SRE_FILENAME);

		bot.tree()
			.contextMenu("Run As")
			.menu("1 Transform SRE to Probabilistic Automaton")
			.click();

		bot.waitUntil(holdsTrue("PDFA opened in editor", b -> {
			String title = b.activeEditor()
				.getTitle();
			return title.equals("test.pdfa");
		}));
	}
}
