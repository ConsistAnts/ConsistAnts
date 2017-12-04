package de.hu_berlin.cchecker.ui.tests;

import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForJobs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public abstract class AbstractUITest {
	protected static SWTWorkbenchBot bot = new SWTWorkbenchBot();

	protected static final Logger LOGGER = Logger.getLogger(AbstractUITest.class.getSimpleName());

	private static final String MAPPED_PROJECT_FILE_NAME = "_project";
	private static final String PROJECT_FILE_NAME = ".project";

	@BeforeClass
	public static void beforeClass() throws IOException {
		// close welcome view if present
		bot.views()
			.stream().filter(v -> v.getTitle().equals("Welcome"))
			.findFirst()
			.ifPresent(v -> v.close());
	}
	
	@AfterClass
	public static void afterClass() {
		focusPackageExplorer();
		// Select all
		bot.tree().select(bot.tree().getAllItems());
		
		bot.tree()
			.contextMenu("Delete").click();
		
		bot.waitUntil(shellIsActive("Delete Resources"));
		
		bot.checkBox("Delete project contents on disk (cannot be undone)").click();
		bot.button("OK").click();
		
		waitForJobs(null, "All jobs");
		
		waitForWorkspaceWindow();
	}
	
	public static void focusPackageExplorer() {
		bot.viewById("org.eclipse.jdt.ui.PackageExplorer").setFocus();
	}
	
	/**
	 * Opens the Import wizard and import the given project as a 
	 * 'Existing Projects into Workspace' import.
	 * 
	 * <p>Note that due to compatibility reasons, the '.project' files in the test projects
	 * should be renamed to '_project'. They will be renamed on the fly.</p>
	 * 
	 * @param projectName The name of the project to import. (in test-projects/ of this project)
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static void importProject(String projectName) throws IOException {
		String projectLocation = getTemporaryProjectLocation(projectName);
	
		bot.menu("File")
			.menu("Import...")
			.click();
		bot.waitUntil(shellIsActive("Import"));
	
		bot.tree()
			.getTreeItem("General")
			.expand();
	
		bot.tree()
			.getTreeItem("General")
			.getNode("Existing Projects into Workspace")
			.select();
	
		bot.button("Next >")
			.click();
	
		bot.radio("Select root directory:")
			.click();
		bot.comboBox(0)
			.setText(projectLocation);
	
		bot.comboBox(0)
			.pressShortcut(KeyStroke.getInstance(SWT.CR), KeyStroke.getInstance(SWT.LF));
	
		bot.button("Finish")
			.click();
		
		waitForWorkspaceWindow();
	}
	
	protected static void waitForTreeItems(SWTBotTreeItem item) {
		bot.waitUntil(holdsTrue("Tree item " + item.toString() + " is expanded", bot -> {
			if (item.select().isExpanded()) {
				item.collapse();
			}
			item.expand();
			
			return item.getItems().length > 0;
		}));
	}
	
	protected static ICondition treeHasItem(SWTBot bot, String item) {
		return holdsTrue("Tree has item " + item, b -> {
			for (SWTBotTreeItem swtBotTreeItem : bot.tree().getAllItems()) {
				if (swtBotTreeItem.getText().equals(item)) {
					return true;
				}
			}
			return false;
		});
	}

	protected static ICondition shellIsActive(String name) {
		return new ICondition() {
	
			@Override
			public boolean test() throws Exception {
				return bot.activeShell()
					.getText()
					.equals(name);
			}
	
			@Override
			public void init(SWTBot bot) {
				// nop
			}
	
			@Override
			public String getFailureMessage() {
				return String.format("Shell with name '%s' didn't appear. Active shell name = %s.", name, bot.activeShell().getText());
			}
		};
	}
	
	protected static ICondition editorIsActive(String editorTitle) {
		return new ICondition() {
	
			@Override
			public boolean test() throws Exception {
				return bot.activeEditor().getTitle().equals(editorTitle);
			}
	
			@Override
			public void init(SWTBot bot) {
				// nop
			}
	
			@Override
			public String getFailureMessage() {
				return String.format("Editor with title '%s' didn't appear", editorTitle);
			}
		};
	}
	
	protected static ICondition holdsTrue(String description, Predicate<SWTWorkbenchBot> predicate) {
		return new ICondition() {
			
			@Override
			public boolean test() throws Exception {
				return predicate.test(bot);
			}
			
			@Override
			public void init(SWTBot bot) {
				// nothing to do
			}
			
			@Override
			public String getFailureMessage() {
				return "The following condition wasn't met: " + description + ".";
			}
		};
	}

	protected static void waitForWorkspaceWindow() {
		bot.waitUntil(holdsTrue("Workspace window is visible", bot -> {
			return bot.activeShell().getText().endsWith("Eclipse SDK");
		}));
	}

	/**
	 * Copies the test project resources of the given project to a temporary
	 * location and returns an absolute path to that location.
	 *
	 * @throws IOException
	 */
	private static String getTemporaryProjectLocation(String projectName) throws IOException {
		Path tempFolder = Files.createTempDirectory(projectName);

		File projectDirectory = new File("test-projects/" + projectName).getAbsoluteFile();
		if (!projectDirectory.isDirectory()) {
			throw new IllegalArgumentException(
					"There is no test-project directory for project name '" + projectName + "'.");
		}

		// copy test project resources
		copyFolder(projectDirectory, tempFolder.toFile());

		return tempFolder.toString();
	}

	/**
	 * Returns a new path by replacing the last segment in the given Path p with the
	 * given filename.
	 */
	private static Path replaceFilename(Path p, String filename) {
		if (p.getNameCount() <= 1) {
			return Paths.get(filename);
		} else {
			return p.getRoot().resolve(p.subpath(0, p.getNameCount() - 1)
					.toString()).resolve(filename);
		}
	}

	/**
	 * Recursively copies the given folder to the given destination.
	 */
	private static void copyFolder(File folder, File destination) throws IOException {
		Files.walk(folder.toPath())
			.forEach(p -> {
				// build destination path
				Path sourcePath = folder.toPath()
					.relativize(p);
				Path destPath = destination.toPath()
					.resolve(sourcePath);

				// map Eclipse project configuration files
				if (destPath.getFileName()
					.toString()
					.equals(MAPPED_PROJECT_FILE_NAME)) {
					destPath = replaceFilename(destPath, PROJECT_FILE_NAME);
				}
				// skip non-file elements
				if (!p.toFile()
					.isFile()) {
					return;
				}
				try {
					Files.copy(p.toAbsolutePath(), destPath.toAbsolutePath());
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Failed to copy test project resource '" + p + "' to '" + destPath.toAbsolutePath() + "'");
				}
			});
	}
}
