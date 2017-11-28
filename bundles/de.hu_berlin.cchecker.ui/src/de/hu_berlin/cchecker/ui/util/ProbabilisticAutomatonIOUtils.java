package de.hu_berlin.cchecker.ui.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import de.hu_berlin.cchecker.core.models.pdfa.ProbabilisticAutomata;
import de.hu_berlin.cchecker.ui.CCheckerUiPlugin;
import de.hu_berlin.cchecker.ui.util.exceptions.InvalidProbabilisticAutomatonFileException;

/**
 * Utility class for reading and writing {@link ProbabilisticAutomata} models in
 * an Eclipse workspace.
 */
public class ProbabilisticAutomatonIOUtils {

	/**
	 * Attempts to parse a {@link ProbabilisticAutomata} from a file with the given
	 * IPath and return the automaton. If parsing fails, this will throw an
	 * {@link InvalidProbabilisticAutomatonFileException}.
	 * 
	 * @param automatonPath
	 *            The path of the automaton file to load
	 * @param forceReload
	 *            Specifies whether a re-load from the file should be forced.
	 *            Otherwise a cached version of the automaton may be returned.
	 * 
	 * @throws InvalidProbabilisticAutomatonFileException
	 */
	public static ProbabilisticAutomata getProbabilisticAutomatonFromPath(IPath automatonPath, boolean forceReload)
			throws InvalidProbabilisticAutomatonFileException {
		IPath resourcePath = automatonPath;
		ResourceSet rs = CCheckerUiPlugin.getInstance().getWorkspaceResourceSet();
		try {
			Resource resource = rs.getResource(URI.createPlatformResourceURI(resourcePath.toString(), true), true);

			if (forceReload && resource.isLoaded()) {
				resource.unload();
				resource.load(null);
			}

			return (ProbabilisticAutomata) resource.getContents().get(0);
		} catch (Exception e) {
			throw new InvalidProbabilisticAutomatonFileException(
					"Cannot parse a ProbabilisticAutomaton from " + resourcePath.toFile().getName() + ".",
					resourcePath.toFile().getName());
		}

	}

	/**
	 * Creates a new resource for the given {@link ProbabilisticAutomata} at the
	 * given {@link IPath}, saves the new file and refreshes the containing
	 * {@link IContainer} in Eclipse.
	 * 
	 * This method can only be run from the main thread. If you're not on the main thread
	 * use {@link #saveAutomatonToFile(ProbabilisticAutomata, IPath)} instead.
	 * 
	 * @param automaton
	 *            The automaton to write to disk
	 * @param automataFilePath
	 *            The path to write the automaton to
	 * @param openInEditor
	 *            Specifies whether the file should be opened in an editor after
	 *            saving.
	 */
	public static void saveAutomatonToFileUI(ProbabilisticAutomata automaton, IPath automataFilePath,
			boolean openInEditor) {
		// Create a new resource, add the automaton to its contents, then save
		// the file.
		try {
			saveAutomatonToFile(automaton, automataFilePath);

		} catch (WrappedException | IOException e) {
			ErrorMessages.showProbabilisticAutomataFileCannotBeOpenedForWriting(automataFilePath.toString());
		}

		IContainer parentFolder = IOUtils.getContainerInWorkspace(ResourcesPlugin.getWorkspace().getRoot(),
				automataFilePath.removeLastSegments(1));

		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());

		// Refresh the workspace so Eclipse automatically shows the new file.
		try {
			progressDialog.run(false, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Refreshing workspace", 1);
					try {
						parentFolder.refreshLocal(1, monitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e1) {
			e1.printStackTrace();
		}

		if (openInEditor) {
			IFile automataFile = parentFolder.getFile(new Path(automataFilePath.lastSegment()));
			// Opens up the new automata file as a text file in a new tab in the editor.
			if (null != automataFile) {
				IOUtils.openFileInTextEditor(automataFile);
			}
		}
	}

	/**
	 * Actually saves the given automaton to the given path.
	 * 
	 * This method can be run from a background thread.
	 * 
	 * @param automaton
	 *            The automaton to save.
	 * @param automataFilePath
	 *            The path to save it to.
	 * 
	 * @throws IOException
	 *             if saving fails
	 */
	public static void saveAutomatonToFile(ProbabilisticAutomata automaton, IPath automataFilePath)
			throws IOException {
		ResourceSet workspaceRS = CCheckerUiPlugin.getInstance().getWorkspaceResourceSet();
		Resource automataResource = workspaceRS.createResource(URI.createURI(automataFilePath.toString(), true));

		// Add model to resource and save
		automataResource.getContents().clear();
		automataResource.getContents().add(automaton);
		automataResource.save(null);
	}

	private ProbabilisticAutomatonIOUtils() {
		// Make this class non-instantiable
	}

}
